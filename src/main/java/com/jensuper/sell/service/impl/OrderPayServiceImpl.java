package com.jensuper.sell.service.impl;

import com.jensuper.sell.config.WeChatPayConfig;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.service.OrderPayService;
import com.jensuper.sell.service.OrderService;
import com.jensuper.sell.util.WxUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderPayServiceImpl implements OrderPayService {

    @Autowired
    private WeChatPayConfig weChatPayConfig;

    @Autowired
    private OrderService orderService;

    /**
     * 1. 订单支付配置
     * 2. 调起支付
     * 3. 返回支付结果
     * @param orderDTO
     * @return
     */
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

    /**
     * 微信异步通知
     * 1. 验证通知
     * 2. 修改支付状态
     * @param notifyData
     */
    @Override
    public void wxNotify(String notifyData) {
        //支付配置
        BestPayServiceImpl bestPayService = weChatPayConfig.payConfig();
        //1. 微信签名
        //2. 支付状态
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);

        //3. 验证订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if (orderDTO == null) {
            log.error("【微信异步通知】订单不存在，orderId = {}",orderDTO.getOrderId());
            throw new SellException(ResultEnums.ORDER_NOT_EXIT);
        }

        //4. 验证订单金额与微信通知金额
        if (!WxUtil.checkMoney(orderDTO.getOrderAmount().doubleValue(), payResponse.getOrderAmount())) {
            log.error("【微信异步通知】微信通知金额与订单进度不相同，orderId = {},微信通知金额 = {},订单金额 = {}",
                    payResponse.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnums.WX_ORDER_MONEY_DIFF);
        }

        //5. 修改支付状态
        orderService.pay(orderDTO);
    }
}
