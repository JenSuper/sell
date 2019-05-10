package com.jensuper.sell.controller;

import com.jensuper.sell.VO.ResultVO;
import com.jensuper.sell.converter.OrderForm2OrderDto;
import com.jensuper.sell.dto.OrderDTO;
import com.jensuper.sell.entity.OrderDetail;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.form.OrderForm;
import com.jensuper.sell.service.BuyerService;
import com.jensuper.sell.service.OrderService;
import com.jensuper.sell.util.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *  1）验证订单参数
     *  2）将订单表单实体类转换为订单dto
     *  3）创建订单
     *  4）构建返回结果（订单id）
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        //1. 参数验证
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数错误，orderForm={}",orderForm);
            throw new SellException(ResultEnums.PARAM_ERRO.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //2. 参数实体类转换
        OrderDTO orderDTO = OrderForm2OrderDto.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnums.CART_EMPTY);
        }

        //3. 订单信息入库
        OrderDTO dtoRet = orderService.create(orderDTO);

        //4. 构建返回结果
        Map<String, String> mapRet = new HashMap<>();
        mapRet.put("orderId", dtoRet.getOrderId());
        return ResultVoUtil.success(mapRet);

    }

    /**
     * 订单列表
     *  1) 根据openid、page、size查询订单列表
     *  2）查询结果为page对象
     *  3）将orderDTO返回
     *  备注：日期返回格式：秒
     *        需要将日期序列化，通过JsonSerialize注解实现
     *        字段为空不返回
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/openid")
    public ResultVO<List<Page<OrderDTO>>> list(@RequestParam String openid,
                                               @RequestParam(value = "page",defaultValue = "0") Integer page,
                                               @RequestParam(value = "size",defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空，openid={}",openid);
            throw new SellException(ResultEnums.PARAM_ERRO);
        }
        //1.查询订单列表：openid pageable
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
        if (CollectionUtils.isEmpty(orderDTOPage.getContent())) {
            log.error("【查询订单列表】订单信息为空，orderList = {}",orderDTOPage.getContent());
        }
        //返回结果
        return ResultVoUtil.success(orderDTOPage.getContent());
    }

    /**
     * 3.查询订单详情
     * 	入参：openid、orderid
     * 	1）校验用户id和订单中用户id是否相同
     * 	2）查询订单
     * @param openid
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("openid") String openid,
                           @RequestParam("orderid") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空，openid={}",openid);
            throw new SellException(ResultEnums.PARAM_ERRO);
        }
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
       return ResultVoUtil.success(orderDTO);
    }

    /**
     * 4.取消订单
     * 	入参：openid orderId
     * 	1）校验用户id和订单中用户id是否相同
     * 	2) 如果订单不存在，抛异常
     * 	2）取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderid") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【取消订单】openid为空，openid={}",openid);
            throw new SellException(ResultEnums.PARAM_ERRO);
        }
        buyerService.cancelOrder(openid, orderId);
        return ResultVoUtil.success();
    }

}
