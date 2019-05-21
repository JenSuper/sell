package com.jensuper.sell.controller;

import com.jensuper.sell.entity.ProductCategory;
import com.jensuper.sell.entity.ProductInfo;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.service.OrderService;
import com.jensuper.sell.service.ProductCategoryService;
import com.jensuper.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: ProductController
 * @Description:商品
 * @author:jichao
 * @date: 2019/5/20
 * @Copyright: 2019/5/20 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 商品列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findProductAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/product/list", map);
    }

    /**
     * 商品上架
     *
     * @param orderId
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView on_sale(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        try {
            productInfoService.onSale(orderId);
        } catch (SellException e) {
            log.error("【商品上架】异常，msg = {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnums.PRODUCT_UPDATE_SUCCESS);
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    /**
     * 商品下架
     *
     * @param orderId
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView off_sale(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        try {
            productInfoService.offSale(orderId);
        } catch (SellException e) {
            log.error("【商品下架】异常，msg = {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnums.PRODUCT_UPDATE_SUCCESS);
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);
    }

    /**
     * 商品编辑/新增页面
     *
     * @param productId
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        //查询商品类目
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList", categoryList);

        //如果订单编号为空，新增页面
        if (!StringUtils.isEmpty(productId)) {
            //如果订单编号不为空，回显
            ProductInfo productInfo = productInfoService.findByProductId(productId);
            if (productInfo == null) {
                log.error("【商品编辑】商品不存在,productId={}", productId);
                map.put("msg", ResultEnums.PRODUCT_NOT_EXIT.getMsg());
                map.put("url", "/sell/seller/product/list");
                return new ModelAndView("/common/error", map);
            }
            map.put("productInfo", productInfo);
        }
        return new ModelAndView("/product/index", map);
    }
}
