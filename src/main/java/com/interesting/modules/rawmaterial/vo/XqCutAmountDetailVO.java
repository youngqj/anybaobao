package com.interesting.modules.rawmaterial.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class XqCutAmountDetailVO {
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal cutAmount;

    @ApiModelProperty(value = "付款时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date payTime;

    @ApiModelProperty(value = "付款备注")
    private String payRemark;

    @ApiModelProperty(value = "类型 0付款 1扣款")
    private Integer type;

}
