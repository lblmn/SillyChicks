package com.bing.monkey.haagendzs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bing.monkey.haagendzs.entity.HaagendazsToken;
import com.bing.monkey.haagendzs.entity.SignHistory;
import com.bing.monkey.haagendzs.repositories.SignHistoryRepository;
import com.bing.monkey.haagendzs.repositories.TokenRepository;
import com.bing.monkey.haagendzs.service.SignService;
import com.bing.monkey.haagendzs.util.MessageSenderUtil;
import com.bing.monkey.haagendzs.util.RequestUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class SignServiceImpl implements SignService {

    @Resource
    private TokenRepository tokenRepository;

    @Resource
    private MessageSenderUtil messageSenderUtil;

    @Resource
    private SignHistoryRepository signHistoryRepository;

    @Override
    public JSONObject check() {
        JSONObject _return = new JSONObject();
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Specification<SignHistory> surveyQuestionTypeSpecification = (Specification<SignHistory>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate;
            predicate = criteriaBuilder.equal(root.get("signDate").as(String.class), today);
            return predicate;
        };
        _return.put("data", signHistoryRepository.findAll(surveyQuestionTypeSpecification));
        return _return;
    }

    @Override
    public JSONObject sign() {
        String baby_unionId = "onoRB5tgD5dvz88weMSu4saKwFMU";
        String baby_uid = "UID_ZtRVPrJYQ9JEgxyMZX1RoP4zCV6z";
        String my_unionId = "onoRB5l_PxGubiPtAJ3m8ORaTSTs";
        String my_uid = "UID_XpJVE4FfL5KDTbuuIKjsbFXxGddp";
        JSONObject _return = new JSONObject();
        // 获取token
        HaagendazsToken one = tokenRepository.getOne(1);
        String token = one.getToken();
        // 获取新的token
        String newToken = RequestUtil.getNewToken(token);
        // 用新token签到
        int babySignRes = RequestUtil.signIn(newToken, baby_unionId);
        int mySignRes = RequestUtil.signIn(newToken, my_unionId);
        // 发送通知
        sendMsg(babySignRes, baby_uid);
        sendMsg(mySignRes, my_uid);
        // 更新token
        one.setToken(token);
        tokenRepository.save(one);
        // 记录记录
        saveLog(babySignRes, 2);
        saveLog(mySignRes, 1);

        // 返回结果
        _return.put("data", "DONE");
        return _return;
    }

    private void saveLog(int SignRes, int type) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        SignHistory signHistory = new SignHistory();
        if (SignRes == 0) {
            signHistory.setResult(1);
        } else if (SignRes == -1) {
            signHistory.setResult(2);
        } else {
            signHistory.setResult(3);
        }
        signHistory.setType(type);
        signHistory.setSignDate(today);
        signHistoryRepository.save(signHistory);
    }

    private void sendMsg(Integer code, String uid) {
        String baby_uid = "UID_ZtRVPrJYQ9JEgxyMZX1RoP4zCV6z";
        String my_uid = "UID_XpJVE4FfL5KDTbuuIKjsbFXxGddp";
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss:SS"));
        List<String> baby_uids = new ArrayList<>();
        baby_uids.add(baby_uid);
        List<String> my_uids = new ArrayList<>();
        my_uids.add(my_uid);
        if (code == 0) {
            if (uid.equalsIgnoreCase("UID_ZtRVPrJYQ9JEgxyMZX1RoP4zCV6z")) {
                messageSenderUtil.send(baby_uids, now + "\n签到机器人为您在\nHaagendazs 小程序\n签到成功");
                messageSenderUtil.send(my_uids, now + "\n签到机器人为MM在\nHaagendazs 小程序\n签到成功");
            } else {
                messageSenderUtil.send(my_uids, now + "\n签到机器人为GOD在\nHaagendazs 小程序\n签到成功");
            }
        } else if (code == -1) {
            if (uid.equalsIgnoreCase("UID_ZtRVPrJYQ9JEgxyMZX1RoP4zCV6z")) {
                messageSenderUtil.send(baby_uids, now + "\n签到机器人为您在\nHaagendazs 小程序\n检测到您今日已经签到,今日跳过");
                messageSenderUtil.send(my_uids, now + "\n签到机器人为MM在\nHaagendazs 小程序\n检测到MM今日已经签到,今日跳过");
            } else {
                messageSenderUtil.send(my_uids, now + "\n签到机器人为GOD在\nHaagendazs 小程序\n检测到GOD今日已经签到,今日跳过");
            }
        } else {
            if (uid.equalsIgnoreCase("UID_ZtRVPrJYQ9JEgxyMZX1RoP4zCV6z")) {
                messageSenderUtil.send(baby_uids, now + "\n签到机器人为您在\nHaagendazs 小程序\n签到失败，失败次数过多，请手动签到");
                messageSenderUtil.send(my_uids, now + "\n签到机器人为MM在\nHaagendazs 小程序\n签到失败，失败次数过多，请提醒MM手动签到");
            } else {
                messageSenderUtil.send(my_uids, now + "\n签到机器人为GOD在\nHaagendazs 小程序\n签到失败，失败次数过多，请手动签到");
            }
        }
    }
}
