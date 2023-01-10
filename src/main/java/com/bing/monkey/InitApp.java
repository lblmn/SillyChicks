package com.bing.monkey;

import com.bing.monkey.haagendzs.service.NewSignService;
import com.bing.monkey.test.TestService;
import com.bing.monkey.vaccinum.service.ZmyyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
public class InitApp implements ApplicationRunner {

    @Autowired
    private ZmyyService zmyyService;

    @Autowired
    private NewSignService newSignService;

    @Autowired
    private TestService testService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        zmyyService.getClinicList();
//        newSignService.sign();
//        testService.start();
    }
}
