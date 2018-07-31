package com.liaoin.diving.service;

import com.aliyuncs.exceptions.ClientException;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.entity.SystemAliyunSms;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.view.NoticeView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    void insert(User user);

    void update(User user);

    void delete(Integer[] ids);

    User findOne(Integer id);

    PageBean<User> pageQuery(Pageable pageable, User user);

    User findByMobile(String mobile);

    User findByNickname(String nickname);

    void sendSms(String mobile, String code, SystemAliyunSms systemAliyunSms) throws ClientException;

    Result checkCode(String mobile, String code);

    Result register(String mobile, String nickname, String sex) throws UnsupportedEncodingException;

    int login(String mobile, String code, User user);

    // 查询所有状态正常的用户
    List<User> findAllUser();

    Page<User> findFried(Integer current,Integer size);

    List<Content> collect(User loginUser, Integer contentId);

    User findById(Integer id);

    /**
     * 查询最新评论用户
     * @return
     */
    List<NoticeView> findNewNotice(Integer id, Integer start, Integer pageSize);

    List<User> findAgree(PageHelp pageHelp, Integer id);

    List<User> findFans(PageHelp pageHelp, Integer id);

    Integer findFansNum(Integer id);

    /**
     * 更新积分
     * @param id
     * @param num
     * @return 修改后的用户信息
     */
    User updatePoints(Integer id, Integer num);

   /* // 通知点赞
    void adLikeNum(User user);
    //通知关注
    void  adFollowNum(User user);
    //通知赞同
    void adAgreeNum(User user);
    //通知评论
    void noticeNum(User user);*/

}
