package com.bing.monkey.haagendzs.service;

import com.alibaba.fastjson.JSONObject;
import com.bing.monkey.haagendzs.entity.HaaSignHistory;
import com.bing.monkey.haagendzs.entity.SignDateRange;
import com.bing.monkey.haagendzs.entity.query.HaaSignHistoryQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
*
*
*/
public interface HaaSignHistoryService {

    Page<HaaSignHistory> list(HaaSignHistoryQuery haaSignHistoryQuery);

    HaaSignHistory add(HaaSignHistory haaSignHistory);

    boolean delete(String[] ids);

    List<String> listUser();

    List<String> listYear();

    List<String> listMonth();

    SignDateRange listTimeRange();
}
