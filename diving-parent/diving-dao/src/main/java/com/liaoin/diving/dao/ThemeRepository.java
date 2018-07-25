package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author : huqingxi
 * @describe :
 * @date 2018/06/07
 */
public interface ThemeRepository extends JpaRepository<Theme, Integer>, JpaSpecificationExecutor<Theme> {
}
