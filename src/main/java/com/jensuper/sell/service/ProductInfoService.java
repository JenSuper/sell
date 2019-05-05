package com.jensuper.sell.service;

import com.jensuper.sell.dto.CarDTO;
import com.jensuper.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findByProductId(String productId);

    List<ProductInfo> findByProductStatus();

    Page<ProductInfo> findProductAll();

    ProductInfo saveProduct(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CarDTO> carDTOList);

    //减库存
    void decreaseStock(List<CarDTO> carDTOList);

}
