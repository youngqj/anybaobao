package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SurplusOrderPageVO2 {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "包装方式单位")
    private String packagingUnitTxt;

    @ApiModelProperty(value = "原订单数量")
    private Integer originNum;

    @ApiModelProperty(value = "未出库数量")
    private Integer notExitNum;

    @ApiModelProperty(value = "已出库数量")
    private Integer yetExitNum;

    @ApiModelProperty(value = "单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "待出库金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal notExitMoney;
}
