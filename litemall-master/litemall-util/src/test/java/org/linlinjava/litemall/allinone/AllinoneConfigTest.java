package org.linlinjava.litemall.allinone;


import cn.hutool.core.date.LocalDateTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.util.service.DyAddressService;

import org.linlinjava.litemall.util.service.DyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;


//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AllinoneConfigTest {




    private final DyTaskService dyTaskService = new DyTaskService();





    @Test
    public void test(){
        DyAddressService dyAddressService = new DyAddressService();
        DyTaskService dyTaskService1 = new DyTaskService();
        try {
            dyTaskService1.taskPush("2024-08-15 10:40:00","2","2","123",26);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}























//    }


