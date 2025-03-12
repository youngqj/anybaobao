package com.interesting.modules.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryXqCustomerDTO {

    private Integer pageNo = 1;

    private Integer pageSize = 10;
    /**客户姓名*/
    @ApiModelProperty(value = "客户姓名")
    private String name;
    /**客户简称*/
    @ApiModelProperty(value = "客户简称")
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

    @ApiModelProperty(value = "导出字段名")
    private String exportColumn;

}