package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
    @Query(value = "select  * from t_category where id = ?1 and is_delete = 0", nativeQuery = true)
    Category findById(Integer id);


}