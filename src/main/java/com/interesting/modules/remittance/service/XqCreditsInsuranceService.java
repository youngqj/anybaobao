package com.interesting.modules.remittance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.order.dto.sub.AddOrderCreditsInsuranceDTO;
import com.interesting.modules.order.dto.sub.AddOrderProdDTO;
import com.interesting.modules.order.dto.sub.AddOrderRemiExpDateDTO;
import com.interesting.modules.remittance.entity.XqCreditsInsurance;

import java.util.List;

public interface XqCreditsInsuranceService extends IService<XqCreditsInsurance> {

    void updateCreditsInsurance(String id, List<AddOrderCreditsInsuranceDTO> creditsInsuranceDTOS);

}
