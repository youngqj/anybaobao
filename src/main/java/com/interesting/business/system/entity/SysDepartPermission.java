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
 * @Description: 部门权限表
 * @Author: interesting-boot
 * @Date:   2020-02-11
 * @Version: V1.0
 */
@Data
@TableName("sys_depart_permission")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_depart_permission对象", description="部门权限表")
public class SysDepartPermission {

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private String id;
	/**部门id*/
    @ApiModelProperty(value = "部门id")
	private String departId;
	/**权限id*/
    @ApiModelProperty(value = "权限id")
	private String permissionId;
	/**数据规则id*/
	@ApiModelProperty(value = "数据规则id")
	private String dataRuleIds;

	public SysDepartPermission() {

	}

	public SysDepartPermission(String departId, String permissionId) {
		this.departId = departId;
		this.permissionId = permissionId;
	}
}
