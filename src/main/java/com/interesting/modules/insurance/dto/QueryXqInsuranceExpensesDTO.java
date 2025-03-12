package com.interesting.modules.insurance.dto;

import lombok.Data;

@Data
public class QueryXqInsuranceExpensesDTO {

    private Integer pageNo = 1;

    private Integer pageSize = 10;
}