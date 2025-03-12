package com.interesting.modules.overseas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EditOverSeasInventoryFeeDTO {
    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "产品id")
    private String productId;


    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @ApiModelProperty(value = "费用")
    private BigDecimal amount;

    @ApiModelProperty(value = "发票日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date invoiceDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "到期付款日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date FinishDate;

    @ApiModelProperty(value = "申请日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applicationDate;

    @ApiModelProperty(value = "申请日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualDate;


    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "付款费用")
    private BigDecimal payAmount;

    @ApiModelProperty(value = "付款账户")
    private String payAccount;

    @ApiModelProperty(value = "财务审核")
    private String financeAudit;


    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;
}
