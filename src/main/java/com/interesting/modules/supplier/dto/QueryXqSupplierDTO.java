package com.interesting.modules.supplier.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryXqSupplierDTO {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private String exportColumn;

    @ApiModelProperty(value = "供应商类型")
    private String type;

    /**供应商名称*/
    @ApiModelProperty(value = "供应商名称")
    private String name;
    /**供应商简称*/
    @ApiModelProperty(value = "供应商简称")
    private String abbr;
    /**税号*/
    @ApiModelProperty(value = "税号")
    private String taxNum;
    /**联系人*/
    @ApiModelProperty(value = "联系人")
    private String contactor;
    /**公司邮箱*/
    @ApiModelProperty(value = "公司邮箱")
    private String email;
}