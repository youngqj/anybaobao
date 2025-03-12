package com.interesting.modules.accmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 查询辅料库存用料dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class QueryXqAccInventoryDTO2 extends QueryXqAccInventoryDTO {
    /**订单号*/
    @ApiModelProperty(value = "初始来源单据号")
    private String receiptNum;

    /**单价*/
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

}
