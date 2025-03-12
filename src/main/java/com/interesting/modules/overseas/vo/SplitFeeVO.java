package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SplitFeeVO {
    /**
     * id
     */
    @ApiModelProperty("主键id")
    private String id;

    /**
     * 仓库id
     */
    @ApiModelProperty("仓库id")
    private String warehouseFeeId;
    /**
     * 区间开始
     */
    @ApiModelProperty("区间开始")
    private Integer lotStart;

    /**
     * 区间结束
     */
    @ApiModelProperty("区间结束")
    private Integer lotEnd;

    /**
     * 第四个月费用
     */
    @ApiModelProperty("价格")
    private BigDecimal price;

}
