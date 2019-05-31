package com.jensuper.sell.util;

import com.jensuper.sell.common.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * All rights Reserved, Designed By www.rongdasoft.com
 *
 * @version V1.0
 * @Title: RedisUtil
 * @Description: redis工具类
 * @author:jichao
 * @date: 2019/5/29
 * @Copyright: 2019/5/29 www.rongdasoft.com
 * Inc. All rights reserved.
 */
@Component
@Slf4j
public class RedisUtil {


    @Autowired
    private static StringRedisTemplate stringRedisTemplate;

    /**
     * 保存 key value
     * 设置过期时间为24小时
     * @param key
     * @param value
     * @return
     */
    public static boolean setValue(String key,String value) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        try {
            stringRedisTemplate.opsForValue().set(key, value, Const.REDIS_EXPIRE_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("【redis】保存数据异常", e);
            return false;
        }
        return true;
    }

    /**
     * 删除redis
     * @param key
     * @return
     */
    public static Boolean delete(String key) {
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            log.error("【reids】删除key异常", e);
            return false;
        }
        return true;
    }


}
