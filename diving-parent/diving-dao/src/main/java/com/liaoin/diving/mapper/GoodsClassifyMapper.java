package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.GoodsClassify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/31 15:16
 */
public interface GoodsClassifyMapper {
    /**
     * 通过商品id 查询商品分类list
     * @param id 商品id
     * @return
     */
    List<GoodsClassify> findGoodsClassifyByGoodsId(@Param("id") Integer id);

    /**
     * 设置一个商品的商品分类
     * @param goodsId
     * @return
     */
    Integer setOneGoodsClassify(@Param("goodsId") Integer goodsId, @Param("goodsClassify") GoodsClassify goodsClassify);

    /**
     * 增加一个商品的商品分类
     * @param goodsClassify
     * @return
     */
    Integer addOneGoodsClassify(GoodsClassify goodsClassify);
}