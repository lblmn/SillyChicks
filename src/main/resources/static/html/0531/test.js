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
    mdString("1653976715567", {
        openId: 'oJlot5Emce9Zv_BXMGO0J-WCnpXE',
        unionId: 'onoRB5l_PxGubiPtAJ3m8ORaTSTs'
    });
}

mdString = function (e, r) {
    var o, i = e,
        u = "";
    if ("object" != (r) || r instanceof Array)
        if (r && r.length > 0) {
            for (var c in r) {
                console.log(c), console.log(t.getStorageSync(r[c])), u += "".concat(r[c], "=").concat(t.getStorageSync(r[c])).concat(r.length > 1 ? "&" : "");
            }
            u += "&timestamp=".concat(i);
        } else {
            var s = 'onoRB5l_PxGubiPtAJ3m8ORaTSTs',
                f = 'oJlot5Emce9Zv_BXMGO0J-WCnpXE';
            u = "unionId=".concat(s, "&openId=").concat(f, "&timestamp=").concat(i);
        }
    else {
        console.log(r);
        for (var l = 0, p = Object.keys(r); l < p.length; l++) {
            var d = p[l];
            console.log(d), u += "".concat(d, "=").concat(r[d], "&");
        }
        u += "timestamp=".concat(i);
    }
    console.log("原来", u);
    var h = Array.from(u).sort().join("");
    console.log("排序", h);
    console.log("排序md5", hexMD5(h));
    o = hexMD5(hexMD5(h) + ",key=HE8@EqkD7GN4")
    console.log("加key后加密", o)
    return o;
}


