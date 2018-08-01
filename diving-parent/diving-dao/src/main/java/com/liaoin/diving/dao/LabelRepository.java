package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LabelRepository extends JpaRepository<Label, Integer>, JpaSpecificationExecutor<Label> {

}