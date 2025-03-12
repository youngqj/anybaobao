package com.interesting.modules.commission.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CommissionHistoryTmpVO {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
