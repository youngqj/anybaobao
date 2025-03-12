package com.interesting.modules.order.vo.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import com.interesting.modules.accmaterial.vo.AccInventoryDetailsVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class XqOrderAcsrVO {
    /**id*/
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * orderId
     */
    @ApiModelProperty(value = "orderId")
    private String orderId;
    /**产品id*/
    @ApiModelProperty(value = "产品id")
    private String productId;
    /**产品名称*/
    @ApiModelProperty(value = "产品名称")
    private String productName;
    /**包装方式*/
    @ApiModelProperty(value = "包装方式")
    private String packaging;
    /**包装方式*/
    @ApiModelProperty(value = "包装方式质量id")
    private String packagingUnit;
    @ApiModelProperty(value = "包装方式质量")
    private String packagingUnitTxt;
    /**版面要求*/
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;
    /**辅料供应商id*/
    @ApiModelProperty(value = "辅料供应商id")
    private String supplierId;
    @ApiModelProperty(value = "辅料供应商")
    private String supplier;
    /**采购合同号*/
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;
    /**辅料名称*/
    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;
    @ApiModelProperty("材质规格")
    private String materialSpecification;
    /**尺寸*/
    @ApiModelProperty(value = "尺寸")
    private String size;
    /**订单数量*/
    @ApiModelProperty(value = "订单数量")
    private Integer orderQuantity;

    @ApiModelProperty("币种")
    private String currency;
    /**采购单价*/
    @ApiModelProperty(value = "采购单价")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal unitPrice;
    /**采购金额*/
    @ApiModelProperty(value = "采购金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal purchaseAmount = new BigDecimal("0.00");
    /**税率*/
    @ApiModelProperty(value = "税率")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal taxRate;
    /**含税金额*/
    @ApiModelProperty(value = "含税金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal taxIncludedAmount = new BigDecimal("0.00");
    /**采购备注栏*/
    @ApiModelProperty(value = "采购备注栏")
    private String purchaseNote;
    /**财务审核时间*/
    @ApiModelProperty(value = "财务审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private java.util.Date financeAuditDate;
    /**财务审核金额*/
    @ApiModelProperty(value = "财务审核金额")
    @JsonSerialize(using = BigDecimalSerializer.class)
    private java.math.BigDecimal financeAuditAmount;

    @ApiModelProperty("所选原料的辅料分类id")
    private String categoryId;

    @ApiModelProperty("所选的辅料的辅料分类id")
    private String categoryId2;

    @ApiModelProperty("用料详情")
    private List<AccInventoryDetailsVO> useInventoryDetails;

    private String useInventoryDetailsStr;

    @ApiModelProperty("退料详情")
    private List<AccInventoryDetailsVO> backInventoryDetails;

    private String backInventoryDetailsStr;

    @ApiModelProperty("库存数量")
    private BigDecimal skuNum;

    @ApiModelProperty("用料数量")
    private Integer useNum;

    @ApiModelProperty("退料数量")
    private Integer backNum;

    @ApiModelProperty("辅料id")
    private String accessoryId;

    /**
     * 总箱数
     */
    @ApiModelProperty(value = "总箱数")
    private Integer totalBoxes;
}
