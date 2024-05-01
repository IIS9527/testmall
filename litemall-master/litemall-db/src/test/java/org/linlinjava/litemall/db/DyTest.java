package org.linlinjava.litemall.db;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.dao.LitemallGoodsDyPriceMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsDyPrice;
import org.linlinjava.litemall.db.domain.LitemallGoodsDyPriceExample;
import org.linlinjava.litemall.db.service.LitemallGoodsDyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;
//
//@WebAppConfiguration
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class DyTest {
//
//    @Autowired
//    LitemallGoodsDyService litemallGoodsDyService;
//
//    @Resource
//    LitemallGoodsDyPriceMapper litemallGoodsDyPriceMapper;
//
//    @Test
//    public  void  test(){
//        LitemallGoodsDyPriceExample example = new LitemallGoodsDyPriceExample();
//
//        example.or().andGoodsIdEqualTo(1181013);
//
//        List<LitemallGoodsDyPrice> litemallGoodsDyPrices = litemallGoodsDyPriceMapper.selectByExample(example);
//
//        System.out.println(litemallGoodsDyPrices);
//
//
//        System.out.println( "sss"+litemallGoodsDyService.countPrice( litemallGoodsDyPrices ,3,"2000-1-1 00:00:00",20));
//
//
//
//    }
//}
