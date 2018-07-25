package com.liaoin.diving.service;

import com.liaoin.diving.common.Cart;
import com.liaoin.diving.entity.Goods;
import com.liaoin.diving.entity.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(User loginUser);

    void addToCart(User loginUser, Goods goods, BigDecimal num);

    void updateCart(User loginUser, Cart cart);
}
