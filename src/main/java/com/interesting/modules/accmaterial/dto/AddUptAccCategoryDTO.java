package com.interesting.modules.accmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddUptAccCategoryDTO {
    private String id;

    @ApiModelProperty("辅料分类名称")
    @NotBlank(message = "辅料分类名称不能为空")
    private String name;
}
