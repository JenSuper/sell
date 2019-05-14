package com.jensuper.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: WechatMpServiceConfig
 * @Description:
 * @author:jichao
 * @date: 2019/5/13
 * @Copyright: 2019/5/13 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Component
public class WechatMpServiceConfig {

    @Autowired
    private WechatAcountConfig wechatConfig;
    
    @Bean
    public WxMpService getWxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(getWxMpConfigStorage());//设置存储配置
        return wxMpService;
    }

    @Bean
    private WxMpConfigStorage getWxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(wechatConfig.getAppId());//用户id
        wxMpConfigStorage.setSecret(wechatConfig.getSecret());//用户secret
        return wxMpConfigStorage;
    }
}
