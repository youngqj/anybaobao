package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EnterStorageDetailVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "入库单号")
    private String warehouseEnterNo;

    @ApiModelProperty(value = "来源订单号")
    private String orderNum;

    @ApiModelProperty(value = "单据日期")
    private Date receiptDate;

    private List<EnterStorageDetailItemVO> details;
}
