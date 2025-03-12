package com.interesting.modules.customer.vo;

import com.interesting.common.annotation.Excel;
import com.interesting.modules.commissioncompany.dto.AddCommissionCompanyDTO;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.vo.XqFreightCostInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class XqCustomerVO {

	@Excel(name = "序号",width = 10)
	@ApiModelProperty("序号")
	private Integer serialNumber;
	/**id*/
    @ApiModelProperty(value = "id")
	private String id;
	/**客户姓名*/

	@Excel(name = "客户名称",width = 38)
    @ApiModelProperty(value = "客户姓名")
	private String name;
	/**客户简称*/

	@Excel(name = "客户简称")
    @ApiModelProperty(value = "客户简称")
	private String abbr;
	/**税号*/
	@Excel(name = "税号",width = 15)
    @ApiModelProperty(value = "税号")
	private String taxNum;
	/**联系人*/
	@Excel(name = "联系人",width = 15)
    @ApiModelProperty(value = "联系人")
	private String contactor;
	/**公司邮箱*/
	@Excel(name = "公司邮箱",width = 30)
    @ApiModelProperty(value = "公司邮箱")
	private String email;
	/**联系电话*/
	@Excel(name = "联系电话",width = 20)
    @ApiModelProperty(value = "联系电话")
	private String phone;
	/**公司地址*/
	@Excel(name = "公司地址",width = 66)
    @ApiModelProperty(value = "公司地址")
	private String address;
	/**开户银行*/
	@Excel(name = "开户银行",width = 20)
    @ApiModelProperty(value = "开户银行")
	private String bankDeposit;
	/**银行卡号*/
	@Excel(name = "银行卡号",width = 20)
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

//	@ApiModelProperty("佣金公司")
//	private List<CommissionCompanyVO> commissionCompanys;

	@ApiModelProperty("货运费用")
	private List<XqFreightCostInfoVO> freightCostInfos;

}
