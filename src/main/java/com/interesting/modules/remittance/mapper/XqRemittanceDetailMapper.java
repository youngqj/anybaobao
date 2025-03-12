package com.interesting.modules.remittance.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.remittance.entity.XqRemittanceDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.remittance.vo.XqRemittanceDetailVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.remittance.dto.QueryXqRemittanceDetailDTO;

/**
 * @Description: 收汇情况表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface XqRemittanceDetailMapper extends BaseMapper<XqRemittanceDetail> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqRemittanceDetailVO> pageList(@Param("page") Page<XqRemittanceDetailVO> page, @Param("dto") QueryXqRemittanceDetailDTO dto);
}
