package com.interesting.modules.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.product.entity.XqProductCategory;
import com.interesting.modules.product.vo.XqProductCategoryListVO;

public interface XqProductCategoryService extends IService<XqProductCategory> {
    IPage<XqProductCategoryListVO> queryPage(Page<XqProductCategoryListVO> page, String name);
}
