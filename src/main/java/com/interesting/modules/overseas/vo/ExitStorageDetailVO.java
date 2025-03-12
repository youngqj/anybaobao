package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExitStorageDetailVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "出库单号")
    private String warehouseExitNo;

    @ApiModelProperty(value = "单据日期")
    private Date receiptDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    private List<ExitStorageDetailItemVO> details;
}
