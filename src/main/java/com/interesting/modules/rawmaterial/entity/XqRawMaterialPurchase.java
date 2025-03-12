package com.interesting.modules.rawmaterial.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 原料采购表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
@Data
@TableName("xq_raw_material_purchase")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="xq_raw_material_purchase对象", description="原料采购表")
public class XqRawMaterialPurchase {

	/**ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
	private String id;

	private Integer sortNum;

	/**订单表id*/
    @ApiModelProperty(value = "订单表id")
	private String orderId;
	/**原料供应商id*/
    @ApiModelProperty(value = "原料供应商id")
	private String supplierId;
	/**采购合同号*/
    @ApiModelProperty(value = "采购合同号")
	private String purchaseContract;

    /**
     * 补充协议号
     */
    @ApiModelProperty(value = "补充协议号")
    private String agreementNo;
	/**重量*/
    @ApiModelProperty(value = "重量")
	private java.math.BigDecimal weight;

	@ApiModelProperty("质量单位id")
	private String weightUnit;
	/**币种*/
    @ApiModelProperty(value = "币种")
	private String currency;
	/**采购单价*/
    @ApiModelProperty(value = "采购单价")
	private java.math.BigDecimal unitPrice;

    /**
     * 实际采购单价
     */
    @ApiModelProperty(value = "实际采购单价")
    private java.math.BigDecimal realUnitPrice;
	/**采购金额*/
    @ApiModelProperty(value = "采购金额")
	private java.math.BigDecimal purchaseAmount;
	/**退税率*/
    @ApiModelProperty(value = "退税率")
	private java.math.BigDecimal taxRefundRate;
	/**退税金额*/
    @ApiModelProperty(value = "退税金额")
	private java.math.BigDecimal taxRefundAmount;
	/**采购备注*/
    @ApiModelProperty(value = "采购备注")
	private String purchaseNote;
	/**付款时间*/
    @ApiModelProperty(value = "付款时间")
	private java.util.Date paymentTime;
	/**付款金额*/
    @ApiModelProperty(value = "付款金额")
	private java.math.BigDecimal paymentAmount;
    /**
     * 扣款金额
     */
    @ApiModelProperty(value = "扣款金额")
    private java.math.BigDecimal cutAmount;
    /**
     * 未付款金额
     */
    @ApiModelProperty(value = "未付款金额")
    private java.math.BigDecimal unPaymentAmount;
	/**付款状态*/
    @ApiModelProperty(value = "付款状态")
	private java.math.BigDecimal paymentStatus;

	@ApiModelProperty("全额付款状态")
	private Integer financeCompleteState;

	/**付款到期日*/
	@ApiModelProperty(value = "付款到期日")
	private java.util.Date paymentExpireDate;

	/**财务备注*/
    @ApiModelProperty(value = "财务备注")
	private String financialNote;
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

	@ApiModelProperty(value = "产品id")
	private String productId;


    /**
     * 版面要求
     */
    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;

	@ApiModelProperty(value = "采购方式（字典）")
	private String purchaseType;

	@ApiModelProperty("辅料分类id")
	private String categoryId;

	/**包装方式*/
	@ApiModelProperty(value = "包装方式")
	private String packaging;

	/**
     * 首付款到期日
	 */
    @ApiModelProperty(value = "首付款到期日")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private java.util.Date firstPaymentExpireDate;

    /**
     * 首付款金额
     */
    @ApiModelProperty(value = "首付款金额")
    private java.math.BigDecimal firstPayment;

    /**
     * 尾付款金额
     */
    @ApiModelProperty(value = "尾付款金额")
    private java.math.BigDecimal lastPayment;


    /**
     * 首付款比例
     */
    @ApiModelProperty(value = "首付款比例")
    private java.math.BigDecimal firstPaymentRate;


	@ApiModelProperty(value = "开票资料")
	private String kpzl;

	@ApiModelProperty(value = "进项开票日期")
    private String jxkprq;

	@ApiModelProperty(value = "进项发票号")
	private String jxfph;

	@ApiModelProperty(value = "发票金额")
	private java.math.BigDecimal fpje;

	@ApiModelProperty(value = "发票税额")
	private java.math.BigDecimal fpse;


	@ApiModelProperty(value = "出口日期")
    private String ckrq;

	@ApiModelProperty(value = "HS编码")
	private String hsCode;

	@ApiModelProperty(value = "报关金额CFR")
	private java.math.BigDecimal bgjeCfr;

	@ApiModelProperty(value = "海运费")
	private java.math.BigDecimal hyf;

	@ApiModelProperty(value = "报关金额FOB")
	private java.math.BigDecimal bgjeFob;

	@ApiModelProperty(value = "开票汇率")
	private java.math.BigDecimal kphl;

	@ApiModelProperty(value = "出口发票金额CNY")
	private java.math.BigDecimal ckfpje;

	@ApiModelProperty(value = "出口发票日期")
    private String ckfprq;

	@ApiModelProperty(value = "出口发票号码")
	private String ckfphm;

	@ApiModelProperty(value = "退税率")
	private java.math.BigDecimal tsl;

	@ApiModelProperty(value = "退税时间")
    private String tssj;

	@ApiModelProperty(value = "退税金额")
	private java.math.BigDecimal tsje;

	@ApiModelProperty(value = "收到退税时间")
	private String sdtssj;

	@ApiModelProperty(value = "首次收汇金额")
	private java.math.BigDecimal shje;

	@ApiModelProperty(value = "首次收汇时间")
    private String shsj;
	@ApiModelProperty(value = "二次收汇金额")
	private java.math.BigDecimal shje2;

	@ApiModelProperty(value = "二次收汇时间")
    private String shsj2;
	@ApiModelProperty(value = "末次收汇金额")
	private java.math.BigDecimal shje3;

	@ApiModelProperty(value = "末次收汇时间")
    private String shsj3;

	@ApiModelProperty(value = "收汇银行")
	private String shyh;

	@ApiModelProperty(value = "出口发票抬头")
	private String ckfptt;

	@ApiModelProperty(value = "采购总价CNY")
	private java.math.BigDecimal purchaseAmountCny;

	@ApiModelProperty(value = "预扣金额")
	private java.math.BigDecimal withholdAmount;

	@ApiModelProperty(value = "报检备注")
	private String inspectionNote;
}
