package com.interesting.modules.accmaterial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InstOrUpdtAccInventoryDTO {
    @ApiModelProperty(value = "编辑时传id")
    private String id;

    @ApiModelProperty("来源订单号")
    private String sourceRepNum;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty("库存数量")
    private Integer inventoryNum;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "用料时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date relativeTime;
}
