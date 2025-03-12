package com.interesting.modules.customer.dto;

import com.interesting.modules.commissioncompany.dto.AddCommissionCompanyDTO;
import com.interesting.modules.freightcost.dto.AddXqFreightCostInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AddXqCustomerDTO {
    	/**客户姓名*/
        @ApiModelProperty(value = "客户姓名", required = true)
		@NotBlank(message = "客户姓名不能为空")
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
        @ApiModelProperty(value = "联系电话", required = true)
        //@NotBlank(message = "联系电话不能为空")
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

		@ApiModelProperty(value = "佣金公司信息")
		private List<AddCommissionCompanyDTO> commissionCompanys;

		@ApiModelProperty(value = "货运信息")
		private List<AddXqFreightCostInfoDTO> freightCostInfos;

}