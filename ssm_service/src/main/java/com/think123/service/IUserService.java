package com.think123.service;

import com.think123.domain.Role;
import com.think123.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    List<UserInfo> findAll() throws  Exception;

    void save(UserInfo userInfo);

    UserInfo findById(String id)throws  Exception;

    List<Role> findOtherRoles(String userId) throws  Exception;

    void addRoleToUser(String userId, String[] roleIds) throws Exception;
}
