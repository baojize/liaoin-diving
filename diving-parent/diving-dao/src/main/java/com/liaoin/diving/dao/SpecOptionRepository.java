package com.liaoin.diving.dao;

import com.liaoin.diving.entity.Spec;
import com.liaoin.diving.entity.SpecOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpecOptionRepository extends JpaRepository<SpecOption, Integer>, JpaSpecificationExecutor<SpecOption> {
    void deleteBySpec(Spec spec);
}