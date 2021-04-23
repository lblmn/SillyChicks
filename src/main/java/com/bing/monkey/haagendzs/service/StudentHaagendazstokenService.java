package com.bing.monkey.haagendzs.service;

import com.bing.monkey.haagendzs.entity.Haatoken;
import com.bing.monkey.haagendzs.entity.query.HaatokenQuery;
import org.springframework.data.domain.Page;

/**
*
*
*/
public interface StudentHaagendazstokenService {

    Page<Haatoken> list(HaatokenQuery haatokenQuery);

    Haatoken add(Haatoken haatoken);

    boolean delete(String[] ids);
}
