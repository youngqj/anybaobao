package com.interesting.modules.airports.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @TableName xq_accessory_info
 */
@Data
@TableName("xq_airport")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqAirport implements Serializable {

    private static final long SerialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "提单日期")
    private Date billOfLandingDate;

    @ApiModelProperty(value = "提单号")
    private String billOfLandingNo;

    @ApiModelProperty(value = "工厂")
    private String factoryName;

    @ApiModelProperty(value = "客户")
    private String customer;

    @ApiModelProperty(value = "目的港")
    private String portOfDestination;

    @ApiModelProperty(value = "国内费用")
    private BigDecimal gnfy;

    @ApiModelProperty(value = "国内费用申请日期")
    private Date gnfyApplyTime;

    @ApiModelProperty(value = "国内财务审核金额")
    private BigDecimal gnfyAudit;

    @ApiModelProperty(value = "国内财务审核日期")
    private Date gnfyAuditTime;

    @ApiModelProperty(value = "国内汇款金额")
    private BigDecimal gnhkje;

    @ApiModelProperty(value = "国内实际付款日期")
    private Date gnsjfkr;

    @ApiModelProperty(value = "清关货代")
    private String qghd;
    @ApiModelProperty(value = "国外费用")
    private BigDecimal gwfy;
    @ApiModelProperty(value = "国外费用申请日期")
    private Date gwfyApplyTime;
    @ApiModelProperty(value = "国外汇款金额")
    private BigDecimal gwhkje;
    @ApiModelProperty(value = "国外实际汇款日")
    private Date gwsjfkr;
    @ApiModelProperty(value = "国外财务审核金额")
    private BigDecimal gwfyAudit;
    @ApiModelProperty(value = "国外财务审核日期")
    private Date gwfyAuditTime;


    @ApiModelProperty(value = "清关货代2")
    private String qghd2;
    @ApiModelProperty(value = "国外费用2")
    private BigDecimal gwfy2;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "国外费用申请日期2")
    private Date gwfyApplyTime2;
    @ApiModelProperty(value = "国外汇款金额2")
    private BigDecimal gwhkje2;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "国外实际汇款日2")
    private Date gwsjfkr2;
    @ApiModelProperty(value = "国外财务审核金额2")
    private BigDecimal gwfyAudit2;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "国外财务审核日期2")
    private Date gwfyAuditTime2;


    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "财务备注")
    private String financeRemark;

    @ApiModelProperty(value = "出运机场")
    private String airportName;

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