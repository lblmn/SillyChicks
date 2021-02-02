package com.bing.haagendzs.utils;

import com.bing.haagendzs.services.NewSignService;
import com.bing.haagendzs.services.SignService;
import com.bing.haagendzs.services.SysImgsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Component
public class ScheduledUtil {

    @Autowired
    private SignService signService;

    @Resource
    private NewSignService newSignService;

    @Resource
    private SysImgsService sysImgsService;


    @Scheduled(cron = "0 0 8 * * ?")//多长时间执行一次  根据自己的需要去改
    public void sign() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss:SS"));
        log.error(now + "开始签到");
        newSignService.sign();
        log.error(now + "完成签到");
    }

//    @Scheduled(cron = "0 0 */1 * * ?")//多长时间执行一次  根据自己的需要去改
//    public void addPicUrl() {
//        sysImgsService.requestAndSaveImg();
//    }
}
