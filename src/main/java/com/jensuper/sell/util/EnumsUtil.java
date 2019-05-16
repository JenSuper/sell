package com.jensuper.sell.util;

import com.jensuper.sell.enums.CodeEnums;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: EnumsUtil
 * @Description:枚举工具类
 * @author:jichao
 * @date: 2019/5/16
 * @Copyright: 2019/5/16 www.rongdasoft.com
 * Inc. All rights reserved.
 */
public class EnumsUtil {


    /**
     * 获取枚举实体
     * 1. 获取枚举中所有实体
     * 2. 比较参数中code与实体中code，找到code相同的枚举实体
     * 备注：
     *  泛型必须指定继承实体
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnums> T getEnumsByCode(Integer code, Class<T> enumClass) {
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.getCode().equals(code)) {
                return enumConstant;
            }
        }
        return null;
    }
}
