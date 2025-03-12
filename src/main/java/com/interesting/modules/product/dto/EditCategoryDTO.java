package com.interesting.modules.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EditCategoryDTO {

    @ApiModelProperty(value = "品类id")
    private String id;

    @ApiModelProperty(value = "品类名称")
    private String category;

}
