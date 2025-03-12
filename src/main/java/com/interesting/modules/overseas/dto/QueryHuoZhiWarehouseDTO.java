package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/5 9:57
 * @Project: trade-manage
 * @Description:
 */


@Data
public class QueryHuoZhiWarehouseDTO {


    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "产品名称")
    private String productName;

}
