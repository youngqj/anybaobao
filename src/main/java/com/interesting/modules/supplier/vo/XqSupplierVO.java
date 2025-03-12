package com.interesting.modules.supplier.vo;

import com.interesting.common.annotation.Excel;
import com.interesting.modules.freightcost.vo.XqFreightCostInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class XqSupplierVO {

	@Excel(name = "序号")
	@ApiModelProperty(value = "序号")
	private int serialNumber;
	/**id*/
    @ApiModelProperty(value = "id")
	private String id;
	/**供应商名称*/
	@Excel(name = "供应商名称",width = 38)
    @ApiModelProperty(value = "供应商名称")
	private String name;
	/**供应商简称*/
	@Excel(name = "供应商简称",width = 15)
    @ApiModelProperty(value = "供应商简称")
	private String abbr;
	/**税号*/
	@Excel(name = "税号",width = 15)
    @ApiModelProperty(value = "税号")
	private String taxNum;

	@Excel(name = "税率",width = 15)
	@ApiModelProperty("税率%")
	private BigDecimal taxRate;
	/**联系人*/
	@Excel(name = "联系人",width = 15)
    @ApiModelProperty(value = "联系人")
	private String contactor;
	/**公司邮箱*/
	@Excel(name = "公司邮箱",width = 20)
    @ApiModelProperty(value = "公司邮箱")
	private String email;
	/**联系电话*/
	@Excel(name = "联系电话",width = 20)
    @ApiModelProperty(value = "联系电话")
	private String phone;
	/**公司地址*/
	@Excel(name = "公司地址",width = 38)
    @ApiModelProperty(value = "公司地址")
	private String address;
	/**开户银行*/
	@Excel(name = "开户银行",width = 28)
    @ApiModelProperty(value = "开户银行")
	private String bankDeposit;
	/**银行卡号*/
	@Excel(name = "银行卡号",width = 28)
    @ApiModelProperty(value = "银行卡号")
	private String bankCardNumber;
	/**备注说明*/
	@Excel(name = "备注说明",width = 38)
    @ApiModelProperty(value = "备注说明")
	private String remark;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;

	@ApiModelProperty("供应商类型：1原料供应商，2辅料供应商")
	private String type;

	@Excel(name = "供应商类型",width = 15)
	@ApiModelProperty("供应商类型")
	private String typeTxt;

	@ApiModelProperty("货运费用")
	private List<XqFreightCostInfoVO> freightCostInfos;
}
