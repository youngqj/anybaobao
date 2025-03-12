package com.interesting.modules.truck.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InstUptTruckInfoDTO {
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "卡车公司")
    private String truckCompany;

    @ApiModelProperty(value = "卡车费用")
    private BigDecimal truckFee;

    @ApiModelProperty("申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;

    @ApiModelProperty("付款日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;

    @ApiModelProperty(value = "特殊情况备注")
    private String remark;

    @ApiModelProperty(value = "财务确认金额")
    private BigDecimal financeAmount;

    @ApiModelProperty(value = "财务审核日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date financeAuditTime;

    /**客户订单号*/
    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;


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



}
