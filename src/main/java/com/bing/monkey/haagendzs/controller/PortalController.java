package com.bing.monkey.haagendzs.controller;

import com.alibaba.fastjson.JSONObject;
import com.bing.monkey.haagendzs.service.NewSignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/portal")
@RestController
public class PortalController {
//
//    @Resource
//    private SignService signService;

    @Resource
    private NewSignService newSignService;

//    @GetMapping("/check")
//    public JSONObject checkLogin() {
//        return signService.check();
//    }
//
//    @GetMapping("/sign")
//    public JSONObject sign() {
//        return signService.sign();
//    }

    @GetMapping("/signNew")
    public JSONObject signNew() {
        return newSignService.sign();
    }



}
