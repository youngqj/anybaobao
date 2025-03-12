package com.interesting.modules.paymentmethod.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 付款方式表(XqOrderPaymentMethod)实体类
 *
 * @author 郭征宇
 * @since 2023-09-15 11:51:47
 */

@Data
@EqualsAndHashCode
@ApiModel(value = "XqOrderPaymentMethod表",description = ("付款方式表"))
@TableName("xq_order_payment_method")
public class XqOrderPaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;
    /**
     * 付款方式显示的值
     */     
    @ApiModelProperty("付款方式显示的值")
    private String text;
    /**
     * 实际天数
     */     
    @ApiModelProperty("实际天数")
    private Integer value;
    /**
     * 创建人
     */     
    @ApiModelProperty("创建人")
    private String createBy;
    /**
     * 创建时间
     */     
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 更新人
     */     
    @ApiModelProperty("更新人")
    private String updateBy;
    /**
     * 更新时间
     */     
    @ApiModelProperty("更新时间")
    private Date updateTime;
    /**
     * 逻辑删除 0否 1是
     */     
    @ApiModelProperty("逻辑删除 0否 1是")
    private Integer izDelete;


}

