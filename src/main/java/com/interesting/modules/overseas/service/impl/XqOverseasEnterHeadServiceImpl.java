package com.interesting.modules.overseas.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.service.ISysCodeRoleService;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.job.CreditsSignInQuartz;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.mapper.XqOrderMapper;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.mapper.XqOrderDetailMapper;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.overseas.dto.*;
import com.interesting.modules.overseas.entity.XqInventoryOverseas;
import com.interesting.modules.overseas.entity.XqOverseasEnterDetail;
import com.interesting.modules.overseas.entity.XqOverseasEnterHead;
import com.interesting.modules.overseas.entity.XqOverseasWarehouseFee;
import com.interesting.modules.overseas.mapper.XqOverseasEnterDetailMapper;
import com.interesting.modules.overseas.mapper.XqOverseasEnterHeadMapper;
import com.interesting.modules.overseas.service.XqInventoryOverseasService;
import com.interesting.modules.overseas.service.XqOverseasEnterDetailService;
import com.interesting.modules.overseas.service.XqOverseasEnterHeadService;
import com.interesting.modules.overseas.service.XqOverseasWarehouseFeeService;
import com.interesting.modules.overseas.vo.*;
import com.interesting.modules.warehouse.entity.XqWarehouseCostDetail;
import com.interesting.modules.warehouse.service.XqWarehouseCostDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 26773
* @description 针对表【xq_overseas_enter_head(海外仓-入库单主表)】的数据库操作Service实现
* @createDate 2023-07-25 11:13:58
*/
@Service
public class XqOverseasEnterHeadServiceImpl extends ServiceImpl<XqOverseasEnterHeadMapper, XqOverseasEnterHead>
    implements XqOverseasEnterHeadService{

    @Autowired
    private XqOverseasEnterDetailMapper xqOverseasEnterDetailMapper;
    @Autowired
    private XqOverseasEnterDetailService xqOverseasEnterDetailService;
    @Autowired
    private XqOverseasEnterHeadService xqOverseasEnterHeadService;
    @Autowired
    private XqOrderMapper xqOrderMapper;
    @Autowired
    private IXqOrderService xqOrderService;
    @Autowired
    private XqInventoryOverseasService xqInventoryOverseasService;
    @Autowired
    private IXqOrderDetailService xqOrderDetailService;
    @Autowired
    private XqOrderDetailMapper xqOrderDetailMapper;
    @Autowired
    private ISysCodeRoleService sysCodeRoleService;

    @Override
    public IPage<EnterStoragePageVO> pageEnterStorage(QueryPageEnterStorageDTO dto) {
        Page<EnterStoragePageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        if (StringUtils.isNotBlank(dto.getReceiptDateBegin()) && StringUtils.isNotBlank(dto.getReceiptDateEnd())) {
            if (dto.getReceiptDateBegin().equals(dto.getReceiptDateEnd())) {
                dto.setReceiptDateBegin(dto.getReceiptDateBegin() + " 00:00:00");
                dto.setReceiptDateEnd(dto.getReceiptDateEnd() + " 23:59:59");
            }
        }
        return this.baseMapper.pageEnterStorage(page, dto);
    }

    @Override
    public EnterStorageDetailVO getEnterStorage(String id) {
        EnterStorageDetailVO vo = new EnterStorageDetailVO();
        XqOverseasEnterHead byId = this.getById(id);
        BeanUtils.copyProperties(byId, vo);

        List<EnterStorageDetailItemVO> details =
                xqOverseasEnterDetailMapper.listDetailsById(id);

        vo.setDetails(details);
        return vo;
    }

    @Autowired
    private XqOverseasWarehouseFeeService xqOverseasWarehouseFeeService;

    @Autowired
    private CreditsSignInQuartz creditsSignInQuartz;
    @Autowired
    private XqWarehouseCostDetailService xqWarehouseCostDetailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addEnterStorage(InstUptEnterStorageDTO dto) {
//        String warehouseEnterNo = dto.getWarehouseEnterNo();
//        this.lambdaQuery().eq(XqOverseasEnterHead::getWarehouseEnterNo, warehouseEnterNo).oneOpt()
//                .ifPresent(i -> {
//                    throw new InterestingBootException("入库单号已存在");
//                });

        XqOverseasEnterHead xqOverseasEnterHead = new XqOverseasEnterHead();
        BeanUtils.copyProperties(dto, xqOverseasEnterHead);
        xqOverseasEnterHead.setId(null);
        xqOverseasEnterHead.setWarehouseEnterNo(sysCodeRoleService.getCodeNumByType2(4, 1));

        this.save(xqOverseasEnterHead);

        dto.getDetails().forEach(i -> {
            XqOverseasEnterDetail xqOverseasEnterDetail = new XqOverseasEnterDetail();
            BeanUtils.copyProperties(i, xqOverseasEnterDetail);
            xqOverseasEnterDetail.setSourceId(xqOverseasEnterHead.getId());
            xqOverseasEnterDetail.setId(null);

            xqOverseasEnterDetailService.save(xqOverseasEnterDetail);


            // 计算第一个月仓储费
            XqOverseasWarehouseFee warehouseFee = xqOverseasWarehouseFeeService.lambdaQuery()
                    .eq(XqOverseasWarehouseFee::getWarehouseId, xqOverseasEnterDetail.getWarehouseId())
                    .last("limit 1").one();
            if(warehouseFee != null){
                XqWarehouseCostDetail xqWarehouseCostDetail = creditsSignInQuartz.firstMonthFee(xqOverseasEnterDetail, warehouseFee);
                if(xqWarehouseCostDetail != null){
                    xqWarehouseCostDetailService.save(xqWarehouseCostDetail);
                }
            }
            i.setId(xqOverseasEnterDetail.getId());
        });

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
//        Integer count = xqOverseasEnterDetailService
//                .lambdaQuery().in(XqOverseasEnterDetail::getOrderDetailId, orderDetailIds)
//                .count();
//        if (count < 1) {
//            xqOrder.setEnterState(CommonConstant.ORDER_FINISH_INITIAL);
//            xqOrderMapper.updateById(xqOrder);
//            return;
//        }
//
//
//        for (XqOrderDetail orderDetail : orderDetails) {
//            String orderDetailId = orderDetail.getId();
//
//            List<XqOverseasEnterDetail> enterDetails = xqOverseasEnterDetailMapper
//                    .selectList(new QueryWrapper<XqOverseasEnterDetail>()
//                            .eq("order_detail_id", orderDetailId));
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

    public void judgeOrderCompleted(String orderNum) {
        XqOrder xqOrder = xqOrderMapper
                .selectOne(new QueryWrapper<XqOrder>().eq("order_num", orderNum));
        if (xqOrder == null) {
            return;
        }

        List<XqOrderDetail> orderDetails = xqOrderDetailMapper
                .selectList(new QueryWrapper<XqOrderDetail>().eq("order_id", xqOrder.getId()));

        List<XqOverseasEnterHead> list = xqOverseasEnterHeadService.lambdaQuery()
                .eq(XqOverseasEnterHead::getOrderNum, orderNum)
                .eq(XqOverseasEnterHead::getAuditStatus, "1")
                .list();

        List<String> headIds = list.stream().map(XqOverseasEnterHead::getId).collect(Collectors.toList());

        if (CollectionUtil.isEmpty(list)) {
            xqOrder.setEnterState(CommonConstant.ORDER_FINISH_INITIAL);
            xqOrderMapper.updateById(xqOrder);
            return;
        }

        for (XqOrderDetail orderDetail : orderDetails) {
            String orderDetailId = orderDetail.getId();

            List<XqOverseasEnterDetail> enterDetails = xqOverseasEnterDetailMapper
                    .selectList(new QueryWrapper<XqOverseasEnterDetail>()
                            .eq("order_detail_id", orderDetailId)
                            .in("source_id", headIds));

            int totalEnterNum = enterDetails.stream().mapToInt(XqOverseasEnterDetail::getEnterNum).sum();

            Integer totalBoxes = orderDetail.getTotalBoxes();
            if (totalEnterNum < totalBoxes) {
                xqOrder.setEnterState(CommonConstant.ORDER_FINISH_HALF);
                xqOrderMapper.updateById(xqOrder);
                return;
            }
        }

        xqOrder.setEnterState(CommonConstant.ORDER_FINISH_COMPLETE);
        xqOrderMapper.updateById(xqOrder);
    }


    public void saveDetailsToInventory(InstUptEnterStorageDTO dto) {

        List<XqInventoryOverseas> collect1 = dto.getDetails().stream().map(i -> {
            XqInventoryOverseas xqInventoryOverseas = new XqInventoryOverseas();
            Integer enterNum = i.getEnterNum();
            String id = i.getId();
            String r = i.getProductId();
            String warehouseId = i.getWarehouseId();
            String warehouseLot = i.getWarehouseLot();


            xqInventoryOverseas.setDirection(CommonConstant.ENTER_DIRECTION)
                    .setSourceType("cgrk")
                    .setNum(enterNum)
                    .setSourceId(id)
                    .setItemId(r)
                    .setWarehouseId(warehouseId)
                    .setWarehouseLot(warehouseLot)
                    .setRelativeTime(new Date());

            return xqInventoryOverseas;
        }).collect(Collectors.toList());

        xqInventoryOverseasService.saveBatch(collect1);
    }

    public void saveDetailsToInventory(List<XqOverseasEnterDetail> list) {

        List<XqInventoryOverseas> collect1 = list.stream().map(i -> {
            XqInventoryOverseas xqInventoryOverseas = new XqInventoryOverseas();
            Integer enterNum = i.getEnterNum();
            String id = i.getId();
            String r = i.getProductId();
            String warehouseId = i.getWarehouseId();
            String warehouseLot = i.getWarehouseLot();

            xqInventoryOverseas.setDirection(CommonConstant.ENTER_DIRECTION)
                    .setSourceType("cgrk")
                    .setNum(enterNum)
                    .setSourceId(id)
                    .setItemId(r)
                    .setWarehouseId(warehouseId)
                    .setWarehouseLot(warehouseLot)
                    .setRelativeTime(new Date());

            return xqInventoryOverseas;
        }).collect(Collectors.toList());

        xqInventoryOverseasService.saveBatch(collect1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteEnterStorage(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));

        if (this.lambdaQuery().in(XqOverseasEnterHead::getId, strings)
                .eq(XqOverseasEnterHead::getAuditStatus, "1").count() > 0) {
            throw new InterestingBootException("已审核的入库单不能删除");
        }

        List<String> collect1 = this.lambdaQuery().in(XqOverseasEnterHead::getId, strings).list()
                .stream().map(XqOverseasEnterHead::getOrderNum)
                .collect(Collectors.toList());

        this.removeByIds(strings);

        List<String> collect = xqOverseasEnterDetailService.lambdaQuery()
                .in(XqOverseasEnterDetail::getSourceId, strings)
                .select(XqOverseasEnterDetail::getId)
                .list().stream().map(XqOverseasEnterDetail::getId)
                .collect(Collectors.toList());

        xqOverseasEnterDetailService.lambdaUpdate()
                .in(XqOverseasEnterDetail::getSourceId, strings)
                .remove();

        // remove inventory
        xqInventoryOverseasService.physicalRemoveBySourceIds(collect);

        for (String string : collect1) {
            judgeOrderCompleted(string);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editEnterStorage(InstUptEnterStorageDTO dto) {
        XqOverseasEnterHead xqOverseasEnterHead = new XqOverseasEnterHead();
        BeanUtils.copyProperties(dto, xqOverseasEnterHead);
        this.updateById(xqOverseasEnterHead);

        xqOverseasEnterDetailMapper.removeBySourceId(dto.getId());

        dto.getDetails().forEach(i -> {
            XqOverseasEnterDetail xqOverseasEnterDetail = new XqOverseasEnterDetail();
            BeanUtils.copyProperties(i, xqOverseasEnterDetail);
            xqOverseasEnterDetail.setSourceId(xqOverseasEnterHead.getId());

            xqOverseasEnterDetailService.save(xqOverseasEnterDetail);
        });

        return true;
    }



    @Override
    public IPage<RelativeOrderPageVO> pageRelativeOrder(QueryPageRelativeOrderDTO dto) {
        Page<RelativeOrderPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        dto.setEnterState("2");
        IPage<RelativeOrderPageVO> pageList = xqOrderMapper.pageRelativeOrder(page, dto);
        return pageList;
    }

    @Override
    public IPage<SurplusOrderPageVO> pageSurplusOrder(@Valid QueryPageSurplusOrderDTO dto) {
        Page<SurplusOrderPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<SurplusOrderPageVO> results = xqOverseasEnterDetailMapper.pageSurplusOrder(page, dto);

        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editOrderStatus(EditEnterOrderStatusDTO dto) {
        List<String> strings = Arrays.asList(dto.getIds().split(","));
        for (String id : strings) {
            XqOverseasEnterHead byId = this.getById(id);
            if (dto.getAuditStatus().equals("1")) {
                if (byId.getAuditStatus().equals("1")) {
                    throw new InterestingBootException("已审核的单据不能再次审核");
                }

                // judge surplus num
                judgeSurplusNum(id);

                // insert inventory
                List<XqOverseasEnterDetail> list = xqOverseasEnterDetailService.lambdaQuery()
                        .eq(XqOverseasEnterDetail::getSourceId, id)
                        .list();
                saveDetailsToInventory(list);

                byId.setAuditStatus("1");
                this.updateById(byId);

                judgeOrderCompleted(byId.getOrderNum());

            } else {
                if (byId.getAuditStatus().equals("0")) {
                    throw new InterestingBootException("未审核的单据不能反审核");
                }
                byId.setAuditStatus("0");
                this.updateById(byId);

                List<XqOverseasEnterDetail> list = xqOverseasEnterDetailService.lambdaQuery()
                        .eq(XqOverseasEnterDetail::getSourceId, id)
                        .list();

                for (XqOverseasEnterDetail t : list) {
                    // check inventory num
                    Integer currentInventory = xqInventoryOverseasService
                            .getInventoryNum(t.getProductId(), t.getWarehouseId(),t.getWarehouseLot(),
                                    byId.getOrderNum());
                    if (t.getEnterNum() > currentInventory) {
                        throw new InterestingBootException("反审核失败，库存不足");
                    }
                }


                List<String> removeIds = list.stream().map(XqOverseasEnterDetail::getId)
                        .collect(Collectors.toList());



                xqInventoryOverseasService.physicalRemoveBySourceIds(removeIds);

                judgeOrderCompleted(byId.getOrderNum());


            }
        }

        return true;
    }

    @Override
    public IPage<EnterStorageDetailPageVO> pageEnterStorageDetail(QueryPageEnterStorageDetailDTO dto) {
        Page<EnterStorageDetailPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        if (StringUtils.isNotBlank(dto.getReceiptDateBegin()) && StringUtils.isNotBlank(dto.getReceiptDateEnd())) {
            if (dto.getReceiptDateBegin().equals(dto.getReceiptDateEnd())) {
                dto.setReceiptDateBegin(dto.getReceiptDateBegin() + " 00:00:00");
                dto.setReceiptDateEnd(dto.getReceiptDateEnd() + " 23:59:59");
            }
        }
        return this.baseMapper.pageEnterStorageDetail(page, dto);
    }

    private void judgeSurplusNum(String id) {
        List<XqOverseasEnterDetail> list2 = xqOverseasEnterDetailService.lambdaQuery()
                .eq(XqOverseasEnterDetail::getSourceId, id)
                .list();
        for (XqOverseasEnterDetail xqOverseasEnterDetail : list2) {
            String orderDetailId = xqOverseasEnterDetail.getOrderDetailId();
            Integer enterNum = xqOverseasEnterDetail.getEnterNum();
            XqOrderDetail byId1 = xqOrderDetailService.getById(orderDetailId);
            Integer totalBoxes = byId1.getTotalBoxes();

            List<String> collect = xqOverseasEnterHeadService.lambdaQuery()
                    .eq(XqOverseasEnterHead::getAuditStatus, "1")
                    .select(XqOverseasEnterHead::getId)
                    .list()
                    .stream().map(XqOverseasEnterHead::getId)
                    .collect(Collectors.toList());

            int sum = 0;
            if (CollectionUtil.isNotEmpty(collect)) {
                sum = xqOverseasEnterDetailService.lambdaQuery()
                        .in(XqOverseasEnterDetail::getSourceId, collect)
                        .eq(XqOverseasEnterDetail::getOrderDetailId, orderDetailId)
                        .select(XqOverseasEnterDetail::getEnterNum)
                        .list()
                        .stream().mapToInt(XqOverseasEnterDetail::getEnterNum)
                        .sum();
            }


            if (enterNum + sum > totalBoxes) {
                throw new InterestingBootException("入库数量超过订单数量");
            }
        }
    }
}




