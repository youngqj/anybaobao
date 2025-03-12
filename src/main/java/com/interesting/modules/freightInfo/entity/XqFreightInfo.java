package com.interesting.modules.freightInfo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.interesting.common.annotation.Excel;
import com.sun.javafx.css.Declaration;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description: 货运信息表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Data
@TableName("xq_freight_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_freight_info对象", description="货运信息表")
public class XqFreightInfo {

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private String id;
	/**订单id*/
    @ApiModelProperty(value = "订单id")
	private String orderId;
	/**船公司*/
    @ApiModelProperty(value = "船公司")
	private String shipCompany;
	/**船名航次*/
    @ApiModelProperty(value = "船名航次")
	private String voyageNumber;
	/**货代公司*/
    @ApiModelProperty(value = "货代公司")
	private String forwarderCompany;
	/**货代联系人*/
    @ApiModelProperty(value = "货代联系人")
	private String forwarderContact;
	/**(预计离开时间)Estimate Time of Departure*/
    @ApiModelProperty(value = "(预计离开时间)Estimate Time of Departure")
	private java.util.Date etd;
	/**(预计到达时间)Estimate Time of Arrival*/
    @ApiModelProperty(value = "(预计到达时间)Estimate Time of Arrival")
	private java.util.Date eta;
	/**提单号码*/
    @ApiModelProperty(value = "提单号码")
	private String billOfLading;
	/**货柜号码*/
    @ApiModelProperty(value = "货柜号码")
	private String containerNo;
	/**封铅号*/
    @ApiModelProperty(value = "封铅号")
	private String sealNo;
	/**报检备注*/
    @ApiModelProperty(value = "报检备注")
	private String inspectionNote;
	/**报检提交日期*/
    @ApiModelProperty(value = "报检提交日期")
	private java.util.Date inspectionSubmitDate;
	/**ISF收到回执时间*/
    @ApiModelProperty(value = "ISF收到回执时间")
	private java.util.Date isfReceiptTime;
	/**提箱时间*/
    @ApiModelProperty(value = "提箱时间")
	private java.util.Date pickUpDate;
	/**装柜时间*/
    @ApiModelProperty(value = "装柜时间")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private java.util.Date loadingDate;
	/**入港时间*/
    @ApiModelProperty(value = "入港时间")
	private java.util.Date arrivalDate;
	/**国外空返时间*/
    @ApiModelProperty(value = "国外空返时间")
	private java.util.Date foreignAirReturnDate;
	/**装运港*/
    @ApiModelProperty(value = "装运港")
	private String loadingPort;
	/**目的港*/
    @ApiModelProperty(value = "目的港")
	private String destinationPort;
	/**净重*/
    @ApiModelProperty(value = "净重")
	private java.math.BigDecimal netWeight;
	/**毛重*/
    @ApiModelProperty(value = "毛重")
	private java.math.BigDecimal grossWeight;
	/**体积*/
    @ApiModelProperty(value = "体积")
	private java.math.BigDecimal volume;
	/**集装箱温度/通风口*/
    @ApiModelProperty(value = "集装箱温度/通风口")
	private String containerTemperature;

	@ApiModelProperty(value = "海关放行时间")
	private Date customsClearanceTime;

	@ApiModelProperty(value = "国外提箱时间")
	private Date foreignPickTime;

	@ApiModelProperty(value = "退运时间")
	private Date returnCargoTime;

	@ApiModelProperty(value = "到岗时间")
	private Date arrivePortTime;

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

	@ApiModelProperty("出口报关单号码")
	private String exportDeclarationNum;

	// ----------------------------------------

	@Excel(name = "船公司", width = 15)
	@ApiModelProperty(value = "船公司")
	private String returnShipCompany;

	@Excel(name = "船名航次", width = 15)
	@ApiModelProperty(value = "船名航次")
	private String returnVoyageNumber;

	@Excel(name = "装运港", width = 15)
	@ApiModelProperty(value = "装运港")
	private String returnLoadingPort;

	@Excel(name = "目的港", width = 15)
	@ApiModelProperty(value = "目的港")
	private String returnDestinationPort;

	@Excel(name = "提单号码", width = 20)
	@ApiModelProperty(value = "提单号码")
	private String returnBillOfLading;

	@Excel(name = "货柜号码", width = 18)
	@ApiModelProperty(value = "货柜号码")
	private String returnContainerNo;

	@Excel(name = "净重(KGS)", width = 15)
	@ApiModelProperty(value = "退货净重(KGS）")
	private java.math.BigDecimal returnNetWeight;

	@Excel(name = "毛重(KGS)", width = 15)
	@ApiModelProperty(value = "退货毛重(KGS)")
	private java.math.BigDecimal returnGrossWeight;

	@Excel(name = "箱数", width = 10)
	@ApiModelProperty(value = "退货箱数")
	private Integer returnBoxNum;


}
