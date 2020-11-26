package com.bing.haagendzs.controllers;

import com.alibaba.fastjson.JSONObject;
import com.bing.haagendzs.service.SignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/portal")
@RestController
public class PortalController {

    @Resource
    private SignService signService;

    @GetMapping("/check")
    public JSONObject checkLogin() {
        return signService.check();
    }

    @GetMapping("/sign")
    public JSONObject sign() {
        return signService.sign();
    }

}
