package com.think123.ssm.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.think123.domain.Permission;
import com.think123.domain.Role;
import com.think123.service.IRoleService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @AutoWired
    private IRoleService roleService;

    @RequestMapping("/findAll.do")
    @RolesAllowed("ADMIN")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv=new ModelAndView();
        List<Role> list = roleService.findAll();
        mv.addObject("roleList",list);
        mv.setViewName("role-list");
        return mv;
    }

    @RequestMapping("/save.do")
    @Secured("ROLE_ADMIN")
    public String save(Role role) throws Exception {
        roleService.save(role);
        return "redirect:findAll.do";
    }
    //根据rouleId查询所有可以添加的权限
    @RequestMapping("/findUserByIdAndAllPermission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findUserByIdAndAllPermission(@RequestParam(name="id",required = true) String roleId) throws Exception {
        Role role = roleService.findById(roleId);
        //查找别的permission
        List<Permission> permissions=roleService.findOtherPermission(roleId);
        ModelAndView mv=new ModelAndView();
        mv.addObject("role",role);
        mv.addObject("permissionList",permissions);
        mv.setViewName("role-permission-add");
        return mv;
    }
    //给角色添加权限
    @RequestMapping("/addPermissionToRole")
    public String  addPermissionToRole(@RequestParam(name = "roleId",required = true)String roleId,@RequestParam(name="ids",required = true)String []permissionIds ) throws Exception {
        roleService.addPermissionToRole(roleId,permissionIds);
        return "redirect:findAll.do";
    }

}