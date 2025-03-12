package com.interesting.business.system.vo;
import com.interesting.business.system.entity.SysDictItem;
import lombok.Data;

import java.util.List;

@Data
public class SysDictPage {

    /**
     * 主键
     */
    private String id;
    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 删除状态
     */
    private Integer izDelete;
    /**
     * 描述
     */
    private String description;

    private List<SysDictItem> sysDictItemList;

}
