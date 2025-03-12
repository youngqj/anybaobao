package com.interesting.common.api.dto;

import com.interesting.common.validation.MinAndMax;
import io.swagger.annotations.ApiModelProperty;


/**
 * @author cc
 * @date 2020/12/25 15:59
 * 查询基础dto
 */

public class QueryBaseDto {

    @ApiModelProperty(value = "页码", name = "pageNo", example = "1")
    @MinAndMax(min = 0, max = Integer.MAX_VALUE)
    private Integer pageNo = 1;

    @ApiModelProperty(value = "限制条数", name = "pageSize", example = "20")
    @MinAndMax(min = 0, max = 100)
    private Integer pageSize = 10;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
