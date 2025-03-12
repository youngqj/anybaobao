package com.interesting.modules.overseas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.entity.XqOverseasEnterHead;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.overseas.vo.*;

/**
* @author 26773
* @description 针对表【xq_overseas_enter_head(海外仓-入库单主表)】的数据库操作Service
* @createDate 2023-07-25 11:13:58
*/
public interface XqOverseasEnterHeadService extends IService<XqOverseasEnterHead> {

    IPage<EnterStoragePageVO> pageEnterStorage(QueryPageEnterStorageDTO dto);

    EnterStorageDetailVO getEnterStorage(String id);

    boolean addEnterStorage(InstUptEnterStorageDTO dto);

    boolean deleteEnterStorage(String ids);

    boolean editEnterStorage(InstUptEnterStorageDTO dto);

    IPage<RelativeOrderPageVO> pageRelativeOrder(QueryPageRelativeOrderDTO dto);

    IPage<SurplusOrderPageVO> pageSurplusOrder(QueryPageSurplusOrderDTO dto);

    boolean editOrderStatus(EditEnterOrderStatusDTO dto);

    IPage<EnterStorageDetailPageVO> pageEnterStorageDetail(QueryPageEnterStorageDetailDTO dto);
}
