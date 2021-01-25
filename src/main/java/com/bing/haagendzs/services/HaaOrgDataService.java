package com.bing.haagendzs.services;

import com.bing.haagendzs.models.HaaOrgData;
import com.bing.haagendzs.models.query.HaaOrgDataQuery;
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
