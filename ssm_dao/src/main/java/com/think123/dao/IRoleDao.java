package com.think123.dao;

import com.think123.domain.Permission;
import com.think123.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IRoleDao {
    //根据用户id查询出所有对应的角色
    @Select("select * from role where id in ( select roleId from users_role where userId=#{userId} )")
    @Results({
            @Result(id=true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column ="id" ,javaType = java.util.List.class ,many = @Many(select="com.think123.dao.IPermissionDao.findPermissionByRoleId"))
    })
    public List<Role> findRoleByUserId(String userId)throws Exception;

    @Select("select * from role")
    List<Role> findAll() throws  Exception;

    @Insert("insert into role(roleName,roleDesc)values(#{roleName},#{roleDesc})")
    void save(Role role) throws  Exception;

    @Select("select * from role where id=#{roleId}")
    Role findByUserId(String roleId);

    @Select("select * from permission where id not in (select permissionId from role_permission where roleI=#{roleId})")
    List<Permission> findOtherPermissions(String roleId);

    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId") String roleId, @Param("permissionId") String permissionId) throws  Exception;
}
