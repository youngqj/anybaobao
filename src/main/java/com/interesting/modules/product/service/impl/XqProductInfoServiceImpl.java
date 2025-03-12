package com.interesting.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.order.dto.sub.AddOrderProdDTO;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.product.dto.QueryProductInfoDTO;
import com.interesting.modules.product.entity.XqProductCategory;
import com.interesting.modules.product.entity.XqProductInfo;
import com.interesting.modules.product.service.XqProductCategoryService;
import com.interesting.modules.product.service.XqProductInfoService;
import com.interesting.modules.product.mapper.XqProductInfoMapper;
import com.interesting.modules.product.vo.XqProductInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author 26773
* @description 针对表【xq_product_info(产品信息表)】的数据库操作Service实现
* @createDate 2023-06-07 16:50:57
*/
@Service
public class XqProductInfoServiceImpl extends ServiceImpl<XqProductInfoMapper, XqProductInfo>
    implements XqProductInfoService{

    @Resource
    private IXqOrderDetailService xqOrderDetailService;
    @Resource
    private XqProductCategoryService xqProductCategoryService;

    @Override
    public IPage<XqProductInfoVO> queryXqProductInfoVOPage(QueryProductInfoDTO dto) {
        Integer pageNo = dto.getPageNo();
        Integer pageSize = dto.getPageSize();
        Page<XqProductInfoVO> page = new Page<>(pageNo, pageSize);


        return this.baseMapper.queryXqProductInfoVOPage(page, dto);

    }


    /**
     * 差异化处理编辑 产品信息
     *
     * @param orderId
     * @param addOrderProdDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderProd(String orderId, List<AddOrderProdDTO> addOrderProdDTOS) {

        addOrderProdDTOS.forEach(i -> {
            if (StringUtils.isBlank(i.getId())) {
                i.setId(UUID.randomUUID().toString());
            }
            if (StringUtils.isBlank(i.getCurrency())) {
                throw new InterestingBootException("产品信息里面的币种不能为空");
            }
        });

        List<XqOrderDetail> originalComs = xqOrderDetailService.list(new LambdaQueryWrapper<XqOrderDetail>().eq(XqOrderDetail::getOrderId, orderId));

        Map<String, AddOrderProdDTO> addComsMap = new LinkedHashMap<>();
        for (AddOrderProdDTO addOrderRawDTO : addOrderProdDTOS) {
            addComsMap.put(addOrderRawDTO.getId(), addOrderRawDTO);
        }

        List<XqOrderDetail> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqOrderDetail> toAddComs = new ArrayList<>();

        for (XqOrderDetail originalCom : originalComs) {
            AddOrderProdDTO addCom = addComsMap.get(originalCom.getId());
            if (addCom != null) {
                // 更新记录
                BeanUtils.copyProperties(addCom, originalCom);

                /* 校验计算逻辑 */
                /* 计算总重 */
                // totalWeight = totalBoxes * weightPerBox
//                BigDecimal weightPerBox = addCom.getWeightPerBox();
//                Integer totalBoxes = addCom.getTotalBoxes();
//                BigDecimal totalWeight = addCom.getTotalWeight();
//                if (weightPerBox != null && totalBoxes != null && totalWeight != null) {
//                    BigDecimal multiply = weightPerBox.multiply(new BigDecimal(totalBoxes)).setScale(2, BigDecimal.ROUND_HALF_UP);
//                    if (multiply.compareTo(totalWeight) != 0) {
//                        throw new InterestingBootException("产品重量计算错误");
//                    }
//                }
//
//                /* 销售金额 */
//                // pricePerLb * totalWeight + pricePerBox * totalBoxes = salesAmount
//                BigDecimal pricePerLb = addCom.getPricePerLb();
//                BigDecimal pricePerBox = addCom.getPricePerBox();
//                BigDecimal salesAmount = addCom.getSalesAmount();
//                if (pricePerLb != null && pricePerBox != null && salesAmount != null) {
//                    BigDecimal multiply = pricePerLb.multiply(totalWeight).setScale(2, BigDecimal.ROUND_HALF_UP);
//                    BigDecimal multiply1 = pricePerBox.multiply(new BigDecimal(totalBoxes)).setScale(2, BigDecimal.ROUND_HALF_UP);
//                    if (multiply.add(multiply1).compareTo(salesAmount) != 0) {
//                        throw new InterestingBootException("产品销售金额计算错误");
//                    }
//                }

                /* 根据产品名，新增产品信息记录，有则直接插入id，没有则新增产品信息再插入id */
                String productName = addCom.getProductName();
                String packing = addCom.getPackaging();
                String productCategory = addCom.getProductCategoryName();
                // 定义要匹配的正则表达式
                String regex = "^[-/+x\\u4e00-\\u9fa5\\w\\d]+(\\|[-/+x\\u4e00-\\u9fa5\\w\\d]+)*$"; // 包装方式的正则表达式

                // 判断是否满足验证规则
                if (packing.indexOf('|') > 0) {
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(packing);

                    if (!matcher.matches()) {
                        throw new InterestingBootException("包装方式必须以横杠隔开");
                    }
                }
//                XqProductInfo one = this.lambdaQuery()
//                        .eq(XqProductInfo::getProductName, productName)
//                        .eq(XqProductInfo::getPackaging, packing)
//                        .eq(XqProductInfo::getForeignTariff, addCom.getForeignTariff())
//                        .eq(XqProductInfo::getHsCodeDomestic, addCom.getHsCodeDomestic())
//                        .eq(XqProductInfo::getHsCodeForeign, addCom.getHsCodeForeign())
//                        .eq(XqProductInfo::getPackagingUnit, addCom.getPackagingUnit())
//                        .eq(XqProductInfo::getProductSpecs, addCom.getProductSpecs())
//                        .eq(XqProductInfo::getProductNameEn, addCom.getProductNameEn())
//                        .eq(XqProductInfo::getProductGrade, addCom.getProductGrade())
//                        .last("LIMIT 1").one();
//                if (one == null) {
////                    XqProductInfo xqProductInfo = new XqProductInfo();
////                    BeanUtils.copyProperties(addCom, xqProductInfo);
////                    xqProductInfo.setId(null);
////                    this.save(xqProductInfo);
////                    originalCom.setProductId(xqProductInfo.getId());
//                    throw new InterestingBootException("未找到产品：" + productName + "     " + addCom.getProductNameEn() + "     " + addCom.getProductSpecs() + "     " + addCom.getProductGrade() + "     " + addCom.getPackaging() + "     " + addCom.getHsCodeDomestic() + "     " + addCom.getHsCodeForeign() + "     " + addCom.getForeignTariff() + ",产品信息必须从产品管理新增");
//
//                } else {
                originalCom.setProductId(addCom.getProductId());
//                }
                if (StringUtils.isNotBlank(productCategory)) {
                    XqProductCategory one1 = xqProductCategoryService.lambdaQuery()
                            .eq(XqProductCategory::getId, productCategory)
                            .last("LIMIT 1").one();
                    if (one1 == null) {
//                        XqProductCategory xqProductCategory = new XqProductCategory();
//                        xqProductCategory.setCategory(productCategory);
//                        xqProductCategoryService.save(xqProductCategory);
//                        originalCom.setProductCategory(xqProductCategory.getId());
                        throw new InterestingBootException("产品品类必须从产品品类管理新增");
                    } else {
                        originalCom.setProductCategory(one1.getId());
                    }
                } else {
                    originalCom.setProductCategory("");
                }


                toUpdateComs.add(originalCom);
                // 从传入的佣金记录列表中移除已经更新的记录
                addComsMap.remove(originalCom.getId());
            } else {
                // 删除记录
                toDeleteIds.add(originalCom.getId());
            }
        }

        xqOrderDetailService.updateBatchById(toUpdateComs);
        xqOrderDetailService.removeByIds(toDeleteIds);

        for (AddOrderProdDTO addCom : addComsMap.values()) {
            XqOrderDetail newCom = new XqOrderDetail();
            BeanUtils.copyProperties(addCom, newCom);

            /* 校验计算逻辑 */
            /* 计算总重 */
            // totalWeight = totalBoxes * weightPerBox
//            BigDecimal weightPerBox = addCom.getWeightPerBox();
//            Integer totalBoxes = addCom.getTotalBoxes();
//            BigDecimal totalWeight = addCom.getTotalWeight();
//            if (weightPerBox != null && totalBoxes != null && totalWeight != null) {
//                BigDecimal multiply = weightPerBox.multiply(new BigDecimal(totalBoxes)).setScale(2, BigDecimal.ROUND_HALF_UP);
//                if (multiply.compareTo(totalWeight) != 0) {
//                    throw new InterestingBootException("产品重量计算错误");
//                }
//            }
//
//            /* 销售金额 */
//            // pricePerLb * totalWeight + pricePerBox * totalBoxes = salesAmount
//            BigDecimal pricePerLb = addCom.getPricePerLb();
//            BigDecimal pricePerBox = addCom.getPricePerBox();
//            BigDecimal salesAmount = addCom.getSalesAmount();
//            if (pricePerLb != null && pricePerBox != null && salesAmount != null) {
//                BigDecimal multiply = pricePerLb.multiply(totalWeight).setScale(2, BigDecimal.ROUND_HALF_UP);
//                BigDecimal multiply1 = pricePerBox.multiply(new BigDecimal(totalBoxes)).setScale(2, BigDecimal.ROUND_HALF_UP);
//                if (multiply.add(multiply1).compareTo(salesAmount) != 0) {
//                    throw new InterestingBootException("产品销售金额计算错误");
//                }
//            }

            /* 根据产品名，新增产品信息记录，有则直接插入id，没有则新增产品信息再插入id */
            String productName = addCom.getProductName();
            String packing = addCom.getPackaging();
            XqProductInfo one = this.lambdaQuery()
                    .eq(XqProductInfo::getProductName, productName)
                    .eq(XqProductInfo::getPackaging, packing)
                    .last("LIMIT 1").one();
            if (one == null) {
//                XqProductInfo xqProductInfo = new XqProductInfo();
//                BeanUtils.copyProperties(addCom, xqProductInfo);
//                xqProductInfo.setId(null);
//                this.save(xqProductInfo);
//                newCom.setProductId(xqProductInfo.getId());
                throw new InterestingBootException("产品信息必须从产品管理新增");
            } else {
                newCom.setProductId(one.getId());
            }

            newCom.setId(null);
            newCom.setOrderId(orderId);
            toAddComs.add(newCom);
        }

        xqOrderDetailService.saveBatch(toAddComs);

    }


}




