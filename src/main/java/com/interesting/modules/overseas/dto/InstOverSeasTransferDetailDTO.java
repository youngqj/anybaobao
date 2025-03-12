package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class InstOverSeasTransferDetailDTO {
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

    @ApiModelProperty(value = "来源仓库id", hidden = true)
    private String sourceWarehouseId;

    @ApiModelProperty(value = "转移仓库id", required = true)
    private String transferWarehouseId;

    @ApiModelProperty(value = "当前库存", required = true)
    private Integer currentInventory;

    @ApiModelProperty(value = "转移库存", required = true)
    @NotNull(message = "转移库存不能为空")
    private Integer transferInventory;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "新仓库lot", required = true)
    @NotBlank(message = "新仓库lot不能为空")
    private String newWarehouseLot;
}
