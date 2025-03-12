package com.interesting.modules.reportform.vo;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2024/1/23 17:03
 * @VERSION: V1.0
 */
@Data
public class SumFreightTYVO extends SumVO{

    @Excel(name = "国内海运费合计")
    @ApiModelProperty("国内海运费合计")
    private BigDecimal totalgnhyf = BigDecimal.ZERO;

    @ApiModelProperty("国内港杂费合计")
    @Excel(name = "国内港杂费合计")
    private BigDecimal totalgngzf = BigDecimal.ZERO;

    @ApiModelProperty("国外卡车非合计")
    @Excel(name = "国外卡车费合计")
    private BigDecimal totalgwkcf = BigDecimal.ZERO;

    @ApiModelProperty("额外费用1合计")
    @Excel(name = "额外费用1合计")
    private BigDecimal totalewfy1 = BigDecimal.ZERO;
}
