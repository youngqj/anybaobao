package com.interesting.modules.accmaterial.dto.accpurchase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageAccPurchaseDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;
}
