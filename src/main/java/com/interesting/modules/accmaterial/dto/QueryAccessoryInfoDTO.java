package com.interesting.modules.accmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryAccessoryInfoDTO {
    private Integer pageNo = 1;

    private Integer pageSize = 10;


    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    @ApiModelProperty(value = "尺寸")
    private String size;
}
