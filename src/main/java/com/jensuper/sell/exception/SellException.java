package com.jensuper.sell.exception;

import com.jensuper.sell.enums.OrderResultEnums;

/**
 * 订单异常处理
 */
public class SellException extends RuntimeException {

    private Integer code;
    private String msg;

    public SellException(OrderResultEnums orderResultEnums) {
        super(orderResultEnums.getMsg());
        this.code = orderResultEnums.getCode();
    }

    public SellException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
