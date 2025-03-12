package com.interesting.modules.accmaterial.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class InventoryCheckDetailRecordVO {
    private String id;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "辅料id")
    private String accessoryId;

    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    @ApiModelProperty(value = "辅料尺寸")
    private String size;

    @ApiModelProperty(value = "材质规格")
    private String materialSpecification;

    @ApiModelProperty(value = "实存数量")
    @NotNull(message = "实存数量不能为空")
    private Integer realInventory;

    @ApiModelProperty(value = "当前库存")
    private Integer currentInventory;

    @ApiModelProperty(value = "单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "盘点数量")
    private Integer checkInventory;

    @ApiModelProperty("初始来源订单号")
    private String sourceRepNum;

    @ApiModelProperty("币种")
    private String currency;
}
