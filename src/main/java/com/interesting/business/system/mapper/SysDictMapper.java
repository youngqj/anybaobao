package com.interesting.business.system.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.business.system.entity.SysDict;
import com.interesting.business.system.model.DuplicateCheckVo;
import com.interesting.business.system.model.TreeSelectModel;
import com.interesting.common.system.vo.DictModel;
import com.interesting.common.system.vo.DictQuery;
import org.apache.ibatis.annotations.*;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @Author Gcc
 * @since 2018-12-28
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 重复检查SQL
     *
     * @return
     */
    public Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo);

    public Long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo);

    public List<DictModel> queryDictItemsByCode(@Param("code") String code);

    @MapKey("value")
    public Map<String, DictModel> queryDictItemsMapByCode(@Param("code") String code);

    @Deprecated
    public List<DictModel> queryTableDictItemsByCode(@Param("table") String table, @Param("text") String text, @Param("code") String code);

    @Deprecated
    public List<DictModel> queryTableDictItemsByCodeAndFilter(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("filterSql") String filterSql);

    @Deprecated
    @Select("select ${key} as \"label\",${value} as \"value\" from ${table}")
    public List<Map<String, String>> getDictByTableNgAlain(@Param("table") String table, @Param("key") String key, @Param("value") String value);

    public String queryDictTextByKey(@Param("code") String code, @Param("key") String key);

    @Deprecated
    public String queryTableDictTextByKey(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("key") String key);

    @Deprecated
    public List<DictModel> queryTableDictByKeys(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keyArray") String[] keyArray);

    /**
     * 查询所有部门 作为字典信息 id -->value,departName -->text
     *
     * @return
     */
    public List<DictModel> queryAllDepartBackDictModel();

    /**
     * 查询所有用户  作为字典信息 username -->value,realname -->text
     *
     * @return
     */
    public List<DictModel> queryAllUserBackDictModel();

    /**
     * 通过关键字查询出字典表
     *
     * @param table
     * @param text
     * @param code
     * @param keyword
     * @return
     */
    @Deprecated
    public List<DictModel> queryTableDictItems(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keyword") String keyword);


    /**
     * 通过关键字查询出字典表
     *
     * @param page
     * @param table
     * @param text
     * @param code
     * @param keyword
     * @return
     */
    IPage<DictModel> queryTableDictItems(Page<DictModel> page, @Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keyword") String keyword);

    /**
     * 根据表名、显示字段名、存储字段名 查询树
     *
     * @param table
     * @param text
     * @param code
     * @param pid
     * @param hasChildField
     * @return
     */
    @Deprecated
    List<TreeSelectModel> queryTreeList(@Param("query") Map<String, String> query, @Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("pidField") String pidField, @Param("pid") String pid, @Param("hasChildField") String hasChildField);

    /**
     * 删除
     *
     * @param id
     */
    @Select("delete from sys_dict where id = #{id}")
    public void deleteOneById(@Param("id") String id);

    /**
     * 查询被逻辑删除的数据
     *
     * @return
     */
    @Select("select * from sys_dict where iz_delete = 1")
    public List<SysDict> queryDeleteList();

    /**
     * 修改状态值
     *
     * @param izDelete
     * @param id
     */
    @Update("update sys_dict set iz_delete = #{flag,jdbcType=INTEGER} where id = #{id,jdbcType=VARCHAR}")
    public void updateDictDelFlag(@Param("flag") int izDelete, @Param("id") String id);


    /**
     * 分页查询字典表数据
     *
     * @param page
     * @param query
     * @return
     */
    @Deprecated
    public Page<DictModel> queryDictTablePageList(Page page, @Param("query") DictQuery query);
}
