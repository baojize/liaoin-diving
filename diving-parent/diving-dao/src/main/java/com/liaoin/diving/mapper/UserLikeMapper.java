package com.liaoin.diving.mapper;


import com.liaoin.diving.view.LikeView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLikeMapper {
    List<LikeView> findLikeViewList(@Param("id") Integer id);
}
