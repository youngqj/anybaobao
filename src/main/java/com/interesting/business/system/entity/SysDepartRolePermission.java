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
 * @Description: 部门角色权限
 * @Author: interesting-boot
 * @Date:   2020-02-12
 * @Version: V1.0
 */
@Data
@TableName("sys_depart_role_permission")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_depart_role_permission对象", description="部门角色权限")
public class SysDepartRolePermission {

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private String id;
	/**部门id*/
    @ApiModelProperty(value = "部门id")
	private String departId;
	/**角色id*/
    @ApiModelProperty(value = "角色id")
	private String roleId;
	/**权限id*/
    @ApiModelProperty(value = "权限id")
	private String permissionId;
	/**dataRuleIds*/
    @ApiModelProperty(value = "dataRuleIds")
	private String dataRuleIds;
	/** 操作时间 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "操作时间")
	private Date operateDate;
	/** 操作ip */
	private String operateIp;

	public SysDepartRolePermission() {
	}

	public SysDepartRolePermission(String roleId, String permissionId) {
		this.roleId = roleId;
		this.permissionId = permissionId;
	}
}
