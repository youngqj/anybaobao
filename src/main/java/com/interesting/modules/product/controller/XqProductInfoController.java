package com.interesting.modules.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.accmaterial.entity.XqAccessoriesPurchase;
import com.interesting.modules.accmaterial.service.IXqAccessoriesPurchaseService;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.product.dto.InstOrUpdtXqProductInfoDTO;
import com.interesting.modules.product.dto.QueryProductInfoDTO;
import com.interesting.modules.product.entity.XqProductCategory;
import com.interesting.modules.product.entity.XqProductInfo;
import com.interesting.modules.product.service.XqProductCategoryService;
import com.interesting.modules.product.service.XqProductInfoService;
import com.interesting.modules.product.vo.XqProductInfoVO;
import com.interesting.modules.rawmaterial.entity.XqRawMaterialPurchase;
import com.interesting.modules.rawmaterial.service.IXqRawMaterialPurchaseService;
import com.interesting.modules.unit.entity.XqUnit;
import com.interesting.modules.unit.service.XqUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Api(tags="产品信息管理")
@RestController
@RequestMapping("/product/xqProductInfo")
public class XqProductInfoController {
    @Autowired
    private XqProductInfoService xqProductInfoService;
    @Autowired
    private IXqOrderDetailService xqOrderDetailService;
    @Autowired
    private XqUnitService xqUnitService;
    @Autowired
    private IXqRawMaterialPurchaseService xqRawMaterialPurchaseService;
    @Autowired
    private IXqAccessoriesPurchaseService xqAccessoriesPurchaseService;
    @Autowired
    private XqProductCategoryService xqProductCategoryService;

    /**
     * 根据品类带出产品
     *
     * @return
     */
    @AutoLog(value = "根据品类带出产品")
    @ApiOperation(value = "根据品类带出产品", notes = "根据品类带出产品")
    @GetMapping(value = "/queryByCategory")
    public Result<?> queryByCategory(@RequestParam("category") String category) {
        List<XqProductInfo> xqProductInfos = xqProductInfoService.list(new LambdaQueryWrapper<XqProductInfo>().eq(XqProductInfo::getProductCategory, category));
        return Result.OK(xqProductInfos);
    }



    /**
     * 查询产品信息
     *
     * @param id
     * @return
     */
    @AutoLog(value = "查询产品信息")
    @ApiOperation(value="查询产品信息", notes="查询产品信息")
    @GetMapping(value = "/queryProdInfoList")
    public Result<?> queryProdInfoList() {
        List<XqProductInfo> list = xqProductInfoService.lambdaQuery().list();
        List<XqProductInfoVO> collect = list.stream().map(i -> {
            XqProductInfoVO xqProductInfoVO = new XqProductInfoVO();

            BeanUtils.copyProperties(i, xqProductInfoVO);
            return xqProductInfoVO;
        }).collect(Collectors.toList());
        return Result.OK(collect);
    }

