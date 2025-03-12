package com.interesting.business.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.interesting.business.system.entity.SysDataLog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface SysDataLogMapper extends BaseMapper<SysDataLog>{
	/**
	 * 通过表名及数据Id获取最大版本
	 * @param tableName
	 * @param dataId
	 * @return
	 */
	public String queryMaxDataVer(@Param("tableName") String tableName, @Param("dataId") String dataId);

}
