package org.linlinjava.litemall.allinone;


import cn.hutool.core.date.LocalDateTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.util.service.DyAddressService;

import org.linlinjava.litemall.util.service.DyTaskService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;


//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class AllinoneConfigTest {


    private final DyAddressService dyAddressService =new DyAddressService();

    private final DyTaskService dyTaskService = new DyTaskService();





    @Test
    public void test(){
        try {
            System.out.println( dyAddressService.getXiGuaName("7363084478077586213"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @Test
//    public void test2(){
//        System.out.println(LocalDateTime.now());
//        System.out.println("ssss"+dyTaskService.taskPush(LocalDateTime.now().toString(),"8","9","11111111111","ffff","asdasdasd",6));
//    }
























    }


