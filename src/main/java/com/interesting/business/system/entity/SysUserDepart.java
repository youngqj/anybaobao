package com.interesting.business.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.interesting.common.system.base.entity.InterestingEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@TableName("sys_user_depart")
public class SysUserDepart implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 部门id
     */
    private String depId;
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;

    public SysUserDepart(String id, String userId, String depId) {
        this.id = id;
        this.userId = userId;
        this.depId = depId;
    }

    public SysUserDepart(String id, String departId) {
        this.userId = id;
        this.depId = departId;
    }

    public SysUserDepart(){

    }
}
