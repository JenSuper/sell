package com.jensuper.sell.util;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: WxUtil
 * @Description: 微信工具类
 * @author:jichao
 * @date: 2019/5/16
 * @Copyright: 2019/5/16 www.rongdasoft.com
 * Inc. All rights reserved.
 */
public class WxUtil {

    //金额误差范围
    private static final Double MONEY_RANGE = 0.01;

    /**
     * 验证金额是否相同
     * 1. 金额不同： 相减大于0.01
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean checkMoney(Double d1, Double d2) {
        if (d1 == null || d2 == null) {
            return false;
        }

        if (d1 - d2 > MONEY_RANGE) {
            return false;
        } else {
            return true;
        }
    }
}
