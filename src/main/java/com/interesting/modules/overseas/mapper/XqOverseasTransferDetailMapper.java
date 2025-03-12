package com.interesting.modules.overseas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.overseas.entity.XqOverseasTransferDetail;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferDetailRecordVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface XqOverseasTransferDetailMapper extends BaseMapper<XqOverseasTransferDetail> {
    List<OverSeasInventoryTransferDetailRecordVO> getInventoryTransferDetListById(@Param("id") String id);
}
