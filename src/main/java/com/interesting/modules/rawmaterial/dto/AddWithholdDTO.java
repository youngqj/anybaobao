package com.interesting.modules.rawmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/4/9 15:31
 * @Project: trade-manage
 * @Description:
 */

@Data
public class AddWithholdDTO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "来源记录id")
    private String sourceId;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal money;

    @ApiModelProperty(value = "付款时间")
    private Date withholdTime;

    @ApiModelProperty(value = "付款备注")
    private String remark;

    @ApiModelProperty(value = "币种")
    private String currency;
}
