package com.interesting.business.message.service;

import com.interesting.business.message.entity.SysMessageTemplate;
import com.interesting.common.system.base.service.InterestingService;

import java.util.List;

/**
 * @Description: 消息模板
 * @Author: interesting-boot
 * @Date: 2019-04-09
 * @Version: V1.0
 */
public interface ISysMessageTemplateService extends InterestingService<SysMessageTemplate> {
    List<SysMessageTemplate> selectByCode(String code);
}
