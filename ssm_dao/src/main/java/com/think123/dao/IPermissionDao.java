package com.think123.dao;

import com.think123.domain.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPermissionDao {
    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{roleId})")
    public List<Permission> findPermissionByRoleId(String roleId);

    @Select("select * rom permission")
    List<Permission> findAll() throws  Exception;

    @Select("insert into permission(permissionName,url) values (#{permissionName},#{url})")
    void save(Permission permission)throws  Exception;
}
