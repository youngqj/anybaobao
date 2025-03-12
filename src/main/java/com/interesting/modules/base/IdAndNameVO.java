package com.interesting.modules.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/15 9:56
 * @VERSION: V1.0
 */
@Data
public class IdAndNameVO {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("名称")
    private String name;

}
