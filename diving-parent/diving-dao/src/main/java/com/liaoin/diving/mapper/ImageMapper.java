package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Image;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Allen
 * @Description:
 * @Date: Created in 2018/7/27 17:37
 */
public interface ImageMapper {
    List<Image> findImageByGoodsId(@Param("goodsId") Integer id);
}
