package com.bing.monkey.haagendzs.repositories;

import com.bing.monkey.haagendzs.entity.SysImgs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface SysImgsRepo extends JpaRepository<SysImgs, Integer>, JpaSpecificationExecutor<SysImgs> {
    List<SysImgs> findAllByAcgurl(String acgurl);
}
