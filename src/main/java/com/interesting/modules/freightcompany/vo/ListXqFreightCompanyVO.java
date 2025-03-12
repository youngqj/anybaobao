package com.interesting.modules.freightcompany.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/12/25 16:28
 * @VERSION: V1.0
 */
@Data
public class ListXqFreightCompanyVO {

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
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

}
