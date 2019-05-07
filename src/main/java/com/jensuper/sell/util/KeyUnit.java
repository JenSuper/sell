package com.jensuper.sell.util;

import java.util.Random;

/**
 * 数据库字段生成
 */
public class KeyUnit {

    /**
     * 唯一键生成
     * 1. 随机数+两位随机数
     * 2. 需要对方法加同步锁，避免并发情况下导致订单id重复
     * @return
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        return random.nextInt(90) + 10+String.valueOf(System.currentTimeMillis());
    }
}
