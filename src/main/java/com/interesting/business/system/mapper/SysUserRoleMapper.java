package com.interesting.business.system.mapper;

import java.util.List;

import com.interesting.business.system.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.interesting.business.system.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author: interesting-boot
 * @since 2018-12-21
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("select role_code from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<String> getRoleByUserName(@Param("username") String username);

    @Select("select role_code from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<Integer> getRoleNumsByUserName(@Param("username") String username);

    @Select("select id from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<String> getRoleIdByUserName(@Param("username") String username);


    /**
     * 根据用户id获取角色列表
     *
     * @param id
     * @return
     */
    @Select("select sys_role.* from sys_role left join sys_user_role on sys_role.id = sys_user_role.role_id where sys_user_role.user_id = #{id}")
    List<SysRole> getRoleByUserId(String id);
}
