package com.jensuper.sell.service.impl;

import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.service.BuyerService;
import com.jensuper.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: BuyerServiceImpl
 * @Description:
 * @author:jichao
 * @date: 2019/5/9
 * @Copyright: 2019/5/9 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 查询单个订单
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwn(openid, orderId);
        //查不到此订单
        if (orderDTO == null) {
            return null;
        }
        return orderDTO;
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwn(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查询不到订单信息，orderId={}",orderId);
            throw new SellException(ResultEnums.ORDER_NOT_EXIT);
        }
        return orderService.cancel(orderDTO);
    }

    /**
     * 验证openid是否与订单中openid一样
     * 1. 查询订单是否存在
     * 2. 验证订单用户id与参数中用户id是否相同
     * @param openid
     * @param orderId
     * @return
     */
    private OrderDTO checkOrderOwn(String openid,String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("【验证订单用户id与参数openid】查询不到订单信息，orderId={}",orderId);
            throw new SellException(ResultEnums.ORDER_NOT_EXIT);
        }
        if (!openid.equals(orderDTO.getBuyerOpenid())) {
            log.error("【验证订单用户id与参数openid】不相等,openid = {},orderId = {}",openid,orderId);
            throw new SellException(ResultEnums.ORDER_OWEN_ERROR);
        }
        return orderDTO;
    }
}
