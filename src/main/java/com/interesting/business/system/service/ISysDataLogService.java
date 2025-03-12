package com.interesting.business.system.service;

import com.interesting.business.system.entity.SysDataLog;

import com.baomidou.mybatisplus.extension.service.IService;

public interface ISysDataLogService extends IService<SysDataLog> {

	/**
	 * 添加数据日志
	 * @param tableName
	 * @param dataId
	 * @param dataContent
	 */
	public void addDataLog(String tableName, String dataId, String dataContent);

}
