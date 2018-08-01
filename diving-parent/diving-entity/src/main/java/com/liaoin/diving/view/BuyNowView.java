package com.liaoin.diving.view;

import com.liaoin.diving.entity.GoodsClassify;
import com.liaoin.diving.entity.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Allen
 * @Description: 立即抢购
 * @Date: Created in 2018/7/31 11:19
 */
@Getter
@Setter
@ToString
public class BuyNowView {
    private Integer id;//商品id
    private BigDecimal price;//原价
    private BigDecimal discount;//折扣
    private BigDecimal money;//现价
    private List<Image> imageList;//图片
    private List<GoodsClassify> goodsClassifyList;//分类名list
    //    private List<GoodsSize> goodsSizeList;//尺寸list
}
