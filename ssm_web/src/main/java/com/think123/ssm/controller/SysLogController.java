package com.think123.ssm.controller;

import com.think123.domain.SysLog;
import com.think123.service.ISysLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {
    @AutoWired
    private ISysLogService sysLogService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv=new ModelAndView();
        List<SysLog> sysLogList=sysLogService.findAll();
        mv.addObject("sysLogs",sysLogList);
        mv.setViewName("sysLog-list");
        return mv;
    }
}