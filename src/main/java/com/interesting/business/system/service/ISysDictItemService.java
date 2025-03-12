package com.interesting.business.system.service;

import com.interesting.business.system.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author Gcc
 * @since 2018-12-28
 */
public interface ISysDictItemService extends IService<SysDictItem> {
    /**
     * 根据id  获取字典配置项
     * @param mainId
     * @return
     */
    public List<SysDictItem> selectItemsByMainId(String mainId);

    /**
     * 根据code 获取字典配置值
     * @param code
     * @return
     */
    List<SysDictItem> selectItemsByMainCode(String code);

    /**
     * 根据code 获取Map对象
     * @param item1
     * @return
     */
    Map<String, SysDictItem> selectMapItemsNameByMainCode(String item1);
}
