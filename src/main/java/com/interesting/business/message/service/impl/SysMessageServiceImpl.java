package com.interesting.business.message.service.impl;

import com.interesting.business.message.entity.SysMessage;
import com.interesting.business.message.mapper.SysMessageMapper;
import com.interesting.business.message.service.ISysMessageService;
import com.interesting.common.system.base.service.impl.InterestingServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description: 消息
 * @Author: interesting-boot
 * @Date: 2019-04-09
 * @Version: V1.0
 */
@Service
public class SysMessageServiceImpl extends InterestingServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {

}
