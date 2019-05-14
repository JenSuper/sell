package com.jensuper.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: WechatConfig
 * @Description:
 * @author:jichao
 * @date: 2019/5/13
 * @Copyright: 2019/5/13 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAcountConfig {

    private String AppId;
    private String Secret;

    private String MchId;
    private String MchKey;
    private String NotifyUrl;
}
