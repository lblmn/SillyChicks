package com.bing.haagendzs.models.query;

import com.bing.haagendzs.models.HaaOrgData;
import com.bing.haagendzs.utils.Pager;
import lombok.Data;

@Data
public class HaaOrgDataQuery {
    private HaaOrgData haaOrgData;
    private Pager pager;
}
