var globalUnionId = "onoRB5l_PxGubiPtAJ3m8ORaTSTs"


doSign = function () {
    var n = this;
    if (this.canSign) {
        if (this.isfirst) return !1;
        this.isfirst = !0;
        var e = new Date().getTime();
        r.default.signIn({
            unionId: t.getStorageSync("unionId"),
            sign: l.default.mdString(timestamp, ["unionId"]),
            timestamp: e
        }).then(function (e) {
            0 == e.code ? n.getPoints() : (t.showToast({
                title: e.msg ? e.msg : "访问异常，小哈正在努力恢复，请稍后重试",
                icon: "none"
            }), n.isfirst = !1);
        });
    } else this.showFlag = !0;
}

window.onload = function () {
    mdString("1631894912372", "unionId");
}

mdString = function (timestamp, unionId) {
    var o = timestamp, i = "", a = "";
    if ("object" !== (void 0 === unionId) || unionId instanceof Array)
        if (unionId && unionId.length > 0) {
            for (var c in unionId)
                console.log(c),
                    console.log(globalUnionId[c]),
                    a += "".concat(unionId[c], "=")
                        .concat(globalUnionId[c])
                        .concat(unionId.length > 1 ? "&" : "");
            a += "&timestamp=".concat(o);
        } else {
            var s = globalUnionId, u = 'oJlot5Emce9Zv_BXMGO0J-WCnpXE';
            a = "unionId=".concat(s, "&openid=").concat(u, "&timestamp=").concat(o);
        }
    else {
        console.log(unionId);
        for (var f = 0, l = Object.keys(globalUnionId); f < l.length; f++) {
            var p = l[f];
            console.log(p),
                a += "".concat(p, "=").concat(globalUnionId[p], "&");
        }
        a += "timestamp=".concat(o);
    }
    console.log("原来", a);
    var h = Array.from(a).sort().join("");
    return console.log("排序", h),
        console.log("排序md5", hexMD5(h)),
        i = hexMD5(hexMD5(h) + ",key=HE8@EqkD7GN4"),
        console.log("加key后加密", i),
        i;
}


hexMD5 = function (e) {
    return f(u(l(e)));
}


function n(e, t) {
    var n = (65535 & e) + (65535 & t);
    return (e >> 16) + (t >> 16) + (n >> 16) << 16 | 65535 & n;
}

function r(e, t) {
    return e << t | e >>> 32 - t;
}

function o(e, t, o, i, a, c) {
    return n(r(n(n(t, e), n(i, c)), a), o);
}

function i(e, t, n, r, i, a, c) {
    return o(t & n | ~t & r, e, t, i, a, c);
}

function a(e, t, n, r, i, a, c) {
    return o(t & r | n & ~r, e, t, i, a, c);
}

function c(e, t, n, r, i, a, c) {
    return o(t ^ n ^ r, e, t, i, a, c);
}

function s(e, t, n, r, i, a, c) {
    return o(n ^ (t | ~r), e, t, i, a, c);
}

