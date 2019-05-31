package com.jensuper.sell.common;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: Const
 * @Description: 常量
 * @author:jichao
 * @date: 2019/5/31
 * @Copyright: 2019/5/31 www.rongdasoft.com
 * Inc. All rights reserved.
 */
public class Const {

    /* ---------------------------------- redis ---------------------------------- */
    /**
     * token前缀
     */
    public static final String REDIS_KEY_PREFF = "token_%s";

    /**
     * 过期时间  一天 单位：秒
     */
    public static final long REDIS_EXPIRE_TIME = 86400 * 7;

    /* ---------------------------------- cookie ---------------------------------- */
    /**
     * cookie token
     */
    public static final String COOKIE_TOKEN = "token";

    /**
     * cookie 过期时间 一天 单位：秒
     */
    public static final Integer COOKIE_EXPIRE_TIME = 86400 * 7;

}
