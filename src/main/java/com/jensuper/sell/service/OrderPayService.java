package com.jensuper.sell.service;

import com.jensuper.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: OrderPayService
 * @Description: 微信支付
 * @author:jichao
 * @date: 2019/5/15
 * @Copyright: 2019/5/15 www.rongdasoft.com
 * Inc. All rights reserved.
 */
public interface OrderPayService {
    PayResponse orderPay(OrderDTO orderDTO);

    void wxNotify(String notifyData);
}
