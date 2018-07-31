package com.liaoin.diving.mapper;

import com.liaoin.diving.view.NoticeView;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface NoticeMapper {
    List<NoticeView> findNewNotice(Integer id);
}
