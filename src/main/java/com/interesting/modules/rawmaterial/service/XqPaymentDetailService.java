package com.interesting.modules.rawmaterial.service;

import com.interesting.modules.rawmaterial.dto.AddWithholdDTO;
import com.interesting.modules.rawmaterial.dto.InstOrUpdtPaymentDetailDTO;
import com.interesting.modules.rawmaterial.entity.XqPaymentDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_payment_detail】的数据库操作Service
* @createDate 2023-06-29 14:14:22
*/
public interface XqPaymentDetailService extends IService<XqPaymentDetail> {

    void updatePaymentDetails(String id, List<InstOrUpdtPaymentDetailDTO> paymentDetails);
    void updateWithholdDetails(String sourceId, List<AddWithholdDTO> instOrUpdtPaymentDetailDTOS);
}
