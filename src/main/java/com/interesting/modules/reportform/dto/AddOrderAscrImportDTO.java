package com.interesting.modules.reportform.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddOrderAscrImportDTO {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "辅料供应商")
    private String supplierName;

    /**
     * 采购合同号
     */
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;

    @ApiModelProperty(value = "订单号")
    private String orderNum;
    /**
     * 辅料名称
     */
    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    /**
     * 尺寸
     */
    @ApiModelProperty(value = "尺寸")
    private String size;

    @ApiModelProperty("材质规格")
    private String materialSpecification;

    /**
     * 订单数量
     */
    @ApiModelProperty(value = "订单数量")
    private Integer orderQuantity;

    @ApiModelProperty("用料数量")
    private Integer useNum;

    @ApiModelProperty("退料数量")
    private Integer backNum;

    @ApiModelProperty("币种")
    private String currency;

    @ApiModelProperty("辅料分类")
    private String categoryName;
    /**
     * 采购单价
     */
    @ApiModelProperty(value = "采购单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal unitPrice;
    /**
     * 采购金额
     */
    @ApiModelProperty(value = "采购金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal purchaseAmount;
    /**
     * 税率
     */
    @ApiModelProperty(value = "税率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal taxRate;
    /**
     * 含税金额
     */
    @ApiModelProperty(value = "含税金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal taxIncludedAmount;

}
