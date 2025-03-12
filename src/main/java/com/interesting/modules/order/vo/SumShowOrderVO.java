package com.interesting.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2024/1/26 16:46
 * @VERSION: V1.0
 */
@Data
public class SumShowOrderVO {

    private String currency;

    private Map<String, BigDecimal> map;
}
