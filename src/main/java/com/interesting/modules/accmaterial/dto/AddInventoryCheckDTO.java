package com.interesting.modules.accmaterial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.interesting.modules.accmaterial.vo.InventoryCheckDetailRecordVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AddInventoryCheckDTO {
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("盘点人id")
    @NotBlank(message = "盘点人不能为空")
    private String checkUserId;

    @ApiModelProperty("盘点时间")
    @NotNull(message = "盘点时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    @ApiModelProperty("盘点仓库id")
    @NotNull(message = "盘点仓库id不能为空")
    private String warehouseId;

    @ApiModelProperty("盘点详细记录")
    List<AddInventoryCheckDetailRecordDTO> pdDetails;
}
