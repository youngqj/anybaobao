package com.interesting.common.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 在线用户信息
 * </p>
 *
 * @author: interesting-boot
 * @since 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUser {

    private String id;
    /**
     * 登录人名字
     */
    private String realname;

    private String username;

    private Integer status;
    /**
     * 电话
     */
    private String phone;
    private String password;

    private Integer roleCode;

    private List<Integer> roleCodes;

    private String departIds;
    private Integer userIdentity;
    private String orgCode;
    private Date createTime;

}
