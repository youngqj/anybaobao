package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class RelativeInventoryLotPageVO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /**订单号*/
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    /**产品信息*/
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**产品信息*/
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**产品信息*/
    @ApiModelProperty(value = "总数量")
    private Integer totalNum;

    /**产品信息*/
    @ApiModelProperty(value = "已发数量")
    private Integer yetNum;

    /**产品信息*/
    @ApiModelProperty(value = "剩余数量")
    private Integer surplusNum;

    @ApiModelProperty(value = "包装方式单位")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "包装方式单位")
    private String packagingUnitTxt;

    @ApiModelProperty(value = "单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal unitPrice;
}
