package com.interesting.modules.freightInfo.dto;

import lombok.Data;

@Data
public class QueryXqFreightInfoDTO {

    private Integer pageNo = 1;

    private Integer pageSize = 10;
}