package com.interesting.business.system.loca.api;

import com.alibaba.fastjson.JSONObject;
import com.interesting.common.api.CommonAPI;
import com.interesting.common.api.dto.OnlineAuthDTO;
import com.interesting.common.system.vo.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 相比较local版
 * 去掉了一些方法：
 * addLog getDatabaseType queryAllDepart
 * queryAllUser(Wrapper wrapper) queryAllUser(String[] userIds, int pageNo, int pageSize)
 * 修改了一些方法：
 * createLog
 * sendSysAnnouncement 只保留了一个，其余全部干掉
 * <p>
 * cloud接口数量43  local：35 common：9  额外一个特殊queryAllRole一个当两个用
 */
@Component
public interface ISysBaseAPI extends CommonAPI {

    /**
     * 6根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/sys/api/getUserById")
    LoginUser getUserById(@RequestParam("id") String id);

    /**
     * 7通过用户账号查询角色集合
     *
     * @param username
     * @return
     */
    @GetMapping("/sys/api/getRolesByUsername")
    List<String> getRolesByUsername(@RequestParam("username") String username);

    /**
     * 8通过用户账号查询部门集合
     *
     * @param username
     * @return 部门 id
     */
    @GetMapping("/sys/api/getDepartIdsByUsername")
    List<String> getDepartIdsByUsername(@RequestParam("username") String username);

    /**
     * 9通过用户账号查询部门 name
     *
     * @param username
     * @return 部门 name
     */
    @GetMapping("/sys/api/getDepartNamesByUsername")
    List<String> getDepartNamesByUsername(@RequestParam("username") String username);

    /**
     * 16查询表字典 支持过滤数据
     *
     * @param table
     * @param text
     * @param code
     * @param filterSql
     * @return
     */
    @GetMapping("/sys/api/queryFilterTableDictInfo")
    List<DictModel> queryFilterTableDictInfo(@RequestParam("table") String table, @RequestParam("text") String text, @RequestParam("code") String code, @RequestParam("filterSql") String filterSql);

    /**
     * 17查询指定table的 text code 获取字典，包含text和value
     *
     * @param table
     * @param text
     * @param code
     * @param keyArray
     * @return
     */
    @Deprecated
    @GetMapping("/sys/api/queryTableDictByKeys")
    public List<String> queryTableDictByKeys(@RequestParam("table") String table, @RequestParam("text") String text, @RequestParam("code") String code, @RequestParam("keyArray") String[] keyArray);

    /**
     * 18查询所有用户 返回ComboModel
     *
     * @return
     */
    @GetMapping("/sys/api/queryAllUserBackCombo")
    public List<ComboModel> queryAllUserBackCombo();


    /**
     * 20获取所有角色 带参
     * roleIds 默认选中角色
     *
     * @return
     */
    @GetMapping("/sys/api/queryAllRole")
    public List<ComboModel> queryAllRole(@RequestParam(name = "roleIds", required = false) String[] roleIds);

    /**
     * 21通过用户账号查询角色Id集合
     *
     * @param username
     * @return
     */
    @GetMapping("/sys/api/getRoleIdsByUsername")
    public List<String> getRoleIdsByUsername(@RequestParam("username") String username);

    /**
     * 22通过部门编号查询部门id
     *
     * @param orgCode
     * @return
     */
    @GetMapping("/sys/api/getDepartIdsByOrgCode")
    public String getDepartIdsByOrgCode(@RequestParam("orgCode") String orgCode);

    /**
     * 23查询所有部门
     *
     * @return
     */
    @GetMapping("/sys/api/getAllSysDepart")
    public List<SysDepartModel> getAllSysDepart();

    /**
     * 24查找父级部门
     *
     * @param departId
     * @return
     */
    @GetMapping("/sys/api/getParentDepartId")
    DictModel getParentDepartId(@RequestParam("departId") String departId);

    /**
     * 25根据部门Id获取部门负责人
     *
     * @param deptId
     * @return
     */
    @GetMapping("/sys/api/getDeptHeadByDepId")
    public List<String> getDeptHeadByDepId(@RequestParam("deptId") String deptId);


    /**
     * 27根据id获取所有参与用户
     * userIds
     *
     * @return
     */
    @GetMapping("/sys/api/queryAllUserByIds")
    public List<LoginUser> queryAllUserByIds(@RequestParam("userIds") String[] userIds);


    /**
     * 29根据name获取所有参与用户
     * userNames
     *
     * @return
     */
    @GetMapping("/sys/api/queryUserByNames")
    List<LoginUser> queryUserByNames(@RequestParam("userNames") String[] userNames);


    /**
     * 30获取用户的角色集合
     *
     * @param username
     * @return
     */
    @GetMapping("/sys/api/getUserRoleSet")
    Set<String> getUserRoleSet(@RequestParam("username") String username);

