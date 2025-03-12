package com.interesting.business.system.entity;

import com.interesting.common.aspect.annotation.Dict;
import com.interesting.common.system.base.entity.InterestingEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @Author Gcc
 * @since 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDictItem extends InterestingEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 字典id
     */
    private String dictId;

    /**
     * 字典项文本
     */
    @ApiModelProperty(value = "字典项文本")
    private String itemText;

    /**
     * 字典项值
     */
    @ApiModelProperty(value = "字典项值")
    private String itemValue;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;


    /**
     * 状态（1启用 0不启用）
     */
    @Dict(dicCode = "dict_item_status")
    @ApiModelProperty(value = "状态（1启用 0不启用）")
    private Integer status;


}
