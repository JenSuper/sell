package com.jensuper;

import org.junit.Test;

import java.util.Random;

public class TestDemo {
    @Test
    public void test() {
        Random random = new Random();
        int i = random.nextInt(90) + 10;
        int i2 = random.nextInt(90);
        System.out.println(i);
        System.out.println(i2);
    }
}
