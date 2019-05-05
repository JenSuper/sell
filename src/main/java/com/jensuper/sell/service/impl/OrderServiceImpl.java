package com.jensuper.sell.service.impl;

import com.jensuper.sell.dto.CarDTO;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.entity.OrderDetail;
import com.jensuper.sell.entity.OrderMaster;
import com.jensuper.sell.entity.ProductInfo;
import com.jensuper.sell.enums.OrderPayStatusEnums;
import com.jensuper.sell.enums.OrderResultEnums;
import com.jensuper.sell.enums.OrderStatusEnums;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.repository.OrderDetailRepository;
import com.jensuper.sell.repository.OrderMatserRepository;
import com.jensuper.sell.service.OrderService;
import com.jensuper.sell.service.ProductInfoService;
import com.jensuper.sell.unit.KeyUnit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMatserRepository orderMatserRepository;
    /**
     * 生成订单
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDto) {

        String orderId = KeyUnit.getUniqueKey();//订单id
        BigDecimal amount = new BigDecimal(BigInteger.ZERO);//商品总价

        /* 查询商品（数量、单价） */
        for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            //1. 根据商品id查询商品信息
            ProductInfo product = productInfoService.findByProductId(orderDetail.getProductId());
            if (product == null) {
                new SellException(OrderResultEnums.PRODUCT_NOT_EXIT);
            }
            //2. 计算总价 (商品单价*数量)+总价<总价存放在订单表中，所以要得到所有商品的价格>
            amount = product.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(amount);

            //3. 入库订单详情表 orderdetail
            orderDetail.setDetailId(KeyUnit.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(product,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        /* 将订单存入数据库（ordermaster ） */
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(amount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(OrderPayStatusEnums.PAY_OFF.getCode());
        orderMatserRepository.save(orderMaster);

        //4. 扣库存:需要将订单中商品信息转换为购物车信息，用来减库存
        List<CarDTO> carList = new ArrayList<>();
        carList = orderDto.getOrderDetailList().stream().map(vo ->
                new CarDTO(vo.getProductId(),vo.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(carList);
        return orderDto;
    }

    /**
     * 查询订单、订单详情
     *  条件：订单id
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {
        /* 1. 查询订单 */
        OrderMaster orderMaster = orderMatserRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(OrderResultEnums.ORDER_NOT_EXIT);
        }
        /* 2. 查询订单详情 */
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(OrderResultEnums.ORDERDETAIL_NOT_EXIT);
        }
        /* 构建订单返回结果 */
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;

    }

    @Override
    public List<OrderDTO> findAll(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDto) {
        return null;
    }

    @Override
    public OrderDTO finsh(OrderDTO orderDto) {
        return null;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDto) {
        return null;
    }
}
