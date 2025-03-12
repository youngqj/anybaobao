package com.interesting.modules.freightInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.service.ISysCodeRoleService;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.freightInfo.dto.QueryXqFreightInfoDTO;
import com.interesting.modules.freightInfo.entity.XqFreightInfo;
import com.interesting.modules.freightInfo.mapper.XqFreightInfoMapper;
import com.interesting.modules.freightInfo.service.IXqFreightInfoService;
import com.interesting.modules.freightInfo.vo.XqFreightInfoVO;
import com.interesting.modules.order.dto.sub.AddOrderFretDTO;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.interesting.modules.orderdetail.service.IXqOrderDetailService;
import com.interesting.modules.reportform.dto.QueryPageSellProfitDTO;
import com.interesting.modules.reportform.vo.SellProfitPageVO;
import com.interesting.modules.reportform.vo.SellProfitPageVO1;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.List;

/**
 * @Description: 货运信息表
 * @Author: interesting-boot
 * @Date: 2023-06-02
 * @Version: V1.0
 */
@Service
public class XqFreightInfoServiceImpl extends ServiceImpl<XqFreightInfoMapper, XqFreightInfo> implements IXqFreightInfoService {

    @Resource
    private IXqOrderService xqOrderService;
    @Resource
    private ISysCodeRoleService sysCodeRoleService;
    @Resource
    private IXqOrderDetailService xqOrderDetailService;


    @Override
    public IPage<XqFreightInfoVO> pageList(Page<XqFreightInfoVO> page, QueryXqFreightInfoDTO dto) {
        return this.baseMapper.pageList(page, dto);
    }


