package com.liaoin.diving.mapper;

/*import com.github.pagehelper.PageHelper;*/
import com.liaoin.diving.common.PageHelp;
import com.liaoin.diving.entity.Content;
import com.liaoin.diving.view.SecondHandView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface SecondHandMapper {
    SecondHandView findById(Integer id);

    List<SecondHandView> findAll();

    // 通过二级分类查询
    List<SecondHandView> findByCategory();

    // 通过大分类查询
    List<SecondHandView> findByBigCategory(@Param("id") Integer id);
}
