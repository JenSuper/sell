//package com.jensuper.sell.service.impl;
//
//import com.jensuper.sell.entity.OrderMaster;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class OrderMatserServiceImplTest {
//
//    @Autowired
//    private OrderMatserService orderMatserService;
//    @Test
//    public void findAllByBuyerOpenid() {
//        List<OrderMaster> result = orderMatserService.findAllByBuyerOpenid("0001");
//        System.out.println(result);
//        Assert.assertNotEquals(0,result.size());
//    }
//
//    /* 保存订单信息 */
//    @Test
//    public void save() {
//        OrderMaster orderMaster = new OrderMaster();
//        orderMaster.setOrderId("112");
//        orderMaster.setBuyerName("鸣人");
//        orderMaster.setBuyerPhone("10086");
//        orderMaster.setBuyerAddress("北京市西城区");
//        orderMaster.setBuyerOpenid("0001");
//        orderMaster.setOrderAmount(new BigDecimal("12.5"));
//        OrderMaster result = orderMatserService.save(orderMaster);
//        System.out.println(result);
//        Assert.assertNotNull(result);
//    }
//}