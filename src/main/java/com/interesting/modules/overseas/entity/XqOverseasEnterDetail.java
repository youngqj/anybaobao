package com.interesting.modules.overseas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("xq_overseas_enter_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqOverseasEnterDetail implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "来源订单id")
    private String orderDetailId;

    @ApiModelProperty(value = "主表id")
    private String sourceId;

    @ApiModelProperty(value = "仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "仓库lot")
    private String warehouseLot;

    @ApiModelProperty(value = "产品id")
    private String productId;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "入库数量")
    private Integer enterNum;

    @ApiModelProperty(value = "入库单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "入库金额")
    private BigDecimal enterMoney;

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

    @ApiModelProperty(value = "每箱毛重")
    private BigDecimal grossWeightPerBox;

    @ApiModelProperty(value = "总毛重")
    private BigDecimal grossWeightTotal;

    @ApiModelProperty(value = "托盘数量")
    private Integer palletNum;
}