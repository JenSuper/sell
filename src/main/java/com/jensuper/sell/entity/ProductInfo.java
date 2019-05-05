package com.jensuper.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品信息
 */
@Entity
@Data
public class ProductInfo {

    /* 商品编号 */
    @Id
    private String productId;
    /* 商品名称 */
    private String productName;
    /* 商品价格 */
    private BigDecimal productPrice;
    /* 库存 */
    private Integer productStock;
    /* 商品描述 */
    private String productDescription;
    /* 商品logo */
    private String productIcon;
    /* 商品状态 0 正常 1下架 */
    private Integer productStatus;
    /* 商品类目编号 */
    private Integer categoryType;

}
