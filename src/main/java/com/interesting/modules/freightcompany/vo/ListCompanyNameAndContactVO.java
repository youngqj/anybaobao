package com.interesting.modules.freightcompany.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/12/25 16:55
 * @VERSION: V1.0
 */
@Data
public class ListCompanyNameAndContactVO {
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
    @ApiModelProperty("联系人名称+电话")
    private String contactNameAndPhone;

}
