package com.interesting.modules.overseas.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.service.ISysCodeRoleService;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.CommonUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.common.util.oConvertUtils;
import com.interesting.modules.order.mapper.XqOrderMapper;
import com.interesting.modules.orderdetail.mapper.XqOrderDetailMapper;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.entity.XqInventoryOverseas;
import com.interesting.modules.overseas.entity.XqOverseasExitDetail;
import com.interesting.modules.overseas.entity.XqOverseasExitHead;
import com.interesting.modules.overseas.mapper.XqOverseasEnterDetailMapper;
import com.interesting.modules.overseas.mapper.XqOverseasExitDetailMapper;
import com.interesting.modules.overseas.mapper.XqOverseasExitHeadMapper;
import com.interesting.modules.overseas.service.XqInventoryOverseasService;
import com.interesting.modules.overseas.service.XqOverseasEnterDetailService;
import com.interesting.modules.overseas.service.XqOverseasExitDetailService;
import com.interesting.modules.overseas.service.XqOverseasExitHeadService;
import com.interesting.modules.overseas.vo.*;
import com.interesting.modules.reportform.vo.PageRemittanceReportVO;
import com.interesting.modules.reportform.vo.SumRemittanceFinanceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 26773
* @description 针对表【xq_overseas_exit_head(海外仓-出库单子表)】的数据库操作Service实现
* @createDate 2023-07-26 15:41:42
*/
@Service
public class XqOverseasExitHeadServiceImpl extends ServiceImpl<XqOverseasExitHeadMapper, XqOverseasExitHead>
    implements XqOverseasExitHeadService{
    @Autowired
    private XqOrderMapper xqOrderMapper;
    @Autowired
    private XqOverseasExitDetailMapper xqOverseasExitDetailMapper;
    @Autowired
    private XqOverseasExitDetailService xqOverseasExitDetailService;
    @Autowired
    private XqInventoryOverseasService xqInventoryOverseasService;
    @Autowired
    private XqOrderDetailMapper xqOrderDetailMapper;
    @Autowired
    private XqOverseasEnterDetailService xqOverseasEnterDetailService;
    @Autowired
    private XqOverseasEnterDetailMapper xqOverseasEnterDetailMapper;
    @Autowired
    private ISysCodeRoleService sysCodeRoleService;
    @Autowired
    private XqOverseasExitHeadService xqOverseasExitHeadService;


    @Override
    public IPage<RelativeOrderPageVO> pageRelativeOrder(QueryPageRelativeOrderDTO dto) {
        Page<RelativeOrderPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        dto.setExitState("2");
        IPage<RelativeOrderPageVO> results = xqOrderMapper.pageRelativeOrder(page, dto);
        return results;
    }

    @Override
    public IPage<ExitStoragePageVO> pageExitStorage(QueryPageExitStorageDTO dto) {
        Page<ExitStoragePageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        if (StringUtils.isNotBlank(dto.getReceiptDateBegin()) && StringUtils.isNotBlank(dto.getReceiptDateEnd())) {
            if (dto.getReceiptDateBegin().equals(dto.getReceiptDateEnd())) {
                dto.setReceiptDateBegin(dto.getReceiptDateBegin() + " 00:00:00");
                dto.setReceiptDateEnd(dto.getReceiptDateEnd() + " 23:59:59");
            }
        }
        return this.baseMapper.pageExitStorage(page, dto);
    }

    @Override
    public ExitStorageDetailVO getExitStorage(String id) {
        ExitStorageDetailVO vo = new ExitStorageDetailVO();
        XqOverseasExitHead byId = this.getById(id);
        BeanUtils.copyProperties(byId, vo);

        List<ExitStorageDetailItemVO> details =
                xqOverseasExitDetailMapper.listDetailsById(id);

        vo.setDetails(details);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addExitStorage(InstUptExitStorageDTO dto) {
//        String warehouseExitNo = dto.getWarehouseExitNo();
//        this.lambdaQuery().eq(XqOverseasExitHead::getWarehouseExitNo, warehouseExitNo).oneOpt().ifPresent(i -> {
//            throw new RuntimeException("出库单号已存在");
//        });

        XqOverseasExitHead xqOverseasExitHead = new XqOverseasExitHead();
        BeanUtils.copyProperties(dto, xqOverseasExitHead);
        xqOverseasExitHead.setId(null);
        xqOverseasExitHead.setWarehouseExitNo(sysCodeRoleService.getCodeNumByType2(5, 1));
        this.save(xqOverseasExitHead);

        dto.getDetails().forEach(i -> {
//            Integer currentInventory = xqInventoryOverseasService
//                    .getInventoryNum(i.getProductId(), i.getWarehouseId(),i.getWarehouseLot());
//            if (i.getExitNum() > currentInventory) {
//                throw new InterestingBootException("出库数量不能大于库存数量");
//            }
            // judge exit num
            Integer currentInventory = xqInventoryOverseasService
                    .getInventoryNum(i.getProductId(), i.getWarehouseId(),i.getWarehouseLot(),
                            i.getSourceRepNum());
            if (i.getExitNum() > currentInventory) {
                throw new InterestingBootException("出库数量不能大于库存数量");
            }


            XqOverseasExitDetail xqOverseasExitDetail = new XqOverseasExitDetail();
            BeanUtils.copyProperties(i, xqOverseasExitDetail);
            xqOverseasExitDetail.setSourceId(xqOverseasExitHead.getId());
            xqOverseasExitDetail.setCustomerOrderNum(xqOverseasExitDetail.getCustomerOrderNum().trim());
            xqOverseasExitDetail.setId(null);

            xqOverseasExitDetailService.save(xqOverseasExitDetail);
        });

        // insert to inventory
//        saveDetailsToInventory(dto);

//        judgeOrderCompleted(dto.getOrderNum());
        return true;
    }
//    public void judgeOrderCompleted(String orderNum) {
//        XqOrder xqOrder = xqOrderMapper
//                .selectOne(new QueryWrapper<XqOrder>().eq("order_num", orderNum));
//        if (xqOrder == null) {
//            return;
//        }
//
//        List<XqOrderDetail> orderDetails = xqOrderDetailMapper
//                .selectList(new QueryWrapper<XqOrderDetail>().eq("order_id", xqOrder.getId()));
//
//        List<String> orderDetailIds = orderDetails.stream().map(XqOrderDetail::getId).collect(Collectors.toList());
//
//        Integer count = xqOverseasExitDetailService
//                .lambdaQuery().in(XqOverseasExitDetail::getOrderDetailId, orderDetailIds)
//                .count();
//        if (count < 1) {
//            xqOrder.setExitState(CommonConstant.ORDER_FINISH_INITIAL);
//            xqOrderMapper.updateById(xqOrder);
//            return;
//        }
//
//
//        for (XqOrderDetail orderDetail : orderDetails) {
//            String orderDetailId = orderDetail.getId();
//
//            List<XqOverseasExitDetail> enterDetails = xqOverseasExitDetailMapper
//                    .selectList(new QueryWrapper<XqOverseasExitDetail>()
//                            .eq("order_detail_id", orderDetailId));
//
//            int totalEnterNum = enterDetails.stream().mapToInt(XqOverseasExitDetail::getExitNum).sum();
//
//            Integer totalBoxes = orderDetail.getTotalBoxes();
//            if (totalEnterNum < totalBoxes) {
//                xqOrder.setExitState(CommonConstant.ORDER_FINISH_HALF);
//                xqOrderMapper.updateById(xqOrder);
//                return;
//            }
//        }
//
//        xqOrder.setExitState(CommonConstant.ORDER_FINISH_COMPLETE);
//        xqOrderMapper.updateById(xqOrder);
//    }




    public void saveDetailsToInventory(InstUptExitStorageDTO dto) {

        List<XqInventoryOverseas> collect1 = dto.getDetails().stream().map(i -> {
            XqInventoryOverseas xqInventoryOverseas = new XqInventoryOverseas();
            Integer exitNum = i.getExitNum();
            String id = i.getId();
            String r = i.getProductId();
            String warehouseId = i.getWarehouseId();
            String warehouseLot = i.getWarehouseLot();


            xqInventoryOverseas.setDirection(CommonConstant.EXIT_DIRECTION)
                    .setSourceType("xsck")
                    .setNum(-exitNum)
                    .setSourceId(id)
                    .setItemId(r)
                    .setWarehouseId(warehouseId)
                    .setWarehouseLot(warehouseLot)
                    .setRelativeTime(new Date());

            return xqInventoryOverseas;
        }).collect(Collectors.toList());

        xqInventoryOverseasService.saveBatch(collect1);
    }

    public void saveDetailsToInventory(List<XqOverseasExitDetail> list) {

        List<XqInventoryOverseas> xsck = list.stream().map(i -> {
            XqInventoryOverseas xqInventoryOverseas = new XqInventoryOverseas();
            Integer exitNum = i.getExitNum();
            String id = i.getId();
            String r = i.getProductId();
            String warehouseId = i.getWarehouseId();
            String warehouseLot = i.getWarehouseLot();


            xqInventoryOverseas.setDirection(CommonConstant.EXIT_DIRECTION)
                    .setSourceType("xsck")
                    .setNum(-exitNum)
                    .setSourceId(id)
                    .setItemId(r)
                    .setWarehouseId(warehouseId)
                    .setWarehouseLot(warehouseLot)
                    .setRelativeTime(new Date());

            return xqInventoryOverseas;
        }).collect(Collectors.toList());

        xqInventoryOverseasService.saveBatch(xsck);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteExitStorage(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
//        List<String> collect1 = this.lambdaQuery().in(XqOverseasExitHead::getId, strings).list()
//                .stream().map(XqOverseasExitHead::getOrderNum)
//                .collect(Collectors.toList());
//
        if (this.lambdaQuery().in(XqOverseasExitHead::getId, strings)
                .eq(XqOverseasExitHead::getAuditStatus, "1").count() > 0) {
            throw new InterestingBootException("已审核的出库单不能删除");
        }

        this.removeByIds(strings);

        List<String> collect = xqOverseasExitDetailService.lambdaQuery()
                .in(XqOverseasExitDetail::getSourceId, strings)
                .select(XqOverseasExitDetail::getId)
                .list().stream().map(XqOverseasExitDetail::getId)
                .collect(Collectors.toList());

        xqOverseasExitDetailService.lambdaUpdate()
                .in(XqOverseasExitDetail::getSourceId, strings)
                .remove();

        // remove inventory
        xqInventoryOverseasService.physicalRemoveBySourceIds(collect);

//        for (String s : collect1) {
//            judgeOrderCompleted(s);
//        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editExitStorage(InstUptExitStorageDTO dto) {
        String id = dto.getId();
        if (this.getById(id).getAuditStatus().equals("1")) {
            throw new InterestingBootException("已审核的出库单不能修改");
        }

        XqOverseasExitHead xqOverseasExitHead = new XqOverseasExitHead();
        BeanUtils.copyProperties(dto, xqOverseasExitHead);
        this.updateById(xqOverseasExitHead);

//        List<String> removeIds = xqOverseasExitDetailService.lambdaQuery()
//                .eq(XqOverseasExitDetail::getSourceId, dto.getId())
//                .select(XqOverseasExitDetail::getId)
//                .list().stream().map(XqOverseasExitDetail::getId)
//                .collect(Collectors.toList());

        xqOverseasExitDetailMapper.removeBySourceId(dto.getId());
//        xqInventoryOverseasService.physicalRemoveBySourceIds(removeIds);

        dto.getDetails().forEach(i -> {
//            Integer currentInventory = xqInventoryOverseasService
//                    .getInventoryNum(i.getProductId(), i.getWarehouseId(),i.getWarehouseLot());
//            if (i.getExitNum() > currentInventory) {
//                throw new InterestingBootException("出库数量不能大于库存数量");
//            }

            // judge exit num
            Integer currentInventory = xqInventoryOverseasService
                    .getInventoryNum(i.getProductId(), i.getWarehouseId(),i.getWarehouseLot(),
                            i.getSourceRepNum());
            if (i.getExitNum() > currentInventory) {
                throw new InterestingBootException("出库数量不能大于库存数量");
            }

            XqOverseasExitDetail xqOverseasExitDetail = new XqOverseasExitDetail();
            BeanUtils.copyProperties(i, xqOverseasExitDetail);
            xqOverseasExitDetail.setSourceId(xqOverseasExitHead.getId());

            xqOverseasExitDetailService.save(xqOverseasExitDetail);
//            i.setId(xqOverseasExitDetail.getId());
        });

//        saveDetailsToInventory(dto);

//        judgeOrderCompleted(dto.getOrderNum());
        return true;
    }

    @Override
    public IPage<SurplusOrderPageVO2> pageSurplusOrder(QueryPageSurplusOrderDTO dto) {
        Page<SurplusOrderPageVO2> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        return this.xqOverseasExitDetailMapper.pageSurplusOrder(page, dto);
    }

    @Override
    public IPage<RelativeInventoryLotPageVO> pageRelativeLot(QueryPageRelativeLotDTO dto) {


        Page<RelativeInventoryLotPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<RelativeInventoryLotPageVO> results = this.baseMapper.pageRelativeLot(page, dto);

        results.getRecords().forEach(i -> {
            i.setId(UUID.randomUUID().toString());
        });
        return results;
    }

//    public void judgeOrderCompleted(String orderNum) {
//        XqOrder xqOrder = xqOrderMapper
//                .selectOne(new QueryWrapper<XqOrder>().eq("order_num", orderNum));
//        if (xqOrder == null) {
//            return;
//        }
//
//        List<XqOrderDetail> orderDetails = xqOrderDetailMapper
//                .selectList(new QueryWrapper<XqOrderDetail>().eq("order_id", xqOrder.getId()));
//
//        List<XqOverseasEnterHead> list = xqOverseasEnterHeadService.lambdaQuery()
//                .eq(XqOverseasEnterHead::getOrderNum, orderNum)
//                .eq(XqOverseasEnterHead::getAuditStatus, "1")
//                .list();
//
//        List<String> headIds = list.stream().map(XqOverseasEnterHead::getId).collect(Collectors.toList());
//
//        if (CollectionUtil.isEmpty(list)) {
//            xqOrder.setEnterState(CommonConstant.ORDER_FINISH_INITIAL);
//            xqOrderMapper.updateById(xqOrder);
//            return;
//        }
//
//        for (XqOrderDetail orderDetail : orderDetails) {
//            String orderDetailId = orderDetail.getId();
//
//            List<XqOverseasEnterDetail> enterDetails = xqOverseasEnterDetailMapper
//                    .selectList(new QueryWrapper<XqOverseasEnterDetail>()
//                            .eq("order_detail_id", orderDetailId)
//                            .in("source_id", headIds));
//
//            int totalEnterNum = enterDetails.stream().mapToInt(XqOverseasEnterDetail::getEnterNum).sum();
//
//            Integer totalBoxes = orderDetail.getTotalBoxes();
//            if (totalEnterNum < totalBoxes) {
//                xqOrder.setEnterState(CommonConstant.ORDER_FINISH_HALF);
//                xqOrderMapper.updateById(xqOrder);
//                return;
//            }
//        }
//
//        xqOrder.setEnterState(CommonConstant.ORDER_FINISH_COMPLETE);
//        xqOrderMapper.updateById(xqOrder);
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editOrderStatus(EditEnterOrderStatusDTO dto) {
        List<String> strings = Arrays.asList(dto.getIds().split(","));
        for (String id : strings) {
            XqOverseasExitHead byId = this.getById(id);
            if (dto.getAuditStatus().equals("1")) {
                if (byId.getAuditStatus().equals("1")) {
                    throw new InterestingBootException("已审核的单据不能再次审核");
                }
                byId.setAuditStatus("1");
                this.updateById(byId);

                // insert inventory
                List<XqOverseasExitDetail> list = xqOverseasExitDetailService.lambdaQuery()
                        .eq(XqOverseasExitDetail::getSourceId, id)
                        .list();

                for (XqOverseasExitDetail t : list) {
                    // judge exit num
                    Integer currentInventory = xqInventoryOverseasService
                            .getInventoryNum(t.getProductId(), t.getWarehouseId(),t.getWarehouseLot(),
                                    t.getSourceRepNum());
                    if (t.getExitNum() > currentInventory) {
                        throw new InterestingBootException("出库数量不能大于库存数量");
                    }
                }

                saveDetailsToInventory(list);

            } else {
                if (byId.getAuditStatus().equals("0")) {
                    throw new InterestingBootException("未审核的单据不能反审核");
                }
                byId.setAuditStatus("0");
                this.updateById(byId);

                List<String> removeIds = xqOverseasExitDetailService.lambdaQuery()
                        .eq(XqOverseasExitDetail::getSourceId, id)
                        .select(XqOverseasExitDetail::getId)
                        .list().stream().map(XqOverseasExitDetail::getId)
                        .collect(Collectors.toList());
                if (removeIds.size() > 0) {
                    xqInventoryOverseasService.physicalRemoveBySourceIds(removeIds);
                }
            }
        }

        return true;
    }

    @Override
    public IPage<ExitStorageDetailPageVO> pageExitStorageDetail(QueryPageExitStorageDetailDTO dto) {
        Page<ExitStorageDetailPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        if (StringUtils.isNotBlank(dto.getColumn())) {
            String column = oConvertUtils.camelToUnderline(dto.getColumn());
            dto.setColumn(column);
        }
        return this.baseMapper.pageExitStorageDetail(page, dto);
    }

    @Override
    public void exitStorageDetailExport(QueryPageExitStorageDetailDTO dto, Page<ExitStorageDetailPageVO> page, HttpServletResponse response) {
        IPage<ExitStorageDetailPageVO> exitStorageDetailPageVOIPage = this.baseMapper.pageExitStorageDetail(page, dto);
        List<ExitStorageDetailPageVO> records = exitStorageDetailPageVOIPage.getRecords();
        List<SumRemmitAmountVO> voList = this.sumRemmitAmount(dto);
        CommonUtils.export(dto.getExportColumn(), records, response, ExitStorageDetailPageVO.class, SumRemmitAmountVO.class, voList, false);
    }

    @Override
    public List<InstUptExitStorageDetailItemDTO> exitStorageDetailList(String orderNum) {
        return this.baseMapper.exitStorageDetailList(orderNum);
    }

    @Override
    public List<SumRemmitAmountVO> sumRemmitAmount(QueryPageExitStorageDetailDTO dto) {
        return this.baseMapper.sumRemmitAmount(dto);
    }
}




