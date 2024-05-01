package org.linlinjava.litemall.db.service;


//import cn.hutool.core.date.DateUnit;
//import cn.hutool.core.date.DateUtil;
import org.linlinjava.litemall.db.dao.LitemallGoodsDyPriceMapper;

import org.linlinjava.litemall.db.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LitemallGoodsDyService {

    @Resource
    LitemallGoodsDyPriceMapper litemallGoodsdypriceMapper;


    public void addDyProductPrice(LitemallGoodsDyPrice litemallGoodsdyprice) {
        litemallGoodsdyprice.setAddTime(LocalDateTime.now());
        litemallGoodsdyprice.setUpdateTime(LocalDateTime.now());
        litemallGoodsdypriceMapper.insert(litemallGoodsdyprice);
    }
    public void updateDyProductPrice(LitemallGoodsDyPrice litemallGoodsdyprice) {
        litemallGoodsdyprice.setUpdateTime(LocalDateTime.now());
        litemallGoodsdypriceMapper.insert(litemallGoodsdyprice);
    }

    public void deleteByGid(Integer gid) {
        LitemallGoodsDyPriceExample example = new LitemallGoodsDyPriceExample();
        example.or().andGoodsIdEqualTo(gid);
        litemallGoodsdypriceMapper.deleteByExample(example);
    }

    public List<LitemallGoodsDyPrice> queryByGid(Integer goodsId) {
        LitemallGoodsDyPriceExample example = new LitemallGoodsDyPriceExample();
        example.or().andGoodsIdEqualTo(goodsId);
        return litemallGoodsdypriceMapper.selectByExample(example);
    }



    public BigDecimal countPrice(List<LitemallGoodsDyPrice>  litemallGoodsDyPrice,LocalDateTime date, Integer duration){


        int startHour =  date.getHour();;

//        System.out.println(startHour);

        BigDecimal durationPrice = new BigDecimal(0);

        for (int i = 0; i < duration; i++) {
            int hour = (startHour + i)%24;
            if (hour == 0){ hour++;}
            for (LitemallGoodsDyPrice goodsDyPrice : litemallGoodsDyPrice) {
                if (goodsDyPrice.getSegment().equals(Integer.toString(hour))) {
                    durationPrice = durationPrice.add(goodsDyPrice.getPrice());
                    break;
                }
            }
        }
       return  durationPrice;
    }

    public Integer countIntegral(List<LitemallGoodsDyPrice>  litemallGoodsDyPrice,LocalDateTime date, Integer duration){


        int startHour =  date.getHour();

        Integer integral= 0;

        for (int i = 0; i < duration; i++) {
            int hour = (startHour + i)%24;
            if (hour == 0){ hour++;}
            for (LitemallGoodsDyPrice goodsDyPrice : litemallGoodsDyPrice) {
                if (goodsDyPrice.getSegment().equals(Integer.toString(hour))) {
                    integral = integral+goodsDyPrice.getIntegral();
                    break;
                }
            }
        }
        return integral/duration;
    }







}
