package com.interesting.modules.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.product.entity.XqProductCategory;
import com.interesting.modules.product.vo.XqProductCategoryListVO;
import org.apache.ibatis.annotations.Param;

public interface XqProductCategoryMapper extends BaseMapper<XqProductCategory> {
    IPage<XqProductCategoryListVO> queryPage(@Param("page") Page<XqProductCategoryListVO> page, @Param("name") String name);
}

