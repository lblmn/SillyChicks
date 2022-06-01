package com.bing.monkey.haagendzs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bing.monkey.common.constant.Localconstant;
import com.bing.monkey.common.entity.Pager;
import com.bing.monkey.haagendzs.constant.HaagendzsConstant;
import com.bing.monkey.haagendzs.entity.HaaOrgData;
import com.bing.monkey.haagendzs.entity.HaaSignHistory;
import com.bing.monkey.haagendzs.entity.Haatoken;
import com.bing.monkey.haagendzs.entity.query.HaaOrgDataQuery;
import com.bing.monkey.haagendzs.repositories.HaatokenRepo;
import com.bing.monkey.haagendzs.service.HaaOrgDataService;
import com.bing.monkey.haagendzs.service.HaaSignHistoryService;
import com.bing.monkey.haagendzs.service.NewSignService;
import com.bing.monkey.haagendzs.util.RequestUtil;
import com.bing.monkey.wxpusher.service.MessageSenderService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewSignServiceImpl implements NewSignService {

    @Resource
    private HaaOrgDataService haaOrgDataService;

    @Resource
    private HaaSignHistoryService haaSignHistoryService;

    @Resource
    private MessageSenderService messageSenderService;

    @Resource
    private HaatokenRepo haatokenRepo;

    @Override
    public JSONObject check() {
        return null;
    }

    @Override
    public JSONObject sign() {
        JSONObject _return = new JSONObject();
        List<HaaSignHistory> haaSignHistories = new ArrayList<>();
        // 从数据库中取出需要签到unionId，封装到list
        HaaOrgDataQuery haaOrgDataQuery = new HaaOrgDataQuery();
        Pager pager = new Pager();
        pager.setPage(false);
        haaOrgDataQuery.setPager(pager);
        Page<HaaOrgData> list = haaOrgDataService.list(haaOrgDataQuery);
        // 从数据库中取出token，并获取新的token
        Haatoken one = haatokenRepo.getOne(1);
        // 更新token
        String newToken = RequestUtil.getNewToken(one.getToken());
        list.stream().forEach(haaOrgData -> {
            // 保存签到记录
            // 使用新的token进行签到，并汇聚结果
            JSONObject signInResult = RequestUtil.signIn(newToken, haaOrgData);
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            HaaSignHistory haaSignHistory = new HaaSignHistory();
            haaSignHistory.setName(haaOrgData.getName());
            haaSignHistory.setResult(signInResult.getInteger("code") == 0 ? 1 : 3);
            haaSignHistory.setSignDate(date);
            haaSignHistory.setMemo(signInResult.getString("msg"));
            haaSignHistory.setUid(haaOrgData.getUid());
            haaSignHistories.add(haaSignHistory);
            haaSignHistoryService.add(haaSignHistory);
        });
        one.setToken(newToken);
        haatokenRepo.save(one);
        // 推送结果
        sendMsgToEvery(haaSignHistories);
        sendMsgToGod(haaSignHistories);
        _return.put("result", haaSignHistories);
        return _return;
    }

    /**
     * 给开发者通知
     *
     * @param haaSignHistories
     */
    private void sendMsgToGod(List<HaaSignHistory> haaSignHistories) {

        for (HaaSignHistory signHistory :
                haaSignHistories) {
            int res = signHistory.getResult();
            switch (res) {
                case 0:
                    messageSenderService.notify(HaagendzsConstant.SIGN_IN_TOKEN, Localconstant.GODUID, "\n签到机器人为" + signHistory.getName() + "在\nHaagendazs 小程序\n签到成功", null);
                    break;
                case -1:
                    messageSenderService.notify(HaagendzsConstant.SIGN_IN_TOKEN, Localconstant.GODUID, "\n签到机器人为" + signHistory.getName() + "在\nHaagendazs 小程序\n检测到" + signHistory.getName() + "今日已经签到,今日跳过", null);
                    break;
                default:
                    messageSenderService.notify(HaagendzsConstant.SIGN_IN_TOKEN, Localconstant.GODUID, "\n签到机器人为" + signHistory.getName() + "在\nHaagendazs 小程序\n签到失败，失败次数过多，请手动签到", null);
                    break;
            }
        }
    }

    /**
     * 给订阅者进行通知
     *
     * @param haaSignHistories
     */
    private void sendMsgToEvery(List<HaaSignHistory> haaSignHistories) {
        for (HaaSignHistory signHistory :
                haaSignHistories) {
            int res = signHistory.getResult();
            switch (res) {
                case 0:
                    messageSenderService.notify(HaagendzsConstant.SIGN_IN_TOKEN, signHistory.getUid(), "\n签到成功\n签到机器人为您在\nHaagendazs 小程序\n签到成功", null);
                    break;
                case -1:
                    messageSenderService.notify(HaagendzsConstant.SIGN_IN_TOKEN, signHistory.getUid(), "\n签到成功\n签到机器人为您在\nHaagendazs 小程序\n检测到您今日已经签到,今日跳过", null);
                    break;
                default:
                    messageSenderService.notify(HaagendzsConstant.SIGN_IN_TOKEN, signHistory.getUid(), "\n签到成功\n签到机器人为您在\nHaagendazs 小程序\n签到失败，失败次数过多，请手动签到", null);
                    break;
            }
        }
    }

}
