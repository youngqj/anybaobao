package com.interesting.modules.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.customer.dto.QueryXqCustomerDTO;
import com.interesting.modules.customer.entity.XqCustomer;
import com.interesting.modules.customer.vo.CustomerIdNameAndAddressVO;
import com.interesting.modules.customer.vo.XqCustomerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 客户表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface XqCustomerMapper extends BaseMapper<XqCustomer> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqCustomerVO> pageList(@Param("page") Page<XqCustomerVO> page, @Param("dto") QueryXqCustomerDTO dto);

    List<CustomerIdNameAndAddressVO> listCustomer();

}
