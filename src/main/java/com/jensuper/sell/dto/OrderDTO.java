package com.jensuper.sell.dto;

import com.jensuper.sell.entity.OrderDetail;
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
    private Date createTime;//创建时间
    private Date updateTime;//修改时间

    List<OrderDetail> orderDetailList;
}