package com.liaoin.diving.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号生成工具，20180605001
 */
public class IdUtils {

    private static Logger logger = LoggerFactory.getLogger(IdUtils.class);

    private static String oldDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private static long count = 1L;

    public static synchronized String makeOrderNum() {
        // 如果不是同一天，需要重置计数器
        String newDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (!newDate.equals(oldDate)) {
            count = 1L;
            oldDate = newDate;
        }
        // 取系统当前时间作为订单号变量前半部分，精确到秒
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String sequence = new DecimalFormat("000000").format(count++);
        // 组装订单号
        String orderNum = time + sequence;
        logger.info("生成订单号：" + orderNum);
        return orderNum;
    }

    public static void main(String[] args) {
        // 测试多线程调用订单号生成工具
        try {
            for (int i = 0; i < 200; i++) {
                Thread t1 = new Thread(IdUtils::makeOrderNum, "at" + i);
                t1.start();

                Thread t2 = new Thread(IdUtils::makeOrderNum, "bt" + i);
                t2.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}