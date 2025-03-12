package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/14 14:54
 * @Project: trade-manage
 * @Description:
 */

@Data
public class TiHuoLiRunVO {

    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "仓库id",hidden = true)
    private String warehouseId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "发货时间")
    private Date bolTime;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "原始订单号")
    private String sourceRepNum;
    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;
    @ApiModelProperty(value = "包装(每箱重量)")
    private BigDecimal weightPerBox;
    @ApiModelProperty(value = "提货数量")
    private Integer exitNum;
    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
    @ApiModelProperty(value = "总价")
    private BigDecimal exitMoney;
    @ApiModelProperty(value = "单个成本")
    private BigDecimal dgcb;
    @ApiModelProperty(value = "总成本")
    private BigDecimal zcb;
    @ApiModelProperty(value = "毛利")
    private BigDecimal ml;
    @ApiModelProperty(value = "总榜数")
    private BigDecimal zbs;
    @ApiModelProperty(value = "佣金")
    private BigDecimal commission;
    @ApiModelProperty(value = "卡车额外费用")
    private BigDecimal kcewfy;
    @ApiModelProperty(value = "中信保费用")
    private BigDecimal creditInsurancePremiumConvert;
    @ApiModelProperty(value = "保理利息")
    private BigDecimal factoringInterest;
    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;
    @ApiModelProperty(value = "额外费用")
    private BigDecimal extraFree;

}
