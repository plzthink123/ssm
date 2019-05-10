package com.think123.ssm.controller;

import com.think123.domain.Permission;
import com.think123.service.IPermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @AutoWired
    private IPermissionService permissionService;
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws  Exception{
        ModelAndView mv=new ModelAndView();
        List<Permission> all = permissionService.findAll();
        mv.addObject("permissionList",all);
        mv.setViewName("permission-show");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(Permission permission) throws Exception {
        permissionService.save(permission);
        return "redirect:findAll.do";
    }
}