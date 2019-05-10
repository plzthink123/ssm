package com.think123.service;

import com.think123.domain.Permission;

import java.util.List;

public interface IPermissionService  {
    public List<Permission> findAll() throws  Exception;

    void save(Permission permission) throws  Exception;
}
