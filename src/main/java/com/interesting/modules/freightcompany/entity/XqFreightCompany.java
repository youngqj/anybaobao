package com.interesting.modules.freightcompany.entity;

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
 * 货代公司(XqFreightCompany)实体类
 *
 * @author 郭征宇
 * @since 2023-12-25 16:27:00
 */

@Data
@EqualsAndHashCode
@ApiModel(value = "XqFreightCompany表",description = ("货代公司"))
@TableName("xq_freight_company")
public class XqFreightCompany implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;
    /**
     * 公司名称
     */     
    @ApiModelProperty("公司名称")
    private String companyName;
    /**
     * 联系人名称
     */     
    @ApiModelProperty("联系人名称")
    private String contactName;
    /**
     * 联系人联系方式
     */     
    @ApiModelProperty("联系人联系方式")
    private String contactPhone;
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

