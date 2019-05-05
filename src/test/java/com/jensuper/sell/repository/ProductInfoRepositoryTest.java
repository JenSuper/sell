package com.jensuper.sell.repository;

import com.jensuper.sell.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveProductInfo() {
        ProductInfo pi = new ProductInfo();
        pi.setProductId("123456");
        pi.setProductName("好吃点");
        pi.setProductDescription("每天吃一点");
        pi.setProductIcon("http://adc.jpg");
        pi.setProductStatus(0);
        pi.setProductStock(50);
        pi.setProductPrice(new BigDecimal(5.5));
        pi.setCategoryType(4);
        ProductInfo info = repository.save(pi);
        Assert.assertNotNull(info);

    }
    @Test
    public void findByProductStatus() {
        List<ProductInfo> infoList = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,infoList);
    }
}