package com.interesting.modules.reportform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/15 10:19
 * @Project: trade-manage
 * @Description:
 */

@Data
public class TiHuoLiRunMonthVO {

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "一月")
    private BigDecimal january = BigDecimal.ZERO;
    @ApiModelProperty(value = "二月")
    private BigDecimal february = BigDecimal.ZERO;
    @ApiModelProperty(value = "三月")
    private BigDecimal march = BigDecimal.ZERO;
    @ApiModelProperty(value = "四月")
    private BigDecimal april = BigDecimal.ZERO;
    @ApiModelProperty(value = "五月")
    private BigDecimal may = BigDecimal.ZERO;
    @ApiModelProperty(value = "六月")
    private BigDecimal june = BigDecimal.ZERO;
    @ApiModelProperty(value = "七月")
    private BigDecimal july = BigDecimal.ZERO;
    @ApiModelProperty(value = "八月")
    private BigDecimal august = BigDecimal.ZERO;
    @ApiModelProperty(value = "九月")
    private BigDecimal september = BigDecimal.ZERO;
    @ApiModelProperty(value = "十月")
    private BigDecimal october = BigDecimal.ZERO;
    @ApiModelProperty(value = "十一月")
    private BigDecimal november = BigDecimal.ZERO;
    @ApiModelProperty(value = "十二月")
    private BigDecimal december = BigDecimal.ZERO;

}
