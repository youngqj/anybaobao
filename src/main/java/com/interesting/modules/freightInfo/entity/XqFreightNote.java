package com.interesting.modules.freightInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("xq_freight_note")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "xq_freight_note对象", description = "货运情况表")
public class XqFreightNote {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String orderId;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer sortNum;

    /**
     * 录入人
     */
    @ApiModelProperty(value = "录入人")
    private String createPerson;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date insuranceDate;

    /**
     * 事项
     */
    @ApiModelProperty(value = "事项")
    private String transInfo;

    /**
     * 单据证明
     */
    @ApiModelProperty(value = "单据证明")
    private String photos;

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
