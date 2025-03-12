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
import com.interesting.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * @Description: 部门角色
 * @Author: interesting-boot
 * @Date:   2020-02-12
 * @Version: V1.0
 */
@Data
@TableName("sys_depart_role")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_depart_role对象", description="部门角色")
public class SysDepartRole {

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private String id;
	/**部门id*/
	@ApiModelProperty(value = "部门id")
	@Dict(dictTable ="sys_depart",dicText = "depart_name",dicCode = "id")
	private String departId;
	/**部门角色名称*/
    @ApiModelProperty(value = "部门角色名称")
	private String roleName;
	/**部门角色编码*/
    @ApiModelProperty(value = "部门角色编码")
	private String roleCode;
	/**描述*/
    @ApiModelProperty(value = "描述")
	private String description;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**更新人*/

    @ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;


}
