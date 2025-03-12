package com.interesting.modules.overseas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interesting.modules.accmaterial.vo.InventoryCheckDetailRecordVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class OverSeasInventoryCheckDetailVO {
    private String id;

    @ApiModelProperty(value = "单据编号")
    private String repNum;

    @ApiModelProperty(value = "盘点人id")
    private String checkUserId;

    @ApiModelProperty(value = "盘点人")
    private String checkUserName;

    @ApiModelProperty(value = "盘点仓库id")
    private String warehouseId;

    @ApiModelProperty(value = "盘点仓库名称")
    private String warehouseName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "盘点时间")
    private Date checkTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("盘点详细记录")
    List<OverSeasInventoryCheckDetailRecordVO> pdDetails;
}
