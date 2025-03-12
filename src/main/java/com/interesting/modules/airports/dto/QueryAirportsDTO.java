package com.interesting.modules.airports.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class QueryAirportsDTO {
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    @ApiModelProperty(value = "提单号")
    private String billOfLandingNo;

    @ApiModelProperty(value = "工厂")
    private String factoryName;

    @ApiModelProperty(value = "工厂id")
    private List<String> factoryIdList;

    @ApiModelProperty(value = "客户")
    private String customer;

    @ApiModelProperty(value = "客户id")
    private List<String> customerIdList;

    @ApiModelProperty(value = "目的港")
    private String portOfDestination;

    @ApiModelProperty(value = "国内费用申请日期开始")
    private String gnfyApplyTimeStart;

    @ApiModelProperty(value = "国内费用申请日期结束")
    private String gnfyApplyTimeEnd;


    @ApiModelProperty(value = "国内费用申请日期开始")
    private String gwfyApplyTimeStart;

    @ApiModelProperty(value = "国内费用申请日期结束")
    private String gwfyApplyTimeEnd;
}
