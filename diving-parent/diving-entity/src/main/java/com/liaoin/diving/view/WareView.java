package com.liaoin.diving.view;

import com.liaoin.diving.entity.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/8/2 15:16
 */
@Getter
@Setter
@ToString
public class WareView {
    Integer id;
    String name;
    BigDecimal oriprice;
    BigDecimal price;
    BigDecimal discount;
    Integer stock;//计算库存
    Image image = new Image();//获取图片
//    List<Image> imageList = new ArrayList<>();
}
