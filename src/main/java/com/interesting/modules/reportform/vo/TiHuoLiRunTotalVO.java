package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/15 14:54
 * @Project: trade-manage
 * @Description:
 */

@Data
public class TiHuoLiRunTotalVO {

    @ApiModelProperty(value = "月份")
    private String month;
    @ApiModelProperty(value = "销售数量")
    private Integer exitNum = 0;
    @ApiModelProperty(value = "总榜数")
    private BigDecimal zbs = BigDecimal.ZERO;
    @ApiModelProperty(value = "销售总金额")
    private BigDecimal exitMoney = BigDecimal.ZERO;
    @ApiModelProperty(value = "利润")
    private BigDecimal ml = BigDecimal.ZERO;
    @ApiModelProperty(value = "仓库费用")
    private BigDecimal warehouseFree = BigDecimal.ZERO;

    @ApiModelProperty(value = "仓库名称信息")
    private List<WarehouseHuoZhiVO> warehouseNames;

    @ApiModelProperty(value = "额外费用总计")
    private BigDecimal feeTotal  = BigDecimal.ZERO;

    @ApiModelProperty(value = "PFS纯利润")
    private BigDecimal netProfit  = BigDecimal.ZERO;
    @ApiModelProperty(value = "额外费用")
    private BigDecimal extraFree;

}
