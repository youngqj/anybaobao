package com.interesting.business.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.interesting.business.system.entity.SysCategory;
import com.interesting.business.system.model.TreeSelectModel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 分类字典
 * @Author: interesting-boot
 * @Date:   2019-05-29
 * @Version: V1.0
 */
@Mapper
public interface SysCategoryMapper extends BaseMapper<SysCategory> {

	/**
	  *  根据父级ID查询树节点数据
	 * @param pid
	 * @return
	 */
	public List<TreeSelectModel> queryListByPid(@Param("pid") String pid, @Param("query") Map<String, String> query);

	@Select("SELECT ID FROM sys_category WHERE CODE = #{code,jdbcType=VARCHAR}")
	public String queryIdByCode(@Param("code") String code);


}
