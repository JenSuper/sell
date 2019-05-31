package com.jensuper.sell.controller;

import com.jensuper.sell.common.Const;
import com.jensuper.sell.entity.SellerInfo;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.service.SellerInfoService;
import com.jensuper.sell.util.CookieUtil;
import com.jensuper.sell.util.RedisUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: SellerInfoController
 * @Description: 卖家
 * @author:jichao
 * @date: 2019/5/27
 * @Copyright: 2019/5/27 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Controller
@RequestMapping("/seller/user")
public class SellerUserController {

    @Autowired
    private SellerInfoService sellerInfoService;

    /**
     * 卖家微信登录
     * 1. 根据openid查询到用户
     * 2. 将用户openid存到redis中
     * 3. 将redis中openid key设置到cookie中
     * 4. 页面跳转
     *
     * @param openId
     * @param map
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openId") String openId, Map<String, Object> map,
                              HttpServletResponse response) {
        //1. 通过用户openid查询用户信息
        SellerInfo sellerInfo = sellerInfoService.findByOpenid(openId);

        if (sellerInfo == null) {
            map.put("msg", ResultEnums.PARAM_ERRO);
            map.put("url", "/common/error");
            return new ModelAndView("/product/list", map);
        }
        //将用户openid存入redis中
        UUID uuid = UUID.randomUUID();
        RedisUtil.setValue(String.format(Const.REDIS_KEY_PREFF, uuid), sellerInfo.getOpenid());

        //设置到cookie
        CookieUtil.setCookie(uuid.toString(), response);

        map.put("url", "/sell/seller/product/list");
        map.put("msg", ResultEnums.LOGOUT_SUCCESS.getMsg());

        return new ModelAndView("/product/list", map);
    }

    /**
     * 登出
     * 验证cookie是否存在
     * 1. 清除redis中用户openid
     * 2. 清除浏览器中cookie
     * 3. 页面跳转
     *
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> map) {
        //从req取出cookie
        Cookie cookie = CookieUtil.getCookie(request, Const.COOKIE_TOKEN);

        if (cookie != null) {
            //删除redis中openid
            RedisUtil.delete(String.format(Const.REDIS_KEY_PREFF, cookie.getValue()));

            //清空cookie
            CookieUtil.delCookie(response, Const.COOKIE_TOKEN);
        }
        map.put("url", "/sell/seller/product/list");
        map.put("msg", ResultEnums.LOGIN_SUCCESS.getMsg());

        return new ModelAndView("/sell/seller/product/list", map);
    }

}
