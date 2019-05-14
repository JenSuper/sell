package com.jensuper.sell.config;

import com.jensuper.sell.dto.OrderDTO;
import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: WeChatPayConfig
 * @Description:
 * @author:jichao
 * @date: 2019/5/14
 * @Copyright: 2019/5/14 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Component
public class WeChatPayConfig {

    @Autowired
    private WechatAcountConfig wechatAcountConfig;

    @Bean
    public BestPayServiceImpl payConfig() {
        //微信公众账号支付配置
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAcountConfig.getAppId());
        wxPayH5Config.setAppSecret(wechatAcountConfig.getSecret());
        wxPayH5Config.setMchId(wechatAcountConfig.getMchId());
        wxPayH5Config.setMchKey(wechatAcountConfig.getMchKey());
        wxPayH5Config.setNotifyUrl(wechatAcountConfig.getNotifyUrl());

        //支付类, 所有方法都在这个类里
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return bestPayService;
    }
}
