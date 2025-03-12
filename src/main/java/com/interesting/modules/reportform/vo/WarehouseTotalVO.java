package com.interesting.modules.reportform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/14 16:10
 * @Project: trade-manage
 * @Description:
 */

@Data
public class WarehouseTotalVO {

    @ApiModelProperty(value = "提货数量")
    private Integer exitNum;
    @ApiModelProperty(value = "总价")
    private BigDecimal exitMoney;
    @ApiModelProperty(value = "总榜数")
    private BigDecimal zbs;
    @ApiModelProperty(value = "毛利")
    private BigDecimal ml;
    @ApiModelProperty(value = "总成本")
    private BigDecimal zcb;
    @ApiModelProperty(value = "仓库费用")
    private BigDecimal warehouseFree;
    @ApiModelProperty(value = "佣金")
    private BigDecimal commissiontotal;
    @ApiModelProperty(value = "卡车额外费用")
    private BigDecimal kcewfy;
    @ApiModelProperty(value = "中信保费用")
    private BigDecimal creditInsurancePremiumConvert;
    @ApiModelProperty(value = "保理利息")
    private BigDecimal factoringInterest;
    @ApiModelProperty(value = "额外费用")
    private BigDecimal extraFree;

}
