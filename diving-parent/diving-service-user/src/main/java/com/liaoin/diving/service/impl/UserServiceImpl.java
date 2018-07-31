package com.liaoin.diving.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.dao.SystemAliyunSmsRepository;
import com.liaoin.diving.dao.UserRepository;
import com.liaoin.diving.entity.*;
import com.liaoin.diving.mapper.NoticeMapper;
import com.liaoin.diving.mapper.UserMapper;
import com.liaoin.diving.service.GroupService;
import com.liaoin.diving.service.UserService;
import com.liaoin.diving.utils.UpdateUtils;
import com.liaoin.diving.view.NoticeView;
import com.zhangquanli.aliyun.sms.AliyunSmsUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private SystemAliyunSmsRepository systemAliyunSmsRepository;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private GroupService groupService;

    @Override
    public void insert(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        User one = userRepository.findOne(user.getId());
        //判断是否修改俱乐部
        if (!Objects.isNull(user.getGroupId())){
            //新的添加
            groupService.updateMemberNum(user.getGroupId(),1L);
            //旧的删除
            groupService.updateMemberNum(one.getGroupId(),-1L);
        }
        UpdateUtils.copyNonNullProperties(user, one);
        userRepository.save(one);
    }

    @Override
    public void delete(Integer[] ids) {
        List<User> userList = new ArrayList<>();
        for (Integer id : ids) {
            User user = userRepository.findOne(id);
            if (user == null) {
                continue;
            }
            user.setIsDelete("1");
            userList.add(user);
        }
        userRepository.save(userList);
    }

    @Override
    public User findOne(Integer id) {
        User user = userRepository.findOne(id);
        return user;
    }

    @Override
    public PageBean<User> pageQuery(Pageable pageable, User user) {
        // 1.查询条件
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (user != null) {
                    if (user.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), user.getId()));
                    }
                    if (user.getBirthday() != null) {
                        list.add(cb.equal(root.get("birthday").as(Date.class), user.getBirthday()));
                    }
                    if (user.getCreateTime() != null) {
                        list.add(cb.equal(root.get("createTime").as(Date.class), user.getCreateTime()));
                    }
                    if ((user.getHaunt() != null) && (!user.getHaunt().trim().equals(""))) {
                        list.add(cb.like(root.get("haunt").as(String.class), "%" + user.getHaunt() + "%"));
                    }
                    if ((user.getMobile() != null) && (!user.getMobile().trim().equals(""))) {
                        list.add(cb.like(root.get("mobile").as(String.class), "%" + user.getMobile() + "%"));
                    }
                    if ((user.getNickname() != null) && (!user.getNickname().trim().equals(""))) {
                        list.add(cb.like(root.get("nickname").as(String.class), "%" + user.getNickname() + "%"));
                    }
                    if ((user.getPassword() != null) && (!user.getPassword().trim().equals(""))) {
                        list.add(cb.like(root.get("password").as(String.class), "%" + user.getPassword() + "%"));
                    }
                    if (user.getPoints() != null) {
                        list.add(cb.equal(root.get("points").as(Integer.class), user.getPoints()));
                    }
                    if ((user.getSex() != null) && (!user.getSex().trim().equals(""))) {
                        list.add(cb.like(root.get("sex").as(String.class), "%" + user.getSex() + "%"));
                    }
                    if ((user.getSignature() != null) && (!user.getSignature().trim().equals(""))) {
                        list.add(cb.like(root.get("signature").as(String.class), "%" + user.getSignature() + "%"));
                    }
                    if (user.getUpdateTime() != null) {
                        list.add(cb.equal(root.get("updateTime").as(Date.class), user.getUpdateTime()));
                    }
                }
                // 逻辑删除条件
                list.add(cb.equal(root.get("isDelete").as(String.class),"0"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<User> page = userRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }

    @Override
    public User findByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    @Override
    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public void sendSms(String mobile, String code, SystemAliyunSms systemAliyunSms) throws ClientException {
        // 1.将短信验证码放入redis中
        redisTemplate.boundValueOps("validateCode" + mobile).set(code, 5, TimeUnit.MINUTES);
        // 2.发送短信，获取验证码的模板编号
        String templateCode = null;
        for (SystemSmsTemplate systemSmsTemplate : systemAliyunSms.getSystemSmsTemplateList()) {
            String templateName = systemSmsTemplate.getTemplateName();
            if ("验证码".equals(templateName)) {
                templateCode = systemSmsTemplate.getTemplateCode();
                break;
            }
        }
        String templateParam = "{\"code\":\"" + code + "\"}";
        AliyunSmsUtils.sendSms(systemAliyunSms.getAccessKeyId(), systemAliyunSms.getAccessKeySecret(), systemAliyunSms.getSignName(), mobile, templateCode, templateParam);
    }

    @Override
    public Result checkCode(String mobile, String code) {
        // 1.校验短信验证码
        String validateCode = (String) redisTemplate.boundValueOps("validateCode" + mobile).get();
        if (!(code != null && code.equals(validateCode))) {
            return new Result(300, "验证码错误", null);
        }
        return new Result(200, "验证成功", null);
    }

    @Override
    public Result register(String mobile, String nickname, String sex) throws UnsupportedEncodingException {
        // 1.从redis中删除验证码
        redisTemplate.delete("validateCode" + mobile);

        // 2.新增用户信息
        User user = new User();
        user.setMobile(mobile);
        String password = DigestUtils.md5DigestAsHex("77843969565847541789845".getBytes("UTF-8"));
        user.setPassword(password);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setSignature("这个人很懒, Ta什么都没留下");
        //默认不删除
        user.setIsDelete("0");
        //积分0
        user.setPoints(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userRepository.save(user);
        return new Result(200, "注册成功", user);
    }

    @Override
    public int login(String mobile, String code, User user) {
        // 1.校验短信验证码
        String validateCode = (String) redisTemplate.boundValueOps("validateCode" + mobile).get();
        if (!(code != null && code.equals(validateCode))) {
            return 300;
        }
        // 2.从redis中删除验证码
        redisTemplate.delete("validateCode" + mobile);
        return 200;
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = userMapper.findAllUser();
        return users;
    }

    /**
     * 找朋友
     * @return
     */
    @Override
    public Page<User> findFried(Integer current,Integer size) {
        //查询正常的用户
        return userRepository.findAllByIsDelete("0", new PageRequest(current,size));
    }

    /**
     * 收藏
     * @param loginUser
     * @param contentId
     * @return
     */
    @Override
    public List<Content> collect(User loginUser, Integer contentId) {
        List<Content> collectList = loginUser.getCollectList();
        if (collectList.contains(contentId)){

        }
        return null;
    }

    @Override
    public User findById(Integer id) {
        User user = userRepository.findById(id);
        return user;
    }

    /**
     * 最新评论
     * @param id
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<NoticeView> findNewNotice(Integer id, Integer start, Integer pageSize) {
        PageHelper.startPage(start, pageSize);
        List<NoticeView> noticeList = noticeMapper.findNewNotice(id);
        return noticeList;
    }

    @Override
    public List<User> findAgree(PageHelp pageHelp, Integer id) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<User> users = userMapper.findAgree(id);
        return users;
    }

    @Override
    public List<User> findFans(PageHelp pageHelp, Integer id) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<User> fans = userMapper.findFans(id);
        return fans;
    }

    @Override
    public Integer findFansNum(Integer id) {
        return userMapper.findFansNum(id);
    }

    /**
     * 更新积分
     * @param id 用户ID
     * @param num 积分更换值
     * @return
     */
    @Override
    public User updatePoints(Integer id, Integer num) {
        User user = userRepository.findById(id);
        int oldPoints = user.getPoints();
        user.setPoints(oldPoints+num);
        return userRepository.save(user);
    }


}

