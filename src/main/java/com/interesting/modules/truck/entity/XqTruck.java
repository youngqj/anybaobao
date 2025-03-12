package com.interesting.modules.truck.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("xq_truck")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqTruck {
    private static final long SerialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date happenDate;

    @ApiModelProperty(value = "客户")
    private String customer;

    @ApiModelProperty(value = "订单号")
    private String customerOrderNum;

    @ApiModelProperty(value = "中文名")
    private String productName;

    @ApiModelProperty(value = "装货仓库")
    private String loadingWarehouse;

    @ApiModelProperty(value = "卸货仓库")
    private String unLoadingWarehouse;


    @ApiModelProperty(value = "BOL/客户PO号")
    private String orderNum;

    @ApiModelProperty(value = "到货时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date arrivalTime;

    @ApiModelProperty(value = "卡车")
    private String truck;

    @ApiModelProperty(value = "国外费用")
    private BigDecimal price;

    @ApiModelProperty(value = "国外费用明细")
    private String priceDetail;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal payment;

    @ApiModelProperty(value = "付款时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "费用确认")
    private String confirmFinance;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;
}
