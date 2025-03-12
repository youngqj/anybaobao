package com.interesting.modules.commission.dto;

import lombok.Data;

@Data
public class QueryXqOrderCommissionDTO {

    private Integer pageNo = 1;

    private Integer pageSize = 10;
}