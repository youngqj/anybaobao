package com.interesting.business.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.interesting.business.system.entity.SysTenant;
import com.interesting.business.system.mapper.SysTenantMapper;
import com.interesting.business.system.service.ISysTenantService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {


}
