package com.interesting.modules.accmaterial.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class XqAccessoryInfoVO {
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "辅料名称")
    private String accessoryName;

    @ApiModelProperty(value = "尺寸")
    private String size;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "库存")
    private BigDecimal skuNum;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty("辅料分类id")
    private String categoryId;

    @ApiModelProperty("辅料分类名称")
    private String categoryName;

    @ApiModelProperty("材质规格")
    private String materialSpecification;

}
