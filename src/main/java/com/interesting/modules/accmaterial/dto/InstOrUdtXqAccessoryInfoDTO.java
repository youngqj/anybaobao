package com.interesting.modules.accmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InstOrUdtXqAccessoryInfoDTO {
    @ApiModelProperty("编辑时传id")
    private String id;

    @ApiModelProperty(value = "辅料名称", required = true)
    @NotBlank(message = "辅料名称不能为空")
    private String accessoryName;

    @ApiModelProperty(value = "尺寸", required = true)
    @NotBlank(message = "尺寸不能为空")
    private String size;

    @ApiModelProperty("辅料分类id")
    private String categoryId;

    @ApiModelProperty("材质规格")
    private String materialSpecification;
}
