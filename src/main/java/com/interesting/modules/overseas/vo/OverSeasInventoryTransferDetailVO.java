package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class OverSeasInventoryTransferDetailVO {
    private String id;

    @ApiModelProperty(value = "单据编号")
    private String repNum;

    @ApiModelProperty(value = "调拨人id")
    private String transferUserId;

    @ApiModelProperty(value = "调拨人")
    private String transferUserName;

    @ApiModelProperty(value = "调拨仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "调拨仓库名称")
    private String warehouseName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "调拨时间")
    private Date transferTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("调拨详细记录")
    List<OverSeasInventoryTransferDetailRecordVO> transferDetails;
}
