package com.liaoin.diving.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {

    private BigDecimal num = new BigDecimal(0);
    private BigDecimal amount = new BigDecimal(0);
    private List<CartOption> cartOptionList = new ArrayList<>();

    public BigDecimal getNum() {
        for (CartOption cartOption : cartOptionList) {
            num = num.add(cartOption.getNum());
        }
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getAmount() {
        for (CartOption cartOption : cartOptionList) {
            amount = amount.add(cartOption.getAmount());
        }
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<CartOption> getCartOptionList() {
        return cartOptionList;
    }

    public void setCartOptionList(List<CartOption> cartOptionList) {
        this.cartOptionList = cartOptionList;
    }
}
