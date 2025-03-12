package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OverSeasInventoryTransferPageVO {
    private String id;

    @ApiModelProperty(value = "lot编号")
    private String warehouseLot;

    @ApiModelProperty("商品名称")
    private String productName;


    @ApiModelProperty("调拨人名称")
    private String transUserName;

    @ApiModelProperty("调拨时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transferTime;

    @ApiModelProperty("调出仓库名称")
    private String warehouseName;


    @ApiModelProperty("调入仓库名称")
    private String warehouseName1;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;
}
