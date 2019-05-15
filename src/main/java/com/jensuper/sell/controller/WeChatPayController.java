package com.jensuper.sell.controller;

import com.jensuper.sell.config.WeChatPayConfig;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.service.OrderPayService;
import com.jensuper.sell.service.OrderService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    private OrderPayService orderPayService;

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
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl, Map<String,Object> map) {
        //1.查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        //2.获取支付参数
        PayResponse payResponse = orderPayService.orderPay(orderDTO);
        //3.调用JSAPI掉起微信支付
        map.put("payRespone", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("/pay/create", map);
    }
}
