package com.bing.haagendzs.repositories;

import com.bing.haagendzs.models.HaaSignHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface HaaSignHistoryRepo extends JpaRepository<HaaSignHistory, Integer>, JpaSpecificationExecutor<HaaSignHistory> {
}