package com.jensuper.sell.enums;

import lombok.Getter;

/**
 * 创建订单返回结果
 */
@Getter
public enum ResultEnums {
    PARAM_ERRO(1,"参数不正确"),

    PRODUCT_NOT_EXIT(10,"商品不存在"),
    PRODUCT_STOCK_ERRO(11,"库存异常"),

    CART_EMPTY(12,"购物车为空"),

    ORDER_NOT_EXIT(13,"订单不存在"),
    ORDERDETAIL_NOT_EXIT(14,"订单详情不存在"),
    ORDER_STATUS_ERRO(15,"订单状态异常"),
    ORDER_UPDATE_FAIL(16,"修改订单失败"),
    ORDER_PAY_ERROR(17,"订单支付状态异常"),
    ORDER_PAY_UPDATE_FAIL(18,"订单支付状态更新失败"),
    ORDER_OWEN_ERROR(19,"订单支付状态更新失败"),
    WX_ORDER_MONEY_DIFF(20,"微信通知金额与订单金额不相同"),

    ORDER_CANCEL_SUCCESS(21, "订单取消成功"),
    ORDER_FINSH_SUCCESS(22, "订单完结成功"),
    ;
    private Integer code;
    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
