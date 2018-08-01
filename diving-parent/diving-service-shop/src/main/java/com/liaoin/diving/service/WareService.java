package com.liaoin.diving.service;

import com.liaoin.diving.entity.Size;
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Type;
import com.liaoin.diving.entity.Ware;
import com.liaoin.diving.entity.WareOrder;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface WareService {
    void add(Ware ware);

    void del(Integer id);

    void upload(Ware ware);

    Ware findById(Integer id);

    List<Ware> findAll(PageHelp pageHelp);

    /**
     * 根据商品id 查询商品所拥有的类型
     * @param id
     * @return
     */
    List<Type> findType(Integer id);

    /**
     * 根据商品id 查询商品所拥有的尺寸
     * @param id
     * @return
     */
    List<Size> findSize(Integer id);

    /**
     * 查询指定商品所有库存
     * @return
     */
    Integer findStock(Integer id);

    /**
     * 根据type和size 查询库存
     * @param typeId
     * @param sizeId
     * @return
     */
    Integer findStock4TypeAndSize(Integer typeId, Integer sizeId);

    void save(WareOrder wareOrder);

}
