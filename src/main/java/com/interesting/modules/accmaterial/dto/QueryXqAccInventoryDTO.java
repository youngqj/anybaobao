package com.interesting.modules.accmaterial.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QueryXqAccInventoryDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    @ApiModelProperty(value = "辅料id")
    private String accessoryId;
}
