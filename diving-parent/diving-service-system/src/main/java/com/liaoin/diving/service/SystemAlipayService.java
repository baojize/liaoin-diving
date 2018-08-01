package com.liaoin.diving.service;

import com.liaoin.diving.entity.SystemAlipay;

public interface SystemAlipayService {
    void update(SystemAlipay systemAlipay);

    SystemAlipay findOne(int id);
}
