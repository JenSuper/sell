package com.jensuper.sell.controller;

import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.GET;
import java.net.URL;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: SellerOrderController
 * @Description:
 * @author:jichao
 * @date: 2019/5/16
 * @Copyright: 2019/5/16 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 卖家订单列表
     * 1. 查询所有订单
     * 2. 跳转到订单列表页
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        //初始化page
        PageRequest pageable = new PageRequest(page - 1, size);
        //查询订单列表
        Page<OrderDTO> dtoPage = orderService.findList(pageable);
        //构造返回结果
        map.put("pageContent", dtoPage);
        map.put("currentPage", page);
        map.put("pageSize", size);
        return new ModelAndView("/order/list", map);
    }

    /**
     * 取消订单
     * 1. 查询订单是否存在
     * 2. 取消订单
     * 3. 跳转到成功页面
     *
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            // 查询订单
            OrderDTO orderDTO = orderService.findOne(orderId);
            //取消订单
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端取消订单】，发生异常", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }
        //返回取消成功结果
        map.put("msg", ResultEnums.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success", map);
    }

    /**
     * 订单详情
     * 1. 查询订单是否存在
     * 2. 返回订单信息
     * 3. 跳转到详情页
     *
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e) {
            log.error("【卖家端查询订单详情】，发生异常", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("/order/detail", map);
    }

    /**
     * 完结订单
     * 1. 查询订单是否存在
     * 2. 完结订单
     * 3. 跳转到成功页
     *
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finsh")
    public ModelAndView finsh(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端完结订单】，发生异常", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }

        map.put("msg", ResultEnums.ORDER_FINSH_SUCCESS);
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("/order/success", map);
    }
}
