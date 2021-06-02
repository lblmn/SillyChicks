package com.bing.monkey.vaccinum;

import com.bing.monkey.vaccinum.service.VaccinumService;
import com.bing.monkey.vaccinum.service.ZmyyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ScheduleGetContent {

    @Autowired
    private VaccinumService vaccinumService;
    
    @Autowired
    private ZmyyService zmyyService;

    @Scheduled(cron = "0 0/30 * * * ?")//多长时间执行一次 根据自己的需要去改
    public void sign() {
        vaccinumService.getPageContent();
        zmyyService.getClinicList();
    }
}
