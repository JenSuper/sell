package com.jensuper.sell.service;

import com.jensuper.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderDto);

    /**
     * 查询订单
     * 条件 订单id
     */
    OrderDTO findOne(String orderId);

    /**
     * 查询订单列表
     * 买家openid 分页对象
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单
     */
    OrderDTO cancel(OrderDTO orderDto);

    /**
     * 完结订单
     */
    OrderDTO finsh(OrderDTO orderDto);

    /**
     * 支付订单
     */
    OrderDTO pay(OrderDTO orderDto);
}
