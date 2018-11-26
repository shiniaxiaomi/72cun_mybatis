/**
 * Created by LuYingJie on 2018/11/13.
 */


javascript:(function (x, a, v, m, t) {
    var f = "", c = [], j = /http.+png|http.+jpg|http.+gif|http.+svg|http.+jpeg|http.+BMP|data/g,
        b = x.getElementsByTagName("meta"), y = x.getElementsByTagName("img"), l = x.getElementsByTagName("a");
    for (var s = 0; s < b.length; s++) {
        if (b[s].name.toLowerCase() == "description") {
            f = b[s].content;
        }
    }
    for (var s = 0; s < y.length; s++) {
        if (y[s].src.toLowerCase().indexOf("logo") > 0) {
            c.push(y[s].src);
        }
    }
    for (var s = 0; s < l.length; s++) {
        var o = l[s].getElementsByTagName("img");
        if ((" " + l[s].className + " ").toLowerCase().indexOf("logo") > -1) {
            if (o.length > 0) {
                c.push(o[0].src);
            } else {
                if (l[s].currentStyle) {
                    var p = l[s].currentStyle.backgroundImage;
                    var k = l[s].parentNode.currentStyle.backgroundImage;
                    if (p != "none") {
                        p = p.match(j)[0];
                        if (p != "data") {
                            c.push(p);
                        }
                    } else {
                        if (k != "none") {
                            k = k.match(j)[0];
                            if (k != "data") {
                                c.push(k);
                            }
                        }
                    }
                } else {
                    var p = getComputedStyle(l[s], false)["backgroundImage"];
                    var k = getComputedStyle(l[s].parentNode, false)["backgroundImage"];
                    if (p != "none") {
                        p = p.match(j)[0];
                        if (p != "data") {
                            c.push(p);
                        }
                    } else {
                        if (k != "none") {
                            k = k.match(j)[0];
                            if (k != "data") {
                                c.push(k);
                            }
                        }
                    }
                }
            }
        } else {
            if ((" " + l[s].parentNode.className + " ").toLowerCase().indexOf("logo") > -1) {
                if (o.length > 0) {
                    c.push(o[0].src);
                } else {
                    if (l[s].currentStyle) {
                        var k = l[s].parentNode.currentStyle.backgroundImage;
                        var p = l[s].currentStyle.backgroundImage;
                        if (k != "none") {
                            k = k.match(j)[0];
                            if (k != "data") {
                                c.push(k);
                            }
                        } else {
                            if (p != "none") {
                                p = p.match(j)[0];
                                if (p != "data") {
                                    c.push(p);
                                }
                            }
                        }
                    } else {
                        var k = getComputedStyle(l[s].parentNode, false)["backgroundImage"];
                        var p = getComputedStyle(l[s], false)["backgroundImage"];
                        if (k != "none") {
                            k = k.match(j)[0];
                            if (k != "data") {
                                c.push(k);
                            }
                        } else {
                            if (p != "none") {
                                p = p.match(j)[0];
                                if (p != "data") {
                                    c.push(p);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    var q = [], g = {};
    for (var s = 0, u; (u = c[s]) != null; s++) {
        if (!g[u]) {
            q.push(u);
            g[u] = true;
        }
    }
    var n = "http://www.36zhen.com/api/addWebsite?url=" + v(location.href) + "&name=" + v(x.title) + "&description=" + v(f) + "&imgUrl=" + v(c);
    if (!window.open(n, "36zhen", "toolbar=0,resizable=1,scrollbars=yes,status=1,width=" + m + ",height=" + t + ",left=" + (a.width - m) / 2 + ",top=" + (a.height - t) / 2)) {
        window.location.href = n;
    }
})(document, screen, encodeURIComponent, 700, 500);


javascript:(function (x, sc, width, height) {
    var url = 'https://www.usetools.cn?url=[url]&title=[title]';
    url = url.replace('[url]', encodeURIComponent(location.href))
        .replace('[title]', encodeURIComponent(x.title));
    console.dir(url);
    window.open(url, '_blank', 'toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=' + width + ', height=' + height + ',left=' + (sc.width - width) / 2 + ',top=' + (sc.height - 50 - height) / 2);
})(document, screen, 1250, 660);


javascript:(function (x, sc, width, height) {
    var newWin = window.open('', '_blank', 'toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=' + width + ', height=' + height + ',left=' + (sc.width - width) / 2 + ',top=' + (sc.height - 50 - height) / 2);
    var formStr = '';
    formStr = "<form style='visibility:hidden;' method='POST' action='https://localhost'>" +
        "<input type='hidden' name='url' value='[url]' />" +
        "<input type='hidden' name='title' value='[title]' />" +
        "</form>";
    formStr.replace('[url]', encodeURIComponent(location.href))
        .replace('[title]', encodeURIComponent(x.title));
    newWin.document.body.innerHTML = formStr;
    newWin.document.forms[0].submit();
    return newWin;
})(document, screen, 1250, 660);


javascript:(function (x, sc, width, height) {
    var newWin = window.open('', '_blank', 'toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=' + width + ', height=' + height + ',left=' + (sc.width - width) / 2 + ',top=' + (sc.height - 50 - height) / 2);
    var formStr = '';
    formStr = "<form style='visibility:hidden;' method='POST' action='https://www.usetools.cn/collection/url'>" + "<input type='hidden' name='url' value='[url]' />" + "<input type='hidden' name='title' value='[title]' />" + "</form>";
    formStr = formStr.replace('[url]', encodeURIComponent(location.href)).replace('[title]', encodeURIComponent(x.title));
    newWin.document.body.innerHTML = formStr;
    newWin.document.forms[0].submit();
    return newWin;
})(document, screen, 1250, 660);


javascript:(function (x, sc, width, height) {
    var newWin = window.open('https://www.usetools.cn/collection/url', '_blank', 'toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=' + width + ', height=' + height + ',left=' + (sc.width - width) / 2 + ',top=' + (sc.height - 50 - height) / 2);
    var formStr = '';
    formStr = "<form style='visibility:hidden;' method='POST' action='https://www.usetools.cn/collection/url'>"+
        "<input type='hidden' name='url' value='[url]' />"+
        "<input type='hidden' name='title' value='[title]' />"+
        "</form>";
    formStr=formStr.replace('[url]', encodeURIComponent(location.href))
        .replace('[title]', encodeURIComponent(x.title));
    newWin.document.body.innerHTML = formStr;
    newWin.document.forms[0].submit();
    return newWin;
})(document, screen, 1250, 660);
