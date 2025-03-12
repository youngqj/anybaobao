package com.interesting.modules.overseas.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.overseas.dto.QueryPageSurplusOrderDTO;
import com.interesting.modules.overseas.entity.XqOverseasEnterDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.overseas.vo.EnterStorageDetailItemVO;
import com.interesting.modules.overseas.vo.SurplusOrderPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_overseas_enter_detail(海外仓-入库单主表)】的数据库操作Mapper
* @createDate 2023-07-25 11:24:02
* @Entity com.interesting.modules.overseas.entity.XqOverseasEnterDetail
*/
@Mapper
public interface XqOverseasEnterDetailMapper extends BaseMapper<XqOverseasEnterDetail> {

    List<EnterStorageDetailItemVO> listDetailsById(@Param("id") String id);

    boolean removeBySourceId(@Param("sourceId") String sourceId);

    IPage<SurplusOrderPageVO> pageSurplusOrder(@Param("page") Page<SurplusOrderPageVO> page,
                                               @Param("dto") QueryPageSurplusOrderDTO dto);
}




