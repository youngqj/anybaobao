package com.interesting.business.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.interesting.common.aspect.annotation.Dict;
import com.interesting.common.system.base.entity.InterestingEntity;
import com.interesting.common.validation.ValidationGroups;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author: interesting-boot
 * @since 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser extends InterestingEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 企业id
     */
    @ApiModelProperty(value = "所属企业")
    @NotNull(groups = ValidationGroups.Add.class, message = "企业id不能为空")
    @NotNull(groups = ValidationGroups.Update.class, message = "企业id不能为空")
    private String enterpriseInfoId;
    /**
     * 登录账号
     */
    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "姓名")
    private String realname;
    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * md5密码盐
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    @Dict(dicCode = "sex")
    @ApiModelProperty(value = "性别 对应字典code：sex")
    private Integer sex;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 部门code(当前选择登录部门)
     */
    private String orgCode;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "所属部门显示")
    private transient String orgCodeTxt;

    /**
     * 状态(1：正常  2：冻结 ）
     */
    @Dict(dicCode = "user_status")
    private Integer status;


    /**
     * 工号，唯一键
     */
    private String workNo;

    /**
     * 职务，关联职务表
     */
    @Dict(dictTable = "sys_position", dicText = "name", dicCode = "code")
    private String post;

    /**
     * 座机号
     */
    private String telephone;

    /**
     * 同步工作流引擎1同步0不同步
     */
    private Integer activitiSync;

    /**
     * 身份（0 普通成员 1 上级）
     */
    private Integer userIdentity;

    /**
     * 负责部门
     */
    @Dict(dictTable = "sys_depart", dicText = "depart_name", dicCode = "id")
    private String departIds;

    /**
     * 设备id uniapp推送用
     */
    private String clientId;

    @TableField(exist = false)
    private String selectedroles;

    @TableField(exist = false)
    @ApiModelProperty(value = "所属部门")
    private String selecteddeparts;

    @ApiModelProperty(value = "身份证号")
    private String identityCard;

    @ApiModelProperty("单位")
    private String unit;

}
