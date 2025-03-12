package com.interesting.modules.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XqProductCategoryVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "品类")
    private String category;

}
