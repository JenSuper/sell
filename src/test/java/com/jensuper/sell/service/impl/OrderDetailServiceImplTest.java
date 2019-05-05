//package com.jensuper.sell.service.impl;
//
//import com.jensuper.sell.entity.OrderDetail;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class OrderDetailServiceImplTest {
//
//    @Autowired
//    private OrderDetailService orderDetailService;
//
//    @Test
//    public void findByOrderId() {
//        Pageable pageable = new PageRequest(0, 1);
//        Page<OrderDetail> result = orderDetailService.findByOrderId("111", pageable);
//        System.out.println(result);
//        Assert.assertNotEquals(0,result.getTotalElements());
//    }
//
//    @Test
//    public void svae() {
//        OrderDetail orderDetail = new OrderDetail();
//        orderDetail.setDetailId("1");
//        orderDetail.setOrderId("111");
//        orderDetail.setProductId("123455");
//        orderDetail.setProductName("饺子");
//        orderDetail.setProductPrice(new BigDecimal("15.5"));
//        orderDetail.setProductQuantity(5);
//        orderDetail.setProductIcon("www.xxx.png");
//        OrderDetail result = orderDetailService.save(orderDetail);
//        System.out.println(result);
//        Assert.assertNotNull(result);
//    }
//}