package com.jensuper.sell.service.impl;

import com.jensuper.sell.dto.CarDTO;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.enums.ProductStatusEnums;
import com.jensuper.sell.entity.ProductInfo;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.repository.ProductInfoRepository;
import com.jensuper.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;


    /**
     * 根据商品id查询商品
     *
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findByProductId(String productId) {
        return productInfoRepository.findOne(productId);
    }

    /**
     * 查询上架商品
     *
     * @return
     */
    @Override
    public List<ProductInfo> findByProductStatus() {
        return productInfoRepository.findByProductStatus(ProductStatusEnums.UP.getCode());
    }

    /**
     * 分页查询商品
     *
     * @return
     */
    @Override
    public Page<ProductInfo> findProductAll() {
        Pageable pageable = new PageRequest(0, 2);
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo saveProduct(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    /**
     * 加库存
     *
     * @param carDTOList
     */
    @Override
    @Transactional
    public void increaseStock(List<CarDTO> carDTOList) {
        for (CarDTO carDTO : carDTOList) {
            //1. 查询商品详情
            ProductInfo productInfo = productInfoRepository.findOne(carDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIT);
            }
            //2. 修改商品数量（数据库数量+购物车数量）
            Integer num = carDTO.getAmount() + productInfo.getProductStock();
            productInfo.setProductStock(num);
            productInfoRepository.save(productInfo);
        }
    }

    /**
     * 减库存
     *
     * @param carDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CarDTO> carDTOList) {
        /* 1. 遍历购物车中所有商品 */
        for (CarDTO carDTO : carDTOList) {
            //1.1 查询商品信息
            ProductInfo productInfo = productInfoRepository.findOne(carDTO.getProductId());
            if (productInfo == null) {//商品不存在，抛出异常
                new SellException(ResultEnums.PRODUCT_NOT_EXIT);
            }

            //1.2 判断库存是否足够:如果小于0，则抛出库存不足异常
            Integer num = productInfo.getProductStock() - carDTO.getAmount();
            if (num < 0) {
                new SellException(ResultEnums.PRODUCT_STOCK_ERRO);
            }

            //1.3 更新商品库存
            productInfo.setProductStock(num);
            productInfoRepository.save(productInfo);
        }
    }
}
