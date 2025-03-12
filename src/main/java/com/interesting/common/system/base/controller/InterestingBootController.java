package com.interesting.common.system.base.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description: Controller基类
@Author: interesting-boot
 * @Date: 2019-4-21 8:13
 * @Version: 1.0
 */
@Slf4j
public class InterestingBootController<T, S extends IService<T>> {
    @Autowired
    S service;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

}
