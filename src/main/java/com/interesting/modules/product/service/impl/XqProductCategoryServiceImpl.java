package com.interesting.modules.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.product.entity.XqProductCategory;
import com.interesting.modules.product.entity.XqProductInfo;
import com.interesting.modules.product.mapper.XqProductCategoryMapper;
import com.interesting.modules.product.service.XqProductCategoryService;
import com.interesting.modules.product.vo.XqProductCategoryListVO;
import com.interesting.modules.product.vo.XqProductCategoryVO;
import com.interesting.modules.product.vo.XqProductInfoVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class XqProductCategoryServiceImpl extends ServiceImpl<XqProductCategoryMapper, XqProductCategory>
        implements XqProductCategoryService {
    @Autowired
    XqProductCategoryService xqProductCategoryService;

    /**
     * 查询产品信息
     *
     * @param
     * @return
     */
    @AutoLog(value = "查询产品品种信息")
    @ApiOperation(value = "查询产品品种信息", notes = "查询产品品种信息")
    @GetMapping(value = "/queryProdCategoryList")
    public Result<?> queryProdCategoryList() {
        List<XqProductCategory> list = xqProductCategoryService.lambdaQuery().list();
        List<XqProductCategoryVO> collect = list.stream().map(i -> {
            XqProductCategoryVO vo = new XqProductCategoryVO();

            BeanUtils.copyProperties(i, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.OK(collect);
    }

    @Override
    public IPage<XqProductCategoryListVO> queryPage(Page<XqProductCategoryListVO> page, String name) {
        return baseMapper.queryPage(page, name);
    }
}
