package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.Cart;
import com.liaoin.diving.common.CartOption;
import com.liaoin.diving.common.PageBean;
import com.liaoin.diving.common.Result;
import com.liaoin.diving.dao.GoodsRepository;
import com.liaoin.diving.dao.OrderRepository;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.entity.Order;
import com.liaoin.diving.entity.OrderOption;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.OrderService;
import com.liaoin.diving.utils.IdUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private GoodsRepository goodsRepository;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void insert(Order order) {
        //计算价格

        //设置订单号
        order.setOrderId((new Date()).getTime()+"");
        orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(String[] orderIds) {
        List<Order> orderList = new ArrayList<>();
        for (String orderId : orderIds) {
            Order order = orderRepository.findOne(orderId);
            if (order == null) {
                continue;
            }
            order.setIsDelete("1");
            orderList.add(order);
        }
        orderRepository.save(orderList);
    }

    @Override
    public Order findOne(String orderId) {
        return orderRepository.findOne(orderId);
    }

    @Override
    public PageBean<Order> pageQuery(Pageable pageable, Order order) {
        // 1.查询条件
        Specification<Order> specification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 普通条件
                List<Predicate> list = new ArrayList<>();
                if (order != null) {
                    if ((order.getOrderId() != null) && (!order.getOrderId().trim().equals(""))) {
                        list.add(cb.like(root.get("orderId").as(String.class), "%" + order.getOrderId() + "%"));
                    }
                    if (order.getAmount() != null) {
                        list.add(cb.equal(root.get("amount").as(BigDecimal.class), order.getAmount()));
                    }
                    if (order.getCloseTime() != null) {
                        list.add(cb.equal(root.get("closeTime").as(java.util.Date.class), order.getCloseTime()));
                    }
                    if (order.getCreateTime() != null) {
                        list.add(cb.equal(root.get("createTime").as(java.util.Date.class), order.getCreateTime()));
                    }
                    if (order.getDeliveryTime() != null) {
                        list.add(cb.equal(root.get("deliveryTime").as(java.util.Date.class), order.getDeliveryTime()));
                    }
                    if (order.getEndTime() != null) {
                        list.add(cb.equal(root.get("endTime").as(java.util.Date.class), order.getEndTime()));
                    }
                    if ((order.getMessage() != null) && (!order.getMessage().trim().equals(""))) {
                        list.add(cb.like(root.get("message").as(String.class), "%" + order.getMessage() + "%"));
                    }
                    if (order.getMoney() != null) {
                        list.add(cb.equal(root.get("money").as(BigDecimal.class), order.getMoney()));
                    }
                    if (order.getPayTime() != null) {
                        list.add(cb.equal(root.get("payTime").as(java.util.Date.class), order.getPayTime()));
                    }
                    if ((order.getPayWay() != null) && (!order.getPayWay().trim().equals(""))) {
                        list.add(cb.like(root.get("payWay").as(String.class), "%" + order.getPayWay() + "%"));
                    }
                    if (order.getPostage() != null) {
                        list.add(cb.equal(root.get("postage").as(BigDecimal.class), order.getPostage()));
                    }
                    if ((order.getStatus() != null) && (!order.getStatus().trim().equals(""))) {
                        list.add(cb.like(root.get("status").as(String.class), "%" + order.getStatus() + "%"));
                    }
                    if (order.getUpdateTime() != null) {
                        list.add(cb.equal(root.get("updateTime").as(java.util.Date.class), order.getUpdateTime()));
                    }
                    if (order.getNum() != null) {
                        list.add(cb.equal(root.get("num").as(BigDecimal.class), order.getNum()));
                    }

                }
                // 逻辑删除条件
                list.add(cb.notEqual(root.get("isDelete").as(String.class),"1"));
                // 合并查询条件
                Predicate[] predicates = new Predicate[list.size()];
                query.where(cb.and(list.toArray(predicates)));
                return query.getRestriction();
            }
        };
        // 2.获取数据
        Page<Order> page = orderRepository.findAll(specification, pageable);
        if (page.getTotalElements() == 0) {
            return null;
        }
        // 3.封装数据
        PageBean<Order> pageBean = new PageBean<>();
        pageBean.setTotal(page.getTotalElements());
        pageBean.setRows(page.getContent());
        return pageBean;
    }

    @Override
    public Result submit(User loginUser, Order order) {
        // 1.更新redis中的购物车
        Cart cart = (Cart) redisTemplate.boundHashOps("cart").get(loginUser.getMobile());
        if (cart != null) {
            for (OrderOption orderOption : order.getOrderOptionList()) {
                List<CartOption> cartOptionList = cart.getCartOptionList();
                for (int i = cartOptionList.size() - 1; i >= 0; i--) {
                    CartOption cartOption = cartOptionList.get(i);
                    if (orderOption.getGoods().equals(cartOption.getGoods())) {
                        cartOptionList.remove(i);
                    }
                }
            }
            if (cart.getCartOptionList().size() == 0) {
                redisTemplate.boundHashOps("cart").delete(loginUser.getMobile());
            } else {
                redisTemplate.boundHashOps("cart").put(loginUser.getMobile(), cart);
            }
        }
        // 2.检验订单信息
        double orderNum = 0D;
        double orderMoney = 0D;
        for (OrderOption orderOption : order.getOrderOptionList()) {
            // 2.1 校验商品小计
            Goods goods = goodsRepository.findOne(orderOption.getGoods().getId());
            orderOption.setGoods(goods);
            double money = goods.getPrice().doubleValue() * goods.getDiscount().doubleValue() * orderOption.getNum().doubleValue();
            if (orderOption.getMoney().doubleValue() != money) {
                return new Result(300, "商品" + goods.getId() + "小计异常", null);
            }
            orderNum += orderOption.getNum().doubleValue();
            orderMoney += money;
        }
        // 2.2 校验订单金额
        if (order.getMoney().doubleValue() != orderMoney) {
            return new Result(300, "订单金额异常", null);
        }
        // 2.3 校验商品数量
        if (order.getNum().doubleValue() != orderNum) {
            return new Result(300, "商品数量异常", null);
        }
        // 2.4 校验订单总金额
        if (order.getAmount().doubleValue() != (orderMoney + order.getPostage().doubleValue())) {
            return new Result(300, "订单总金额异常", null);
        }
        // 3.保存订单信息
        order.setOrderId(IdUtils.makeOrderNum());
        order.setStatus("1");
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setUser(loginUser);
        for (OrderOption orderOption : order.getOrderOptionList()) {
            orderOption.setOrder(order);
        }
        orderRepository.save(order);
        return new Result(200, "更新成功", null);
    }
}
