package com.interesting.modules.accmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AddInventoryCheckDetailRecordDTO {
//    @ApiModelProperty(value = "仓库id", required = true)
//    @NotBlank(message = "仓库id不能为空")
//    private String warehouseId;

//    @ApiModelProperty("仓库名称")
//    private String warehouseName;

    @ApiModelProperty(value = "辅料id", required = true)
    @NotBlank(message = "辅料id不能为空")
    private String accessoryId;

    @ApiModelProperty("来源订单号")
    private String sourceRepNum;
//    @ApiModelProperty(value = "辅料名称")
//    private String accessoryName;

//    @ApiModelProperty(value = "辅料尺寸")
//    private String size;

//    @ApiModelProperty(value = "数量")
//    private Integer num;

    @ApiModelProperty("单价")
    @NotNull(message = "单价不能为空")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "实存数量", required = true)
    @NotNull(message = "实存数量不能为空")
    private Integer realInventory;

    @ApiModelProperty(value = "当前库存", required = true)
    @NotNull(message = "当前库存不能为空")
    private Integer currentInventory;

    @ApiModelProperty(value = "盘点数量", required = true)
    @NotNull(message = "盘点数量不能为空")
    private Integer checkInventory;


    @ApiModelProperty("币种")
    private String currency;

}
