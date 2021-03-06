package com.think123.service.impl;

import com.think123.dao.IRoleDao;
import com.think123.domain.Permission;
import com.think123.domain.Role;
import com.think123.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDao roleDao;
    @Override
    public List<Role> findAll() throws Exception{
        return roleDao.findAll();
    }

    @Override
    public void save(Role role)  throws Exception{
        roleDao.save(role);
    }

    @Override
    public Role findById(String roleId) throws Exception {
        return roleDao.findByUserId(roleId);
    }

    @Override
    public List<Permission> findOtherPermission(String roleId) throws Exception {
        return roleDao.findOtherPermissions(roleId);
    }

    @Override
    public void addPermissionToRole(String roleId, String[] permissionIds) throws Exception {
        for(String permissionId :permissionIds){
            roleDao.addPermissionToRole(roleId,permissionId);
        }
    }
}