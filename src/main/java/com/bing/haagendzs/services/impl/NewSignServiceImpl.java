package com.bing.haagendzs.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.bing.haagendzs.models.HaaOrgData;
import com.bing.haagendzs.models.HaaSignHistory;
import com.bing.haagendzs.models.Haatoken;
import com.bing.haagendzs.models.process.SignRes;
import com.bing.haagendzs.models.query.HaaOrgDataQuery;
import com.bing.haagendzs.repositories.HaatokenRepo;
import com.bing.haagendzs.services.HaaOrgDataService;
import com.bing.haagendzs.services.HaaSignHistoryService;
import com.bing.haagendzs.services.NewSignService;
import com.bing.haagendzs.utils.MessageSender;
import com.bing.haagendzs.utils.Pager;
import com.bing.haagendzs.utils.RequestUtil;
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
    private MessageSender messageSender;

    @Resource
    private HaatokenRepo haatokenRepo;

    @Override
    public JSONObject check() {
        return null;
    }

    @Override
    public JSONObject sign() {
        JSONObject _return = new JSONObject();
        List<SignRes> resList = new ArrayList<>();
        // 从数据库中取出需要签到unionId，封装到list
        HaaOrgDataQuery haaOrgDataQuery = new HaaOrgDataQuery();
        Pager pager = new Pager();
        pager.setPage(false);
        haaOrgDataQuery.setPager(pager);
        Page<HaaOrgData> list = haaOrgDataService.list(haaOrgDataQuery);
        // 从数据库中取出token，并获取新的token
        Haatoken one = haatokenRepo.getOne(1);
        String newToken = RequestUtil.getNewToken(one.getToken());
        // 使用新的token进行签到，并汇聚结果
        for (HaaOrgData haaOrgData :
                list) {
            resList.add(new SignRes(haaOrgData, RequestUtil.signIn(newToken, haaOrgData.getUnionId())));
        }
        // 推送结果
        sendMsgToEvery(resList);
        sendMsgToGod(resList);
        // 更新token
        one.setToken(newToken);
        haatokenRepo.save(one);
        // 保存签到记录
        for (SignRes sr :
                resList) {
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            HaaSignHistory haaSignHistory = new HaaSignHistory();
            haaSignHistory.setName(sr.getName());
            haaSignHistory.setResult(sr.getSignRes() == 0 ? 1 : sr.getSignRes() == -1 ? 2 : 3);
            haaSignHistory.setSignDate(date);
            haaSignHistoryService.add(haaSignHistory);
        }
        _return.put("result", resList);
        return _return;
    }

    private void sendMsgToGod(List<SignRes> resList) {
        String GodUid = "UID_XpJVE4FfL5KDTbuuIKjsbFXxGddp";
        for (SignRes signRes :
                resList) {
            int res = signRes.getSignRes();
            switch (res) {
                case 0:
                    messageSender.sendWithUidAndMsg(GodUid, "\n签到机器人为" + signRes.getName() + "在\nHaagendazs 小程序\n签到成功");
                    break;
                case -1:
                    messageSender.sendWithUidAndMsg(GodUid, "\n签到机器人为" + signRes.getName() + "在\nHaagendazs 小程序\n检测到" + signRes.getName() + "今日已经签到,今日跳过");
                    break;
                default:
                    messageSender.sendWithUidAndMsg(GodUid, "\n签到机器人为" + signRes.getName() + "在\nHaagendazs 小程序\n签到失败，失败次数过多，请手动签到");
                    break;
            }
        }
    }

    private void sendMsgToEvery(List<SignRes> resList) {
        for (SignRes signRes :
                resList) {
            int res = signRes.getSignRes();
            switch (res) {
                case 0:
                    messageSender.sendWithUidAndMsg(signRes.getUid(), "\n签到机器人为您在\nHaagendazs 小程序\n签到成功");
                    break;
                case -1:
                    messageSender.sendWithUidAndMsg(signRes.getUid(), "\n签到机器人为您在\nHaagendazs 小程序\n检测到您今日已经签到,今日跳过");
                    break;
                default:
                    messageSender.sendWithUidAndMsg(signRes.getUid(), "\n签到机器人为您在\nHaagendazs 小程序\n签到失败，失败次数过多，请手动签到");
                    break;
            }
        }
    }

}
