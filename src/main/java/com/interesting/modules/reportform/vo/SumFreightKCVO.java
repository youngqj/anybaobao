package com.interesting.modules.reportform.vo;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2024/1/23 15:30
 * @VERSION: V1.0
 */


@Data
@EqualsAndHashCode(callSuper = true)
public class SumFreightKCVO extends SumVO{

    @Excel(name = "卡车费用合计")
    @ApiModelProperty("卡车费用合计")
    private BigDecimal totalFreightKC = BigDecimal.ZERO;;

}
