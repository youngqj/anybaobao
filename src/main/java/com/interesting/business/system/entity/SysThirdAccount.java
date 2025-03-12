package com.interesting.business.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * @Description: 第三方登录账号表
 * @Author: interesting-boot
 * @Date: 2020-11-17
 * @Version: V1.0
 */
@Data
@TableName("sys_third_account")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "sys_third_account对象", description = "第三方登录账号表")
public class SysThirdAccount {

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private String id;
    /**
     * 第三方登录id
     */
    @ApiModelProperty(value = "第三方登录id")
    private String sysUserId;
    /**
     * 登录来源
     */
    @ApiModelProperty(value = "登录来源")
    private String thirdType;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;
    /**
     * 状态(1-正常,2-冻结)
     */
    @ApiModelProperty(value = "状态(1-正常,2-冻结)")
    private Integer status;
    /**
     * 删除状态(0-正常,1-已删除)
     */
    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    private Integer izDelete;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realname;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String thirdUserUuid;
}
