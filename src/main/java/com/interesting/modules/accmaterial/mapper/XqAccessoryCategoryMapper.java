package com.interesting.modules.accmaterial.mapper;

import com.interesting.business.system.model.TreeSelectModel;
import com.interesting.modules.accmaterial.entity.XqAccessoryCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_accessory_category(辅料分类表)】的数据库操作Mapper
* @createDate 2023-07-03 13:48:00
* @Entity com.interesting.modules.accmaterial.entity.XqAccessoryCategory
*/
public interface XqAccessoryCategoryMapper extends BaseMapper<XqAccessoryCategory> {

    List<TreeSelectModel> queryAccCategoryTree(@Param("pid") String pid);
}




