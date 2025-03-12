package com.interesting.modules.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interesting.modules.freightInfo.dto.AddFreightNoteDTO;
import com.interesting.modules.notes.dto.InstOrUpdtNotesDTO;
import com.interesting.modules.order.dto.sub.*;
import com.interesting.modules.overseas.dto.InstUptExitStorageDetailItemDTO;
import com.interesting.modules.truck.dto.InstUptTruckInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EditXqOrderAllDTO {
    @ApiModelProperty(value = "id")
    private String id;

    /**出货序号*/
    @ApiModelProperty(value = "出货序号")
    private String shippingNum;

    /**订单类型(字典 1仓库订单 2客户订单)*/
    @NotBlank(message = "订单类型不能为空")
    @ApiModelProperty(value = "订单类型(字典 1仓库订单 2客户订单)", required = true)
    private String orderType;

    /**客户id*/
    @ApiModelProperty(value = "客户id")
    private String customerId;

    /**客户id*/
    @ApiModelProperty(value = "客户名称")
    //@NotBlank(message = "客户名称不能为空")
    private String customer;

    @ApiModelProperty("仓库id")
    private String warehouseId;

    /**汇率*/
    @ApiModelProperty(value = "汇率")
    private java.math.BigDecimal exchangeRate;

    /**订单号*/
    @ApiModelProperty(value = "订单号")
    private String orderNum;

    /**原始订单号*/
    @ApiModelProperty(value = "原始订单号")
    private String originOrderNum;

    /**发票号*/
    @ApiModelProperty(value = "发票号")
    private String invoiceNum;

    /**发票日期*/
    @ApiModelProperty(value = "发票日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date invoiceDate;

    /**付款方式*/
    @ApiModelProperty(value = "付款方式")
    private String paymentMethod;

    /**要求发货日*/
    @ApiModelProperty(value = "要求发货日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date shippingDateRequired;

    /**送货地址*/
    @ApiModelProperty(value = "送货地址")
    private String deliveryAddress;

    /**
     * 送货地址
     */
    @ApiModelProperty(value = "送货地址id")
    private String deliveryAddressId;

    /**境外收货人*/
    @ApiModelProperty(value = "境外收货人")
    private String overseasReceiver;

    /**境内发货人*/
    @ApiModelProperty(value = "境内发货人")
    private String domesticSender;

    /**特殊要求*/
    @ApiModelProperty(value = "特殊要求")
    private String specialRequirements;

    /**要求交货日*/
    @ApiModelProperty(value = "要求交货日")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date deliveryDateRequired;

    /**送货预约号*/
    @ApiModelProperty(value = "送货预约号")
    private String deliveryBookingNum;

    /**品管人员*/
    @ApiModelProperty(value = "品管人员")
    private String qualityControlPerson;

    /**跟单人员*/
    @ApiModelProperty(value = "跟单人员")
    private String orderFollowUpPerson;

    /**实际交货日*/
    @ApiModelProperty(value = "实际交货日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date actualDeliveryDate;

    /**质量问题说明*/
    @ApiModelProperty(value = "产品质量问题说明")
    private String qualityIssueDescription;

    /**生产日期*/
    @ApiModelProperty(value = "生产日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.util.Date productionDate;

//    @ApiModelProperty("收汇到期日")
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date remittanceExpireDate;

    /**打码内容*/
    @ApiModelProperty(value = "打码内容")
    private String maskedContent;

    /**订单质量问题说明*/
    @ApiModelProperty(value = "订单质量问题说明")
    private String orderQualityRemark;

    /**订单质量问题说明*/
    @ApiModelProperty(value = "原料质量问题说明")
    private String rawQualityRemark;

    /**收汇情况说明*/
    @ApiModelProperty(value = "收汇情况说明")
    private String remittanceRemark;

    /**备注*/
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 差旅费
     */
    @ApiModelProperty(value = "差旅费")
    private java.math.BigDecimal travelFee;

    /**
     * 国外卡车费
     */
    @ApiModelProperty(value = "国外卡车费")
    private java.math.BigDecimal gwkcf;

    @ApiModelProperty("额外费用")
    private List<XqOrderExtraCostDTO> xqOrderExtraCostDTOS;


    @NotNull(message = "收汇到期日列表不能为null")
    @ApiModelProperty(value = "收汇到期日")
    List<AddOrderRemiExpDateDTO> remiExpDateVOS;

    @NotNull(message = "订单产品不能为null")
    @ApiModelProperty(value = "订单产品")
    List<AddOrderProdDTO> orderProdVOS;

    @NotNull(message = "订单佣金不能为null")
    @ApiModelProperty(value = "订单佣金")
    List<AddOrderComsDTO> orderComsVOS;

    @NotNull(message = "订单国内货运费用不能为null")
    @ApiModelProperty(value = "国内货运费用")
    List<AddOrderFretCostDTO> orderFretCostVOS;

    @NotNull(message = "订单国外货运费用不能为null")
    @ApiModelProperty(value = "国外货运费用")
    List<AddOrderFretCostDTO> orderFretCostVOS2;

    @NotNull(message = "退运费用不能为null")
    @ApiModelProperty(value = "退运费用")
    List<AddOrderFretCostDTO> orderFretCostReturnFeeVOS;

    @NotNull(message = "订单货运信息不能为null")
    @ApiModelProperty(value = "货运信息")
    List<AddOrderFretDTO> orderFretVOS;


    @NotNull(message = "货运备注情况不能为null")
    @ApiModelProperty(value = "货运备注情况")
    List<AddFreightNoteDTO> orderFreightNoteVOS;

    @NotNull(message = "订单保险费用信息不能为null")
    @ApiModelProperty(value = "保险费用信息")
    List<AddOrderInsuranceDTO> orderInsuranceVOS;

    @NotNull(message = "订单产品原料不能为null")
    @ApiModelProperty(value = "产品原料")
    List<AddOrderRawDTO> orderRawVOS;

    @NotNull(message = "订单产品原料不能为null")
    @ApiModelProperty(value = "产品原料")
    List<AddOrderRawFinanceDTO> orderRawFinanceVOS;

    @NotNull(message = "订单收汇信息不能为null")
    @ApiModelProperty(value = "收汇信息")
    List<AddOrderRemiDTO> orderRemiVOS;

    @NotNull(message = "订单信保费用不能为null")
    @ApiModelProperty(value = "订单信保费用")
    List<AddOrderCreditsInsuranceDTO> orderCreditsInsuranceVOS;

    @NotNull(message = "订单辅料信息不能为null")
    @ApiModelProperty(value = "辅料信息")
    List<AddOrderAcsrDTO> orderAcsrVOS;

//    @NotNull(message = "订单文件信息不能为null")
//    @ApiModelProperty(value = "订单文件信息（客户订单）")
//    List<InstOrUpdtXqFilesDTO> orderFiles1;
//
//    @NotNull(message = "订单文件信息不能为null")
//    @ApiModelProperty(value = "订单文件信息（收汇）")
//    List<InstOrUpdtXqFilesDTO> orderFiles2;
//
//    @NotNull(message = "订单文件信息不能为null")
//    @ApiModelProperty(value = "订单文件信息（原料采购）")
//    List<InstOrUpdtXqFilesDTO> orderFiles3;
//
//    @NotNull(message = "订单文件信息不能为null")
//    @ApiModelProperty(value = "订单文件信息（辅料采购）")
//    List<InstOrUpdtXqFilesDTO> orderFiles4;
//
//    @NotNull(message = "订单文件信息不能为null")
//    @ApiModelProperty(value = "订单文件信息（货运信息）")
//    List<InstOrUpdtXqFilesDTO> orderFiles5;

    @NotNull(message = "订单质量问题说明不能为null")
    @ApiModelProperty(value = "订单质量问题说明")
    List<InstOrUpdtNotesDTO> orderNotes;

    @NotNull(message = "收汇情况说明不能为null")
    @ApiModelProperty(value = "收汇情况说明")
    List<InstOrUpdtNotesDTO> remiNotes;

    @NotNull(message = "原料质量问题说明不能为null")
    @ApiModelProperty(value = "原料质量问题说明")
    List<InstOrUpdtNotesDTO> rawNotes;

    @ApiModelProperty(value = "海外仓出库信息")
    List<InstUptExitStorageDetailItemDTO> overseasExitDTOS;


    @ApiModelProperty("最终确认flag,传1为最终确认")
    private Integer completeFlag = 0;

    @NotNull(message = "卡车信息不能为null")
    @ApiModelProperty(value = "卡车信息")
    List<InstUptTruckInfoDTO> truckInfos;

    private String completeState;
}
