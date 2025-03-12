package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListWarehouseFeeVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

//    @ApiModelProperty(value = "年份")
//    private Integer year;

    /**
     * 第一个月费用
     */
    @ApiModelProperty("第一个月费用/cwt")
    private BigDecimal firstMonthFee;

    /**
     * 第二个月费用
     */
    @ApiModelProperty("第二个月费用/cwt")
    private BigDecimal secondMonthFee;

    /**
     * 第三个月费用
     */
    @ApiModelProperty("第三个月费用/cwt")
    private BigDecimal thirdMonthFee;

    /**
     * 第四个月费用
     */
    @ApiModelProperty("第四个月费用/cwt")
    private BigDecimal fourthMonthFee;

    /**
     * 第五个月费用
     */
    @ApiModelProperty("第五个月-第十二个月费用/cwt")
    private BigDecimal fifthMonthFee;

    @ApiModelProperty("分类挑选费")
    private BigDecimal sortingFee;

}
