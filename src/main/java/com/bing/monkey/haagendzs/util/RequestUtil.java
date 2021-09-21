package com.bing.monkey.haagendzs.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.util.StringUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

@Log4j2
public class RequestUtil {

    public static String getNewToken(String token) {
        String newToken = null;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"partner\": \"apitest\",\r\n    \"secret\": \"111111\",\r\n    \"openid\": \"gh_68065de13ad5\",\r\n    \"app-id\": \"wx3656c2a2353eb377\"\r\n}");
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

    public static Integer signIn(String token, String unique) {
        int retry = 0;
        int code = doSignIn(token, unique).getInteger("code");
        while (code != 0 && code != -1) {
            if (retry >= 3) {
                break;
            }
            token = RequestUtil.getNewToken(token);
            code = RequestUtil.doSignIn(token, unique).getInteger("code");
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
     * @param unique
     * @return
     */
    private static JSONObject doSignIn(String token, String unique) {
        JSONObject _return = new JSONObject();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://haagendazs.smarket.com.cn/v1/api/wxapp/daily/signIn?unionId=" + unique)
                .method("GET", null)
                .addHeader("Authorization", " Bearer " + token)
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

    private String getMdRes(String timestamp, String unionId) {
        StringBuffer temp = new StringBuffer();
        String encryptionRes = "";
        if (unionId != null && unionId.length() > 0) {
            char[] chars = unionId.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                temp.append(i)
                        .append("=")
                        .append(chars[i])
                        .append("&");
            }
            temp.append("timestamp=")
                    .append(timestamp);
            Arrays.sort(temp.toString().toCharArray());
            System.out.println("temp.toString() = " + temp);
            encryptionRes = DigestUtils.md5Hex(temp.toString()).toLowerCase(Locale.ROOT);
        }
        return encryptionRes;
    }

    @Test
    public void testSomeThings() {
        char[] str = "onoRB5l_PxGubiPtAJ3m8ORaTSTs".toCharArray();
        Arrays.sort(str);
        System.out.println("str = " + Arrays.toString(str));
//        System.out.println(getMdRes("1631894912372", "onoRB5l_PxGubiPtAJ3m8ORaTSTs"));
    }
}
