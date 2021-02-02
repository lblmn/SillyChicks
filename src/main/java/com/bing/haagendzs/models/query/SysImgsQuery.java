package com.bing.haagendzs.models.query;

import com.bing.haagendzs.models.SysImgs;
import com.bing.haagendzs.utils.Pager;
import lombok.Data;

@Data
public class SysImgsQuery {
    private SysImgs sysImgs;
    private Pager pager;
}
