package com.interesting.modules.overseas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 海外仓-出库单子表
 * @TableName xq_overseas_exit_detail
 */
@Data
@TableName("xq_overseas_exit_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqOverseasExitDetail implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "客户订单号")
    private String customerOrderNum;

    @ApiModelProperty(value = "初始来源订单号")
    private String sourceRepNum;

    @ApiModelProperty(value = "bol")
    private String bol;

    @ApiModelProperty(value = "bol时间")
    private Date bolTime;

    @ApiModelProperty(value = "pod时间")
    private Date podTime;

    @ApiModelProperty(value = "货款到期日")
    private Date paymentExpireDate;

    @ApiModelProperty(value = "配送方式")
    private String deliverMethod;

    @ApiModelProperty(value = "特殊情况备注")
    private String specialRemark;

    @ApiModelProperty(value = "来源订单id")
    private String orderDetailId;

    @ApiModelProperty(value = "主表id")
    private String sourceId;

    @ApiModelProperty(value = "仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "出库数量")
    private Integer exitNum;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "出库金额")
    private BigDecimal exitMoney;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "投保日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date insuranceEffectiveDate;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;

    /**发票号*/
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    /**客户id*/
    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty("信报保险费")
    private BigDecimal creditInsurancePremium;

    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;

    @ApiModelProperty(value = "汇率转换后 信保保险费")
    private java.math.BigDecimal creditInsurancePremiumConvert;
}