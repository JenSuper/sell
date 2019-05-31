package com.jensuper.sell.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: ProductForm
 * @Description:商品表单
 * @author:jichao
 * @date: 2019/5/22
 * @Copyright: 2019/5/22 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Data
public class ProductForm {

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
    /* 商品类目编号 */
    private Integer categoryType;
}
