package com.bing.haagendzs.models.query;

import com.bing.haagendzs.models.Haatoken;
import com.bing.haagendzs.utils.Pager;
import lombok.Data;

@Data
public class HaatokenQuery {
    private Haatoken haatoken;
    private Pager pager;
}
