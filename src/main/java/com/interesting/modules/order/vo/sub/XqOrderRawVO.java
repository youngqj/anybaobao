package com.interesting.modules.order.vo.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import com.interesting.modules.rawmaterial.dto.AddWithholdDTO;
import com.interesting.modules.rawmaterial.vo.XqCutAmountDetailVO;
import com.interesting.modules.rawmaterial.vo.XqPaymentDetailVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.List;

@Data
public class XqOrderRawVO {
    /**ID*/
    @ApiModelProperty(value = "ID")
    private String id;
    /**订单表id*/
    @ApiModelProperty(value = "订单表id")
    private String orderId;
    /**原料供应商id*/
    @ApiModelProperty(value = "原料供应商id")
    private String supplierId;
    /**
     * 补充协议号
     */
    @ApiModelProperty(value = "补充协议号")
    private String agreementNo;
    @ApiModelProperty(value = "原料供应商")
    private String supplier;
    /**采购合同号*/
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContract;
    /**产品名称*/
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**
     * 版面要求
     */
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;

    /**产品名称*/
    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty("辅料分类id")
    private String categoryId;

    @ApiModelProperty("辅料分类名称")
    private String categoryTxt;

    /**重量*/
    @ApiModelProperty(value = "重量")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal weight;

    @ApiModelProperty("质量单位id")
    private String weightUnit;

    @ApiModelProperty("质量单位")
    private String weightUnitTxt;

    /**币种*/
    @ApiModelProperty(value = "币种")
    private String currency;
    /**采购单价*/
    @ApiModelProperty(value = "采购单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal unitPrice;

    /**
     * 实际采购单价
     */
    @ApiModelProperty(value = "实际采购单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal realUnitPrice;
    /**采购金额*/
    @ApiModelProperty(value = "采购金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal purchaseAmount;
    /**退税率*/
    @ApiModelProperty(value = "退税率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal taxRefundRate;
    /**退税金额*/
    @ApiModelProperty(value = "退税金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal taxRefundAmount;
    /**采购备注*/
    @ApiModelProperty(value = "采购备注")
    private String purchaseNote;
    /**付款时间*/
    @ApiModelProperty(value = "付款时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date paymentTime;
    /**付款金额*/
    @ApiModelProperty(value = "付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal paymentAmount;

    /**
     * 未付款金额
     */
    @ApiModelProperty(value = "未付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal unPaymentAmount;
    /**付款状态*/
    @ApiModelProperty(value = "付款状态")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal paymentStatus;

    /**付款到期日*/
    @ApiModelProperty(value = "尾付款付款到期日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date paymentExpireDate;

    /**
     * 付款到期日
     */
    @ApiModelProperty(value = "首付款付款到期日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date firstPaymentExpireDate;

    /**财务备注*/
    @ApiModelProperty(value = "财务备注")
    private String financialNote;

    @ApiModelProperty(value = "采购方式（字典）")
    private String purchaseType;

    @ApiModelProperty(value = "采购方式")
    private String purchaseTypeTxt;

    @ApiModelProperty(value = "付款详情")
    private List<XqPaymentDetailVO> paymentDetails;

    @ApiModelProperty(value = "付款详情字符串")
    private String paymentDetailsStr;

    @ApiModelProperty(value = "扣除详情")
    private List<XqCutAmountDetailVO> cutDetails;

    @ApiModelProperty(value = "付款详情字符串")
    private String cutDetailsStr;

    /**
     * 扣款金额
     */
    @ApiModelProperty(value = "扣款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal cutAmount;

    @ApiModelProperty("全额付款状态")
    private Integer financeCompleteState;

    private String financeCompleteStateName;

    /**包装方式*/
    @ApiModelProperty(value = "包装方式")
    private String packaging;

    /**
     * 首付款
     */
    @ApiModelProperty(value = "首付款")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal firstPayment;
    /**
     * 尾付款
     */
    @ApiModelProperty(value = "尾付款")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal lastPayment;

    /**
     * 首付款比例
     */
    @ApiModelProperty(value = "首付款比例")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal firstPaymentRate;

    @ApiModelProperty(value = "采购总价CNY")
    private java.math.BigDecimal purchaseAmountCny;

    @ApiModelProperty(value = "预扣款信息")
    private List<AddWithholdDTO> withholdDTOS;

    @ApiModelProperty(value = "预扣款信息")
    private String withholdStr;

    @ApiModelProperty(value = "预扣金额")
    private BigDecimal withholdAmount;

    @ApiModelProperty(value = "报检出货方式")
    private String inspectionNote;
}
