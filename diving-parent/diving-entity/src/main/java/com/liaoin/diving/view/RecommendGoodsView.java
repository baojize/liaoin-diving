package com.liaoin.diving.view;

import com.liaoin.diving.entity.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/27 17:00
 */
@Getter
@Setter
@ToString
public class RecommendGoodsView {
    Integer id;
    String name;
    BigDecimal price;
    BigDecimal money;
    BigDecimal discount;
    List<Image> imageList = new ArrayList<>();
}
