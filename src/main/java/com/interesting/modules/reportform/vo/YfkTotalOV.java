package com.interesting.modules.reportform.vo;

import com.interesting.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/4/11 10:56
 * @Project: trade-manage
 * @Description:
 */

@Data
public class YfkTotalOV extends SumVO {

    @Excel(name = "到期应付金额CNY")
    BigDecimal dqyfkCNY = BigDecimal.ZERO;
    @Excel(name = "到期应付金额USD")
    BigDecimal dqyfkUSD = BigDecimal.ZERO;
    @Excel(name = "总应付金额CNY")
    BigDecimal zyfkCNY = BigDecimal.ZERO;
    @Excel(name = "总应付金额USD")
    BigDecimal zyfkUSD = BigDecimal.ZERO;
    @Excel(name = "预扣款金额CNY")
    BigDecimal ykkCNY = BigDecimal.ZERO;
    @Excel(name = "预扣款金额USD")
    BigDecimal ykkUSD = BigDecimal.ZERO;
}
