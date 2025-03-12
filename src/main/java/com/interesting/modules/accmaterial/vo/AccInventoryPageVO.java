package com.interesting.modules.accmaterial.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccInventoryPageVO {
    private String id;


    @ApiModelProperty("仓库id")
    private String warehouseId;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "辅料id")
    private String accessoryId;

    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    @ApiModelProperty(value = "辅料尺寸")
    private String size;

    @ApiModelProperty(value = "材质规格")
    private String materialSpecification;

    @ApiModelProperty(value = "库存数量")
    private Integer inventoryNum;
}
