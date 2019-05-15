package com.jensuper.sell.service.impl;

import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private static final String BUYER_OPENID = "10086";

    @Test
    public void create() {
        /* 买家信息 */
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("狄仁杰");
        orderDTO.setBuyerPhone("188888888888");
        orderDTO.setBuyerAddress("王者峡谷");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        /* 购物车信息 */
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("123455");
        orderDetail2.setProductQuantity(2);
        orderDetailList.add(orderDetail2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】 result = {}", result);

    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 5);
        Page<OrderDTO> list = orderService.findList(pageRequest);
        Assert.assertTrue("卖家端查询订单列表", list.getTotalElements() > 0);
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finsh() {
    }

    @Test
    public void pay() {
    }
}