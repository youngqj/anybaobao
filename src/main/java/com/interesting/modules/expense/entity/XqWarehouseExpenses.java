package com.interesting.modules.expense.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/11 16:39
 * @Project: trade-manage
 * @Description:
 */

@Data
@TableName("xq_warehouse_expenses")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqWarehouseExpenses implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    @ApiModelProperty(value = "费用USD")
    private BigDecimal expense;

    @ApiModelProperty(value = "发票日期")
    private Date invoiceDate;

    @ApiModelProperty(value = "备注1")
    private String remark;

    @ApiModelProperty(value = "状态：0 未结清  1 结清")
    private Integer izSettle;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal paymentAmount;

    @ApiModelProperty(value = "备注2")
    private String remark2;

    @ApiModelProperty(value = "财务审核金额")
    private BigDecimal auditAmount;

    @ApiModelProperty(value = "财务审核日期")
    private Date auditDate;

    @ApiModelProperty(value = "到期付款日")
    private Date dueDate;
    @ApiModelProperty(value = "实际付款日")
    private Date paymentDate;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;
    @ApiModelProperty(value = "申请时间")
    private Date applyForDate;

    public String getKey() {
        return warehouseId + "-" + orderNum + "-" + productName + "-" + invoiceNum;
    }

}
