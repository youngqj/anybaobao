package com.interesting.modules.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddCategoryDTO {
    @ApiModelProperty(value = "品类名称")
    private String category;

}
