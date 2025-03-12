package com.interesting.modules.freightcompany.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/12/25 16:33
 * @VERSION: V1.0
 */
@Data
public class XqFreightCompanyDTO {

    @ApiModelProperty("主键")
    private String id;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称",required = true)
    @NotBlank(message = "公司名称不可为空")
    private String companyName;
    /**
     * 联系人名称
     */
    @ApiModelProperty("联系人名称")
    private String contactName;
    /**
     * 联系人联系方式
     */
    @ApiModelProperty("联系人联系方式")
    private String contactPhone;

}
