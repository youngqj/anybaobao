package com.interesting.business.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.interesting.common.aspect.annotation.Dict;
import com.interesting.common.system.base.entity.InterestingEntity;
import com.interesting.common.validation.ValidationGroups;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
public class SysUserVo extends InterestingEntity {

    private static final long serialVersionUID = 1L;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * md5密码盐
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "街道id")
    private String streetId;
    @ApiModelProperty(value = "社区id")
    private String communityId;
    @ApiModelProperty(value = "小区id")
    private String villageId;

    @ApiModelProperty(value = "街道名称")
    private String streetIdName;
    @ApiModelProperty(value = "社区名称")
    private String communityIdName;
    @ApiModelProperty(value = "小区名称")
    private String villageIdName;
}