function u(e) {
    for (var t = 1732584193, r = -271733879, o = -1732584194, u = 271733878, f = 0; f < e.length; f += 16) {
        var l = t, p = r, d = o, h = u;
        r = s(r = s(r = s(r = s(r = c(r = c(r = c(r = c(r = a(r = a(r = a(r = a(r = i(r = i(r = i(r = i(r, o = i(o, u = i(u, t = i(t, r, o, u, e[f + 0], 7, -680876936), r, o, e[f + 1], 12, -389564586), t, r, e[f + 2], 17, 606105819), u, t, e[f + 3], 22, -1044525330), o = i(o, u = i(u, t = i(t, r, o, u, e[f + 4], 7, -176418897), r, o, e[f + 5], 12, 1200080426), t, r, e[f + 6], 17, -1473231341), u, t, e[f + 7], 22, -45705983), o = i(o, u = i(u, t = i(t, r, o, u, e[f + 8], 7, 1770035416), r, o, e[f + 9], 12, -1958414417), t, r, e[f + 10], 17, -42063), u, t, e[f + 11], 22, -1990404162), o = i(o, u = i(u, t = i(t, r, o, u, e[f + 12], 7, 1804603682), r, o, e[f + 13], 12, -40341101), t, r, e[f + 14], 17, -1502002290), u, t, e[f + 15], 22, 1236535329), o = a(o, u = a(u, t = a(t, r, o, u, e[f + 1], 5, -165796510), r, o, e[f + 6], 9, -1069501632), t, r, e[f + 11], 14, 643717713), u, t, e[f + 0], 20, -373897302), o = a(o, u = a(u, t = a(t, r, o, u, e[f + 5], 5, -701558691), r, o, e[f + 10], 9, 38016083), t, r, e[f + 15], 14, -660478335), u, t, e[f + 4], 20, -405537848), o = a(o, u = a(u, t = a(t, r, o, u, e[f + 9], 5, 568446438), r, o, e[f + 14], 9, -1019803690), t, r, e[f + 3], 14, -187363961), u, t, e[f + 8], 20, 1163531501), o = a(o, u = a(u, t = a(t, r, o, u, e[f + 13], 5, -1444681467), r, o, e[f + 2], 9, -51403784), t, r, e[f + 7], 14, 1735328473), u, t, e[f + 12], 20, -1926607734), o = c(o, u = c(u, t = c(t, r, o, u, e[f + 5], 4, -378558), r, o, e[f + 8], 11, -2022574463), t, r, e[f + 11], 16, 1839030562), u, t, e[f + 14], 23, -35309556), o = c(o, u = c(u, t = c(t, r, o, u, e[f + 1], 4, -1530992060), r, o, e[f + 4], 11, 1272893353), t, r, e[f + 7], 16, -155497632), u, t, e[f + 10], 23, -1094730640), o = c(o, u = c(u, t = c(t, r, o, u, e[f + 13], 4, 681279174), r, o, e[f + 0], 11, -358537222), t, r, e[f + 3], 16, -722521979), u, t, e[f + 6], 23, 76029189), o = c(o, u = c(u, t = c(t, r, o, u, e[f + 9], 4, -640364487), r, o, e[f + 12], 11, -421815835), t, r, e[f + 15], 16, 530742520), u, t, e[f + 2], 23, -995338651), o = s(o, u = s(u, t = s(t, r, o, u, e[f + 0], 6, -198630844), r, o, e[f + 7], 10, 1126891415), t, r, e[f + 14], 15, -1416354905), u, t, e[f + 5], 21, -57434055), o = s(o, u = s(u, t = s(t, r, o, u, e[f + 12], 6, 1700485571), r, o, e[f + 3], 10, -1894986606), t, r, e[f + 10], 15, -1051523), u, t, e[f + 1], 21, -2054922799), o = s(o, u = s(u, t = s(t, r, o, u, e[f + 8], 6, 1873313359), r, o, e[f + 15], 10, -30611744), t, r, e[f + 6], 15, -1560198380), u, t, e[f + 13], 21, 1309151649), o = s(o, u = s(u, t = s(t, r, o, u, e[f + 4], 6, -145523070), r, o, e[f + 11], 10, -1120210379), t, r, e[f + 2], 15, 718787259), u, t, e[f + 9], 21, -343485551),
            t = n(t, l), r = n(r, p), o = n(o, d), u = n(u, h);
    }
    return [t, r, o, u];
}

function f(e) {
    for (var t = "0123456789abcdef", n = "", r = 0; r < 4 * e.length; r++) n += t.charAt(e[r >> 2] >> r % 4 * 8 + 4 & 15) + t.charAt(e[r >> 2] >> r % 4 * 8 & 15);
    return n;
}

function l(e) {
    for (var t = 1 + (e.length + 8 >> 6), n = new Array(16 * t), r = 0; r < 16 * t; r++) n[r] = 0;
    for (r = 0; r < e.length; r++) n[r >> 2] |= (255 & e.charCodeAt(r)) << r % 4 * 8;
    return n[r >> 2] |= 128 << r % 4 * 8, n[16 * t - 2] = 8 * e.length, n;
}