package org.linlinjava.litemall.wx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.domain.LitemallGoodsDyPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class BigDecimalTest {

    @Test
    public void test() {

        List<LitemallGoodsDyPrice> litemallGoodsDyPrice = new ArrayList<>();

        for (int i = 1; i <= 24; i++) {
            LitemallGoodsDyPrice litemallGoodsDyPrice1 = new LitemallGoodsDyPrice();
            litemallGoodsDyPrice1.setSegment(Integer.toString(i));
            litemallGoodsDyPrice1.setIntegral(new BigDecimal(26));
            if ( i == 21 ){
                litemallGoodsDyPrice1.setIntegral(new BigDecimal(40));
            }
            litemallGoodsDyPrice.add(litemallGoodsDyPrice1);
        }


            Integer duration =2;

            int startHour =  20;

            BigDecimal integral= new BigDecimal(0);

            for (int i = 0; i < duration; i++) {
                int hour = (startHour + i)%25;
                if (hour == 0){ hour++;}
                for (LitemallGoodsDyPrice goodsDyPrice : litemallGoodsDyPrice) {
                    if (goodsDyPrice.getSegment().equals(Integer.toString(hour))) {
                        integral = integral.add(goodsDyPrice.getIntegral());
                        break;
                    }
                }
                System.out.println(hour+","+integral.toString());
            }
            System.out.println(integral.divide(new BigDecimal(duration)));
    }
}
