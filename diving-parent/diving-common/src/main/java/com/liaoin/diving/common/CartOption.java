package com.liaoin.diving.common;

import com.liaoin.diving.entity.Goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

// 商品细节描述类  具体商品, 数量, ...
public class CartOption implements Serializable {

    private Goods goods;  // 商品信息
    private BigDecimal num; // 数量
    private BigDecimal amount;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getAmount() {
        return goods.getPrice().multiply(goods.getDiscount()).multiply(this.num);  // 价格 * 折扣 * 数量
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartOption that = (CartOption) o;
        return Objects.equals(goods, that.goods);
    }

    @Override
    public int hashCode() {

        return Objects.hash(goods);
    }
}
