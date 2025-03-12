package com.interesting.modules.rawmaterial.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/4/9 15:26
 * @Project: trade-manage
 * @Description:
 */

@Data
@TableName("xq_withhold_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class XqWithholdDetail  implements Serializable {

    private static final long SerialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "来源记录id")
    private String sourceId;

    @ApiModelProperty(value = "付款金额")
    private BigDecimal money;

    @ApiModelProperty(value = "付款时间")
    private Date withholdTime;

    @ApiModelProperty(value = "付款备注")
    private String remark;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除 0否 1是")
    private Integer izDelete;
}
