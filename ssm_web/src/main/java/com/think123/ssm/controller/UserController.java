package com.think123.ssm.controller;

import com.think123.domain.Role;
import com.think123.domain.UserInfo;
import com.think123.service.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @AutoWired
    private IUserService userService;
    @RequestMapping("findAll.do")
    public ModelAndView  findAll() throws Exception {
        ModelAndView mv=new ModelAndView();
        List<UserInfo>list= userService.findAll();
        mv.addObject("userList",list);
        mv.setViewName("user-list");
        return mv;
    }

    //用户添加
    @RequestMapping("/save.do")
    @PreAuthorize("authentication.principal.username=='tom'")
    public String save(UserInfo userInfo){
        userService.save(userInfo);
        return "redirect:findAll.do";
    }
    //查询指定id的用户
    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception {
        ModelAndView mv=new ModelAndView();
        UserInfo userInfo=userService.findById(id);
        mv.addObject("user",userInfo);
        mv.setViewName("user-show");
        return mv;
    }
    //查询用户,以及用户可以添加的参数
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name="id",required = true) String userId) throws Exception {
        //1.根据id查询用户
        UserInfo userInfo = userService.findById(userId);
        //2.根据id查询可以添加的角色
        List<Role> otherRoles=userService.findOtherRoles(userId);
        ModelAndView mv=new ModelAndView();
        mv.addObject("userInfo",userInfo);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }
    //给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "unserId",required = true) String userId,@RequestParam(name="ids" ,required = true) String roleIds[]) throws Exception {
        userService.addRoleToUser( userId, roleIds);
        return "redirect:findAll.do";
    }
}