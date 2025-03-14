package com.interesting.business.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.entity.SysLog;
import com.interesting.business.system.loca.api.ISysBaseAPI;
import com.interesting.business.system.mapper.SysLogMapper;
import com.interesting.business.system.service.ISysLogService;
import com.interesting.common.util.CommonUtils;
import com.interesting.modules.syslog.dto.QueryPageSysLogDTO;
import com.interesting.modules.syslog.vo.SysLogPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @Author Gcc
 * @since 2018-12-26
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

	@Resource
	private SysLogMapper sysLogMapper;
	@Autowired
	private ISysBaseAPI sysBaseAPI;

	/**
	 * @功能：清空所有日志记录
	 */
	@Override
	public void removeAll() {
		sysLogMapper.removeAll();
	}

	@Override
	public Long findTotalVisitCount() {
		return sysLogMapper.findTotalVisitCount();
	}

	//update-begin--Author:Gcc  Date:20190428 for：传入开始时间，结束时间参数
	@Override
	public Long findTodayVisitCount(Date dayStart, Date dayEnd) {
		return sysLogMapper.findTodayVisitCount(dayStart,dayEnd);
	}

	@Override
	public Long findTodayIp(Date dayStart, Date dayEnd) {
		return sysLogMapper.findTodayIp(dayStart,dayEnd);
	}
	//update-end--Author:Gcc  Date:20190428 for：传入开始时间，结束时间参数

	@Override
	public List<Map<String,Object>> findVisitCount(Date dayStart, Date dayEnd) {
		String dbType = CommonUtils.getDatabaseType();
		return sysLogMapper.findVisitCount(dayStart, dayEnd,dbType);
	}

    @Override
    public IPage<SysLogPageVO> pageSysLog(QueryPageSysLogDTO dto) {
		Page<SysLogPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
		return this.baseMapper.pageSysLog(page, dto);
	}
}
