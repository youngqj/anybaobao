package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FreightKCReportVO {

    @Excel(name = "序号",width = 10)
    @ApiModelProperty("序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "主键id")
    private String id;

    @Excel(name = "客户订单号",width = 18)
    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;
    @Excel(name = "客户",width = 14)
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @Excel(name = "客户PO号",width = 20)
    @ApiModelProperty(value = "客户PO号")
    private String orderNum;
    @Excel(name = "卡车公司",width = 14)
    @ApiModelProperty(value = "卡车公司")
    private String truckCompany;
    @Excel(name = "到货仓库",width = 20)
    @ApiModelProperty(value = "到货仓库")
    private String arrivalWarehouse;
    @Excel(name = "申请日期",width = 10)
    @ApiModelProperty("申请日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;
    @Excel(name = "付款日期",width = 10)
    @ApiModelProperty("付款日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;
    @Excel(name = "付款金额",width = 10)
    @ApiModelProperty("付款金额")
    private BigDecimal payedPrice;
    @Excel(name = "到货日期",width = 10)
    @ApiModelProperty(value = "到货时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date arrivalTime;
    @Excel(name = "提货仓库",width = 35)
    @ApiModelProperty(value = "提货仓库")
    private String deliveryWarehouse;
    @Excel(name = "提货日期",width = 10)
    @ApiModelProperty(value = "提货时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryTime;
    @Excel(name = "卡车费用",width = 10)
    @ApiModelProperty(value = "卡车费用")
    private BigDecimal truckFee;
    @Excel(name = "财务确认金额",width = 15)
    @ApiModelProperty(value = "财务确认金额")
    private BigDecimal financeAmount;
    @Excel(name = "财务审核日期",width = 15)
    @ApiModelProperty(value = "财务审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date financeAuditTime;
    @Excel(name = "特殊情况备注",width = 60)
    @ApiModelProperty(value = "特殊情况备注")
    private String remark;

}
