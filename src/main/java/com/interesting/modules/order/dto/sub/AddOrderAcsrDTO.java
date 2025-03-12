package com.interesting.modules.order.dto.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interesting.modules.accmaterial.dto.InstOrUpdtAccInventoryDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 辅料信息
 *
 */
@Data
public class AddOrderAcsrDTO {
    /**id*/
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(hidden = true)
    private Integer sortNum;

    /**产品id*/
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**包装方式*/
    @ApiModelProperty(value = "包装方式")
    private String packaging;

    /**包装方式*/
    @ApiModelProperty(value = "包装方式单位")
    private String packagingUnit;

    /**版面要求*/
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;

    /**辅料供应商id*/
    @ApiModelProperty(value = "辅料供应商名称", required = true)
    @NotBlank(message = "辅料供应商名称")  //温度计一类没有辅料供应商
    private String supplierId;

    /**采购合同号*/
    @ApiModelProperty(value = "采购合同号")
//    @NotBlank(message = "辅料采购合同号不能为空")
    private String purchaseContractNo;

    /**辅料名称*/
    @ApiModelProperty(value = "辅料名称", required = true)
    @NotBlank(message = "辅料名称不能为空")
    private String accessoryName;

    /**尺寸*/
    @ApiModelProperty(value = "辅料尺寸")
    private String size;

    /**订单数量*/
    @ApiModelProperty(value = "订单数量", required = true)
    private Integer orderQuantity;

    @ApiModelProperty(value = "币种", required = true)
    @NotBlank(message = "币种不能为空")
    private String currency;

    /**采购单价*/
    @ApiModelProperty(value = "采购单价", required = true)
    @NotNull(message = "采购单价不能为空")
    private java.math.BigDecimal unitPrice;

    /**税率*/
    @ApiModelProperty(value = "税率")
    private java.math.BigDecimal taxRate;

    /**采购金额*/
    @ApiModelProperty(value = "采购金额")
    private java.math.BigDecimal purchaseAmount;

    /**含税金额*/
    @ApiModelProperty(value = "含税金额")
    @NotNull(message = "含税金额不能为空")
    private java.math.BigDecimal taxIncludedAmount;

    /**采购备注栏*/
    @ApiModelProperty(value = "采购备注栏")
    private String purchaseNote;

    /**财务审核时间*/
    @ApiModelProperty(value = "财务审核时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date financeAuditDate;

    /**财务审核金额*/
    @ApiModelProperty(value = "财务审核金额")
    private java.math.BigDecimal financeAuditAmount;

    /** 用料详情 */
    private List<InstOrUpdtAccInventoryDTO> useInventoryDetails;

    private String useInventoryDetailsStr;

    /** 退料详情 */
    private List<InstOrUpdtAccInventoryDTO> backInventoryDetails;

    private String backInventoryDetailsStr;

    @ApiModelProperty("材质规格")
    @NotBlank(message = "材质规格不能为空")
    private String materialSpecification;
}
