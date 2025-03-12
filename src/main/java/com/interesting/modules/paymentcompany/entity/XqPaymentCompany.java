package com.interesting.modules.paymentcompany.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 付款公司
 * @Author: jeecg-boot
 * @Date:   2025-03-06
 * @Version: V1.0
 */
@Data
@TableName("xq_payment_company")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="xq_payment_company对象", description="付款公司")
public class XqPaymentCompany implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**公司名称*/
    @ApiModelProperty(value = "公司名称")
    private String companyName;
	/**联系人名称*/
    @ApiModelProperty(value = "联系人名称")
    private String contactName;
	/**联系人联系方式*/
    @ApiModelProperty(value = "联系人联系方式")
    private String contactPhone;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**逻辑删除 0否 1是*/
    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;
}
