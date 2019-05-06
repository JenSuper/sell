package com.jensuper.sell.converter;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: OrderMaster2OrderDtoConverter
 * @Description: 订单实体转换
 * @author:jichao
 * @date: 2019/5/6
 * @Copyright: 2019/5/6
 * Inc. All rights reserved.
 */
public class OrderMaster2OrderDtoConverter {

    /* 订单实体类转换为订单返回值 */
    public static OrderDTO orderMaster2OrderDtoConverter(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    /* 批量转换 */
    public static List<OrderDTO> orderMasterList2OrderDtoConverter(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e -> orderMaster2OrderDtoConverter(e)).collect(Collectors.toList());
    }
}
