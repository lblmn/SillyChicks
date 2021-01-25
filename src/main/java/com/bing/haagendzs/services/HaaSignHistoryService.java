package com.bing.haagendzs.services;

import com.bing.haagendzs.models.query.HaaSignHistoryQuery;
import com.bing.haagendzs.models.HaaSignHistory;
import org.springframework.data.domain.Page;

/**
*
*
*/
public interface HaaSignHistoryService {

    Page<HaaSignHistory> list(HaaSignHistoryQuery haaSignHistoryQuery);

    HaaSignHistory add(HaaSignHistory haaSignHistory);

    boolean delete(String[] ids);
}
