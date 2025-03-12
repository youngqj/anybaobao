package com.interesting.modules.remittance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

@Data
@TableName("xq_credit_insurance")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "xq_credit_insurance对象", description = "信保费用表")
public class XqCreditsInsurance {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("信报保险费")
    private BigDecimal creditInsurancePremium;

    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;

    @ApiModelProperty(value = "投保日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date insuranceEffectiveDate;

    @ApiModelProperty(value = "汇率转换后 信保保险费")
    private java.math.BigDecimal creditInsurancePremiumConvert;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
    /**
     * 逻辑删除 0否 1是
     */
    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;

}
