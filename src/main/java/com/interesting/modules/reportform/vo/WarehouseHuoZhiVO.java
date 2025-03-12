package com.interesting.modules.reportform.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/15 11:23
 * @Project: trade-manage
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseHuoZhiVO {

    private String WarehouseName;

    private BigDecimal num = BigDecimal.ZERO;
}
