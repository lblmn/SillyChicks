package com.bing.haagendzs.repositories;

import com.bing.haagendzs.models.SignHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface SignHistoryRepository extends JpaRepository<SignHistory, Integer>, JpaSpecificationExecutor<SignHistory> {
}
