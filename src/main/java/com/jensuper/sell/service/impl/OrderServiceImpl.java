package com.jensuper.sell.service.impl;

import com.jensuper.sell.converter.OrderMaster2OrderDtoConverter;
import com.jensuper.sell.dto.CarDTO;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.entity.OrderDetail;
import com.jensuper.sell.entity.OrderMaster;
import com.jensuper.sell.entity.ProductInfo;
import com.jensuper.sell.enums.OrderPayStatusEnums;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.enums.OrderStatusEnums;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.repository.OrderDetailRepository;
import com.jensuper.sell.repository.OrderMatserRepository;
import com.jensuper.sell.service.OrderService;
import com.jensuper.sell.service.ProductInfoService;
import com.jensuper.sell.util.KeyUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
@Slf4j
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
                new SellException(ResultEnums.PRODUCT_NOT_EXIT);
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
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
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
            throw new SellException(ResultEnums.ORDER_NOT_EXIT);
        }
        /* 2. 查询订单详情 */
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnums.ORDERDETAIL_NOT_EXIT);
        }
        /* 构建订单返回结果 */
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;

    }

    /**
     * 查询订单列表
     * 条件：用户id及分页条件
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMatserRepository.findAllByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDtoConverter.orderMasterList2OrderDtoConverter(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    /**
     * 取消订单
     *   1）校验当前订单状态：必须是新创建的订单，则不能取消，抛异常
     *   2）查询订单详情：如果为空则抛异常
     *   3）修改订单状态为取消
     *   3）如果订单是支付状态，需要退款
     *   4）加库存
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDto) {
        //必须是新订单，才可以取消订单
        if (!orderDto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("【订单取消】订单状态异常，orderid = {}，orderstatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERRO);
        }
        //查询订单详情
        orderDto.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster masterRet = orderMatserRepository.save(orderMaster);
        if (masterRet == null) {
            log.error("【订单取消】订单状态修改失败");
            throw new SellException(ResultEnums.ORDER_NOT_EXIT);
        }
        //判断是否需要减库存
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();
        if ( CollectionUtils.isEmpty(orderDetailList)) {
            log.error("【取消订单】订单中商品信息为空，orderdetail = {}",orderDetailList);
            throw new SellException(ResultEnums.CART_EMPTY);
        }
        //如果已经付款，需要退款
        if (orderDto.getPayStatus().equals(OrderPayStatusEnums.PAY_SUCCESS.getCode())) {
            //TODO
        }

        //加库存
        List<CarDTO> carDTOList = orderDetailList.stream().map(e-> new CarDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(carDTOList);

        return orderDto;
    }

    /**
     * 订单完成
     *  1）判断订单状态，如果不是新订单，则抛异常
     *  2）修改订单状态为完结
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finsh(OrderDTO orderDto) {
        //如果不是新订单，抛出异常
        if (!orderDto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("【订单完成】订单状态异常，orderStatus={}",orderDto.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERRO);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnums.FINSH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster orderMasterResult = orderMatserRepository.save(orderMaster);

        if (orderMasterResult == null) {
            log.error("【订单完成】修改订单状态失败，orderStatus = {}",orderDto.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    /**
     * 5.订单支付
     * 	1）判断订单状态，必须是新订单才能支付
     * 	2）判断订单支付状态，必须是未支付状态才可以修改
     * 	3）修改订单支付状态
     * @param orderDto
     * @return
     */
    @Override
    public OrderDTO pay(OrderDTO orderDto) {
        //1. 判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("【订单支付】订单状态异常，orderStatus={}",orderDto.getOrderStatus());
            throw new SellException(ResultEnums.ORDER_STATUS_ERRO);
        }

        //2.判断订单支付状态
        if (!orderDto.getPayStatus().equals(OrderPayStatusEnums.PAY_OFF.getCode())) {
            log.error("【订单支付】订单支付状态异常，orderPayStatus={}",orderDto.getPayStatus());
            throw new SellException(ResultEnums.ORDER_PAY_ERROR);
        }

        //3.修改订单支付状态
        orderDto.setPayStatus(OrderPayStatusEnums.PAY_SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster orderMasterResult = orderMatserRepository.save(orderMaster);
        if (orderMasterResult == null) {
            log.error("【订单支付】订单支付状态更新失败，orderPayStatus = {}",orderDto.getPayStatus());
            throw new SellException(ResultEnums.ORDER_PAY_UPDATE_FAIL);
        }
        return orderDto;
    }

    /**
     * 查询所有订单列表
     * 卖家端查询
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMatserRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDtoConverter.orderMasterList2OrderDtoConverter(orderMasterPage.getContent());
        PageImpl<OrderDTO> orderDTOS = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOS;
    }
}
