package com.interesting.modules.overseas.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EnterStoragePageVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "入库单号")
    private String warehouseEnterNo;

    @ApiModelProperty(value = "来源订单号")
    private String orderNum;

    @ApiModelProperty(value = "商品信息")
    private String productInfo;

    @ApiModelProperty(value = "单据日期")
    private Date receiptDate;


    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;
}
