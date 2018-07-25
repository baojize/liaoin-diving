package com.liaoin.diving.service;

import com.liaoin.diving.entity.SystemHuanXin;

/**
 * 环信系统参数服务接口
 *
 * @author 张权立
 * @date 2018/6/19 15:29
 */
public interface SystemHuanXinService {
    void update(SystemHuanXin systemHuanXin);

    SystemHuanXin findOne(int id);
}
