package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/5 10:02
 * @Project: trade-manage
 * @Description:
 */

@Data
public class HuoZhiDetailVO {

    private String id;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("仓库lot")
    private String warehouseLot;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "初始来源订单号")
    private String orderNum;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "产品英文名称")
    private String productNameEn;

    @ApiModelProperty(value = "库存数量")
    private Integer inventoryNum;

    @ApiModelProperty(value = "成本单价")
    private BigDecimal price;

    @ApiModelProperty(value = "货值")
    private BigDecimal totalPrice;


}