    /**
     * 31获取用户的权限集合
     *
     * @param username
     * @return
     */
    @GetMapping("/sys/api/getUserPermissionSet")
    Set<String> getUserPermissionSet(@RequestParam("username") String username);

    /**
     * 32判断是否有online访问的权限
     *
     * @param onlineAuthDTO
     * @return
     */
    @PostMapping("/sys/api/hasOnlineAuth")
    boolean hasOnlineAuth(@RequestBody OnlineAuthDTO onlineAuthDTO);

    /**
     * 33通过部门id获取部门全部信息
     */
    @GetMapping("/sys/api/selectAllById")
    SysDepartModel selectAllById(@RequestParam("id") String id);

    /**
     * 34根据用户id查询用户所属公司下所有用户ids
     *
     * @param userId
     * @return
     */
    @GetMapping("/sys/api/queryDeptUsersByUserId")
    List<String> queryDeptUsersByUserId(@RequestParam("userId") String userId);


    //---

    /**
     * 35查询用户角色信息
     *
     * @param username
     * @return
     */
    @Override
    @GetMapping("/sys/api/queryUserRoles")
    Set<String> queryUserRoles(@RequestParam("username") String username);

    /**
     * 36查询用户权限信息
     *
     * @param username
     * @return
     */
    @Override
    @GetMapping("/sys/api/queryUserAuths")
    Set<String> queryUserAuths(@RequestParam("username") String username);

    /**
     * 37根据 id 查询数据库中存储的 DynamicDataSourceModel
     *
     * @param dbSourceId
     * @return
     */
    @Override
    @GetMapping("/sys/api/getDynamicDbSourceById")
    DynamicDataSourceModel getDynamicDbSourceById(@RequestParam("dbSourceId") String dbSourceId);

    /**
     * 38根据 code 查询数据库中存储的 DynamicDataSourceModel
     *
     * @param dbSourceCode
     * @return
     */
    @Override
    @GetMapping("/sys/api/getDynamicDbSourceByCode")
    DynamicDataSourceModel getDynamicDbSourceByCode(@RequestParam("dbSourceCode") String dbSourceCode);

    /**
     * 39根据用户账号查询用户信息 CommonAPI中定义
     *
     * @param username
     */
    @Override
    @GetMapping("/sys/api/getUserByName")
    LoginUser getUserByName(@RequestParam("username") String username);

    /**
     * 40字典表的 翻译
     *
     * @param table
     * @param text
     * @param code
     * @param key
     * @return
     */
    @Override
    @GetMapping("/sys/api/translateDictFromTable")
    String translateDictFromTable(@RequestParam("table") String table, @RequestParam("text") String text, @RequestParam("code") String code, @RequestParam("key") String key);

    /**
     * 41普通字典的翻译
     *
     * @param code
     * @param key
     * @return
     */
    @Override
    @GetMapping("/sys/api/translateDict")
    String translateDict(@RequestParam("code") String code, @RequestParam("key") String key);

    /**
     * 42查询数据权限
     *
     * @return
     */
    @Override
    @GetMapping("/sys/api/queryPermissionDataRule")
    List<SysPermissionDataRuleModel> queryPermissionDataRule(@RequestParam("component") String component, @RequestParam("requestPath") String requestPath, @RequestParam("username") String username);

    /**
     * 43查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    @GetMapping("/sys/api/getCacheUser")
    SysUserCacheInfo getCacheUser(@RequestParam("username") String username);

    /**
     * 36根据多个用户账号(逗号分隔)，查询返回多个用户信息
     *
     * @param usernames
     * @return
     */
    @GetMapping("/sys/api/queryUsersByUsernames")
    List<JSONObject> queryUsersByUsernames(String usernames);

    /**
     * 37根据多个用户ID(逗号分隔)，查询返回多个用户信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/sys/api/queryUsersByIds")
    List<JSONObject> queryUsersByIds(String ids);

    /**
     * 38根据多个部门编码(逗号分隔)，查询返回多个部门信息
     *
     * @param orgCodes
     * @return
     */
    @GetMapping("/sys/api/queryDepartsByOrgcodes")
    List<JSONObject> queryDepartsByOrgcodes(String orgCodes);

    /**
     * 39根据多个部门编码(逗号分隔)，查询返回多个部门信息
     *
     * @param ids
     * @return
     */
    @GetMapping("/sys/api/queryDepartsByOrgIds")
    List<JSONObject> queryDepartsByOrgIds(String ids);


    /**
     * 41 获取公司下级部门和公司下所有用户id
     *
     * @param orgCode
     */
    @GetMapping("/sys/api/getDeptUserByOrgCode")
    List<Map> getDeptUserByOrgCode(@RequestParam("orgCode") String orgCode);
}
