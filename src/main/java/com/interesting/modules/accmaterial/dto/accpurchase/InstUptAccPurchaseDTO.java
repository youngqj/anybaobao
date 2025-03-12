package com.interesting.modules.accmaterial.dto.accpurchase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 头表dto
 */
@Data
public class InstUptAccPurchaseDTO {
    @ApiModelProperty("id")
    private String id;

    /**
     * 采购合同号
     */
    @ApiModelProperty(value = "采购合同号", required = true)
    @NotBlank(message = "采购合同号不能为空")
    private String purchaseContractNo;

    /**
     * 采购日期
     */
    @ApiModelProperty(value = "采购日期", required = true)
    @NotNull(message = "采购日期不能为空")
    private Date purchaseDate;

    /**
     * 供应商id
     */
    @ApiModelProperty(value = "供应商id", required = true)
    @NotBlank(message = "供应商id不能为空")
    private String supplierId;


    List<InstUptAccPurchaseDTO2> details;
}
