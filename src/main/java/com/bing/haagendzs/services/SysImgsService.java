package com.bing.haagendzs.services;

import com.bing.haagendzs.models.SysImgs;
import com.bing.haagendzs.models.query.SysImgsQuery;
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
