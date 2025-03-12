package com.interesting.modules.commission.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.commission.dto.QueryCommissionTempDTO;
import com.interesting.modules.commission.dto.QueryCommissionTempDetailDTO;
import com.interesting.modules.commission.vo.CommissionHistoryTmpDetailsVO;
import com.interesting.modules.commission.vo.CommissionHistoryTmpVO;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.commission.entity.XqOrderCommission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.commission.vo.XqOrderCommissionVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.commission.dto.QueryXqOrderCommissionDTO;

/**
 * @Description: 佣金表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface XqOrderCommissionMapper extends BaseMapper<XqOrderCommission> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
//    IPage<XqOrderCommissionVO> pageList(@Param("page") Page<XqOrderCommissionVO> page, @Param("dto") QueryXqOrderCommissionDTO dto);

    IPage<CommissionHistoryTmpVO> pageCommissionTemp(@Param("page") Page<CommissionHistoryTmpVO> page, @Param("customerId") String customerId,@Param("dto") QueryCommissionTempDTO dto);

    IPage<CommissionHistoryTmpDetailsVO> pageCommissionTempDetails(
            @Param("page") Page<CommissionHistoryTmpDetailsVO> page,
            @Param("templateId") String templateId,
            @Param("dto") QueryCommissionTempDetailDTO dto);
}
