package com.interesting.business.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.business.system.entity.SysDepart;
import com.interesting.business.system.model.DepartIdModel;
import com.interesting.business.system.model.SysDepartTreeModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * <p>
 *
 * @Author:Steve
 * @Since： 2019-01-22
 */
public interface ISysDepartService extends IService<SysDepart> {

    /**
     * 查询我的部门信息,并分节点进行显示
     *
     * @return
     */
    List<SysDepartTreeModel> queryMyDeptTreeList(String departIds);

    /**
     * 查询所有部门信息,并分节点进行显示
     *
     * @param enterpriseInfoId
     * @return
     */
    List<SysDepartTreeModel> queryTreeList(String enterpriseInfoId);

    /**
     * 查询所有部门信息,并分节点进行显示
     *
     * @return
     */
    List<SysDepartTreeModel> queryTreeList();



    /**
     * 保存部门数据
     *
     * @param sysDepart
     */
    void saveDepartData(SysDepart sysDepart, String username);

    /**
     * 更新depart数据
     *
     * @param sysDepart
     * @return
     */
    Boolean updateDepartDataById(SysDepart sysDepart, String username);

    /**
     * 删除depart数据
     * @param id
     * @return
     */
    /* boolean removeDepartDataById(String id); */

    /**
     * 根据关键字搜索相关的部门数据
     *
     * @param keyWord
     * @return
     */
    List<SysDepartTreeModel> searhBy(String keyWord, String myDeptSearch, String departIds);

    /**
     * 根据部门id删除并删除其可能存在的子级部门
     *
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 查询SysDepart集合
     *
     * @param userId
     * @return
     */
    public List<SysDepart> queryUserDeparts(String userId);

    /**
     * 根据用户名查询部门
     *
     * @param username
     * @return
     */
    List<SysDepart> queryDepartsByUsername(String username);

    /**
     * 根据部门id批量删除并删除其可能存在的子级部门
     *
     * @param id
     * @return
     */
    void deleteBatchWithChildren(List<String> ids);

    /**
     * 根据部门Id查询,当前和下级所有部门IDS
     *
     * @param departId
     * @return
     */
    List<String> getSubDepIdsByDepId(String departId);

    /**
     * 获取我的部门下级所有部门IDS
     *
     * @return
     */
    List<String> getMySubDepIdsByDepId(String departIds);

    /**
     * 根据关键字获取部门信息（通讯录）
     *
     * @return
     */
    List<SysDepartTreeModel> queryTreeByKeyWord(String keyWord);

    /**
     * 获取我的部门下级所有部门
     *
     * @return
     */
    List<SysDepartTreeModel> queryTreeListByPid(String parentId);

    /**
     * 获取公司信息
     *
     * @return
     */
    SysDepart queryCompByOrgCode(String orgCode);

    /**
     * 获取下级部门
     *
     * @return
     */
    List<SysDepart> queryDeptByPid(String pid);

    /**
     * 根据 id批量删除
     *
     * @param idList
     * @param enterpriseInfoId
     */
    void deleteBatchWithChildren(Collection<? extends Serializable> idList, String enterpriseInfoId);

    /**
     * 根据公司id获取 请求树
     * @param enterpriseInfoId
     * @return
     */
    List<DepartIdModel> queryDepartIdTreeList(String enterpriseInfoId);


    /**
     * 根据企业名称获取 部门
     * @param id
     * @return
     */
    SysDepart getByEnterpriseId(String id);
}
