package com.interesting.modules.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class XqOrderCompleteMsgVO {

    @ApiModelProperty("ws类型  1-业务 2-心跳")
    private Integer type;
    @ApiModelProperty("未完成数")
    private Integer unCompleteNum;

    @ApiModelProperty("未完成的订单号")
    private Set<String> unCompleteOrderNums;
}
