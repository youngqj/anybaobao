package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AddOverseasWarehouseFeeDTO {
    @ApiModelProperty(value = "仓库id", required = true)
    @NotBlank(message = "仓库不能为空")
    private String warehouseId;

//    @ApiModelProperty(value = "年份", required = true)
//    @NotNull(message = "年份不能为空")
//    private Integer year;



    /**
     * 分类挑选费
     */
    @ApiModelProperty("分类挑选费")
    private BigDecimal sortingFee;

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
    private BigDecimal transferAccountRelease;

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

    /**
     * 分理费
     */
    @ApiModelProperty("分理费")
    private List<AddSplitFeeDTO> splitFeeDTOList;

    /**
     * 比例
     */
    @ApiModelProperty("比例")
    private BigDecimal rate;

}
