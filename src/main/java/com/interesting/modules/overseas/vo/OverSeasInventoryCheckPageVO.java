package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OverSeasInventoryCheckPageVO {
    private String id;

    @ApiModelProperty(value = "lot编号")
    private String warehouseLot;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("盘点人id")
    private String checkUserId;

    @ApiModelProperty("盘点人名称")
    private String checkUserName;

    @ApiModelProperty("盘点时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    @ApiModelProperty("盘点仓库id")
    private String warehouseId;

    @ApiModelProperty("盘点仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;
}
