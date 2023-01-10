[[4], {
    125: function (e, r, t) {
        "use strict";
        t.d(r, "b", (function () {
            return p
        })), t.d(r, "a", (function () {
            return d
        }));
        var n = t(8), a = t(14), u = t(16), c = t.n(u), s = t(21), o = t(81), i = c.a.agentId, b = function (e) {
            return {err_code: 801, msg: e}
        }, f = function () {
            var e = Object(a.a)(Object(n.a)().mark((function e() {
                var r, t;
                return Object(n.a)().wrap((function (e) {
                    for (; ;) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, Object(o.b)();
                        case 2:
                            if (r = e.sent, t = wx.getStorageSync("QWToken"), (!r.failFnErrCode || "failFn" != r.failFnErrCode) && t) {
                                e.next = 8;
                                break
                            }
                            return e.next = 7, p();
                        case 7:
                            return e.abrupt("return", e.sent);
                        case 8:
                            return e.abrupt("return", !0);
                        case 9:
                        case"end":
                            return e.stop()
                    }
                }), e)
            })));
            return function () {
                return e.apply(this, arguments)
            }
        }(), p = function () {
            var e = Object(a.a)(Object(n.a)().mark((function e() {
                var r, t, a, u, c, b, f, p;
                return Object(n.a)().wrap((function (e) {
                    for (; ;) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, Object(o.e)();
                        case 2:
                            if ((r = e.sent) && r.code) {
                                e.next = 5;
                                break
                            }
                            return e.abrupt("return");
                        case 5:
                            return e.next = 7, s.d.login({code: r.code, agentId: i});
                        case 7:
                            if (200 == (t = e.sent).code) {
                                e.next = 10;
                                break
                            }
                            return e.abrupt("return");
                        case 10:
                            if (a = t.content, u = void 0 === a ? {} : a) {
                                e.next = 13;
                                break
                            }
                            return e.abrupt("return");
                        case 13:
                            return c = u.authInfo, b = void 0 === c ? {} : c, f = u.token, p = void 0 === f ? "" : f, wx.setStorageSync("QWToken", p), wx.setStorageSync("QWAuthInfo", b), e.abrupt("return", p);
                        case 17:
                        case"end":
                            return e.stop()
                    }
                }), e)
            })));
            return function () {
                return e.apply(this, arguments)
            }
        }(), d = function () {
            var e = Object(a.a)(Object(n.a)().mark((function e() {
                var r;
                return Object(n.a)().wrap((function (e) {
                    for (; ;) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, f();
                        case 2:
                            if (e.sent) {
                                e.next = 5;
                                break
                            }
                            return e.abrupt("return", b("获取session_key失败"));
                        case 5:
                            return e.next = 7, Object(o.c)();
                        case 7:
                            if (r = e.sent) {
                                e.next = 10;
                                break
                            }
                            return e.abrupt("return", b("get_cur_external_contact:获取当前外部联系人userid报错"));
                        case 10:
                            return e.abrupt("return", r.userId || r.userid);
                        case 11:
                        case"end":
                            return e.stop()
                    }
                }), e)
            })));
            return function () {
                return e.apply(this, arguments)
            }
        }()
    }
}]
