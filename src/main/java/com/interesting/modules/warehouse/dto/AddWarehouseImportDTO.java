package com.interesting.modules.warehouse.dto;


import com.interesting.modules.freightcost.dto.AddXqFreightCostInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AddWarehouseImportDTO {

    /**序号*/
    @ApiModelProperty(value = "序号")
    private Integer cou;

    /**仓库名称*/
    @ApiModelProperty(value = "仓库名称",required = true)
    @NotBlank(message = "仓库名称")
    private String name;

    @ApiModelProperty("仓库编号")
    private String number;

    @ApiModelProperty(value = "仓库类型：1 国内辅料仓，2海外仓")
    @NotBlank(message = "仓库类型不能为空")
    private String type;

    @ApiModelProperty("理由")
    public String errorInfo;

}
