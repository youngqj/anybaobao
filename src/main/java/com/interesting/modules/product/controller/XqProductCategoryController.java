package com.interesting.modules.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.product.dto.AddCategoryDTO;
import com.interesting.modules.product.dto.EditCategoryDTO;
import com.interesting.modules.product.entity.XqProductCategory;
import com.interesting.modules.product.entity.XqProductInfo;
import com.interesting.modules.product.service.XqProductCategoryService;
import com.interesting.modules.product.service.XqProductInfoService;
import com.interesting.modules.product.vo.XqProductCategoryListVO;
import com.interesting.modules.product.vo.XqProductCategoryVO;
import com.interesting.modules.product.vo.XqProductInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "产品品种管理")
@RestController
@RequestMapping("/product/xqProductCategory")
public class XqProductCategoryController {

    @Autowired
    XqProductCategoryService xqProductCategoryService;

    @Autowired
    IXqOrderDetailService xqOrderDetailService;

    @Autowired
    XqProductInfoService xqProductInfoService;



    /**
     * 查询产品信息
     *
     * @return
     */
    @AutoLog(value = "查询产品品类")
    @ApiOperation(value = "查询产品品类", notes = "查询产品品类")
    @GetMapping(value = "/queryProdCategoryList")
    public Result<?> queryProdCategoryList() {
        List<XqProductCategory> list = xqProductCategoryService.lambdaQuery().list();
        List<XqProductCategoryVO> collect = list.stream().map(i -> {
            XqProductCategoryVO xqProductCategoryVO = new XqProductCategoryVO();
            xqProductCategoryVO.setId(i.getId());
            xqProductCategoryVO.setCategory(i.getCategory());
            return xqProductCategoryVO;
        }).collect(Collectors.toList());
        return Result.OK(collect);
    }

    /**
     * 分页列表查询
     *
     * @return
     */
    @AutoLog(value = "分页列表查询")
    @ApiOperation(value = "分页列表查询", notes = "分页列表查询")
    @GetMapping(value = "/queryPage")
    public Result<IPage<XqProductCategoryListVO>> queryPageList(@RequestParam(name = "name", required = false) String name,
                                                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<XqProductCategoryListVO> page = new Page<XqProductCategoryListVO>(pageNo, pageSize);
        IPage<XqProductCategoryListVO> pageList = xqProductCategoryService.queryPage(page, name);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param
     * @return
     */
    @AutoLog(value = "添加")
    @ApiOperation(value = "添加", notes = "添加")
    @PostMapping(value = "/add")
    public Result<?> addProduct(@RequestBody @Valid AddCategoryDTO dto) {
        XqProductCategory xqProductCategory = new XqProductCategory();
        xqProductCategory.setCategory(dto.getCategory());
        if (xqProductCategoryService.lambdaQuery().eq(XqProductCategory::getCategory, dto.getCategory()).count() > 0) {
            throw new InterestingBootException("已经录入该品类了");
        }
        return xqProductCategoryService.save(xqProductCategory) ? Result.OK("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 编辑
     * * @return
     */
    @AutoLog(value = "编辑")
    @ApiOperation(value = "编辑", notes = "编辑")
    @PostMapping(value = "/edit")
    public Result<?> edit(@RequestBody @Valid EditCategoryDTO dto) {
        String id = dto.getId();
        XqProductCategory category = xqProductCategoryService.getById(id);
        if (category == null) {
            throw new InterestingBootException("未找到对应产品品类");
        }
        XqProductCategory category1 = xqProductCategoryService.getOne(new LambdaQueryWrapper<XqProductCategory>().eq(XqProductCategory::getCategory, dto.getCategory()));
        if (category1 != null) {
            if (!category1.getId().equals(id)) {
                throw new InterestingBootException("当前输入的品类已存在");
            }
        }
        category.setCategory(dto.getCategory());
        return xqProductCategoryService.updateById(category) ? Result.OK("编辑成功！") : Result.error("编辑失败！");
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "批量删除")
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @GetMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids") String ids) {
        List<String> strings = Arrays.asList(ids.split(","));

        if (xqProductInfoService.lambdaQuery().in(XqProductInfo::getProductCategory, strings).count() > 0) {
            return Result.error("该产品品类有关联记录，无法删除！");
        }
        xqProductCategoryService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "通过id查询")
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id") String id) {
        XqProductCategory category = new XqProductCategory();
        category = xqProductCategoryService.getById(id);
        return Result.OK(category);
    }
}
