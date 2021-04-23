package com.bing.monkey.haagendzs.repositories;

import com.bing.monkey.haagendzs.entity.HaaOrgData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface HaaOrgDataRepo extends JpaRepository<HaaOrgData, Integer>, JpaSpecificationExecutor<HaaOrgData> {
}
