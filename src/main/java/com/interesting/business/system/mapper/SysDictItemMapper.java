package com.interesting.business.system.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;
import com.interesting.business.system.entity.SysDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @Author Gcc
 * @since 2018-12-28
 */
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
    @Select("SELECT * FROM sys_dict_item WHERE DICT_ID = #{mainId} order by sort_order asc, item_value asc")
    public List<SysDictItem> selectItemsByMainId(String mainId);

    List<SysDictItem> selectItemsByMainCode(String code);

    /**
     * 获取所有楼栋 以id为key
     *
     * @return
     */
    @MapKey("itemValue")
    Map<String, SysDictItem> selectMapItemsByMainCode(String code);
}
