package com.jensuper.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品实体
 */

@Data
public class ProductVO  {

    @JsonProperty("name")
    private String productName;/* 商品类别名称 json返回字段名称映射为name */

    @JsonProperty("type")
    private Integer productType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
