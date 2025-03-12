package com.interesting.modules.accmaterial.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 辅料库存用料
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccInventoryPageVO2 extends AccInventoryPageVO{
    @ApiModelProperty(value = "单据号(初始源)")
    private String receiptNum;

    @ApiModelProperty(value = "采购单价")
    private BigDecimal unitPrice;

    private String sourceId;

    private String sourceType;

    /**币种*/
    @ApiModelProperty(value = "币种")
    private String currency;
}
