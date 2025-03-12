package com.interesting.modules.warehouse.vo;

import com.interesting.modules.freightcost.vo.XqFreightCostInfoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class XqWarehouseVO {
    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "仓库编号")
    private String serialNum;

    @ApiModelProperty(value = "仓库地址")
    private String address;

    @ApiModelProperty(value = "仓库名称")
    private String name;

    @ApiModelProperty(value = "仓库类型（字典）")
    private String warehouseType;

    @ApiModelProperty(value = "仓库类型")
    private String warehouseTypeTxt;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty("货运费用")
    private List<XqFreightCostInfoVO> freightCostInfos;
}
