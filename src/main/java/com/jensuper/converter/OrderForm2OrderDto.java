package com.jensuper.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.entity.OrderDetail;
import com.jensuper.sell.enums.OrderResultEnums;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDto {

    /* 订单表单实体类转换为订单实体类 */
    public static OrderDTO converter(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        //将String转换为集合
        Gson gson = new Gson();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
            log.error("【对象转换错误】items={}",orderForm.getItems());
            throw new SellException(OrderResultEnums.PARAM_ERRO);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