    /**
     * 差异化处理编辑 货运信息
     *
     * @param orderId
     * @param addOrderFretDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderFretInfo(String orderId, List<AddOrderFretDTO> addOrderFretDTOS, Integer roleCode) {

        AddOrderFretDTO addOrderFretDTO = addOrderFretDTOS.get(0);
        if (addOrderFretDTO != null) {

            XqOrder xqOrder = xqOrderService.getById(orderId);
            if (xqOrder == null) throw new InterestingBootException("修改失败，该订单不存在");

            if (roleCode == 1) {
                this.lambdaUpdate()
                        .set(XqFreightInfo::getDestinationPort, addOrderFretDTO.getDestinationPort())
                        .eq(XqFreightInfo::getId, addOrderFretDTO.getId())
                        .update();
            }

            if (roleCode == 2) {
                this.lambdaUpdate()
                        .set(XqFreightInfo::getExportDeclarationNum, addOrderFretDTO.getExportDeclarationNum())
                        .eq(XqFreightInfo::getId, addOrderFretDTO.getId())
                        .update();
            }


            if (roleCode == 5) {

                this.lambdaUpdate()
                        .set(XqFreightInfo::getLoadingPort, addOrderFretDTO.getLoadingPort())
                        .set(XqFreightInfo::getDestinationPort, addOrderFretDTO.getDestinationPort())
                        .set(XqFreightInfo::getNetWeight, addOrderFretDTO.getNetWeight())
                        .eq(XqFreightInfo::getId, addOrderFretDTO.getId())
                        .update();
            }


            if (roleCode == 6) {

                this.lambdaUpdate()
                        .set(XqFreightInfo::getGrossWeight, addOrderFretDTO.getGrossWeight())
                        .eq(XqFreightInfo::getId, addOrderFretDTO.getId())
                        .update();
            }


            if (roleCode == 7) {
                // 插入出货序号
                // 插入发票日期
                // 订单信息 发票日期
                if (addOrderFretDTO.getCustomsClearanceTime() != null && addOrderFretDTO.getEtd() != null) {
                    Calendar instance = Calendar.getInstance();
                    instance.setTime(addOrderFretDTO.getEtd());
                    if (StringUtils.isBlank(xqOrder.getShippingNum())) {
//                    String str_codeNum = sysCodeRoleService.getCodeNumByType2(1, 1);
                        String str_codeNum = xqOrderService.getShippingNum(addOrderFretDTO.getEtd());
                        xqOrderService.lambdaUpdate()
                                .set(XqOrder::getShippingNum, str_codeNum)
                                .eq(XqOrder::getId, orderId)
                                .update();
                    }
                }

                this.lambdaUpdate()
                        .set(XqFreightInfo::getShipCompany, addOrderFretDTO.getShipCompany())
                        .set(XqFreightInfo::getVoyageNumber, addOrderFretDTO.getVoyageNumber())
                        .set(XqFreightInfo::getForwarderCompany, addOrderFretDTO.getForwarderCompany())
                        .set(XqFreightInfo::getForwarderContact, addOrderFretDTO.getForwarderContact())
                        .set(XqFreightInfo::getEtd, addOrderFretDTO.getEtd())
                        .set(XqFreightInfo::getEta, addOrderFretDTO.getEta())
                        .set(XqFreightInfo::getBillOfLading, addOrderFretDTO.getBillOfLading())
                        .set(XqFreightInfo::getContainerNo, addOrderFretDTO.getContainerNo())
                        .set(XqFreightInfo::getSealNo, addOrderFretDTO.getSealNo())
                        .set(XqFreightInfo::getInspectionNote, addOrderFretDTO.getInspectionNote())
                        .set(XqFreightInfo::getInspectionSubmitDate, addOrderFretDTO.getInspectionSubmitDate())
                        .set(XqFreightInfo::getIsfReceiptTime, addOrderFretDTO.getIsfReceiptTime())
                        .set(XqFreightInfo::getPickUpDate, addOrderFretDTO.getPickUpDate())
                        .set(XqFreightInfo::getLoadingDate, addOrderFretDTO.getLoadingDate())
                        .set(XqFreightInfo::getArrivalDate, addOrderFretDTO.getArrivalDate())
                        .set(XqFreightInfo::getForeignAirReturnDate, addOrderFretDTO.getForeignAirReturnDate())
                        .set(XqFreightInfo::getForeignPickTime, addOrderFretDTO.getForeignPickTime())
                        .set(XqFreightInfo::getLoadingPort, addOrderFretDTO.getLoadingPort())
                        .set(XqFreightInfo::getDestinationPort, addOrderFretDTO.getDestinationPort())
                        .set(XqFreightInfo::getVolume, addOrderFretDTO.getVolume())
                        .set(XqFreightInfo::getCustomsClearanceTime, addOrderFretDTO.getCustomsClearanceTime())
                        .set(XqFreightInfo::getContainerTemperature, addOrderFretDTO.getContainerTemperature())
                        .set(XqFreightInfo::getReturnCargoTime, addOrderFretDTO.getReturnCargoTime())
                        .set(XqFreightInfo::getArrivePortTime, addOrderFretDTO.getArrivePortTime())
                        // 退运部分信息
                        .set(XqFreightInfo::getReturnShipCompany, addOrderFretDTO.getReturnShipCompany())
                        .set(XqFreightInfo::getReturnVoyageNumber, addOrderFretDTO.getReturnVoyageNumber())
                        .set(XqFreightInfo::getReturnBillOfLading, addOrderFretDTO.getReturnBillOfLading())
                        .set(XqFreightInfo::getReturnContainerNo, addOrderFretDTO.getReturnContainerNo())
                        .set(XqFreightInfo::getReturnLoadingPort, addOrderFretDTO.getReturnLoadingPort())
                        .set(XqFreightInfo::getReturnDestinationPort, addOrderFretDTO.getReturnDestinationPort())
                        .set(XqFreightInfo::getReturnNetWeight, addOrderFretDTO.getReturnNetWeight())
                        .set(XqFreightInfo::getReturnGrossWeight, addOrderFretDTO.getReturnGrossWeight())
                        .set(XqFreightInfo::getReturnBoxNum, addOrderFretDTO.getReturnBoxNum())

                        .eq(XqFreightInfo::getId, addOrderFretDTO.getId())
                        .update();

//                List<XqOrderDetail> orderDetails = xqOrderDetailService.list(new LambdaQueryWrapper<XqOrderDetail>().eq(XqOrderDetail::getOrderId, orderId));
//                if (xqOrder.getOrderType().equals("1")) {
//                    if (addOrderFretDTO.getLoadingDate() != null) {
//                        //去更新下销售单价/磅和销售单价/箱
//                        QueryPageSellProfitDTO dto = new QueryPageSellProfitDTO();
//                        dto.setPageNo(1);
//                        dto.setPageSize(999);
//                        dto.setOrderNum(xqOrder.getOrderNum());
//                        List<SellProfitPageVO1> list = xqOrderService.pageSellProfit1(dto);
//                        if (list.size() > 0) {
//                            for (XqOrderDetail detail : orderDetails) {
//                                for (SellProfitPageVO1 vo : list) {
//                                    if (detail.getId().equals(vo.getOrderDetailId())) {
//
//                                        String str = detail.getPackaging();
//                                        BigDecimal packing = BigDecimal.ZERO;
//                                        if (str.contains("/")) {
//                                            packing = replaceAndEvaluateExpression(str);
//                                        } else {
//                                            packing = new BigDecimal(str);
//                                        }
//                                        if (vo.getTotalAmount().compareTo(BigDecimal.ZERO) < 0) {
//                                            detail.setPricePerLb(BigDecimal.ZERO);
//                                            detail.setPricePerBox(BigDecimal.ZERO);
//                                            detail.setSalesAmount(BigDecimal.ZERO);
//
//                                        } else {
//                                            detail.setPricePerLb(vo.getTotalAmount().setScale(3, RoundingMode.HALF_UP));
//                                            detail.setPricePerBox(detail.getPricePerLb().setScale(3, RoundingMode.HALF_UP).multiply(packing).setScale(2, RoundingMode.HALF_UP));
//                                        }
//                                        detail.setSalesAmount(detail.getPricePerLb().multiply(detail.getTotalWeight()).setScale(2, RoundingMode.HALF_UP));
//                                        break; // 找到匹配的productId后跳出内层循环
//                                    }
//                                }
//                            }
//                        } else {
//                            for (XqOrderDetail detail : orderDetails) {
//                                detail.setPricePerLb(BigDecimal.ZERO);
//                                detail.setPricePerBox(BigDecimal.ZERO);
//                                detail.setSalesAmount(BigDecimal.ZERO);
//                            }
//                        }
//                    }
//                    xqOrderDetailService.updateBatchById(orderDetails);
//                }
                if (addOrderFretDTO.getLoadingDate() != null) {
//                    xqOrder.setInvoiceDate(addOrderFretDTO.getLoadingDate());
//                    xqOrderService.updateById(xqOrder);
                    xqOrderService.lambdaUpdate().set(XqOrder::getInvoiceDate, addOrderFretDTO.getLoadingDate()).eq(XqOrder::getId, orderId).update();
                }
            }
        }
    }

    public static BigDecimal replaceAndEvaluateExpression(String expression) {
        String replacedExpression = expression.replace("/", "*");

        // 分割表达式，获取操作数和运算符
        String[] tokens = replacedExpression.split("\\*");
        double operand1 = Double.parseDouble(tokens[0]);
        double operand2 = Double.parseDouble(tokens[1]);

        // 计算结果
        double result = operand1 * operand2;

        // 返回结果
        return BigDecimal.valueOf(result);
    }
}
