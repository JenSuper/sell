package com.jensuper.sell.controller;

import com.jensuper.sell.VO.ProductInfoVO;
import com.jensuper.sell.VO.ProductVO;
import com.jensuper.sell.VO.ResultVO;
import com.jensuper.sell.entity.ProductCategory;
import com.jensuper.sell.entity.ProductInfo;
import com.jensuper.sell.service.ProductCategoryService;
import com.jensuper.sell.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * localhost:8080/sell/buyer/product/list
     * @return
     */
    @GetMapping("/list")
    public ResultVO productListController() {
        // 1. 查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findByProductStatus();
        // 1.1 所有商品类目
        List<Integer> categoryTypeList = productInfoList.stream().map(m -> m.getCategoryType()).collect(Collectors.toList());
        // 2. 查询商品类目
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        // 3. 拼接数据
        // 3.1 商品返回数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory category : categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setProductName(category.getCategoryName());
            productVO.setProductType(category.getCategoryType());
            //3.2 商品类目下的商品信息
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                ProductInfoVO productInfoVO = new ProductInfoVO();
                BeanUtils.copyProperties(productInfo,productInfoVO);
                productInfoVOList.add(productInfoVO);
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        ResultVO resultVO = new ResultVO();
        resultVO.setData(productVOList);
        resultVO.setMsg("成功");
        resultVO.setCode(0);

//        ResultVO resultVO = new ResultVO();
//        ProductVO productVO = new ProductVO();
//        ProductInfoVO productInfoVO = new ProductInfoVO();
//
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//        resultVO.setData(Arrays.asList(productVO));
//        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));

        return resultVO;
    }
}
