package com.bing.haagendzs.models.query;

import com.bing.haagendzs.models.HaaSignHistory;
import com.bing.haagendzs.utils.Pager;
import lombok.Data;

@Data
public class HaaSignHistoryQuery {
    private HaaSignHistory haaSignHistory;
    private Pager pager;
}
