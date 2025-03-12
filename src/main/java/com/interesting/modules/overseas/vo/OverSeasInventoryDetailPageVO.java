package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OverSeasInventoryDetailPageVO {
    private String id;

    @ApiModelProperty("单据编号")
    private String receiptNum;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("仓库lot")
    private String warehouseLot;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "相关时间")
    private Date relativeTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "来源类型（字典）")
    private String sourceType;

    @ApiModelProperty(value = "来源类型")
    private String sourceTypeTxt;

    @ApiModelProperty(value = "初始来源单据号")
    private String sourceRepNum;

}
