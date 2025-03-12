package com.interesting.modules.order.dto.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

@Data
public class AddOrderCreditsInsuranceDTO {
    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("信报保险费")
    private BigDecimal creditInsurancePremium;

    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;

    @ApiModelProperty(value = "汇率转换后 信保保险费")
    private java.math.BigDecimal creditInsurancePremiumConvert;

    @ApiModelProperty(value = "投保日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date insuranceEffectiveDate;
}
