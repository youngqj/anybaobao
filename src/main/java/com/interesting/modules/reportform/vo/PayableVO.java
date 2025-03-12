package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/19 15:42
 * @Project: trade-manage
 * @Description:
 */

@Data
public class PayableVO {

    @Excel(name = "序号")
    @ApiModelProperty("序号")
    private Integer serialNumber ;
    @Excel(name = "工厂名称",width = 25)
    @ApiModelProperty(value = "工厂名称")
    private String name;
    @Excel(name = "订单号",width = 18)
    @ApiModelProperty(value = "订单号")
    private String orderNum;
    @Excel(name = "产品名称",width = 20)
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @Excel(name = "版面要求",width = 20)
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;
    @Excel(name = "装柜日",width = 15)
    @ApiModelProperty(value = "装柜日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date loadingDate;
    @ApiModelProperty(value = "到港日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到港日",width = 15)
    private Date eta;
    @ApiModelProperty(value = "首付比率")
    @Excel(name = "首付比率",width = 10)
    private BigDecimal firstPaymentRate;
    @ApiModelProperty(value = "首款应付到期日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "首款应付到期日",width = 14)
    private Date firstPaymentExpireDate;
    @ApiModelProperty(value = "尾款应付到期日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "尾款应付到期日",width = 14)
    private Date paymentExpireDate;
    @Excel(name = "到期应付金额(CNY)",width = 18)
    @ApiModelProperty(value = "到期应付金额(CNY)")
    private BigDecimal dqyfkCNY;
    @Excel(name = "到期应付金额(USD)",width = 18)
    @ApiModelProperty(value = "到期应付金额(USD)")
    private BigDecimal dqyfkUSD;
    @Excel(name = "总应付金额(CNY)",width = 18)
    @ApiModelProperty(value = "总应付金额(CNY)")
    private BigDecimal zyfkCNY;
    @Excel(name = "总应付金额(USD)",width = 18)
    @ApiModelProperty(value = "总应付金额(USD)")
    private BigDecimal zyfkUSD;
    @Excel(name = "备注（原料采购备注+订单信息+原料采购+货运特殊情况）",width = 60)
    @ApiModelProperty(value = "备注（原料采购备注+订单信息+原料采购+货运特殊情况）")
    private String remark;

    @Excel(name = "类型")
    @ApiModelProperty(value = "类型：1 正常数据  2 预扣款数据")
    private Integer type;

    @Excel(name = "出货方式")
    @ApiModelProperty(value = "出货方式")
    private String chfs;
    @Excel(name = "预付款金额(CNY)",width = 18)
    @ApiModelProperty(value = "预付款金额(CNY)")
    private BigDecimal yfkjeCNY;
    @Excel(name = "预付款金额(USD)",width = 18)
    @ApiModelProperty(value = "预付款金额(USD)")
    private BigDecimal yfkjeUSD;
    @ApiModelProperty(value = "预付款id")
    private String paymentDetailIds;

}
