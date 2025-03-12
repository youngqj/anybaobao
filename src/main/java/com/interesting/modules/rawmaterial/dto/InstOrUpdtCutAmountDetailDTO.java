package com.interesting.modules.rawmaterial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class InstOrUpdtCutAmountDetailDTO {
    @ApiModelProperty(value = "编辑时传id")
    private String id;

    @ApiModelProperty(value = "扣款金额")
    @NotNull(message = "扣款金额不能为空")
    private BigDecimal cutAmount;

    @ApiModelProperty(value = "付款时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "付款时间不能为空")
    private Date payTime;

    @ApiModelProperty(value = "类型 0付款 1扣款")
    private Integer type;

    @ApiModelProperty(value = "付款备注")
    private String payRemark;
}
