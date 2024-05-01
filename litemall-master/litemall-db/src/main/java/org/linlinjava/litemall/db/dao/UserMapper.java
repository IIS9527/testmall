package org.linlinjava.litemall.db.dao;




import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface UserMapper {


    int addUserIntegral(@Param("id") Integer id, @Param("integral") BigDecimal integral);


    int reduceUserIntegral(@Param("id") Integer id,@Param("integral") BigDecimal integral);


}
