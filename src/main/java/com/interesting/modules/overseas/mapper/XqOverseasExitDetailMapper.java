package com.interesting.modules.overseas.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.overseas.dto.QueryPageSurplusOrderDTO;
import com.interesting.modules.overseas.entity.XqOverseasExitDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.overseas.vo.ExitStorageDetailItemVO;
import com.interesting.modules.overseas.vo.SurplusOrderPageVO2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
* @author 26773
* @description 针对表【xq_overseas_exit_detail(海外仓-出库单子表)】的数据库操作Mapper
* @createDate 2023-07-26 15:41:53
* @Entity com.interesting.modules.overseas.entity.XqOverseasExitDetail
*/
@Mapper
public interface XqOverseasExitDetailMapper extends BaseMapper<XqOverseasExitDetail> {

    List<ExitStorageDetailItemVO> listDetailsById(@Param("id") String id);

    boolean removeBySourceId(@Param("id") String id);

    IPage<SurplusOrderPageVO2> pageSurplusOrder(@Param("page") Page<SurplusOrderPageVO2> page,
                                                @Param("dto") QueryPageSurplusOrderDTO dto);


    BigDecimal getPriceByCustomerNo(@Param("customerNo") String customerNo, @Param("orderNum") String orderNum);
}




