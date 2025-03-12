package com.interesting.modules.orderdetail.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddXqOrderDetailDTO {
    	/**主键*/
        @ApiModelProperty(value = "主键")
    	private String id;
    	/**订单表id*/
        @ApiModelProperty(value = "订单表id")
    	private String orderId;
    	/**产品名称*/
        @ApiModelProperty(value = "产品名称")
    	private String productName;
    	/**产品英文名称*/
        @ApiModelProperty(value = "产品英文名称")
    	private String productNameEn;
    	/**国内HS编码*/
        @ApiModelProperty(value = "国内HS编码")
    	private String hsCodeDomestic;
    	/**国外HS编码*/
        @ApiModelProperty(value = "国外HS编码")
    	private String hsCodeForeign;
    	/**国外关税率*/
        @ApiModelProperty(value = "国外关税率")
    	private String foreignTariff;
    	/**包装方式*/
        @ApiModelProperty(value = "包装方式")
    	private BigDecimal packaging;
    	/**每箱重量*/
        @ApiModelProperty(value = "每箱重量")
    	private java.math.BigDecimal weightPerBox;
    	/**总箱数*/
        @ApiModelProperty(value = "总箱数")
    	private Integer totalBoxes;
    	/**总重*/
        @ApiModelProperty(value = "总重")
    	private java.math.BigDecimal totalWeight;
    	/**单价/箱*/
        @ApiModelProperty(value = "单价/箱")
    	private java.math.BigDecimal pricePerBox;
    	/**单价/磅*/
        @ApiModelProperty(value = "单价/磅")
    	private java.math.BigDecimal pricePerLb;
    	/**销售金额*/
        @ApiModelProperty(value = "销售金额")
    	private java.math.BigDecimal salesAmount;
    	/**特殊情况备注*/
        @ApiModelProperty(value = "特殊情况备注")
    	private String specialNotes;
}