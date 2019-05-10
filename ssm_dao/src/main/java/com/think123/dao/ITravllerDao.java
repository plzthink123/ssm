package com.think123.dao;

import com.think123.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ITravllerDao {
    @Select("select * from travller where id in ( select travllerId from order_travller where orderId=#{ordersId})")
    public List<Traveller> findByOrdersId(String odersId) throws Exception;
}
