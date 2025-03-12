package com.interesting.modules.order.dto.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import com.interesting.modules.rawmaterial.dto.InstOrUpdtPaymentDetailDTO;
import com.interesting.modules.rawmaterial.vo.XqPaymentDetailVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddOrderRawFinanceDTO {
    /**
     * ID
     */
    @ApiModelProperty(value = "原料采购id")
    private String id;

    @ApiModelProperty(value = "原料供应商")
    private String supplier;

    /**
     * 采购合同号
     */
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContract;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 采购金额
     */
    @ApiModelProperty(value = "采购金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal purchaseAmount;

    /**
     * 采购备注
     */
    @ApiModelProperty(value = "采购备注")
    private String purchaseNote;
    /**
     * 付款时间
     */
    @ApiModelProperty(value = "付款时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date paymentTime;
    /**
     * 付款金额
     */
    @ApiModelProperty(value = "付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal paymentAmount;
    /**
     * 付款金额
     */
    @ApiModelProperty(value = "未付款金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal unPaymentAmount;
    /**
     * 付款状态
     */
    @ApiModelProperty(value = "付款状态")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal paymentStatus;

    @ApiModelProperty(value = "付款详情")
    private List<XqPaymentDetailVO> paymentDetails;

    @ApiModelProperty(value = "付款详情字符串")
    private String paymentDetailsStr;

    @ApiModelProperty("全额付款状态")
    private Integer financeCompleteState;

    /**
     * 财务备注
     */
    @ApiModelProperty(value = "财务备注")
    private String financialNote;
}
