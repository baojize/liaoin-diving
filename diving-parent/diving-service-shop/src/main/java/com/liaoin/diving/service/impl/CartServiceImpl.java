package com.liaoin.diving.service.impl;

import com.liaoin.diving.common.Cart;
import com.liaoin.diving.common.CartOption;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.entity.User;
import com.liaoin.diving.service.CartService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartServiceImpl implements CartService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public Cart getCart(User loginUser) {
         Cart cart = (Cart) redisTemplate.boundHashOps("cart").get(loginUser.getMobile());
        //Cart cart = (Cart)redisTemplate.opsForHash().get("cart",loginUser.getMobile());
        return cart;
    }

    /**
     *cart 18375701380  carObj
     * @param loginUser 当前用户
     * @param goods 商品
     * @param num 数量
     */
    @Override
    public void addToCart(User loginUser, Goods goods, BigDecimal num) {
        // 封装商品数据
        CartOption cartOption = new CartOption();
        cartOption.setGoods(goods);
        cartOption.setNum(num);

        // 根据获取购物车
        Cart cart = getCart(loginUser);
        // 1.判断购物车是否为空
        if (cart == null) {
            // 1.2 如果购物车是空的,就创建一个
            cart = new Cart();
            cart.getCartOptionList().add(cartOption);
             redisTemplate.boundHashOps("cart").put(loginUser.getMobile(), cart);  // 将购物车加入redis
            //redisTemplate.opsForHash().put("cart", loginUser.getMobile(),"");  // 创建购物车
            return;
        }
        // 2.判断商品是否存在于购物车
        int index = cart.getCartOptionList().indexOf(cartOption);
        if (index > -1) {
            //2.2 存在
            CartOption option = cart.getCartOptionList().get(index);
            option.setNum(option.getNum().add(num));
        } else {
            cart.getCartOptionList().add(cartOption);
        }
         redisTemplate.boundHashOps("cart").put(loginUser.getMobile(), cart);
        //redisTemplate.opsForHash().put("cart", loginUser.getMobile(), cart);
    }

    @Override
    public void updateCart(User loginUser, Cart cart) {

    }
}
