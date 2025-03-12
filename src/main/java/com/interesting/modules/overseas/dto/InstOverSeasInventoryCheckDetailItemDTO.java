package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class InstOverSeasInventoryCheckDetailItemDTO {
    private String id;

    @ApiModelProperty(value = "产品id", required = true)
    @NotBlank(message = "产品id不能为空")
    private String productId;

    @ApiModelProperty(value = "初始来源单据号", required = true)
    //  @NotBlank(message = "初始来源单据号不能为空")
    private String sourceRepNum;

    @ApiModelProperty(value = "仓库lot", required = true)
    @NotBlank(message = "仓库lot不能为空")
    private String warehouseLot;

    @ApiModelProperty(hidden = true)
    private String warehouseId;

    @ApiModelProperty(value = "实存数量", required = true)
    @NotNull(message = "实存数量不能为空")
    private Integer realInventory;

    @ApiModelProperty(value = "当前库存", required = true)
    @NotNull(message = "当前库存不能为空")
    private Integer currentInventory;

    @ApiModelProperty(value = "盘点数量", required = true)
    @NotNull(message = "盘点数量不能为空")
    private Integer checkInventory;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
}
