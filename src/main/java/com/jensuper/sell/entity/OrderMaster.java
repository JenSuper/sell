package com.jensuper.sell.entity;

import com.jensuper.sell.enums.OrderStatusEnums;
import com.jensuper.sell.enums.OrderPayStatusEnums;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
    private String orderId;//订单id
    private String buyerName;//买家名称
    private String buyerPhone;//买家电话
    private String buyerAddress;//买家地址
    private String buyerOpenid;//买家微信openid
    private BigDecimal orderAmount;//订单金额
    private Integer orderStatus = OrderStatusEnums.NEW.getCode();//订单状态，默认为新下单
    private Integer payStatus = OrderPayStatusEnums.PAY_OFF.getCode();//支付状态，默认未支付
    private Date createTime;//创建时间
    private Date updateTime;//修改时间

}
