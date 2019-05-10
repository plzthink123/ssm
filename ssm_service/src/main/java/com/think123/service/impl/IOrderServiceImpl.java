package com.think123.service.impl;

import com.github.pagehelper.PageHelper;
import com.think123.dao.IOrdersDao;
import com.think123.domain.Orders;
import com.think123.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class IOrderServiceImpl implements IOrderService {
    private IOrdersDao ordersDao;
    @Override
    public List<Orders> findAll(int page,int size) throws Exception {
        //pageNum是页码值,pageSize是每页显示条数
        PageHelper.startPage(page,size);
        return ordersDao.findAll();
    }

    @Override
    public Orders findById(String ordersId) throws Exception {
        return ordersDao.findById(ordersId);
    }
}