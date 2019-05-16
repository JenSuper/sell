package com.jensuper.sell.controller;

import com.jensuper.sell.dto.OrderDTO;
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
        return new ModelAndView("/order/list", map);
    }
}
