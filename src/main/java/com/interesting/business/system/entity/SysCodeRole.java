package com.interesting.business.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 单据编码规则表
 * @Author: interesting-boot
 * @Date:   2021-07-31
 * @Version: V1.0
 */
@Data
@TableName("sys_code_role")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sys_code_role对象", description="单据编码规则表")
public class SysCodeRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**编码类型*/
    @ApiModelProperty(value = "编码类型")
    private Integer type;
    /**固定开头*/
    @ApiModelProperty(value = "固定开头")
    private String head;
    /**流水规则*/
    @ApiModelProperty(value = "流水规则")
    private Integer roleNy;
    /**流水位数*/
    @ApiModelProperty(value = "流水位数")
    private Integer roleBit;
    /**当前流水号*/
    @ApiModelProperty(value = "当前流水号")
    private Integer nowNum;
}
