package com.interesting.modules.supplier.dto;


import com.interesting.modules.freightcost.dto.AddXqFreightCostInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AddSupplierImportDTO {

    /**序号*/
    @ApiModelProperty(value = "序号")
    private Integer cou;

    @ApiModelProperty(value = "供应商类型：1原料供应商，2辅料供应商")
    @NotBlank(message = "供应商类型不能为空")
    private String type;

    /**供应商名称*/
    @ApiModelProperty(value = "供应商名称")
    private String name;

    /**联系电话*/
    @ApiModelProperty(value = "联系电话")
    @NotBlank(message = "联系电话不能为空")
    private String phone;

    @ApiModelProperty(value = "货运信息")
    private List<AddXqFreightCostInfoDTO> freightCostInfos;

    @ApiModelProperty("理由")
    public String errorInfo;

}
