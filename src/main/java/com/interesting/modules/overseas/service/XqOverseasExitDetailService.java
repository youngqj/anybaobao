package com.interesting.modules.overseas.service;

import com.interesting.modules.overseas.dto.InstUptExitStorageDetailItemDTO;
import com.interesting.modules.overseas.entity.XqOverseasExitDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.overseas.vo.ExitStorageDetailItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_overseas_exit_detail(海外仓-出库单子表)】的数据库操作Service
* @createDate 2023-07-26 15:41:53
*/
public interface XqOverseasExitDetailService extends IService<XqOverseasExitDetail> {

    void updateOverseaCreditsInsurance(String orderId, List<InstUptExitStorageDetailItemDTO> dto);

}
