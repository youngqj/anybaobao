package com.interesting.modules.overseas.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.util.BeanUtils;
import com.interesting.modules.overseas.dto.InstUptExitStorageDetailItemDTO;
import com.interesting.modules.overseas.entity.XqOverseasExitDetail;
import com.interesting.modules.overseas.service.XqOverseasExitDetailService;
import com.interesting.modules.overseas.mapper.XqOverseasExitDetailMapper;
import com.interesting.modules.overseas.vo.ExitStorageDetailItemVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 26773
* @description 针对表【xq_overseas_exit_detail(海外仓-出库单子表)】的数据库操作Service实现
* @createDate 2023-07-26 15:41:53
*/
@Service
public class XqOverseasExitDetailServiceImpl extends ServiceImpl<XqOverseasExitDetailMapper, XqOverseasExitDetail>
    implements XqOverseasExitDetailService{

    @Override
    public void updateOverseaCreditsInsurance(String orderId, List<InstUptExitStorageDetailItemDTO> dto) {
        List<XqOverseasExitDetail> list = new ArrayList<>();
        for (InstUptExitStorageDetailItemDTO itemDTO : dto) {
            XqOverseasExitDetail xqOverseasExitDetail = this.getById(itemDTO.getId());
            if (xqOverseasExitDetail != null) {
                BeanUtils.copyProperties(itemDTO, xqOverseasExitDetail);
                list.add(xqOverseasExitDetail);

            }
        }
        this.updateBatchById(list);
    }
}




