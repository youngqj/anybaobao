package com.interesting.modules.reportform.vo;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SumFreightGWVO extends SumVO{

    @Excel(name = "港杂费(RMB)",width = 15)
    @ApiModelProperty("港杂费(RMB)")
    private BigDecimal totalgngzf;
    @Excel(name = "港杂费已付",width = 15)
    @ApiModelProperty("港杂费已付")
    private BigDecimal totalgngzfFinanceAmount;

    @Excel(name = "剩余合计(USD)",width = 15)
    @ApiModelProperty("剩余合计(USD)")
    private BigDecimal totalOther;

    @Excel(name = "剩余合计已付",width = 15)
    @ApiModelProperty("剩余合计已付")
    private BigDecimal totalOtherFinanceAmount;
//
//    @Excel(name = "国内额外费用3(RMB)",width = 20)
//    @ApiModelProperty("国内额外费用3(RMB)")
//    private BigDecimal totalgnewfy3Price;
//
//    @Excel(name = "国内额外费用3已付",width = 20)
//    @ApiModelProperty("国内额外费用3已付")
//    private BigDecimal totalgnewfy3FinanceAmount;
//
//    @Excel(name = "国内额外费用4(RMB)",width = 20)
//    @ApiModelProperty("国内额外费用4(RMB)")
//    private BigDecimal totalgnewfy4Price;
//
//    @Excel(name = "国内额外费用4已付",width = 20)
//    @ApiModelProperty("国内额外费用4已付")
//    private BigDecimal totalgnewfy4FinanceAmount;


}
