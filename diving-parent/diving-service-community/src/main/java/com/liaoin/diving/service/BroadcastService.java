package com.liaoin.diving.service;

import com.liaoin.diving.entity.Broadcast;

import java.util.List;

/**
 * @ClassName BroadcastService
 * @Author huqingxi
 * @Date 2018/7/5 14:06
 **/
public interface BroadcastService {

    List<Broadcast> get(Integer size);
}
