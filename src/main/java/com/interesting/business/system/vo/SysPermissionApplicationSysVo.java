package com.interesting.business.system.vo;

import com.interesting.business.system.model.SysPermissionApplicationSys;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 * <p>
 *
 * @author cc
 * @version 1.0
 * @date 2021/11/8
 */
@Data
public class SysPermissionApplicationSysVo {
    @ApiModelProperty(value = "系统标题名称")
    private String appName;

    @ApiModelProperty(value = "系统集合")
    private List<SysPermissionApplicationSys> sysPermissionApplicationSys;


}
