package com.jensuper.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class OrderForm {
    /* 用户名 */
    @NotEmpty(message = "用户名必填")
    private String name;

    /* 手机号 */
    @NotEmpty(message = "手机号必填")
    private String phone;

    /* 地址 */
    @NotEmpty(message = "地址必填")
    private String address;

    /* 用户的微信openid */
    @NotEmpty(message = "openid必填")
    private String openid;

    @NotEmpty(message = "商品不能为空")
    private String items;


}
