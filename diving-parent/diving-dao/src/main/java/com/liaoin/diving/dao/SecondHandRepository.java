package com.liaoin.diving.dao;

import com.liaoin.diving.entity.SecondHand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SecondHandRepository extends JpaRepository<SecondHand, Integer>, JpaSpecificationExecutor<SecondHand> {
    @Query(value = "select * from t_content  c LEFT JOIN t_second_hand  s on c.second_hand_id = s.id where c.is_delete = 0  order by c.create_time desc", nativeQuery = true)
    List<SecondHand> findSecondHand();
}