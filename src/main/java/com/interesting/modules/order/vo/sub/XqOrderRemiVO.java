package com.interesting.modules.order.vo.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XqOrderRemiVO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**订单表id*/
    @ApiModelProperty(value = "订单表id")
    private String orderId;

    @ApiModelProperty("订单号")
    private String orderNum;

    /**收汇日期*/
    @ApiModelProperty(value = "收汇日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date remittanceDate;
    /**收汇金额*/
    @ApiModelProperty(value = "收汇金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal remittanceAmount;
    /**手续费*/
    @ApiModelProperty(value = "手续费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal serviceCharge;
    /**保理利息*/
    @ApiModelProperty(value = "保理利息")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal factoringInterest;
    /**保理利息*/
    @ApiModelProperty(value = "保理金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal factoringMoney;

    @ApiModelProperty(value = "折扣")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal discount;

    @ApiModelProperty(value = "质量")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal quality;

    /**未收款*/
    @ApiModelProperty(value = "未收款")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal outstandingReceivables;

    /**未收汇比例*/
    @ApiModelProperty(value = "未收汇比例")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal outstandingRemittanceRatio;
    /**备注*/
    @ApiModelProperty(value = "备注")
    private String remarks;
    /**信保保险费*/
    @ApiModelProperty(value = "信保保险费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal creditInsurancePremium;
    /**汇率*/
    @ApiModelProperty(value = "汇率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal exchangeRate;

    @ApiModelProperty(value = "汇率转换后 信保保险费")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal creditInsurancePremiumConvert;

    /**投保日期*/
    @ApiModelProperty(value = "投保日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date insuranceEffectiveDate;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String currency;
}
