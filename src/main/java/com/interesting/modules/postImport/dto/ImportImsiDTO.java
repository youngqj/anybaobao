package com.interesting.modules.postImport.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author Wlj
 * @version 1.0
 * @date 2023/3/2
 * @Project smartApartment
 */
@Data
public class ImportImsiDTO {
    @ApiModelProperty("序号")
    private String cou;
    @ApiModelProperty("关键字")
    private String keyWord;
    @ApiModelProperty("结果")
    private String result;

    @ApiModelProperty(hidden = true, value = "导入记录结果")
    private String importResult;

}
