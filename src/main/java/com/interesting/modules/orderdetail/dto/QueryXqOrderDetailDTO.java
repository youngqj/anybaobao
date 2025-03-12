package com.interesting.modules.orderdetail.dto;

import lombok.Data;

@Data
public class QueryXqOrderDetailDTO {

    private Integer pageNo = 1;

    private Integer pageSize = 10;
}