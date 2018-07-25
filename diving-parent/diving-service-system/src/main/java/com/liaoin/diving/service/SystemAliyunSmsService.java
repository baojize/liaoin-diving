package com.liaoin.diving.service;

import com.liaoin.diving.entity.SystemAliyunSms;

public interface SystemAliyunSmsService {
    void update(SystemAliyunSms systemAliyunSms);

    SystemAliyunSms findOne(int id);
}
