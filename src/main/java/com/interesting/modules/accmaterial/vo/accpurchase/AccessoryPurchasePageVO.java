package com.interesting.modules.accmaterial.vo.accpurchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AccessoryPurchasePageVO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    private String id;

    /**
     * 采购合同号
     */
    @ApiModelProperty(value = "采购合同号")
    private String purchaseContractNo;

    /**
     * 采购合同号
     */
    @ApiModelProperty(value = "辅料信息")
    private String accessoryInfo;

    /**
     * 采购日期
     */
    @ApiModelProperty(value = "采购日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date purchaseDate;

    /**
     * 供应商id
     */
    @ApiModelProperty(value = "供应商id")
    private String supplierId;

    /**
     * 供应商id
     */
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;
}
