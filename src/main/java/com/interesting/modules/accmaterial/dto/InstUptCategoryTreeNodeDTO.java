package com.interesting.modules.accmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InstUptCategoryTreeNodeDTO {
    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty(value = "分类名称", required = true)
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty("pid")
    private String pid;
}
