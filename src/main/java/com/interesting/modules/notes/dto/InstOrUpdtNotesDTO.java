package com.interesting.modules.notes.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InstOrUpdtNotesDTO {
    @ApiModelProperty("编辑时传id")
    private String id;

//    @ApiModelProperty(value = "面单id", required = true)
//    @NotBlank(message = "面单id不能为空")
//    private String orderId;

    @ApiModelProperty(value = "问题说明")
    private String note;
}
