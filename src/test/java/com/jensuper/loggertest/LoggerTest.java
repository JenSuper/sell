package com.jensuper.loggertest;


import com.sun.glass.ui.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
//@Slf4j
public class LoggerTest {

    private Logger logger = LoggerFactory.getLogger(LoggerTest.class);
    @Test
    public void test() {
        String name = "张三";
        String age = "12";
        logger.info("name : {},age : {}",name,age);
        logger.info("info----------------------");
        logger.debug("debug-----------------");
        logger.error("erro-------------------");
    }
}
