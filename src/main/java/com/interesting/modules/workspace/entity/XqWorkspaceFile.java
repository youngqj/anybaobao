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
 * 工作区文件表(XqWorkspaceFile)实体类
 *
 * @author 郭征宇
 * @since 2023-11-13 10:01:28
 */

@Data
@EqualsAndHashCode
@ApiModel(value = "XqWorkspaceFile表",description = ("工作区文件表"))
@TableName("xq_workspace_file")
public class XqWorkspaceFile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;
    /**
     * 所属角色id
     */     
    @ApiModelProperty("所属角色id")
    private String roleId;
    /**
     * 所属目录id
     */     
    @ApiModelProperty("所属目录id")
    private String catalogId;
    /**
     * 文件名称
     */     
    @ApiModelProperty("文件名称")
    private String fileName;
    /**
     * 文件类型
     */     
    @ApiModelProperty("文件类型")
    private String fileType;
    /**
     * 文件大小
     */     
    @ApiModelProperty("文件大小")
    private String fileSize;
    /**
     * 文件地址
     */     
    @ApiModelProperty("文件地址")
    private String fileUrl;
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


}

