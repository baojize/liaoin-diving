package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Spec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpecRepository extends JpaRepository<Spec, Integer>, JpaSpecificationExecutor<Spec> {

}