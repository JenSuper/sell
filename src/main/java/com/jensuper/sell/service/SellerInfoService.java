package com.jensuper.sell.service;

import com.jensuper.sell.entity.SellerInfo;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: SellerInfoService
 * @Description:卖家信息service
 * @author:jichao
 * @date: 2019/5/29
 * @Copyright: 2019/5/29 www.rongdasoft.com
 * Inc. All rights reserved.
 */
public interface SellerInfoService {

    SellerInfo findByOpenid(String openId);
}
