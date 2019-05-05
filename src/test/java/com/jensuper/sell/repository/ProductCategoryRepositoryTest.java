package com.jensuper.sell.repository;

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
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repostory;

    @Test
    public void demo() {
        ProductCategory one = repostory.findOne(1);
        System.out.println(one);
    }

    @Test
    public void save() {
        /* 新增 */
        ProductCategory pc = new ProductCategory();
        pc.setCategoryName("每日优鲜");
        pc.setCategoryType(3);
        ProductCategory save = repostory.save(pc);


    }

    @Test
    public void update() {
        /* 修改 */
        ProductCategory cate = repostory.findOne(2);
        cate.setCategoryType(6);
        repostory.save(cate);
    }

    @Test
    public void query() {
        /* 查询 */
        List<Integer> categoryIdList = Arrays.asList(1,3,6);
        List<ProductCategory> category = repostory.findByCategoryTypeIn(categoryIdList);
        System.out.println(category);
        Assert.assertNotNull(category);
    }
}