package com.interesting.modules.overseas.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class InstUptExitStorageDTO {
    @ApiModelProperty(value = "主键")
    private String id;

//    @ApiModelProperty(value = "出库单号")
//    private String warehouseExitNo;

//    @ApiModelProperty(value = "来源订单号")
//    private String orderNum;

    @ApiModelProperty(value = "单据日期")
    private Date receiptDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @NotNull
    private List<InstUptExitStorageDetailItemDTO> details;
}
