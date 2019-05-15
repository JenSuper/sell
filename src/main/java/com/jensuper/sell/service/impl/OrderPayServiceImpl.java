package com.jensuper.sell.service.impl;

import com.jensuper.sell.config.WeChatPayConfig;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.service.OrderPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: BestPayServiceImpl
 * @Description:
 * @author:jichao
 * @date: 2019/5/15
 * @Copyright: 2019/5/15 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Service
public class OrderPayServiceImpl implements OrderPayService {

    @Autowired
    private WeChatPayConfig weChatPayConfig;
    
    @Override
    public PayResponse orderPay(OrderDTO orderDTO) {
        //支付配置
        BestPayServiceImpl bestPayService = weChatPayConfig.payConfig();
        //调用支付
        PayRequest payRequest = new PayRequest();
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName("微信公众账号支付订单");
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        return bestPayService.pay(payRequest);
    }
}
