package com.interesting.modules.rawmaterial.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class XqPaymentDetailVO {
    @ApiModelProperty(value = "主键id")
    private String id;

//    @ApiModelProperty(value = "来源记录id")
//    private String sourceId;

    @ApiModelProperty(value = "付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal payMoney;

    @ApiModelProperty(value = "付款时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date payTime;

    @ApiModelProperty(value = "付款备注")
    private String payRemark;

    @ApiModelProperty(value = "类型 0付款 1扣款")
    private Integer type;

    @ApiModelProperty(value = "预付款id")
    private String prepaymentsId;

//    @ApiModelProperty(value = "创建人")
//    private String createBy;
//
//    @ApiModelProperty(value = "创建时间")
//    private Date createTime;
}
