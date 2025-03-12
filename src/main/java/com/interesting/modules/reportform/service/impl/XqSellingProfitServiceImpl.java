package com.interesting.modules.reportform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanCopyUtils;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.freightInfo.entity.XqFreightInfo;
import com.interesting.modules.freightInfo.service.IXqFreightInfoService;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.reportform.dto.QueryPageSellProfitDTO;
import com.interesting.modules.reportform.entity.XqSellingProfit;
import com.interesting.modules.reportform.mapper.XqSellingProfitMapper;
import com.interesting.modules.reportform.service.XqSellingProfitService;
import com.interesting.modules.reportform.vo.SellProfitPageVO;
import com.interesting.modules.reportform.vo.SellProfitPageVO1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.interesting.modules.freightInfo.service.impl.XqFreightInfoServiceImpl.replaceAndEvaluateExpression;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/6 17:08
 * @Project: trade-manage
 * @Description:
 */

@Service
@Slf4j
public class XqSellingProfitServiceImpl extends ServiceImpl<XqSellingProfitMapper, XqSellingProfit> implements XqSellingProfitService {

    @Autowired
    private IXqOrderService xqOrderService;
    @Autowired
    private XqSellingProfitMapper xqSellingProfitMapper;
    @Autowired
    private IXqFreightInfoService xqFreightInfoService;
    @Autowired
    private IXqOrderDetailService xqOrderDetailService;

    /**
     * 销售利润表初始化
     *
     * @param records
     * @return
     */
    @Override
    public boolean init(List<SellProfitPageVO> records) {

        log.info("清除数据 ------------------ start -------------------------");
        // 先清除原始数据
        boolean update = lambdaUpdate().set(XqSellingProfit::getIzDelete, 1).update();
        log.info("清除数据 ------------------ end ------------------------- " + update);

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        List<XqSellingProfit> xqSellingProfits = new ArrayList<>();

        for (SellProfitPageVO record : records) {
            XqSellingProfit xqSellingProfit = BeanCopyUtils.copyBean(record, XqSellingProfit.class);
            try {

                if (StringUtils.isNotBlank(record.getEtd())) {
                    xqSellingProfit.setEtd(format.parse(record.getEtd()));
                }
                xqSellingProfits.add(xqSellingProfit);
            } catch (Exception e) {
                throw new InterestingBootException("日期格式化失败");
            }

        }

        if (xqSellingProfits.size() > 0) {
            log.info("插入数据 -------------------------- start -------------------------");
            boolean saveBatch = saveBatch(xqSellingProfits);
            log.info("插入数据 -------------------------- end -------------------------" + saveBatch);
            return saveBatch;
        }

        return false;
    }


    /**
     * 根据订单id同步销售利润表
     *
     * @param orderId
     */

    @Async
    public void synchroData(String orderId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        // 查询订单是否有海关放行日期
        XqFreightInfo one = xqFreightInfoService.lambdaQuery()
                .eq(XqFreightInfo::getOrderId, orderId)
                .isNotNull(XqFreightInfo::getCustomsClearanceTime).one();

        // 有海关放行日期 销售利润表才有数据

        if (one != null) {
            // 获取历史销售利润表数据
            List<XqSellingProfit> profits = xqSellingProfitMapper.getSellingProfitListByOrderId(orderId);

            Map<String, XqSellingProfit> profitMap = profits.stream()
                    .collect(Collectors.toMap(XqSellingProfit::getOrderDetailId, p -> p));


            // 更新列表
            List<XqSellingProfit> updateList = new ArrayList<>();
            // 新增列表
            List<XqSellingProfit> addList = new ArrayList<>();

            // 获取最新的销售利润数据
            QueryPageSellProfitDTO dto = new QueryPageSellProfitDTO();
            dto.setPageSize(999);
            dto.setOrderId(orderId);
            IPage<SellProfitPageVO> profitPageVOIPage = xqOrderService.pageSellProfit(dto);
            if (profitPageVOIPage != null && profitPageVOIPage.getRecords().size() > 0) {

                for (SellProfitPageVO record : profitPageVOIPage.getRecords()) {
                    XqSellingProfit xqSellingProfit = profitMap.get(record.getOrderDetailId());
                    if (xqSellingProfit != null) {
                        // 更新
                        BeanUtils.copyProperties(record, xqSellingProfit);
                        try {
                            xqSellingProfit.setEtd(format.parse(record.getEtd()));
                        } catch (ParseException e) {
                            log.info("日期转换失败！");
                        }
                        updateList.add(xqSellingProfit);
                    } else {
                        // 新增
                        XqSellingProfit addProfit = BeanCopyUtils.copyBean(record, XqSellingProfit.class);
                        try {
                            addProfit.setEtd(format.parse(record.getEtd()));
                        } catch (ParseException e) {
                            log.info("日期转换失败！");
                        }
                        addList.add(addProfit);
                    }
                }
            }


            if (addList.size() > 0) {
                saveBatch(addList);
            }
            if (updateList.size() > 0) {
                updateBatchById(updateList);
            }

            //删除
            List<String> idList = updateList.stream().map(XqSellingProfit::getId).collect(Collectors.toList());
            List<String> collect = profits.stream()
                    .filter(xqSellingProfit -> !idList.contains(xqSellingProfit.getId()))
                    .map(XqSellingProfit::getId)
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                removeByIds(collect);
            }
        }


    }

    /**
     * 更新单价
     */
    @Async
    public void updatePrice(String orderId, String orderNum) {
        // 更新销售订单
        List<XqOrderDetail> orderDetails = xqOrderDetailService.list(new LambdaQueryWrapper<XqOrderDetail>().eq(XqOrderDetail::getOrderId, orderId));

        //去更新下销售单价/磅和销售单价/箱
        QueryPageSellProfitDTO dto = new QueryPageSellProfitDTO();
        dto.setPageNo(1);
        dto.setPageSize(999);
        dto.setOrderNum(orderNum);
        List<SellProfitPageVO1> list = xqOrderService.pageSellProfit1(dto);
        if (list.size() > 0) {
            for (XqOrderDetail detail : orderDetails) {
                for (SellProfitPageVO1 vo : list) {
                    if (detail.getId().equals(vo.getOrderDetailId())) {

                        String str = detail.getPackaging();
                        BigDecimal packing = BigDecimal.ZERO;
                        if (str.contains("/")) {
                            packing = replaceAndEvaluateExpression(str);
                        } else {
                            packing = new BigDecimal(str);
                        }
                        if (vo.getTotalAmount().compareTo(BigDecimal.ZERO) < 0) {
                            detail.setPricePerLb(BigDecimal.ZERO);
                            detail.setPricePerBox(BigDecimal.ZERO);
                            detail.setSalesAmount(BigDecimal.ZERO);

                        } else {
                            detail.setPricePerLb(vo.getTotalAmount().setScale(3, RoundingMode.HALF_UP));
                            detail.setPricePerBox(detail.getPricePerLb().setScale(3, RoundingMode.HALF_UP).multiply(packing).setScale(2, RoundingMode.HALF_UP));
                        }
                        detail.setSalesAmount(detail.getPricePerLb().multiply(detail.getTotalWeight()).setScale(2, RoundingMode.HALF_UP));
                        break; // 找到匹配的productId后跳出内层循环
                    }
                }
            }
        } else {
            for (XqOrderDetail detail : orderDetails) {
                detail.setPricePerLb(BigDecimal.ZERO);
                detail.setPricePerBox(BigDecimal.ZERO);
                detail.setSalesAmount(BigDecimal.ZERO);
            }
        }

        xqOrderDetailService.updateBatchById(orderDetails);



    }
}