hexMD5 = function (e) {
    return function (e) {
        for (var t = "0123456789abcdef", n = "", r = 0; r < 4 * e.length; r++) n += t.charAt(e[r >> 2] >> r % 4 * 8 + 4 & 15) + t.charAt(e[r >> 2] >> r % 4 * 8 & 15);
        return n;
    }(function (e) {
        for (var t = 1732584193, r = -271733879, c = -1732584194, s = 271733878, f = 0; f < e.length; f += 16) {
            var l = t,
                p = r,
                d = c,
                h = s;
            t = o(t, r, c, s, e[f + 0], 7, -680876936), s = o(s, t, r, c, e[f + 1], 12, -389564586),
                c = o(c, s, t, r, e[f + 2], 17, 606105819), r = o(r, c, s, t, e[f + 3], 22, -1044525330),
                t = o(t, r, c, s, e[f + 4], 7, -176418897), s = o(s, t, r, c, e[f + 5], 12, 1200080426),
                c = o(c, s, t, r, e[f + 6], 17, -1473231341), r = o(r, c, s, t, e[f + 7], 22, -45705983),
                t = o(t, r, c, s, e[f + 8], 7, 1770035416), s = o(s, t, r, c, e[f + 9], 12, -1958414417),
                c = o(c, s, t, r, e[f + 10], 17, -42063), r = o(r, c, s, t, e[f + 11], 22, -1990404162),
                t = o(t, r, c, s, e[f + 12], 7, 1804603682), s = o(s, t, r, c, e[f + 13], 12, -40341101),
                c = o(c, s, t, r, e[f + 14], 17, -1502002290), r = o(r, c, s, t, e[f + 15], 22, 1236535329),
                t = i(t, r, c, s, e[f + 1], 5, -165796510), s = i(s, t, r, c, e[f + 6], 9, -1069501632),
                c = i(c, s, t, r, e[f + 11], 14, 643717713), r = i(r, c, s, t, e[f + 0], 20, -373897302),
                t = i(t, r, c, s, e[f + 5], 5, -701558691), s = i(s, t, r, c, e[f + 10], 9, 38016083),
                c = i(c, s, t, r, e[f + 15], 14, -660478335), r = i(r, c, s, t, e[f + 4], 20, -405537848),
                t = i(t, r, c, s, e[f + 9], 5, 568446438), s = i(s, t, r, c, e[f + 14], 9, -1019803690),
                c = i(c, s, t, r, e[f + 3], 14, -187363961), r = i(r, c, s, t, e[f + 8], 20, 1163531501),
                t = i(t, r, c, s, e[f + 13], 5, -1444681467), s = i(s, t, r, c, e[f + 2], 9, -51403784),
                c = i(c, s, t, r, e[f + 7], 14, 1735328473), t = a(t, r = i(r, c, s, t, e[f + 12], 20, -1926607734), c, s, e[f + 5], 4, -378558),
                s = a(s, t, r, c, e[f + 8], 11, -2022574463), c = a(c, s, t, r, e[f + 11], 16, 1839030562),
                r = a(r, c, s, t, e[f + 14], 23, -35309556), t = a(t, r, c, s, e[f + 1], 4, -1530992060),
                s = a(s, t, r, c, e[f + 4], 11, 1272893353), c = a(c, s, t, r, e[f + 7], 16, -155497632),
                r = a(r, c, s, t, e[f + 10], 23, -1094730640), t = a(t, r, c, s, e[f + 13], 4, 681279174),
                s = a(s, t, r, c, e[f + 0], 11, -358537222), c = a(c, s, t, r, e[f + 3], 16, -722521979),
                r = a(r, c, s, t, e[f + 6], 23, 76029189), t = a(t, r, c, s, e[f + 9], 4, -640364487),
                s = a(s, t, r, c, e[f + 12], 11, -421815835), c = a(c, s, t, r, e[f + 15], 16, 530742520),
                t = u(t, r = a(r, c, s, t, e[f + 2], 23, -995338651), c, s, e[f + 0], 6, -198630844),
                s = u(s, t, r, c, e[f + 7], 10, 1126891415), c = u(c, s, t, r, e[f + 14], 15, -1416354905),
                r = u(r, c, s, t, e[f + 5], 21, -57434055), t = u(t, r, c, s, e[f + 12], 6, 1700485571),
                s = u(s, t, r, c, e[f + 3], 10, -1894986606), c = u(c, s, t, r, e[f + 10], 15, -1051523),
                r = u(r, c, s, t, e[f + 1], 21, -2054922799), t = u(t, r, c, s, e[f + 8], 6, 1873313359),
                s = u(s, t, r, c, e[f + 15], 10, -30611744), c = u(c, s, t, r, e[f + 6], 15, -1560198380),
                r = u(r, c, s, t, e[f + 13], 21, 1309151649), t = u(t, r, c, s, e[f + 4], 6, -145523070),
                s = u(s, t, r, c, e[f + 11], 10, -1120210379), c = u(c, s, t, r, e[f + 2], 15, 718787259),
                r = u(r, c, s, t, e[f + 9], 21, -343485551), t = n(t, l), r = n(r, p), c = n(c, d),
                s = n(s, h);
        }
        return [t, r, c, s];
    }(function (e) {
        for (var t = 1 + (e.length + 8 >> 6), n = new Array(16 * t), r = 0; r < 16 * t; r++) n[r] = 0;
        for (r = 0; r < e.length; r++) n[r >> 2] |= (255 & e.charCodeAt(r)) << r % 4 * 8;
        return n[r >> 2] |= 128 << r % 4 * 8, n[16 * t - 2] = 8 * e.length, n;
    }(e)));


}



function n(e, t) {
    var n = (65535 & e) + (65535 & t);
    return (e >> 16) + (t >> 16) + (n >> 16) << 16 | 65535 & n;
}
function r(e, t, r, o, i, a) {
    return n(function(e, t) {
        return e << t | e >>> 32 - t;
    }(n(n(t, e), n(o, a)), i), r);
}
function o(e, t, n, o, i, a, u) {
    return r(t & n | ~t & o, e, t, i, a, u);
}
function i(e, t, n, o, i, a, u) {
    return r(t & o | n & ~o, e, t, i, a, u);
}
function a(e, t, n, o, i, a, u) {
    return r(t ^ n ^ o, e, t, i, a, u);
}
function u(e, t, n, o, i, a, u) {
    return r(n ^ (t | ~o), e, t, i, a, u);
}