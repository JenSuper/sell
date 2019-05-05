package com.jensuper.sell.service.impl;

import com.jensuper.sell.entity.ProductInfo;
import com.jensuper.sell.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findByProductId() {
        ProductInfo productInfo = productInfoService.findByProductId("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = productInfoService.findByProductStatus();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findProductAll() {
        Page<ProductInfo> productInfoPage = productInfoService.findProductAll();
        System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    public void saveProduct() {
        ProductInfo pi = new ProductInfo();
        pi.setProductId("123455");
        pi.setProductName("饺子");
        pi.setProductPrice(new BigDecimal(15.5));
        pi.setCategoryType(3);
        pi.setProductStock(5);
        pi.setProductStatus(0);
        ProductInfo info = productInfoService.saveProduct(pi);
        Assert.assertNotEquals(null,info);
    }
}