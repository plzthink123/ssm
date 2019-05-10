package com.think123.dao;

import com.think123.domain.Member;
import com.think123.domain.Orders;
import com.think123.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface IOrdersDao {

    @Select("select * from orders")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderNum" ,column = "orderNum"),
            @Result(property = "orderTime" ,column = "orderTime"),
            @Result(property = "orderStatus" ,column = "orderTimeStr"),
            @Result(property = "peopleCount" ,column = "orderTimeStr"),
            @Result(property = "payType" ,column = "orderTimeStr"),
            @Result(property = "orderDesc" ,column = "orderTimeStr"),
            @Result(property = "product" ,column = "productId",javaType = Product.class,one = @One(select="com.think123.dao.IProductDao.findById")),
    })
    public List<Orders> findAll() throws  Exception;

    @Select("select * from orders where id=#{ordersId}")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "orderNum" ,column = "orderNum"),
            @Result(property = "orderTime" ,column = "orderTime"),
            @Result(property = "orderStatus" ,column = "orderTimeStr"),
            @Result(property = "peopleCount" ,column = "orderTimeStr"),
            @Result(property = "payType" ,column = "orderTimeStr"),
            @Result(property = "orderDesc" ,column = "orderTimeStr"),
            @Result(property = "product" ,column = "productId",javaType = Product.class,one = @One(select="com.think123.dao.IProductDao.findById")),
            @Result(property = "member",column = "memberId",javaType = Member.class,one = @One(select="com.think213.dao.IMemberDao.findById")),
            @Result(property = "traveller", column ="id",javaType = java.util.List.class,many = @Many(select="com.think123.dao.ITravllerDao.findByOrdersId"))
    })
    public  Orders findById(String ordersId)throws Exception;
}