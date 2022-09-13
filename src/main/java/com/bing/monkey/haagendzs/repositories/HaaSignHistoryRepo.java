package com.bing.monkey.haagendzs.repositories;

import com.bing.monkey.haagendzs.entity.HaaSignHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface HaaSignHistoryRepo extends JpaRepository<HaaSignHistory, Integer>, JpaSpecificationExecutor<HaaSignHistory> {

    @Query(value = "select name from haa_sign_his group by name", nativeQuery = true)
    List<String> findAllGroupByName();


    @Query(value = "select sign_date from haa_sign_his group by sign_date", nativeQuery = true)
    List<String> findAllGroupBySignDate();


}
