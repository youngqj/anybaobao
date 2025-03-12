package com.interesting.modules.freightcost.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.freightcost.vo.XqFreightCostInfoVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.freightcost.dto.QueryXqFreightCostInfoDTO;

/**
 * @Description: 货运费用信息
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface XqFreightCostInfoMapper extends BaseMapper<XqFreightCostInfo> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqFreightCostInfoVO> pageList(@Param("page") Page<XqFreightCostInfoVO> page, @Param("dto") QueryXqFreightCostInfoDTO dto);

    XqFreightCostInfo getByType(@Param("orderId") String orderId, @Param("fee_type") String fee_type);

    XqFreightCostInfo getByType1(@Param("orderId") String orderId, @Param("fee_type") String fee_type);
}
