package com.bing.monkey.haagendzs.entity.query;

import com.bing.monkey.common.entity.Pager;
import com.bing.monkey.haagendzs.entity.HaaSignHistory;
import lombok.Data;

@Data
public class HaaSignHistoryQuery {
    private HaaSignHistory haaSignHistory;
    private Pager pager;
}
