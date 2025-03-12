package com.interesting.modules.overseas.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.overseas.dto.QueryPageEnterStorageDTO;
import com.interesting.modules.overseas.dto.QueryPageEnterStorageDetailDTO;
import com.interesting.modules.overseas.entity.XqOverseasEnterHead;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.overseas.vo.EnterStorageDetailPageVO;
import com.interesting.modules.overseas.vo.EnterStoragePageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 26773
* @description 针对表【xq_overseas_enter_head(海外仓-入库单主表)】的数据库操作Mapper
* @createDate 2023-07-25 11:13:58
* @Entity com.interesting.modules.overseas.entity.XqOverseasEnterHead
*/
@Mapper
public interface XqOverseasEnterHeadMapper extends BaseMapper<XqOverseasEnterHead> {

    IPage<EnterStoragePageVO> pageEnterStorage(@Param("page") Page<EnterStoragePageVO> page,
                                               @Param("dto") QueryPageEnterStorageDTO dto);

    IPage<EnterStorageDetailPageVO> pageEnterStorageDetail(@Param("page") Page<EnterStorageDetailPageVO> page,
                                                           @Param("dto") QueryPageEnterStorageDetailDTO dto);
}




