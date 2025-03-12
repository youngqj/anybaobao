package com.interesting.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.interesting.base.mapper.BaseCommonMapper;
import com.interesting.base.service.BaseCommonService;
import com.interesting.common.api.dto.SysLog;
import com.interesting.common.system.vo.LoginUser;
import com.interesting.common.util.IPUtils;
import com.interesting.common.util.SpringContextUtils;
import com.interesting.common.util.oConvertUtils;
import com.interesting.config.FilterContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@Slf4j
public class BaseCommonServiceImpl implements BaseCommonService {

    @Resource
    private BaseCommonMapper baseCommonMapper;

    @Override
    public void addLog(SysLog sysLog) {
        if(oConvertUtils.isEmpty(sysLog.getId())){
            sysLog.setId(String.valueOf(IdWorker.getId()));
        }
        //保存日志（异常捕获处理，防止数据太大存储失败，导致业务失败）JT-238
        try {
            baseCommonMapper.insert(sysLog);
        } catch (Exception e) {
            log.warn(" LogContent length : "+ sysLog.getLogContent().length());
            log.warn(e.getMessage());
        }
    }

    @Override
    public void addLog(String logContent, Integer logType, Integer operatetype, LoginUser user) {
        SysLog sysLog = new SysLog();
        sysLog.setId(String.valueOf(IdWorker.getId()));
        //注解上的描述,操作日志内容
        sysLog.setLogContent(logContent);
        sysLog.setLogType(logType);
        sysLog.setOperateType(operatetype);
        try {
            //获取request
            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
            //设置IP地址
            sysLog.setIp(IPUtils.getIpAddr(request));
        } catch (Exception e) {
            sysLog.setIp("127.0.0.1");
        }
        //获取登录用户信息
        if(user==null){
            try {
                user = FilterContextHandler.getUserInfo();
            } catch (Exception e) {
            }
        }
        if(user!=null){
            sysLog.setUserid(user.getId());
            sysLog.setUsername(user.getRealname());
        }
        sysLog.setCreateTime(new Date());
        //保存日志（异常捕获处理，防止数据太大存储失败，导致业务失败）JT-238
        try {
            baseCommonMapper.insert(sysLog);
        } catch (Exception e) {
            log.warn(" LogContent length : "+sysLog.getLogContent().length());
            log.warn(e.getMessage());
        }
    }

    @Override
    public void addLog(String logContent, Integer logType, Integer operateType) {
        addLog(logContent, logType, operateType, null);
    }



}
