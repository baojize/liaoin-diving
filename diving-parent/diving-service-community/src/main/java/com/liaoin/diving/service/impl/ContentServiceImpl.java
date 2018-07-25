package com.liaoin.diving.service.impl;

import com.github.pagehelper.PageHelper;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.dao.*;
import com.liaoin.diving.entity.*;
import com.liaoin.diving.entity.relationship.UserLike;
import com.liaoin.diving.mapper.ContentMapper;
import com.liaoin.diving.service.ContentService;
import com.liaoin.diving.view.BannerView;
import com.liaoin.diving.vo.NoticeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentRepository contentRepository;
    @Resource
    private DiscussionRepository discussionRepository;
    @Resource
    private TopicRepository topicRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SecondHandRepository secondHandRepository;
    @Resource
    private ImageRepository imageRepository;
    @Resource
    private ContentUserRepository contentUserRepository;
    @Resource
    private ContentMapper contentMapper;

    @Override
    public void insert(Content content) {
        content.setReading(0);
        content.setCreateTime(new Date());
        content.setIsDelete("0");
        contentRepository.save(content);
    }

    @Override
    public void update(Content content) {
        contentRepository.save(content);
    }

    @Override
    public void delete(Integer[] ids) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : ids) {
            Content content = contentRepository.findOne(id);
            if (content == null) {
                continue;
            }
            content.setIsDelete("1");
            contentList.add(content);
        }
        contentRepository.save(contentList);
    }

    @Override
    public Content findOne(Integer id) {
        Content content = contentRepository.findOne(id);
        return  content;
    }

    @Override
    public PageBean<Content> pageQuery(Pageable pageable, Content content) {
        // 1.查询条件
        Specification<Content> specification = new Specification<Content>() {
            @Override
            public Predicate toPredicate(Root<Content> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (content != null) {
                    if (content.getId() != null) {
                        list.add(cb.equal(root.get("id").as(Integer.class), content.getId()));
                    }
                    if (content.getCreateTime() != null) {
                        list.add(cb.equal(root.get("createTime").as(Date.class), content.getCreateTime()));
                    }
                    if (content.getReading() != null) {
                        list.add(cb.equal(root.get("reading").as(Integer.class), content.getReading()));
                    }
                    if ((content.getText() != null) && (!content.getText().trim().equals(""))) {
                        list.add(cb.like(root.get("text").as(String.class), "%" + content.getText() + "%"));
                    }
                    if ((content.getTheme() != null) && (!content.getTheme().trim().equals(""))) {
                        list.add(cb.like(root.get("theme").as(String.class), "%" + content.getTheme() + "%"));
                    }
                    if ((content.getType() != null) && (!content.getType().trim().equals(""))) {
                        list.add(cb.like(root.get("type").as(String.class), "%" + content.getType() + "%"));
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
        Page<Content> page = contentRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Content> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }

    // 点赞与否
    @Override
    @Transactional
    public void like(Content content, User loginUser) {
        //获取点赞列表
//        List<User> likeList = content.getLikeList();
        ContentUser contentUser1 = contentUserRepository.findByContentIdAndUserId(content.getId(),loginUser.getId());
        List<ContentUser> likeList = contentUserRepository.findByContentId(content.getId());
        if (Objects.isNull(contentUser1)){
            //添加
            contentUser1 = new ContentUser();
            contentUser1.setUserId(loginUser.getId());
            contentUser1.setContentId(content.getId());
            contentUser1.setCreateTime(new Date());
            //添加数据到数据库
            contentUserRepository.save(contentUser1);
            //获取文章用户
            User upUser = userRepository.findOne(content.getUser().getId()); // 更新最新消息
            Integer likeNum = upUser.getLikeNum();
            if (Objects.isNull(likeNum)){
                likeNum = 0;
            }
            upUser.setLikeNum(likeNum + 1);
            userRepository.save(upUser);
        }else {
            //删除
            contentUserRepository.delete(contentUser1);
            likeList.remove(contentUser1);
        }


//        if (likeList.contains(loginUser)) {
//            likeList.remove(loginUser);
//        } else {
//            likeList.add(loginUser); // 点赞
//            //获取文章用户
//            User upUser = userRepository.findOne(content.getUser().getId()); // 更新最新消息
//            Integer likeNum = upUser.getLikeNum();
//            if (Objects.isNull(likeNum)){
//                likeNum = 0;
//            }
//            upUser.setLikeNum(likeNum + 1);
//            userRepository.save(upUser);
//        }
        //更新关系表 先删 再加
//        contentRepository.save(content);
//        int n = deleteRelationship(content.getId());
//        saveRelationship(content.getId(),likeList);


    }



    /**
     * 推荐内容查询
     * @return
     */
    @Override
    public Page<Content> recommend(Integer current,Integer size) {
        // 1.查询条件
        Specification<Content> specification = new Specification<Content>() {
            @Override
            public Predicate toPredicate(Root<Content> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("isRecommend").as(String.class), "1"));//推荐
                list.add(cb.equal(root.get("type").as(String.class), "2"));//动态
                // 逻辑删除条件
                list.add(cb.notEqual(root.get("isDelete").as(String.class),"1"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<Content> page = contentRepository.findAll(specification, new PageRequest(current,size));
        return page;
    }

    /**
     * 批量推荐
     * @param ids
     */
    @Override
    public void bacthReco(Integer[] ids) {
        List<Content> contentList = new ArrayList<>();
        for (Integer id : ids) {
            Content content = contentRepository.findOne(id);
            if (content == null) {
                continue;
            }
            content.setIsRecommend("1");
            contentList.add(content);
        }
        contentRepository.save(contentList);
    }

    /**
     * 查询讨论内容(混圈子)
     * @param current
     * @param size
     * @return
     */
    @Override
    public Page<Content> ring(Integer current,Integer size) {
        // 1.查询条件
        Specification<Content> specification = new Specification<Content>() {
            @Override
            public Predicate toPredicate(Root<Content> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("type").as(String.class), "1"));//讨论
                // 逻辑删除条件
                list.add(cb.equal(root.get("isDelete").as(String.class),"0"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<Content> page = contentRepository.findAll(specification, new PageRequest(current,size,new Sort(Sort.Direction.DESC,"createTime")));
        return page;
    }

    @Override
    public List<Content> findByType(String type) {
        return contentRepository.findByTypeAndIsDelete(type, "0");
    }

    @Override
    public List<Content> findColumnConten(Integer id) {
        return contentRepository.findContentToTopicCol(id);
    }

    @Override
    public List<Content> findNewest() {
        return contentRepository.findAllByIsDeleteOrderByCreateTimeDesc("0");
    }

    @Override
    public List<Content> findFocusConten(PageHelp pageHelp, List<Integer> ids) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Content> focusConten = contentMapper.findFocusConten(ids);
        return  focusConten;
    }

    /*@Override
    public Map<String, Object> findFocusConten(List<User> focusList) {
        Map<String, Object> map = new HashMap<>();
        List<Content> contentList = new ArrayList<>();
        for(User user:focusList){
            User curren = userRepository.findOne(user.getId());
            if (Objects.isNull(curren)){
                continue;
            }
            List<Content> contents = contentRepository.findContentByUserId(curren.getId());

            map.put("contents", contents);
        }
        return map;
    }*/

    @Override
    public List<SecondHand> findSecondHand() {
        //secondHandRepository.finda
        return secondHandRepository.findSecondHand();

    }

    @Override
    public List<Content> findHotContent() {
        return contentRepository.findHotContent();
    }

    // 收藏或取消收藏
    @Override
    public void collect(User loginUser, Content content) {
        List<Content> collectList = loginUser.getCollectList();
        if (collectList.contains(content)){
            collectList.remove(content);
        }else {
            collectList.add(content);
        }
    }
    // type :1  讨论  ,2 动态
    @Override
    public Content findById(Integer id) {
        Content content = contentRepository.findById(id, "1");
        Integer reading = content.getReading();
        reading += 1;  // 更新阅读量
        content.setReading(reading);
        contentRepository.save(content);
        return  content;


    }

    @Override
    public Integer likeCount(Integer id) {
        return contentRepository.findLikeCount(id);
    }

    @Override
    public Image findImgById(Integer id) {
        return imageRepository.findOne(id);
        //return contentRepository.findImgById(id);
    }

    @Override
    public List<Integer> findNewMsg4Like(Integer userId) {
        return contentRepository.findNewMsg4Like(userId);

    }

    @Override
    public Integer findAuthor(Integer id) {
        return contentRepository.findAuthor(id);
    }

    @Override
    public List<UserLike> findLikeUserIds(Integer userId) {
        return contentRepository.findLikeUserIds(userId);
    }

    @Override
    public List<NoticeVo> findByLikeVo(Integer userId) {
        return contentRepository.findByLikeVo(userId);
    }

    @Override
    public Content findOther(Integer id) {
        Content content = contentRepository.findOther(id);
        if (Objects.isNull(content)){
            return null;
        }
        return content;
    }

    @Override
    public Integer deleteRelationship(Integer contentId) {
        if (Objects.isNull(contentId)){
            return 0;
        }
        return contentUserRepository.deleteRelationship(contentId);
    }

    @Override
    public Integer saveRelationship(Integer contentId, List<ContentUser> likeList) {

        int n = 0;
        for (ContentUser contentUser:likeList
             ) {
            contentUser.setId(null);
            contentUserRepository.save(contentUser);
            n++;
        }
        return n;
    }

    @Override
    public List<Content> findContentByThemeId(PageHelp pageHelp, Integer id) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Content> contentLis = contentMapper.findContentByThemeId(id);
        return contentLis;
    }

    @Override
    public List<Content> queryColumnConten(PageHelp pageHelp, Integer id) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Content> contentList = contentMapper.queryColumnConten(id);
        return contentList;
    }

    @Override
    public List<BannerView> findAllBanner(PageHelp pageHelp) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<BannerView> bannerViews = contentMapper.findAllBannerView();
        //List<Content> bannerList = contentMapper.findAllBanner();
        return  bannerViews;
    }

    @Override
    public Integer findUpNum(Integer id) {
        Integer upNum = contentMapper.findUpNum(id);
        if (Objects.isNull(upNum)){
            upNum = 0;
        }
        return upNum;
    }

    @Override
    public List<Content> findUserUp(PageHelp pageHelp, Integer id) {
        PageHelper.startPage(pageHelp.getStart(), pageHelp.getPageSize());
        List<Content> contentList = contentMapper.findUserUp(id);
        return contentList;
    }



}

