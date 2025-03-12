package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/5 9:57
 * @Project: trade-manage
 * @Description:
 */


@Data
public class QueryHuoZhiDTO {


    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "仓库id",required = true)
    @NotBlank(message = "仓库id不能为空")
    private String warehouseId;

    @ApiModelProperty("仓库lot")
    private String warehouseLot;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "初始来源订单号")
    private String orderNum;
}
