package com.bing.monkey.haagendzs.entity.query;

import com.bing.monkey.common.entity.Pager;
import com.bing.monkey.haagendzs.entity.Haatoken;
import lombok.Data;

@Data
public class HaatokenQuery {
    private Haatoken haatoken;
    private Pager pager;
}
