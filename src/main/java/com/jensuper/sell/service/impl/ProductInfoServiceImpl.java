package com.jensuper.sell.service.impl;

import com.jensuper.sell.dto.CarDTO;
import com.jensuper.sell.enums.ResultEnums;
import com.jensuper.sell.enums.ProductStatusEnums;
import com.jensuper.sell.entity.ProductInfo;
import com.jensuper.sell.exception.SellException;
import com.jensuper.sell.repository.ProductInfoRepository;
import com.jensuper.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
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
    public Page<ProductInfo> findProductAll(Pageable pageable) {
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

    /**
     * 商品上架
     * 1. 查询商品是否存在
     * 2. 判断商品状态是否为上架
     * 3. 修改商品状态
     * 4. 保存商品
     * @param orderId
     * @return
     */
    @Override
    public ProductInfo onSale(String orderId) {
        ProductInfo productInfo = productInfoRepository.findOne(orderId);
        if (productInfo == null) {
            log.error("【商品上架】查询不到订单,orderId = {}",orderId);
            throw new SellException(ResultEnums.PRODUCT_NOT_EXIT);
        }
        //判断商品状态
        if (productInfo.getProductStatus().equals(ProductStatusEnums.UP.getCode())) {
            log.error("【商品上架】商品状态异常，orderId = {}",orderId);
            throw new SellException(ResultEnums.PRODUCT_STATUS_ERRO);
        }
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());
        return productInfoRepository.save(productInfo);
    }

    /**
     * 商品下架
     * @param orderId
     * @return
     */
    @Override
    public ProductInfo offSale(String orderId) {
        ProductInfo productInfo = productInfoRepository.findOne(orderId);
        if (productInfo == null) {
            log.error("【商品下架】查询不到订单,orderId = {}",orderId);
            throw new SellException(ResultEnums.PRODUCT_NOT_EXIT);
        }
        //判断商品状态
        if (productInfo.getProductStatus().equals(ProductStatusEnums.DOWN.getCode())) {
            log.error("【商品下架】商品状态异常，orderId = {}",orderId);
            throw new SellException(ResultEnums.PRODUCT_STATUS_ERRO);
        }
        productInfo.setProductStatus(ProductStatusEnums.DOWN.getCode());
        return productInfoRepository.save(productInfo);
    }
}
