package com.interesting.modules.order.vo.sub;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import com.interesting.modules.commission.vo.XqOrderCommissionVO;
import com.interesting.modules.orderdetail.vo.XqCommissionOrderVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class XqOrderComsVO {
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;
    /**订单表id*/
    @ApiModelProperty(value = "订单表id")
    private String orderId;

    @ApiModelProperty("客户订单号")
    private String customerOrderNum;

//    /**佣金公司*/
//    @ApiModelProperty(value = "佣金公司id")
//    private String companyId;

    /**佣金公司*/
    @ApiModelProperty(value = "佣金公司")
    private String companyName;

    /**佣金条件*/
    @ApiModelProperty(value = "佣金条件（字典）")
    private String requirements;

    /**佣金条件*/
    @ApiModelProperty(value = "佣金条件（字典）")
    private String requirementsTxt;

    /**佣金比例*/
    @ApiModelProperty(value = "佣金比例")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal percentage;
    /**佣金金额*/
    @ApiModelProperty(value = "佣金金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal amount;
    /**申请日期*/
    @ApiModelProperty(value = "申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date applicationDate;
    /**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal financeConfirmedAmount;
    /**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date financeAuditDate;
    /**备注*/
    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty("佣金类型（字典）")
    private String commissionType;

    @ApiModelProperty("佣金类型")
    private String commissionTypeTxt;

    /**佣金参数*/
    @ApiModelProperty(value = "佣金参数")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal param;

    @ApiModelProperty(value = "佣金详情")
    private List<XqCommissionOrderVO> commissionVOList;

    @ApiModelProperty(value = "佣金详情字符串")
    private String commissionDetailsStr;
}
