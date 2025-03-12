package com.interesting.modules.files.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MoveFilesDTO {
    @NotBlank(message = "ids不能为空")
    private String ids;

    @NotBlank(message = "节点id不能为空")
    private String nodeId;
}
