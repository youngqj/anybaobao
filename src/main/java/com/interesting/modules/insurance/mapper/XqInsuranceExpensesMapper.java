package com.interesting.modules.insurance.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.insurance.entity.XqInsuranceExpenses;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.insurance.vo.XqInsuranceExpensesVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.insurance.dto.QueryXqInsuranceExpensesDTO;

/**
 * @Description: 保险费用信息
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface XqInsuranceExpensesMapper extends BaseMapper<XqInsuranceExpenses> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqInsuranceExpensesVO> pageList(@Param("page") Page<XqInsuranceExpensesVO> page, @Param("dto") QueryXqInsuranceExpensesDTO dto);
}
