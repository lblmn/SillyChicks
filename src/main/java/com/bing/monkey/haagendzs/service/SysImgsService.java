package com.bing.monkey.haagendzs.service;

import com.bing.monkey.haagendzs.entity.SysImgs;
import com.bing.monkey.haagendzs.entity.query.SysImgsQuery;
import org.springframework.data.domain.Page;

/**
*
*
*/
public interface SysImgsService {

    Page<SysImgs> list(SysImgsQuery sysImgsQuery);

    SysImgs add(SysImgs sysImgs);

    boolean delete(String[] ids);

    void requestAndSaveImg();

    SysImgs getRandomImg();
}
