package com.interesting.modules.overseas.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分理费
 *
 * @TableName xq_split_fee
 */
@Data
@TableName("xq_split_fee")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqSplitFee {
    /**
     * id
     */
    @ApiModelProperty("主键id")
    private String id;

    /**
     * 仓库id
     */
    @ApiModelProperty("仓库id")
    private String warehouseFeeId;

    /**
     * 区间开始
     */
    @ApiModelProperty("区间开始")
    private Integer lotStart;

    /**
     * 区间结束
     */
    @ApiModelProperty("区间结束")
    private Integer lotEnd;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private BigDecimal price;

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
