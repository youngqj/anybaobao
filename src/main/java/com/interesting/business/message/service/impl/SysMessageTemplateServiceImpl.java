package com.interesting.business.message.service.impl;

import com.interesting.business.message.entity.SysMessageTemplate;
import com.interesting.business.message.mapper.SysMessageTemplateMapper;
import com.interesting.business.message.service.ISysMessageTemplateService;
import com.interesting.common.system.base.service.impl.InterestingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 消息模板
 * @Author: interesting-boot
 * @Date: 2019-04-09
 * @Version: V1.0
 */
@Service
public class SysMessageTemplateServiceImpl extends InterestingServiceImpl<SysMessageTemplateMapper, SysMessageTemplate> implements ISysMessageTemplateService {

    @Autowired
    private SysMessageTemplateMapper sysMessageTemplateMapper;


    @Override
    public List<SysMessageTemplate> selectByCode(String code) {
        return sysMessageTemplateMapper.selectByCode(code);
    }
}
