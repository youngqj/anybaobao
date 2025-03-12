package com.interesting.business.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 * <p>
 *
 * @author cc
 * @version 1.0
 * @date 2021/11/8
 */
@Data
public class SysPermissionApplicationSys {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 子系统名称
     */
    @ApiModelProperty(value = "系统名称")
    private String name;
    /**
     * 子系统图片
     */
    @ApiModelProperty(value = "子系统图片")
    private String url;
    /**
     * 子系统图片
     */
    @ApiModelProperty(value = "系统跳转路由")
    private String reactUrl;

    /**
     * 菜单排序
     */
    private Double sortNo;
    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 删除状态 0正常 1已删除
     */
    private Integer izDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;


}