    /**
     * 查询已填入的产品信息
     *
     * @param id
     * @return
     */
    @AutoLog(value = "查询已填入的产品信息")
    @ApiOperation(value="查询已填入的产品信息", notes="查询已填入的产品信息")
    @GetMapping(value = "/queryProdInfoListByOrderId")
    public Result<?> queryProdInfoListByOrderId(@RequestParam String orderId) {
        List<XqOrderDetail> list = xqOrderDetailService.lambdaQuery()
                .eq(XqOrderDetail::getOrderId, orderId).list();

        List<XqProductInfoVO> collect = list.stream().map(i -> {
            String productId = i.getProductId();
            XqProductInfo byId = xqProductInfoService.getById(productId);
            XqProductInfoVO xqProductInfoVO = new XqProductInfoVO();
            BeanUtils.copyProperties(byId, xqProductInfoVO);
            // xqProductInfoVO.setId(i.getId());
            xqProductInfoVO.setTotalBoxes(i.getTotalBoxes());
            xqProductInfoVO.setPackaging(i.getPackaging());
            xqProductInfoVO.setPackagingUnit(i.getPackagingUnit());

            String packagingUnit = xqProductInfoVO.getPackagingUnit();
            if (StringUtils.isNotBlank(packagingUnit)) {
                XqUnit byId1 = xqUnitService.getOne(new LambdaQueryWrapper<XqUnit>().eq(XqUnit::getName,packagingUnit).last("limit 1"));
                xqProductInfoVO.setPackagingUnitTxt(byId1==null?"":byId1.getName());
            }

            xqProductInfoVO.setLayoutRequirements(i.getLayoutRequirements());

            return xqProductInfoVO;
        }).collect(Collectors.toList());

        // 根据已填写的原料记录，set相应的categoryId
        List<XqRawMaterialPurchase> list22 = xqRawMaterialPurchaseService.lambdaQuery()
                .eq(XqRawMaterialPurchase::getOrderId, orderId).list();
        Map<String, XqRawMaterialPurchase> collect1 = list22.stream()
                .collect(Collectors.toMap(XqRawMaterialPurchase::getProductId, Function.identity(),
                (k1, k2) -> k1));
        collect.forEach(i -> {
            XqRawMaterialPurchase xqRawMaterialPurchase = collect1.get(i.getId());
            if (xqRawMaterialPurchase != null) {
                i.setCategoryId(xqRawMaterialPurchase.getCategoryId());
            }
        });
        return Result.OK(collect);
    }

//    /**
//     * 查询已填入的产品信息
//     *
//     * @param id
//     * @return
//     */
//    @AutoLog(value = "辅料采购-查询已填入的产品信息")
//    @ApiOperation(value="辅料采购-查询已填入的产品信息", notes="辅料采购-查询已填入的产品信息")
//    @GetMapping(value = "/queryAccProdInfoListByOrderId")
//    public Result<?> queryAccProdInfoListByOrderId(@RequestParam String orderId) {
//        List<XqRawMaterialPurchase> list = xqRawMaterialPurchaseService.lambdaQuery()
//                .eq(XqRawMaterialPurchase::getOrderId, orderId).list();
//
//        List<XqProductInfoVO> collect = list.stream().map(i -> {
//            String productId = i.getProductId();
//            XqProductInfo byId = xqProductInfoService.getById(productId);
//            XqProductInfoVO xqProductInfoVO = new XqProductInfoVO();
//            BeanUtils.copyProperties(byId, xqProductInfoVO);
//
////            xqProductInfoVO.setPackaging(i.getPackaging());
////            xqProductInfoVO.setPackagingUnit(i.getPackagingUnit());
//
////            String packagingUnit = xqProductInfoVO.getPackagingUnit();
////            if (StringUtils.isNotBlank(packagingUnit)) {
////                XqUnit byId1 = xqUnitService.getById(packagingUnit);
////                xqProductInfoVO.setPackagingUnitTxt(byId1.getName());
////            }
//
//            xqProductInfoVO.setCategoryId(i.getCategoryId());
//
//            return xqProductInfoVO;
//        }).collect(Collectors.toList());
//
//        return Result.OK(collect);
//    }

