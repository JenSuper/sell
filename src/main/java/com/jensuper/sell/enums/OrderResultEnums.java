package com.jensuper.sell.enums;

import lombok.Getter;

/**
 * 创建订单返回结果
 */
@Getter
public enum OrderResultEnums {
    PARAM_ERRO(1,"参数不正确"),
    PRODUCT_NOT_EXIT(10,"商品不存在"),
    PRODUCT_STOCK_ERRO(11,"库存异常"),
    ORDER_NOT_EXIT(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIT(13,"订单详情不存在"),
    CART_EMPTY(13,"购物车为空"),
    ;
    private Integer code;
    private String msg;

    OrderResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
