package com.interesting.modules.overseas.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 每月仓库仓储费
 * @TableName xq_overseas_warehouse_fee
 */
@Data
@TableName("xq_overseas_warehouse_fee")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqOverseasWarehouseFee {
    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 仓库id
     */
    @ApiModelProperty("仓库id")
    private String warehouseId;


//    /**
//     * 年份
//     */
//    @ApiModelProperty("年份")
//    private Integer year;

    /**
     * 入库费用
     */
    @ApiModelProperty("入库费用/cwt")
    private BigDecimal enterWarehouseFee;
    /**
     * 第一个月费用
     */
    @ApiModelProperty("第一个月费用/cwt")
    private BigDecimal firstMonthFee;

    /**
     * 第二个月费用
     */
    @ApiModelProperty("第二个月费用/cwt")
    private BigDecimal secondMonthFee;

    /**
     * 第三个月费用
     */
    @ApiModelProperty("第三个月费用/cwt")
    private BigDecimal thirdMonthFee;

    /**
     * 第四个月费用
     */
    @ApiModelProperty("第四个月费用/cwt")
    private BigDecimal fourthMonthFee;

    /**
     * 第五个月费用
     */
    @ApiModelProperty("第五个月费用/cwt")
    private BigDecimal fifthMonthFee;

    /**
     * 比例
     */
    @ApiModelProperty("比例")
    private BigDecimal rate;

    /**
     * 第六个月费用
     */
    @ApiModelProperty("第六个月费用/cwt")
    private BigDecimal sixthMonthFee;

    /**
     * 第七个月费用
     */
    @ApiModelProperty("第七个月费用/cwt")
    private BigDecimal seventhMonthFee;

    /**
     * 第八个月费用
     */
    @ApiModelProperty("第八个月费用/cwt")
    private BigDecimal eighthMonthFee;

    /**
     * 第九个月费用
     */
    @ApiModelProperty("第九个月费用/cwt")
    private BigDecimal ninthMonthFee;

    /**
     * 第十个月费用
     */
    @ApiModelProperty("第十个月费用/cwt")
    private BigDecimal tenthMonthFee;

    /**
     * 第十一个月费用
     */
    @ApiModelProperty("第十一个月费用/cwt")
    private BigDecimal eleventhMonthFee;

    /**
     * 第十二个月费用
     */
    @ApiModelProperty("第十二个月费用/cwt")
    private BigDecimal twelfthMonthFee;

    /**
     * 释放费用
     */
    @ApiModelProperty("释放费用/pallet")
    private BigDecimal transferCharge;

    /**
     * 托盘A费用
     */
    @ApiModelProperty("托盘A费用/pallet")
    private BigDecimal gradeAPallets;

    /**
     * 托盘B费用
     */
    @ApiModelProperty("托盘B费用/pallet")
    private BigDecimal gradeBPallets;

    /**
     * 缠绕膜费用
     */
    @ApiModelProperty("缠绕膜费用/pallet")
    private BigDecimal shrinkWrap;

    /**
     * 转账户释放费
     */
    @ApiModelProperty("转账户释放费")
    private BigDecimal TransferAccountRelease;

    /**
     * 转账户释放费
     */
    @ApiModelProperty("搬箱费")
    private BigDecimal carryBoxFee;

    /**
     * 转账户释放费
     */
    @ApiModelProperty("寄快递操作费")
    private BigDecimal deliveryOperationFee;

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


    /**
     * 分类挑选费
     */
    @ApiModelProperty("分类挑选费")
    private BigDecimal sortingFee;
}
