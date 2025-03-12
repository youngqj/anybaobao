package com.interesting.modules.overseas.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.overseas.dto.InstUptExitStorageDetailItemDTO;
import com.interesting.modules.overseas.dto.QueryPageExitStorageDTO;
import com.interesting.modules.overseas.dto.QueryPageExitStorageDetailDTO;
import com.interesting.modules.overseas.dto.QueryPageRelativeLotDTO;
import com.interesting.modules.overseas.entity.XqOverseasExitHead;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.overseas.vo.ExitStorageDetailPageVO;
import com.interesting.modules.overseas.vo.ExitStoragePageVO;
import com.interesting.modules.overseas.vo.RelativeInventoryLotPageVO;
import com.interesting.modules.overseas.vo.SumRemmitAmountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 26773
 * @description 针对表【xq_overseas_exit_head(海外仓-出库单子表)】的数据库操作Mapper
 * @createDate 2023-07-26 15:41:42
 * @Entity com.interesting.modules.overseas.entity.XqOverseasExitHead
 */
public interface XqOverseasExitHeadMapper extends BaseMapper<XqOverseasExitHead> {

    IPage<ExitStoragePageVO> pageExitStorage(@Param("page") Page<ExitStoragePageVO> page,
                                             @Param("dto") QueryPageExitStorageDTO dto);

    IPage<RelativeInventoryLotPageVO> pageRelativeLot(@Param("page") Page<RelativeInventoryLotPageVO> page,
                                                      @Param("dto") QueryPageRelativeLotDTO dto);


    IPage<ExitStorageDetailPageVO> pageExitStorageDetail(@Param("page") Page<ExitStorageDetailPageVO> page,
                                                         @Param("dto") QueryPageExitStorageDetailDTO dto);


    List<InstUptExitStorageDetailItemDTO> exitStorageDetailList(@Param("orderNum") String orderNum);

    List<SumRemmitAmountVO> sumRemmitAmount(@Param("dto") QueryPageExitStorageDetailDTO dto);
}




