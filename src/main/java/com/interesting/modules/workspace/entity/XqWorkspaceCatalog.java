package com.interesting.modules.workspace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 工作区目录表(XqWorkspaceCatalog)实体类
 *
 * @author 郭征宇
 * @since 2023-11-13 10:01:12
 */

@Data
@EqualsAndHashCode
@ApiModel(value = "XqWorkspaceCatalog表",description = ("工作区目录表"))
@TableName("xq_workspace_catalog")
public class XqWorkspaceCatalog implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;
    /**
     * 角色id
     */     
    @ApiModelProperty("角色id")
    private String roleId;
    /**
     * 上级目录id
     */
    @ApiModelProperty("上级目录id")
    private String pId;
    /**
     * 目录名称
     */     
    @ApiModelProperty("目录名称")
    private String catalogName;
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

    /**
     * 是否空运 0否 1是
     */
    @ApiModelProperty("是否空运 0否 1是")
    private Integer izAir;

}

