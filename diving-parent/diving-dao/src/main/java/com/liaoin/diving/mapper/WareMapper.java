package com.liaoin.diving.mapper;

import com.liaoin.diving.entity.Size;
import com.liaoin.diving.entity.Type;
import com.liaoin.diving.entity.Ware;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface WareMapper {
    Integer add(Ware ware);

    Integer del(Integer id);

    Integer upload(Integer id);

    Ware findById(Integer id);

    List<Ware> findAll();

    List<Type> findType(Integer id);

    List<Size> findSize(Integer id);

    Integer findStock4TypeAndSize(@Param("typeId") Integer typeId, @Param("sizeId") Integer sizeId);

    Integer findStock(@Param("id") Integer id);

}
