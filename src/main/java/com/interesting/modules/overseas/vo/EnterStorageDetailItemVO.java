package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EnterStorageDetailItemVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "来源订单id")
    private String orderDetailId;

    @ApiModelProperty(value = "仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

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

    @ApiModelProperty(value = "入库数量")
    private Integer enterNum;

    @ApiModelProperty(value = "入库单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "入库金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal enterMoney;

    @ApiModelProperty(value = "每箱毛重")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal grossWeightPerBox;

    @ApiModelProperty(value = "总毛重")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal grossWeightTotal;


    @ApiModelProperty(value = "托盘数量")
    private Integer palletNum;
}
