package com.interesting.modules.files.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MoveXqFileDTO {
    @ApiModelProperty(value = "编辑时传")
    private String id;


    @ApiModelProperty(value = "现在目录的pid")
    private String nowPid;

}
