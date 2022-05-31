package com.bing.monkey.haagendzs.util;

import com.alibaba.fastjson.JSONObject;
import com.bing.monkey.haagendzs.entity.HaaOrgData;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

@Log4j2
public class RequestUtil {

    public static String getNewToken(String token) {
        String newToken = null;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"partner\": \"apitest\",\r\n    \"secret\": \"Ou0HT@0W6e\",\r\n    \"openid\": \"gh_68065de13ad5\",\r\n    \"app-id\": \"wx3656c2a2353eb377\"\r\n}");
        Request request = new Request.Builder()
                .url("https://haagendazs.smarket.com.cn/v1/api/token")
                .method("POST", body)
                .addHeader("Authorization", " Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String requestResult = Objects.requireNonNull(response.body()).string();
            log.error(requestResult);
            newToken = JSONObject.parseObject(requestResult).getString("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newToken;
    }

    public static Integer signIn(String token, HaaOrgData haaOrgData) {
        int retry = 0;
        int code = doSignIn(token, haaOrgData).getInteger("code");
        while (code != 0 && code != -1) {
            if (retry >= 3) {
                break;
            }
            token = RequestUtil.getNewToken(token);
            code = RequestUtil.doSignIn(token, haaOrgData).getInteger("code");
            retry++;
        }
        return code;
    }

    /**
     * 2021-09-21新的签到加密逻辑
     * <p>
     * <p>
     * doSign: function() {
     * var n = this;
     * if (this.canSign) {
     * if (this.isfirst) return !1;
     * this.isfirst = !0;
     * var e = new Date().getTime();
     * r.default.signIn({
     * unionId: t.getStorageSync("unionId"),
     * sign: l.default.mdString(e, [ "unionId" ]),
     * timestamp: e
     * }).then(function(e) {
     * 0 == e.code ? n.getPoints() : (t.showToast({
     * title: e.msg ? e.msg : "访问异常，小哈正在努力恢复，请稍后重试",
     * icon: "none"
     * }), n.isfirst = !1);
     * });
     * } else this.showFlag = !0;
     * },
     * <p>
     * mdString: function(e, r) {
     * var o = e, i = "", a = "";
     * if ("object" != (void 0 === r ? "undefined" : t(r)) || r instanceof Array) if (r && r.length > 0) {
     * for (var c in r) console.log(c), console.log(n.getStorageSync(r[c])), a += "".concat(r[c], "=").concat(n.getStorageSync(r[c])).concat(r.length > 1 ? "&" : "");
     * a += "&timestamp=".concat(o);
     * } else {
     * var s = n.getStorageSync("unionId"), u = n.getStorageSync("openId");
     * a = "unionId=".concat(s, "&openid=").concat(u, "&timestamp=").concat(o);
     * } else {
     * console.log(r);
     * for (var f = 0, l = Object.keys(r); f < l.length; f++) {
     * var p = l[f];
     * console.log(p), a += "".concat(p, "=").concat(r[p], "&");
     * }
     * a += "timestamp=".concat(o);
     * }
     * console.log("原来", a);
     * var h = Array.from(a).sort().join("");
     * return console.log("排序", h), console.log("排序md5", d.default.hexMD5(h)), i = d.default.hexMD5(d.default.hexMD5(h) + ",key=HE8@EqkD7GN4"),
     * console.log("加key后加密", i), i;
     * }
     *
     * @param token
     * @param haaOrgData
     * @return
     */
    private static JSONObject doSignIn(String token, HaaOrgData haaOrgData) {
        JSONObject _return = new JSONObject();
        long currentTimeMillis = System.currentTimeMillis();
        String encryptResult = getEncryptResult(String.valueOf(currentTimeMillis), haaOrgData.getUnionId(), haaOrgData.getOpenId());
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n    " +
                        "\"unionId\": \"" + haaOrgData.getUnionId() + "\",\r\n    " +
                        "\"openId\": \"" + haaOrgData.getOpenId() + "\",\r\n    " +
                        "\"sign\": \"" + encryptResult + "\",\r\n    " +
                        "\"socialHubid\": \"" + haaOrgData.getSocialHubId() + "\",\r\n    " +
                        "\"mobile\": \"" + haaOrgData.getMobile() + "\",\r\n    " +
                        "\"timestamp\": " + currentTimeMillis + "\r\n" +
                        "}");
        Request request = new Request.Builder()
                .url("https://haagendazs.smarket.com.cn/v1/api/wxapp/daily/signIn")
                .method("POST", body)
                .addHeader("Authorization", " Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String requestResult = Objects.requireNonNull(response.body()).string();
            log.error(requestResult);
            if (!StringUtils.isEmpty(requestResult)) {
                _return = JSONObject.parseObject(requestResult);
            } else {
                _return = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _return;
    }

    private static String getEncryptResult(String timestamp, String unionId, String openId) {
        String encryptionRes = "";
        StringBuilder temp = new StringBuilder();
        StringBuilder needEncryptedStr = new StringBuilder();
        temp.append("unionId")
                .append("=")
                .append(unionId)
                .append("&openId")
                .append("=")
                .append(openId)
                .append("&timestamp=")
                .append(timestamp);
        char[] encryptBeforeChars = temp.toString().toCharArray();
        Arrays.sort(encryptBeforeChars);
        for (int i = 0; i < encryptBeforeChars.length; i++) {
            needEncryptedStr.append(encryptBeforeChars[i]);
        }
        System.out.println("needEncryptedStr = " + needEncryptedStr);
        encryptionRes = DigestUtils.md5Hex(needEncryptedStr.toString()).toLowerCase(Locale.ROOT);
        encryptionRes = DigestUtils.md5Hex(encryptionRes + ",key=HE8@EqkD7GN4").toLowerCase(Locale.ROOT);
        return encryptionRes;
    }
}
