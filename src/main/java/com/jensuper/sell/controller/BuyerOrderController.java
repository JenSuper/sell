package com.jensuper.sell.controller;

import com.jensuper.sell.converter.OrderForm2OrderDto;
import com.jensuper.sell.VO.ResultVO;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.form.OrderForm;
import com.jensuper.sell.service.OrderService;
import com.jensuper.sell.unit.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param orderForm
     * @param bindingResult
     * @return
     */
    public ResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        //1. 参数验证
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数错误，orderForm={}",orderForm);
            throw new SellException(ResultEnums.PARAM_ERRO.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //2. 参数实体类转换
        OrderDTO orderDTO = OrderForm2OrderDto.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnums.CART_EMPTY);
        }

        //3. 订单信息入库
        OrderDTO dtoRet = orderService.create(orderDTO);

        //4. 构建返回结果
        Map<String, String> mapRet = new HashMap<>();
        mapRet.put("orderId", dtoRet.getOrderId());
        return ResultVoUtil.success(mapRet);

    }
    //订单列表
    //订单详情
    //取消订单
}
