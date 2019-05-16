package com.jensuper.sell.enums;

import lombok.Getter;

@Getter
public enum OrderPayStatusEnums implements CodeEnums{
    PAY_OFF(0,"待支付"),
    PAY_SUCCESS(1,"支付成功")
    ;
    private Integer code;
    private String msg;

    OrderPayStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
