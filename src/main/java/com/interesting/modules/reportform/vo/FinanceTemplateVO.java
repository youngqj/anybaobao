package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interesting.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;

@Data
public class FinanceTemplateVO {

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty("序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "开船日期")
    private String etd;

    @ApiModelProperty("原料供应商")
    private String supplierName;

    @ApiModelProperty("中文名")
    private String productName;

    @ApiModelProperty("原料采购id")
    private String rawPurchaseId;

    @ApiModelProperty("采购币种")
    private String currency;

    @ApiModelProperty(value = "采购数量MT")
    private BigDecimal weight;

    @ApiModelProperty(value = "采购单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "采购总金额")
    private BigDecimal purchaseAmount;

    @ApiModelProperty(value = "开票资料")
    private String kpzl;

    @ApiModelProperty(value = "进项开票日期")
    private String jxkprq;

    @ApiModelProperty(value = "进项发票号")
    private String jxfph;

    @ApiModelProperty(value = "发票金额")
    private BigDecimal fpje;

    @ApiModelProperty(value = "发票税额")
    private BigDecimal fpse;

    @ApiModelProperty(value = "报关单号码")
    private String exportDeclarationNum;


    @ApiModelProperty(value = "出口日期")
    private String ckrq;

    @ApiModelProperty(value = "HS编码")
    private String hsCode;

    @ApiModelProperty(value = "报关金额CFR")
    private BigDecimal bgjeCfr;

    @ApiModelProperty(value = "海运费")
    private BigDecimal hyf;

    @ApiModelProperty(value = "报关金额FOB")
    private BigDecimal bgjeFob;

    @ApiModelProperty(value = "开票汇率")
    private BigDecimal kphl;

    @ApiModelProperty(value = "出口发票金额CNY")
    private BigDecimal ckfpje;

    @ApiModelProperty(value = "出口发票日期")
    private String ckfprq;

    @ApiModelProperty(value = "出口发票号码")
    private String ckfphm;

    @ApiModelProperty(value = "退税率")
    private BigDecimal tsl;

    @ApiModelProperty(value = "退税时间")
    private String tssj;

    @ApiModelProperty(value = "退税金额")
    private BigDecimal tsje;

    @ApiModelProperty(value = "收到退税时间")
    private String sdtssj;

    @ApiModelProperty(value = "收汇金额")
    private BigDecimal shje;

    @ApiModelProperty(value = "收汇时间")
    private String shsj;

    @ApiModelProperty(value = "收汇银行")
    private String shyh;

    @ApiModelProperty(value = "出口发票抬头")
    private String ckfptt;

    @ApiModelProperty(value = "版面要求")
    private String layoutRequirements;


    @ApiModelProperty(value = "销售币种")
    private String salesCurrency;

    @ApiModelProperty(value = "出货方式")
    private String chfs;

    @ApiModelProperty(value = "二次收汇金额")
    private BigDecimal shje2;

    @ApiModelProperty(value = "二次收汇时间")
    private String shsj2;

    @ApiModelProperty(value = "末次收汇金额")
    private BigDecimal shje3;

    @ApiModelProperty(value = "末次收汇时间")
    private String shsj3;

    /**
     * 生成一个包含订单多个属性的唯一键。
     * 该方法将订单号、产品名称、供应商名称、重量、单位价格、版面要求和采购总金额组合成一个字符串，用作订单的唯一标识。
     *
     * @return 返回一个字符串，包含订单的多个属性，各属性之间以"-"分隔。
     */
    public String getKey() {
        // 将订单的各个属性拼接成一个字符串，用于生成唯一键
        return orderNum + "-" + productName + "-" + supplierName + "-" + weight.setScale(2) + "-" + unitPrice.setScale(4) + "-" + layoutRequirements + "-" + purchaseAmount.setScale(2);
    }

}
