package com.bing.monkey.haagendzs.schedule;

import com.bing.monkey.haagendzs.service.NewSignService;
import com.bing.monkey.haagendzs.service.impl.SysImgsServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Component
public class GetNewImgSchedule {

    @Autowired
    private SysImgsServiceImpl sysImgsService;

    @Scheduled(cron = "0 0 9 * * ?")//多长时间执行一次根据自己的需要去改
    public void getNewImg() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            sysImgsService.requestAndSaveImg();
            Thread.sleep(5000);
        }
    }
}
