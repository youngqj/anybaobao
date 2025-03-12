package com.interesting.modules.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.io.Serializable;

/**
 * 面单管理-转让记录表(XqOrderTransferRecord)实体类
 *
 * @author 郭征宇
 * @since 2023-09-15 09:19:48
 */

@Data
@EqualsAndHashCode
@ApiModel(value = "XqOrderTransferRecord表",description = ("面单管理-转让记录表"))
@TableName("xq_order_transfer_record")
public class XqOrderTransferRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;
    /**
     * 订单id
     */     
    @ApiModelProperty("订单id")
    private String orderId;
    /**
     * 转让发起人
     */     
    @ApiModelProperty("转让发起人")
    private String transferFrom;
    /**
     * 转让目标
     */     
    @ApiModelProperty("转让目标")
    private String transferTo;
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

