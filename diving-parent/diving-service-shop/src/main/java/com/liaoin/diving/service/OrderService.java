package com.liaoin.diving.service;

import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.entity.Order;
import com.liaoin.diving.entity.User;
import org.springframework.data.domain.Pageable;

/**
 * @author 张权立
 * @date 2018/06/07
 */
public interface OrderService {
    /**
     * 新增
     *
     * @param order order
     */
    void insert(Order order);

    /**
     * 修改
     *
     * @param order order
     */
    void update(Order order);

    /**
     * 根据订单编号数组，批量删除
     *
     * @param orderIds orderIds
     */
    void delete(String[] orderIds);

    /**
     * 根据订单编号查询
     *
     * @param orderId orderId
     * @return Order
     */
    Order findOne(String orderId);

    /**
     * 条件分页查询
     *
     * @param pageable pageable
     * @param order    order
     * @return PageBean<Order>
     */
    PageBean<Order> pageQuery(Pageable pageable, Order order);

    /**
     * 提交订单
     *
     * @param loginUser loginUser
     * @param order     order
     * @return Result
     */
    Result submit(User loginUser, Order order);
}