    /**
     * 分页列表查询
     *
     * @param xqAccessoriesPurchase
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "分页列表查询")
    @ApiOperation(value="分页列表查询", notes="分页列表查询")
    @GetMapping(value = "/productPage")
    public Result<IPage<XqProductInfoVO>> queryPageList(QueryProductInfoDTO dto) {
        IPage<XqProductInfoVO> pageList = xqProductInfoService.queryXqProductInfoVOPage(dto);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param xqAccessoriesPurchase
     * @return
     */
    @AutoLog(value = "添加")
    @ApiOperation(value="添加", notes="添加")
    @PostMapping(value = "/addProduct")
    public Result<?> addProduct(@RequestBody @Valid InstOrUpdtXqProductInfoDTO dto) {
        XqProductInfo xqProductInfo = new XqProductInfo();
        BeanUtils.copyProperties(dto, xqProductInfo);
        // 定义要匹配的正则表达式
        String regex = "^[-/+x\\u4e00-\\u9fa5\\w\\d]+(\\|[-/+x\\u4e00-\\u9fa5\\w\\d]+)*$"; // 包装方式的正则表达式

        // 判断是否满足验证规则
        if (dto.getPackaging().indexOf('|') > 0) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(dto.getPackaging());

            if (!matcher.matches()) {
                throw new InterestingBootException("包装方式必须以横杠隔开");
            }
        }
        xqProductInfo.setId(null);
        return xqProductInfoService.save(xqProductInfo) ? Result.OK("添加成功！") : Result.error("添加失败！");
    }

    /**
     * 编辑
     *
     * @param xqAccessoriesPurchase
     * @return
     */
    @AutoLog(value = "编辑")
    @ApiOperation(value="编辑", notes="编辑")
    @PostMapping(value = "/editProduct")
    public Result<?> editProduct(@RequestBody @Valid InstOrUpdtXqProductInfoDTO dto) {
        String id = dto.getId();
        XqProductInfo xqProductInfo = xqProductInfoService.getById(id);
        if (xqProductInfo == null) {
            throw new InterestingBootException("未找到对应产品");
        }
        // 定义要匹配的正则表达式
        String regex = "^[-/+x\\u4e00-\\u9fa5\\w\\d]+(\\|[-/+x\\u4e00-\\u9fa5\\w\\d]+)*$"; // 包装方式的正则表达式

        // 判断是否满足验证规则
        if (dto.getPackaging().indexOf('|') > 0) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(dto.getPackaging());

            if (!matcher.matches()) {
                throw new InterestingBootException("包装方式必须以横杠隔开");
            }
        }


        BeanUtils.copyProperties(dto, xqProductInfo);

        return xqProductInfoService.updateById(xqProductInfo) ? Result.OK("编辑成功！") : Result.error("编辑失败！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "通过id删除")
    @ApiOperation(value="通过id删除", notes="通过id删除")
    @GetMapping(value = "/deleteProduct")
    public Result<?> delete(@RequestParam(name="id") String id) {
        if (xqOrderDetailService.lambdaQuery().eq(XqOrderDetail::getProductId, id).count() > 0
        || xqRawMaterialPurchaseService.lambdaQuery().eq(XqRawMaterialPurchase::getProductId, id).count() > 0
        || xqAccessoriesPurchaseService.lambdaQuery().eq(XqAccessoriesPurchase::getProductId, id).count() > 0) {
            return Result.error("该产品有关联记录，无法删除！");
        }

        xqProductInfoService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "批量删除")
    @ApiOperation(value="批量删除", notes="批量删除")
    @GetMapping(value = "/deleteBatchProducts")
    public Result<?> deleteBatch(@RequestParam(name="ids") String ids) {
        List<String> strings = Arrays.asList(ids.split(","));

        if (xqOrderDetailService.lambdaQuery().in(XqOrderDetail::getProductId, strings).count() > 0
                || xqRawMaterialPurchaseService.lambdaQuery().in(XqRawMaterialPurchase::getProductId, strings).count() > 0
                || xqAccessoriesPurchaseService.lambdaQuery().in(XqAccessoriesPurchase::getProductId, strings).count() > 0) {
            return Result.error("该产品有关联记录，无法删除！");
        }

        xqProductInfoService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "通过id查询")
    @ApiOperation(value="通过id查询", notes="通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id") String id) {
        XqProductInfoVO xqProductInfoVO = new XqProductInfoVO();
        XqProductInfo byId = xqProductInfoService.getById(id);
        BeanUtils.copyProperties(byId, xqProductInfoVO);


//        String packagingUnit = byId.getPackagingUnit();
//        if (StringUtils.isNotBlank(packagingUnit)) {
//            XqUnit byId1 = xqUnitService.getById(packagingUnit);
//            xqProductInfoVO.setPackagingUnitTxt(byId1.getName());
//        }

        return Result.OK(xqProductInfoVO);
    }
}
