package com.interesting.modules.orderdetail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Description: 订单产品明细
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Data
@TableName("xq_order_detail")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_order_detail对象", description="订单产品明细")
public class XqOrderDetail {

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
	private String id;
	/**订单表id*/
    @ApiModelProperty(value = "订单表id")
	private String orderId;
	/**产品名称*/
    @ApiModelProperty(value = "产品名称")
	private String productId;

    /**
     * 产品品类
     */
    @ApiModelProperty(value = "产品品类")
    private String productCategory;

    /**
     * 产品规格
     */
    @ApiModelProperty(value = "产品规格")
    private String productSpecs;
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
	private String packaging;
    @ApiModelProperty(value = "等级")
    private String productGrade;
	@ApiModelProperty(value = "包装方式单位id")
	private String packagingUnit;
	/**每箱重量*/
    @ApiModelProperty(value = "每箱重量")
	private java.math.BigDecimal weightPerBox;
	@ApiModelProperty(value = "每箱重量单位id")
	private java.math.BigDecimal weightPerBoxUnit;
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
    /**
     * 明细汇率
     */
    @ApiModelProperty(value = "明细汇率")
    private java.math.BigDecimal detailExchangeRate;
	/**特殊情况备注*/
    @ApiModelProperty(value = "特殊情况备注")
	private String specialNotes;

	/**版面要求*/
	@ApiModelProperty(value = "版面要求")
	private String layoutRequirements;

	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新时间*/
	@ApiModelProperty(value = "更新时间")
	private java.util.Date updateTime;
	/**逻辑删除 0否 1是*/
	@ApiModelProperty(value = "逻辑删除 0否 1是")
	private Integer izDelete;

	@ApiModelProperty("币种")
	private String currency;

}
