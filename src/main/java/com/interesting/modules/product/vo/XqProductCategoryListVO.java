package com.interesting.modules.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XqProductCategoryListVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "品种名称")
    private String category;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
