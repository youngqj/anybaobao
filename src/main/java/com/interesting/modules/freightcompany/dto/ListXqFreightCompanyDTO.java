package com.interesting.modules.freightcompany.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/12/25 16:30
 * @VERSION: V1.0
 */
@Data
public class ListXqFreightCompanyDTO {

    @ApiModelProperty("公司名称")
    private String companyName;

}
