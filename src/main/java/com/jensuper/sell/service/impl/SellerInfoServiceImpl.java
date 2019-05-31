package com.jensuper.sell.service.impl;

import com.jensuper.sell.entity.SellerInfo;
import com.jensuper.sell.repository.SellerInfoRepository;
import com.jensuper.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: SellerInfoServiceImpl
 * @Description:
 * @author:jichao
 * @date: 2019/5/29
 * @Copyright: 2019/5/29 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepository repository;

    /**
     *
     * @param openId
     * @return
     */
    @Override
    public SellerInfo findByOpenid(String openId) {
        return repository.findByOpenid(openId);
    }
}
