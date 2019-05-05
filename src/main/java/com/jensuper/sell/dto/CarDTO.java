package com.jensuper.sell.dto;

import lombok.Data;

/**
 * 购物车
 */
@Data
public class CarDTO {
    private String productId;
    private Integer amount;

    public CarDTO() {
    }

    public CarDTO(String productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
