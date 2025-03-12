package com.interesting.modules.accmaterial.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class InventoryCheckPageVO {
    private String id;

    @ApiModelProperty(value = "单据编号")
    private String repNum;

    @ApiModelProperty("辅料加尺寸 名称")
    private String accessoryInfo;

    @ApiModelProperty("盘点人id")
    private String checkUserId;

    @ApiModelProperty("盘点人名称")
    private String checkUserName;

    @ApiModelProperty("盘点时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    @ApiModelProperty("盘点仓库id")
    private String warehouseId;

    @ApiModelProperty("盘点仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;


//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "创建时间")
//    private Date createTime;
//
//    @ApiModelProperty("创建人")
//    private String createBy;

//    @ApiModelProperty("数量")
//    private Integer num;

}
