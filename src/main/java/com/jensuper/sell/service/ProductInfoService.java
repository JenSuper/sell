package com.jensuper.sell.service;

import com.jensuper.sell.dto.CarDTO;
import com.jensuper.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findByProductId(String productId);

    List<ProductInfo> findByProductStatus();

    Page<ProductInfo> findProductAll(Pageable pageable);

    ProductInfo saveProduct(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CarDTO> carDTOList);

    //减库存
    void decreaseStock(List<CarDTO> carDTOList);

    //商品上架
    ProductInfo onSale(String orderId);
    //商品下架
    ProductInfo offSale(String orderId);

}
