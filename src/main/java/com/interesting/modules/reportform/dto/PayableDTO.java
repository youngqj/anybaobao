package com.interesting.modules.reportform.dto;

import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/19 15:57
 * @Project: trade-manage
 * @Description:
 */

@Data
public class PayableDTO {

    private Integer pageNo = 1;
    private Integer pageSize = 10;

    private String order = "desc";

    private String column;

    private String exportColumn;

    @ApiModelProperty(value = "汇率")
    private BigDecimal exchangeRate = new BigDecimal(6.5);
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
    @ApiModelProperty(value = "订单号")
    private String orderNum;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "首付款开始日期")
    private String firstDateStart;
    @ApiModelProperty(value = "首付款结束日期")
    private String firstDateEnd;
    @ApiModelProperty(value = "尾款款开始日期")
    private String expireDateStart;
    @ApiModelProperty(value = "尾款款结束日期")
    private String expireDateEnd;

    @ApiModelProperty(value = "出货方式")
    private String chfs;
    @ApiModelProperty(value = "出货方式",hidden = true)
    private List<String> chfsList;

}
