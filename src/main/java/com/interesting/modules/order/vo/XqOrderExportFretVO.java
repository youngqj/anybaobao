package com.interesting.modules.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XqOrderExportFretVO {
    @ApiModelProperty(value = "代理商名称")
    private String agentName;
}
