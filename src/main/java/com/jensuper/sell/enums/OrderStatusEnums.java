package com.jensuper.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnums {
    NEW(0,"新下单"),
    SUCCESS(1,"支付成功"),
    CANCEL(2,"订单取消"),
    FINSH(3,"订单完成")
    ;

    private Integer code;//商品code
    private String msg;//商品code信息

    OrderStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
