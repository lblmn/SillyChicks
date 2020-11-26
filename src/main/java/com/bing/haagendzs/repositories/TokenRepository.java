package com.bing.haagendzs.repositories;

import com.bing.haagendzs.models.HaagendazsToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface TokenRepository extends JpaRepository<HaagendazsToken, Integer>, JpaSpecificationExecutor<HaagendazsToken> {
}
