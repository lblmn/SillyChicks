package com.bing.monkey.vaccinum.controller;

import com.bing.monkey.vaccinum.service.VaccinumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Intellij IDEA.
 * User:  Bing
 * Date:  2021/9/22
 */
@RestController
@RequestMapping("/vacc")
public class VaccinumController {

    @Autowired
    private VaccinumService vaccinumService;

    @GetMapping("/testSendVaccMsg")
    public void testSendVaccMsg() {
        vaccinumService.testMsg();
    }

}
