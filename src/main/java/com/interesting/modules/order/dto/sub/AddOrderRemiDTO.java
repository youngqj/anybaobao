package com.interesting.modules.order.dto.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * 收汇信息
 *
 */
@Data
public class AddOrderRemiDTO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;

//    /**订单表id*/
//    @ApiModelProperty(value = "订单表id")
//    private String orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    /**收汇日期*/
    @ApiModelProperty(value = "收汇日期", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "收汇日期不能为空")
    private java.util.Date remittanceDate;

    /**收汇金额*/
    @ApiModelProperty(value = "收汇金额", required = true)
    @NotNull(message = "收汇金额不能为空")
    private java.math.BigDecimal remittanceAmount;

    /**手续费*/
    @ApiModelProperty(value = "手续费")
    private java.math.BigDecimal serviceCharge;

    /**保理利息*/
    @ApiModelProperty(value = "保理利息")
    private java.math.BigDecimal factoringInterest;

    /**保理利息*/
    @ApiModelProperty(value = "保理金额")
    private java.math.BigDecimal factoringMoney;

    @ApiModelProperty(value = "折扣")
    private java.math.BigDecimal discount;

    @ApiModelProperty(value = "质量")
    private java.math.BigDecimal quality;

    /**未收款*/
    @ApiModelProperty(value = "未收款")
    private java.math.BigDecimal outstandingReceivables;

    /**未收汇比例*/
    @ApiModelProperty(value = "未收汇比例")
    private java.math.BigDecimal outstandingRemittanceRatio;

    /**备注*/
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**信保保险费*/
    @ApiModelProperty(value = "信保保险费")
    private java.math.BigDecimal creditInsurancePremium;

    /**汇率*/
    @ApiModelProperty(value = "汇率")
    private java.math.BigDecimal exchangeRate;

    @ApiModelProperty(value = "汇率转换后 信保保险费")
    private java.math.BigDecimal creditInsurancePremiumConvert;

    /**投保日期*/
    @ApiModelProperty(value = "投保日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date insuranceEffectiveDate;


    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String currency;
}
