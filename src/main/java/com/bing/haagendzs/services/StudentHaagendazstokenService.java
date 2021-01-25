package com.bing.haagendzs.services;

import com.bing.haagendzs.models.Haatoken;
import com.bing.haagendzs.models.query.HaatokenQuery;
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
