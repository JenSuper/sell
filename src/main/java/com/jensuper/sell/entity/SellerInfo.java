package com.jensuper.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: SellerInfo
 * @Description:卖家信息实体类
 * @author:jichao
 * @date: 2019/5/29
 * @Copyright: 2019/5/29 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Data
@Entity
public class SellerInfo {
    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
}
