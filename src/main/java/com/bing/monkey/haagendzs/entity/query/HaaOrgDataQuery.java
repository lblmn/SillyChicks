package com.bing.monkey.haagendzs.entity.query;

import com.bing.monkey.common.entity.Pager;
import com.bing.monkey.haagendzs.entity.HaaOrgData;
import lombok.Data;

@Data
public class HaaOrgDataQuery {
    private HaaOrgData haaOrgData;
    private Pager pager;
}
