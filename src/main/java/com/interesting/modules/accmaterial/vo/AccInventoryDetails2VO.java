package com.interesting.modules.accmaterial.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccInventoryDetails2VO {
    @ApiModelProperty(value = "编辑时传id")
    private String id;

    @ApiModelProperty("来源记录id")
    private String sourceId;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("辅料id")
    private String accessoryId;

    @ApiModelProperty("辅料名称")
    private String accessoryName;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "来源类型")
    private String sourceTypeTxt;

    @ApiModelProperty(value = "来源类型(字典)")
    private String sourceType;

    @ApiModelProperty("辅料分类id")
    private String categoryId;

    @ApiModelProperty("辅料分类名称")
    private String categoryName;

    @ApiModelProperty(value = "相关时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date relativeTime;

    @ApiModelProperty("订单号(来源号)")
    private String orderNum;

    @ApiModelProperty("单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unitPrice;

    @ApiModelProperty("订单号(初始号)")
    private String orderNum2;

    @ApiModelProperty("币种")
    private String currency;
}
