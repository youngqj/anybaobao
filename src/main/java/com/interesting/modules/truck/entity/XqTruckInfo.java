package com.interesting.modules.truck.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 卡车信息表
 * @TableName xq_truck_info
 */
@Data
@TableName("xq_truck_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqTruckInfo implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "卡车公司")
    private String truckCompany;

    @ApiModelProperty(value = "卡车费用")
    private BigDecimal truckFee;

    @ApiModelProperty(value = "特殊情况备注")
    private String remark;

    @ApiModelProperty(value = "财务确认金额")
    private BigDecimal financeAmount;

    @ApiModelProperty(value = "财务审核日期")
    private Date financeAuditTime;

    @ApiModelProperty("申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;

    @ApiModelProperty("付款日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;


    @ApiModelProperty("付款金额")
    private BigDecimal payedPrice;

    @ApiModelProperty(value = "提货时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;

    @ApiModelProperty(value = "到货时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date arrivalTime;


    @ApiModelProperty(value = "提货仓库")
    private String deliveryWarehouse;
    @ApiModelProperty(value = "到货仓库")
    private String arrivalWarehouse;
    @ApiModelProperty(value = "客户")
    private String customerName;


    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Integer izDelete;

    /**客户订单号*/
    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;
}