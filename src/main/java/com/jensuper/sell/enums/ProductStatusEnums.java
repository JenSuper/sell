package com.jensuper.sell.enums;

import lombok.Getter;

/**
 * 商品枚举类型
 */
@Getter
public enum ProductStatusEnums implements CodeEnums{
    UP(0,"上架"),
    DOWN(1,"下架")
    ;

    private Integer code;//商品code
    private String msg;//商品code信息

    ProductStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
