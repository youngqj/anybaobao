package com.interesting.modules.airports.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.airports.dto.AddXqAirportDTO;
import com.interesting.modules.airports.dto.EditXqAirportDTO;
import com.interesting.modules.airports.dto.QueryAirportsDTO;
import com.interesting.modules.airports.entity.XqAirport;
import com.interesting.modules.airports.mapper.XqAirportMapper;
import com.interesting.modules.airports.service.XqAirportService;
import com.interesting.modules.airports.vo.SumAirPortVO;
import com.interesting.modules.airports.vo.XqAirportListVO;
import com.interesting.modules.customer.entity.XqCustomer;
import com.interesting.modules.customer.service.IXqCustomerService;
import com.interesting.modules.supplier.entity.XqSupplier;
import com.interesting.modules.supplier.service.IXqSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class XqAirportServiceImpl extends ServiceImpl<XqAirportMapper, XqAirport> implements XqAirportService {

    @Autowired
    private IXqSupplierService xqSupplierService;
    @Autowired
    private IXqCustomerService xqCustomerService;

    @Override
    public IPage<XqAirportListVO> page(QueryAirportsDTO dto) {
        if (StringUtils.isNotBlank(dto.getGnfyApplyTimeStart()) && StringUtils.isNotBlank(dto.getGnfyApplyTimeEnd())) {
            if (dto.getGnfyApplyTimeStart().equals(dto.getGnfyApplyTimeEnd())) {
                dto.setGnfyApplyTimeStart(dto.getGnfyApplyTimeStart() + " 00:00:00");
                dto.setGnfyApplyTimeEnd(dto.getGnfyApplyTimeEnd() + " 23:59:59");
            }
        }
        if (StringUtils.isNotBlank(dto.getGwfyApplyTimeStart()) && StringUtils.isNotBlank(dto.getGwfyApplyTimeEnd())) {
            if (dto.getGwfyApplyTimeStart().equals(dto.getGwfyApplyTimeEnd())) {
                dto.setGwfyApplyTimeStart(dto.getGwfyApplyTimeStart() + " 00:00:00");
                dto.setGwfyApplyTimeEnd(dto.getGwfyApplyTimeEnd() + " 23:59:59");
            }
        }
        Page<XqAirportListVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());

        IPage<XqAirportListVO> xqAirportListVOIPage = baseMapper.page(page, dto);
        xqAirportListVOIPage.getRecords().forEach(xqAirportListVO -> {
            if (StringUtils.isNotBlank(xqAirportListVO.getFactoryName())) {
                List<String> factoryIdList = Arrays.asList(xqAirportListVO.getFactoryName().split(","));
                List<XqSupplier> xqSupplierList = xqSupplierService.listByIds(factoryIdList);
                String supplierNames = xqSupplierList.stream().map(XqSupplier::getName).collect(Collectors.joining("/"));
                xqAirportListVO.setFactoryName(supplierNames);
            }
            if (StringUtils.isNotBlank(xqAirportListVO.getCustomer())) {
                List<String> customerIdList = Arrays.asList(xqAirportListVO.getCustomer().split(","));
                List<XqCustomer> xqCustomerList = xqCustomerService.listByIds(customerIdList);
                String customerNames = xqCustomerList.stream().map(XqCustomer::getName).collect(Collectors.joining("/"));
                xqAirportListVO.setCustomer(customerNames);
            }
        });
        return xqAirportListVOIPage;
    }

    @Override
    public List<SumAirPortVO> sumAirPort(QueryAirportsDTO dto) {
        if (StringUtils.isNotBlank(dto.getGnfyApplyTimeStart()) && StringUtils.isNotBlank(dto.getGnfyApplyTimeEnd())) {
            if (dto.getGnfyApplyTimeStart().equals(dto.getGnfyApplyTimeEnd())) {
                dto.setGnfyApplyTimeStart(dto.getGnfyApplyTimeStart() + " 00:00:00");
                dto.setGnfyApplyTimeEnd(dto.getGnfyApplyTimeEnd() + " 23:59:59");
            }
        }
        if (StringUtils.isNotBlank(dto.getGwfyApplyTimeStart()) && StringUtils.isNotBlank(dto.getGwfyApplyTimeEnd())) {
            if (dto.getGwfyApplyTimeStart().equals(dto.getGwfyApplyTimeEnd())) {
                dto.setGwfyApplyTimeStart(dto.getGwfyApplyTimeStart() + " 00:00:00");
                dto.setGwfyApplyTimeEnd(dto.getGwfyApplyTimeEnd() + " 23:59:59");
            }
        }
        return baseMapper.sumAirPort(dto);
    }

    @Override
    public boolean add(AddXqAirportDTO dto) {
        XqAirport xqAirport = new XqAirport();
        BeanUtils.copyProperties(dto, xqAirport);
        return this.save(xqAirport);
    }

    @Override
    public boolean deleteByIds(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        return this.removeByIds(strings);
    }

    @Override
    public boolean edit(EditXqAirportDTO dto) {
        XqAirport xqAirport = this.getById(dto.getId());
        if (xqAirport == null) {
            throw new InterestingBootException("未找到当前空运信息");
        }

        BeanUtils.copyProperties(dto, xqAirport);
        return updateById(xqAirport);
    }
}