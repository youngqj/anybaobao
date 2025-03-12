package com.interesting.modules.overseas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("xq_overseas_transfer_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqOverseasTransferDetail {
    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "库存表id")
    private String sourceId;

    @ApiModelProperty(value = "初始来源单据号")
    private String sourceRepNum;

    @ApiModelProperty(value = "原仓库id")
    private String sourceWarehouseId;

    @ApiModelProperty(value = "原仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "新仓库lot")
    private String newWarehouseLot;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "当前库存")
    private Integer currentInventory;

    @ApiModelProperty(value = "调拨数量")
    private Integer transferInventory;

    @ApiModelProperty(value = "原仓库id")
    private String transferWarehouseId;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;
}
