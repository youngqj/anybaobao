package com.interesting.modules.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/18 11:45
 * @VERSION: V1.0
 */
@Data
public class CustomerIdNameAndAddressVO {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("仓库名称")
    private String customerName;

    @ApiModelProperty("地址")
    private String address;
}
