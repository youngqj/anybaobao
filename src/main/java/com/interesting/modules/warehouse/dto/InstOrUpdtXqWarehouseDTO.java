package com.interesting.modules.warehouse.dto;

import com.interesting.modules.freightcost.dto.AddXqFreightCostInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class InstOrUpdtXqWarehouseDTO {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "仓库编号")
    private String serialNum;

    @ApiModelProperty(value = "仓库名称", required = true)
    @NotBlank(message = "仓库名称不能为空")
    private String name;

    @ApiModelProperty(value = "仓库地址")
    private String address;

    @ApiModelProperty(value = "仓库类型（字典）", required = true)
    @NotBlank(message = "仓库类型不能为空")
    private String warehouseType;

    @ApiModelProperty(value = "货运信息")
    private List<AddXqFreightCostInfoDTO> freightCostInfos;
}
