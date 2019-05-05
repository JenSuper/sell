package com.jensuper.sell.service;

import com.jensuper.sell.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryIdList);

    ProductCategory save(ProductCategory productCategory);
}
