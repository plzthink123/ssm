package com.think123.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.think123.domain.Orders;
import com.think123.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @AutoWired
    private IOrderService orderService;
    //查询全部订单,未分页
  /*  @RequestMapping("findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv=new ModelAndView();
        List<Orders> ordersListist = orderService.findAll();
        mv.addObject("ordersList",ordersListist);
        mv.setViewName("orders-list");
        return mv;
    }*/
  @RequestMapping("findAll.do")
  public ModelAndView findAll(@RequestParam(name="page",required = true,defaultValue = "1")Integer page,@RequestParam(name="size",required = true,defaultValue = "5") Integer size) throws Exception {
      ModelAndView mv=new ModelAndView();
      List<Orders> ordersList = orderService.findAll(page, size);
      //pageInfo就是一个分页的bean
      PageInfo pageInfo=new PageInfo(ordersList);
      mv.addObject("pageInfo",pageInfo);
      mv.setViewName("orders-page-list");
      return mv;
  }
  @RequestMapping("/findById.do")
  public ModelAndView findById(@RequestParam(name = "id",required = true)String ordersId) throws Exception {
      ModelAndView mv=new ModelAndView();
      Orders orders=orderService.findById(ordersId);
      mv.addObject("orders",orders);
      mv.setViewName("orders-show");
      return mv;
  }
}