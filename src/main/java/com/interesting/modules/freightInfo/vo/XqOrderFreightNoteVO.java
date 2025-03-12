package com.interesting.modules.freightInfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class XqOrderFreightNoteVO {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String orderId;
    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date insuranceDate;

    /**
     * 事项
     */
    @ApiModelProperty(value = "事项")
    private String transInfo;

    /**
     * 录入人
     */
    @ApiModelProperty(value = "录入人")
    private String createPerson;

    /**
     * 单据证明
     */
    @ApiModelProperty(value = "单据证明")
    private String photos;


}
