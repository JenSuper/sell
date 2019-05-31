package com.jensuper.sell.util;

import com.jensuper.sell.common.Const;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: CookieUtil
 * @Description: cookie工具类
 * @author:jichao
 * @date: 2019/5/31
 * @Copyright: 2019/5/31 www.rongdasoft.com
 * Inc. All rights reserved.
 */
public class CookieUtil {


    /**
     * 设置cookie
     * @param cookieValue
     * @param response
     */
    public static void setCookie(String cookieValue, HttpServletResponse response) {
        Cookie cookie = new Cookie(Const.COOKIE_TOKEN,cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(Const.COOKIE_EXPIRE_TIME);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     * @param request
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request,String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 删除cookie
     * @param response
     * @param key
     * @return
     */
    public static void delCookie(HttpServletResponse response,String key) {
        Cookie cookie = new Cookie(key,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
