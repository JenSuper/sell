package com.jensuper.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 订单详情
 */
@Entity
@Data
@DynamicUpdate
public class OrderDetail {

    @Id
    private String detailId;//订单详情id
    private String orderId;// 订单id
    private String productId;// 商品id
    private String productName;// 商品名称
    private BigDecimal productPrice;// 商品单价
    private Integer productQuantity;// 商品数量
    private String productIcon;// 小图logo
    private String createTime;// 创建时间
    private String updateTime;// 更新时间

}
