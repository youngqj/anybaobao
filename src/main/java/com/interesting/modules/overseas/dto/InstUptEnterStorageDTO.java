package com.interesting.modules.overseas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interesting.modules.overseas.vo.EnterStorageDetailItemVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class InstUptEnterStorageDTO {
    @ApiModelProperty(value = "主键")
    private String id;

//    @ApiModelProperty(value = "入库单号", required = true)
//    @NotBlank(message = "入库单号不能为空")
//    private String warehouseEnterNo;

    @ApiModelProperty(value = "来源订单号", required = true)
    @NotBlank(message = "来源订单号不能为空")
    private String orderNum;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "单据日期", required = true)
    @NotNull(message = "单据日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiptDate;

    @NotNull
    private List<InstUptEnterStorageDetailItemDTO> details;


}
