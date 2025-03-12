package com.interesting.business.system.service;

import java.util.List;

import com.interesting.business.system.entity.SysPermission;
import com.interesting.business.system.model.TreeModel;
import com.interesting.common.exception.InterestingBootException;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author: interesting-boot
 * @since 2018-12-21
 */
public interface ISysPermissionService extends IService<SysPermission> {

    public List<TreeModel> queryListByParentId(String parentId);

    /**
     * 真实删除
     */
    public void deletePermission(String id) throws InterestingBootException;

    /**
     * 逻辑删除
     */
    public void deletePermissionLogical(String id) throws InterestingBootException;

    public void addPermission(SysPermission sysPermission) throws InterestingBootException;

    public void editPermission(SysPermission sysPermission) throws InterestingBootException;

    public List<SysPermission> queryByUser(String username);

    /**
     * 根据permissionId删除其关联的SysPermissionDataRule表中的数据
     *
     * @param id
     * @return
     */
    public void deletePermRuleByPermId(String id);

    /**
     * 查询出带有特殊符号的菜单地址的集合
     *
     * @return
     */
    public List<String> queryPermissionUrlWithStar();

    /**
     * 判断用户否拥有权限
     *
     * @param username
     * @param sysPermission
     * @return
     */
    public boolean hasPermission(String username, SysPermission sysPermission);

    /**
     * 根据用户和请求地址判断是否有此权限
     *
     * @param username
     * @param url
     * @return
     */
    public boolean hasPermission(String username, String url);

    /**
     * 根据用户名获取父菜单
     *
     * @param username
     * @return
     */
    List<SysPermission> queryParentByUser(String username);

    /**
     * 根据用户名和父菜单id获取 目录
     *
     * @param username
     * @param parentId
     * @return
     */
    List<SysPermission> queryByUserIdAndParentId(String username, String parentId);
}
