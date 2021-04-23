package com.bing.monkey.haagendzs.service;

import com.bing.monkey.haagendzs.entity.HaaOrgData;
import com.bing.monkey.haagendzs.entity.query.HaaOrgDataQuery;
import org.springframework.data.domain.Page;

/**
 *
 */
public interface HaaOrgDataService {

    Page<HaaOrgData> list(HaaOrgDataQuery haaOrgDataQuery);

    HaaOrgData getOne(HaaOrgDataQuery haaOrgDataQuery);

    HaaOrgData add(HaaOrgData haaOrgData);

    boolean delete(String[] ids);
}
