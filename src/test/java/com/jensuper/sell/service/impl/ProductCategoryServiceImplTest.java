package com.jensuper.sell.service.impl;

import com.jensuper.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void findOne() {
        ProductCategory category = productCategoryService.findOne(2);
        Assert.assertEquals(Integer.valueOf(2),category.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> categoryList = productCategoryService.findAll();
        Assert.assertNotEquals(0,categoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotNull(categoryList);
    }

    @Test
    public void save() {
        ProductCategory category = new ProductCategory();
        category.setCategoryName("聚划算");
        category.setCategoryType(5);
        ProductCategory save = productCategoryService.save(category);
    }
}