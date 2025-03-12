package com.interesting.modules.overseas.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.entity.XqOverseasExitHead;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.overseas.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author 26773
* @description 针对表【xq_overseas_exit_head(海外仓-出库单子表)】的数据库操作Service
* @createDate 2023-07-26 15:41:42
*/
public interface XqOverseasExitHeadService extends IService<XqOverseasExitHead> {

    IPage<RelativeOrderPageVO> pageRelativeOrder(QueryPageRelativeOrderDTO dto);

    IPage<ExitStoragePageVO> pageExitStorage(QueryPageExitStorageDTO dto);

    ExitStorageDetailVO getExitStorage(String id);

    boolean addExitStorage(InstUptExitStorageDTO dto);

    boolean deleteExitStorage(String ids);


    boolean editExitStorage(InstUptExitStorageDTO dto);

    IPage<SurplusOrderPageVO2> pageSurplusOrder(QueryPageSurplusOrderDTO dto);

    IPage<RelativeInventoryLotPageVO> pageRelativeLot(QueryPageRelativeLotDTO dto);

    boolean editOrderStatus(EditEnterOrderStatusDTO dto);

    IPage<ExitStorageDetailPageVO> pageExitStorageDetail(QueryPageExitStorageDetailDTO dto);

    void exitStorageDetailExport(QueryPageExitStorageDetailDTO dto, Page<ExitStorageDetailPageVO> page, HttpServletResponse response);

    List<InstUptExitStorageDetailItemDTO> exitStorageDetailList(String orderNum);

    List<SumRemmitAmountVO> sumRemmitAmount(QueryPageExitStorageDetailDTO dto);
}
