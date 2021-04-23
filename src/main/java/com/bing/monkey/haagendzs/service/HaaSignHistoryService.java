package com.bing.monkey.haagendzs.service;

import com.bing.monkey.haagendzs.entity.HaaSignHistory;
import com.bing.monkey.haagendzs.entity.query.HaaSignHistoryQuery;
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
