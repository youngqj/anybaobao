package com.interesting.modules.commission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.modules.commission.entity.XqCommissionDistribution;
import com.interesting.modules.commission.mapper.XqCommissionDistributionMapper;
import com.interesting.modules.commission.service.XqCommissionDistributionService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class XqCommissionDistributionServiceImpl extends ServiceImpl<XqCommissionDistributionMapper, XqCommissionDistribution>
        implements XqCommissionDistributionService {
}
