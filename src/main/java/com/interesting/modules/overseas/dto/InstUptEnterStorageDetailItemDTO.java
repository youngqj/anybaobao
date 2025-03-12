package com.interesting.modules.overseas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class InstUptEnterStorageDetailItemDTO {
    @ApiModelProperty(value = "主键")
    private String id;


    @ApiModelProperty(value = "来源订单id")
    private String orderDetailId;

    @ApiModelProperty(value = "仓库id", required = true)
    @NotBlank(message = "仓库id不能为空")
    private String warehouseId;

    @ApiModelProperty(value = "仓库lot", required = true)
    @NotBlank(message = "仓库lot不能为空")
    private String warehouseLot;

    @ApiModelProperty(value = "产品id", required = true)
    @NotBlank(message = "产品id不能为空")
    private String productId;

    @ApiModelProperty(value = "包装方式")
    private String packaging;

    @ApiModelProperty(value = "包装方式单位(id)")
    private String packagingUnit;

    @ApiModelProperty(value = "入库数量", required = true)
    @NotNull(message = "入库数量不能为空")
    private Integer enterNum;

    @ApiModelProperty(value = "入库单价", required = true)
    @NotNull(message = "入库单价不能为空")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "入库金额", required = true)
    @NotNull(message = "入库金额不能为空")
    private BigDecimal enterMoney;


    @ApiModelProperty(value = "每箱毛重", required = true)
    private BigDecimal grossWeightPerBox;

    @ApiModelProperty(value = "总毛重", required = true)
    private BigDecimal grossWeightTotal;

    @ApiModelProperty(value = "托盘数", required = true)
    private Integer palletNum;


    @ApiModelProperty(value = "入库日期", required = true)
    // @NotNull(message = "入库日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enterDate;
}
