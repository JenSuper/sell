package com.jensuper.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jensuper.sell.entity.OrderDetail;
import com.jensuper.sell.util.serializer.Date2LongSerializeJson;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private String orderId;//订单id
    private String buyerName;//买家名称
    private String buyerPhone;//买家电话
    private String buyerAddress;//买家地址
    private String buyerOpenid;//买家微信openid
    private BigDecimal orderAmount;//订单金额
    private Integer orderStatus;//订单状态，默认为新下单
    private Integer payStatus;//支付状态，默认未支付
    @JsonSerialize(using = Date2LongSerializeJson.class)
    private Date createTime;//创建时间
    @JsonSerialize(using = Date2LongSerializeJson.class)
    private Date updateTime;//修改时间
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)//为空不返回
    List<OrderDetail> orderDetailList;
}
