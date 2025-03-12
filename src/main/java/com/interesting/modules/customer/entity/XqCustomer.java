package com.interesting.modules.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 客户表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Data
@TableName("xq_customer")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_customer对象", description="客户表")
public class XqCustomer {

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
	private String id;
	/**客户姓名*/
    @ApiModelProperty(value = "客户姓名")
	private String name;
	/**客户简称*/
    @ApiModelProperty(value = "客户简称")
	private String abbr;
	/**税号*/
    @ApiModelProperty(value = "税号")
	private String taxNum;
	/**联系人*/
    @ApiModelProperty(value = "联系人")
	private String contactor;
	/**公司邮箱*/
    @ApiModelProperty(value = "公司邮箱")
	private String email;
	/**联系电话*/
    @ApiModelProperty(value = "联系电话")
	private String phone;
	/**公司地址*/
    @ApiModelProperty(value = "公司地址")
	private String address;
	/**开户银行*/
    @ApiModelProperty(value = "开户银行")
	private String bankDeposit;
	/**银行卡号*/
    @ApiModelProperty(value = "银行卡号")
	private String bankCardNumber;
	/**备注说明*/
    @ApiModelProperty(value = "备注说明")
	private String remark;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新时间*/
    @ApiModelProperty(value = "更新时间")
	private java.util.Date updateTime;
	/**逻辑删除 0否 1是*/
    @ApiModelProperty(value = "逻辑删除 0否 1是")
	private Integer izDelete;
}
