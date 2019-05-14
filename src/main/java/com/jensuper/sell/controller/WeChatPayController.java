package com.jensuper.sell.controller;

import com.jensuper.sell.config.WeChatPayConfig;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.service.OrderService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: WeChatPayController
 * @Description:
 * @author:jichao
 * @date: 2019/5/14
 * @Copyright: 2019/5/14 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class WeChatPayController {

    @Autowired
    private WeChatPayConfig weChatPayConfig;

    @Autowired
    private OrderService orderService;

    /** *
     * 支付
     * 使用第三方SDK实现
     * @author jichao
     * @date 2019/5/14 14:00
     * @param orderId
     * @param returnUrl
     * @return String
     */
    @RequestMapping("/create")
    public String create(@RequestParam("orderId") String orderId,
                       @RequestParam("returnUrl") String returnUrl) {
        //1. 支付配置类
        BestPayServiceImpl bestPayService = weChatPayConfig.payConfig();
        //2. 订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        //3. 支付参数
        PayRequest payRequest = new PayRequest();
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        payRequest.setOrderId(orderId);
        payRequest.setOrderName("微信公众账号支付订单");
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        bestPayService.pay(payRequest);

        return "redirect:" + returnUrl;
    }
}
