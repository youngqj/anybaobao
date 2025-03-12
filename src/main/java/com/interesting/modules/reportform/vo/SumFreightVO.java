package com.interesting.modules.reportform.vo;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SumFreightVO extends SumVO{

    @Excel(name = "陆运费")
    @ApiModelProperty("国内陆运费合计")
    private BigDecimal totalgnlyf;
    @Excel(name = "舱单费")
    @ApiModelProperty("国内舱单费合计")
    private BigDecimal totalgncdf;
    @ApiModelProperty("国内报关费合计")
    @Excel(name = "报关费")
    private BigDecimal totalgnbgf;
    @ApiModelProperty("海运保险费合计")
    @Excel(name = "海运保险费")
    private BigDecimal totalhybxf;
    @Excel(name = "送货费")
    @ApiModelProperty("送货费合计")
    private BigDecimal totalshf;
    @ApiModelProperty("国内陆运费付款合计")
    @Excel(name = "陆运费付款金额")
    private BigDecimal totalgnlyfFinanceAmount;
    @ApiModelProperty("国内舱单费付款合计")
    @Excel(name = "舱单费付款金额")
    private BigDecimal totalgncdfFinanceAmount;
    @ApiModelProperty("国内报关费付款合计")
    @Excel(name = "报关费付款金额")
    private BigDecimal totalgnbgfFinanceAmount;
    @ApiModelProperty("海运保险费付款合计")
    @Excel(name = "海运保险费付款金额")
    private BigDecimal totalhybxfFinanceAmount;
    @ApiModelProperty("送货费合计付款")
    @Excel(name = "送货费付款金额")
    private BigDecimal totalshfFinanceAmount;



    @Excel(name = "国内额外费用1(RMB)",width = 20)
    @ApiModelProperty("国内额外费用1(RMB)")
    private BigDecimal totalgnewfy1Price;

    @Excel(name = "国内额外费用1已付",width = 20)
    @ApiModelProperty("国内额外费用1已付")
    private BigDecimal totalgnewfy1FinanceAmount;

    @Excel(name = "国内额外费用2(RMB)",width = 20)
    @ApiModelProperty("国内额外费用2(RMB)")
    private BigDecimal totalgnewfy2Price;

    @Excel(name = "国内额外费用2已付",width = 20)
    @ApiModelProperty("国内额外费用2已付")
    private BigDecimal totalgnewfy2FinanceAmount;

}
