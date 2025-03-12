package com.interesting.modules.order.vo.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class XqOrderFretCostVO {
    /**id*/
    @ApiModelProperty(value = "id")
    private String id;
    /**订单id*/
    @ApiModelProperty(value = "订单id")
    private String orderId;
    @ApiModelProperty(value = "产品id")
    private String productId;
    /**
     * 版面要求
     */
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;

    @ApiModelProperty(value = "供应商id")
    private String supplierId;
    @ApiModelProperty(value = "客户id")
    private String customerId;
    /**代理商*/
    @ApiModelProperty(value = "代理商")
    private String agent;
    @ApiModelProperty("地址")
    private String address;
    /**备注*/
    @ApiModelProperty(value = "备注")
    private String remark;
    /**币种*/
    @ApiModelProperty(value = "币种")
    private String currency;
    /**价格*/
    @ApiModelProperty(value = "价格")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal price = new BigDecimal("0.00");
    /*付款时间*/
    @ApiModelProperty(value = "付款时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payTime;
    /**申请时间*/
    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date applicationTime;
    /**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal financeAmount = new BigDecimal("0.00");
    /**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date financeAuditTime;
    /**费用类型（字典）*/
    @ApiModelProperty(value = "费用类型（字典）")
    private String feeType;
    @ApiModelProperty(value = "费用类型")
    private String feeTypeTxt;
    /**是否为国内费用 1国内 0国外*/
    @ApiModelProperty(value = "是否为国内费用 1国内 0国外")
    private Integer izDomestic;

    /** 相关附件地址 */
    @ApiModelProperty(value = "相关附件地址，多个以逗号分割")
    private String filesUrl;

    /** 是否为默认带出数据 */
    @ApiModelProperty(value = "是否为默认带出  0-手动编辑(红色) 1-自动带出(蓝色) 2-价格为0(灰色)")
    private Integer izDefaultColor;

    /**仓库id*/
    @ApiModelProperty(value = "仓库id")
    private String warehouseId;
}
