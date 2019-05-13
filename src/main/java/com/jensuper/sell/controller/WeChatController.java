package com.jensuper.sell.controller;

import com.jensuper.sell.config.WechatMpServiceConfig;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: WeChatController
 * @Description:
 * @author:jichao
 * @date: 2019/5/13
 * @Copyright: 2019/5/13 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Autowired
    private WechatMpServiceConfig wechatMpServiceConfig;

    //网页授权
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        WxMpService wxMpService = wechatMpServiceConfig.getWxMpService();
        String url = "http://nfjne7.natappfree.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("redirectUrl = {}",redirectUrl);
        return "redirect:" + redirectUrl;

    }

    //获取用户信息
    @GetMapping("/userInfo")
    public String getAccesstToken(@RequestParam("code") String code,@RequestParam("state") String returnUrl) {
        WxMpService wxMpService = wechatMpServiceConfig.getWxMpService();
        WxMpOAuth2AccessToken accessToken = null;
        try {
            accessToken = wxMpService.oauth2getAccessToken(code);
            log.info("accessToken = {}", accessToken.getAccessToken());
            log.info("openid = {}", accessToken.getOpenId());
            log.info("scope = {}", accessToken.getScope());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "redirect:http://" + returnUrl + "?openid = " + accessToken.getOpenId();
    }
}
