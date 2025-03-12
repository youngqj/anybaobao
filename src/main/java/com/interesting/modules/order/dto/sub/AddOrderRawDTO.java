package com.interesting.modules.order.dto.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interesting.modules.rawmaterial.dto.InstOrUpdtCutAmountDetailDTO;
import com.interesting.modules.rawmaterial.dto.InstOrUpdtPaymentDetailDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddOrderRawDTO {
    /**ID*/
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(hidden = true)
    private Integer sortNum;

    /**原料供应商id*/
    @ApiModelProperty(value = "原料供应商id")
    private String supplierId;

    /**采购合同号*/
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContract;

    /**
     * 补充协议号
     */
    @ApiModelProperty(value = "补充协议号")
    private String agreementNo;


    @ApiModelProperty(value = "产品id")
    @NotBlank(message = "产品id不能为空")
    private String productId;

    /**
     * 版面要求
     */
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;


    /**重量*/
    @ApiModelProperty(value = "重量")
    private java.math.BigDecimal weight;

    @ApiModelProperty(value = "重量单位id")
    private String weightUnit;

    /**币种*/
    @ApiModelProperty(value = "币种")
    private String currency;
    /**采购单价*/
    @ApiModelProperty(value = "采购单价", required = true)
    @NotNull(message = "原料采购中采购单价不能为空")
    private java.math.BigDecimal unitPrice;


    /**
     * 实际采购单价
     */
    @ApiModelProperty(value = "实际采购单价")
    private java.math.BigDecimal realUnitPrice;
    /**采购金额*/
    @ApiModelProperty(value = "采购金额")
    private java.math.BigDecimal purchaseAmount;
    /**退税率*/
    @ApiModelProperty(value = "退税率")
    private java.math.BigDecimal taxRefundRate;
    /**退税金额*/
    @ApiModelProperty(value = "退税金额")
    private java.math.BigDecimal taxRefundAmount;
    /**采购备注*/
    @ApiModelProperty(value = "采购备注")
    private String purchaseNote;
    /**付款时间*/
    @ApiModelProperty(value = "付款时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date paymentTime;
    /**付款金额*/
    @ApiModelProperty(value = "付款金额")
    private java.math.BigDecimal paymentAmount;

    /**
     * 未付款金额
     */
    @ApiModelProperty(value = "未付款金额")
    private java.math.BigDecimal unPaymentAmount;
    /**付款状态*/
    @ApiModelProperty(value = "付款状态")
    private java.math.BigDecimal paymentStatus;
    /**财务备注*/
    @ApiModelProperty(value = "财务备注")
    private String financialNote;

    @ApiModelProperty("全额付款状态 0未完成 1已完成")
    private Integer financeCompleteState;

    @ApiModelProperty(value = "采购方式（字典）")
    private String purchaseType;

    /**包装方式*/
    @ApiModelProperty(value = "包装方式")
    private String packaging;


    /**付款到期日*/
    @ApiModelProperty(value = "尾款付款到期日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date paymentExpireDate;

    /**
     * 付款到期日
     */
    @ApiModelProperty(value = "首付款到期日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date firstPaymentExpireDate;


    //    @NotNull(message = "付款明细不能为空")
    private List<InstOrUpdtPaymentDetailDTO> paymentDetails;

    private String paymentDetailsStr;


    //    @NotNull(message = "付款明细不能为空")
    private List<InstOrUpdtCutAmountDetailDTO> cutAmountDetails;

    private String cutDetailsStr;

    /**
     * 付款金额
     */
    @ApiModelProperty(value = "扣款金额")
    private java.math.BigDecimal cutAmount;

    @ApiModelProperty("辅料分类id，多个以英文逗号分割")
    private String categoryId;


    /**
     * 首付款金额
     */
    @ApiModelProperty(value = "首付款金额")
    private java.math.BigDecimal firstPayment;

    /**
     * 尾付款金额
     */
    @ApiModelProperty(value = "尾付款金额")
    private java.math.BigDecimal lastPayment;

    /**
     * 首付款金额
     */
    @ApiModelProperty(value = "首付款金额比例")
    private java.math.BigDecimal firstPaymentRate;


    @ApiModelProperty(value = "采购总价CNY")
    private java.math.BigDecimal purchaseAmountCny;


    @ApiModelProperty(value = "预扣金额")
    private java.math.BigDecimal withholdAmount;

    @ApiModelProperty(value = "预扣信息")
    private String withholdStr;

    @ApiModelProperty(value = "报检备注")
    private String inspectionNote;
}
