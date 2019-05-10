package com.jensuper.sell.service;

import com.jensuper.sell.dto.OrderDTO;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: BuyerService
 * @Description:买家
 * @author:jichao
 * @date: 2019/5/9
 * @Copyright: 2019/5/9 www.rongdasoft.com
 * Inc. All rights reserved.
 */
public interface BuyerService {

    OrderDTO findOrderOne(String openid,String orderId);

    OrderDTO cancelOrder(String openid, String orderId);
}
