package com.interesting.modules.order.dto.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 货运费用
 */
@Data
public class AddOrderFretCostDTO {
    /**id*/
    @ApiModelProperty(value = "id")
    private String id;

//    /**订单id*/
//    @ApiModelProperty(value = "订单id")
//    private String orderId;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**
     * 版面要求
     */
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;


    /**代理商*/
    @ApiModelProperty(value = "代理商")
    private String agent;

    /**备注*/
    @ApiModelProperty(value = "备注")
    private String remark;

    /**币种*/
    @ApiModelProperty(value = "币种")
    private String currency;
    /**价格*/
    @ApiModelProperty(value = "价格")
    private java.math.BigDecimal price;

    /**申请时间*/
    @ApiModelProperty(value = "申请时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date applicationTime;

    /*付款时间*/
    @ApiModelProperty(value = "付款时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payTime;

    /** 相关附件地址 */
    @ApiModelProperty(value = "相关附件地址，多个以逗号分割")
    private String filesUrl;

    /**财务确认金额*/
    @ApiModelProperty(value = "财务确认金额")
    private java.math.BigDecimal financeAmount;

    /**财务审核日期*/
    @ApiModelProperty(value = "财务审核日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date financeAuditTime;

    /**费用类型（字典）*/
    @ApiModelProperty(value = "费用类型（字典）")
    private String feeType;

//    @ApiModelProperty(value = "费用类型")
//    private String feeTypeTxt;

    /**是否为国内费用 1国内 0国外*/
    @ApiModelProperty(value = "是否为国内费用 1国内 0国外")
    private Integer izDomestic;

    /**是否为退运费用*/
    @ApiModelProperty(value = "是否为退运费用", hidden = true)
    private Integer izReturnFee;

    /**是否为默認费用*/
    @ApiModelProperty(value = "是否为默认带出  0-手动编辑(红色) 1-自动带出(蓝色) 2-价格为0(灰色)")
    private Integer izDefaultColor;
}
