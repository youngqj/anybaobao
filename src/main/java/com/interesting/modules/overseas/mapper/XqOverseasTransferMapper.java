package com.interesting.modules.overseas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.overseas.dto.QueryPageOverSeasInventoryCheckDTO;
import com.interesting.modules.overseas.dto.QueryPageOverSeasInventoryTransferDTO;
import com.interesting.modules.overseas.entity.XqOverseasTransfer;
import com.interesting.modules.overseas.vo.OverSeasInventoryCheckPageVO;
import com.interesting.modules.overseas.vo.OverSeasInventoryTransferPageVO;
import org.apache.ibatis.annotations.Param;

public interface XqOverseasTransferMapper extends BaseMapper<XqOverseasTransfer> {

    IPage<OverSeasInventoryTransferPageVO> pageInventoryTransfer(@Param("page") Page<OverSeasInventoryTransferPageVO> page,
                                                                 @Param("dto") QueryPageOverSeasInventoryTransferDTO dto);
}
