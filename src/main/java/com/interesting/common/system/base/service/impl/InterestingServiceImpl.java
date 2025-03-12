package com.interesting.common.system.base.service.impl;

//import com.interesting.common.system.base.entity.InterestingEntity;
//import com.interesting.common.system.base.service.InterestingService;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.system.base.entity.InterestingEntity;
import com.interesting.common.system.base.service.InterestingService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: ServiceImpl基类
@Author: interesting-boot
 * @Date: 2019-4-21 8:13
 * @Version: 1.0
 */
@Slf4j
public class InterestingServiceImpl<M extends BaseMapper<T>, T extends InterestingEntity> extends ServiceImpl<M, T> implements InterestingService<T> {

}
