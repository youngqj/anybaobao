package com.interesting.modules.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.accmaterial.vo.AccInventoryDetailsVO;
import com.interesting.modules.files.vo.ListXqFileVO;
import com.interesting.modules.freightInfo.vo.XqOrderFreightNoteVO;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.notes.vo.XqProblemNoteVO;
import com.interesting.modules.order.dto.QueryXqOrderDTO;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.vo.XqOrderAllVO;
import com.interesting.modules.order.vo.XqOrderExportFretVO;
import com.interesting.modules.order.vo.XqOrderExportVO;
import com.interesting.modules.order.vo.XqOrderVO;
import com.interesting.modules.order.vo.sub.*;
import com.interesting.modules.orderdetail.vo.XqCommissionOrderVO;
import com.interesting.modules.overseas.dto.QueryPageRelativeOrderDTO;
import com.interesting.modules.overseas.vo.RelativeOrderPageVO;
import com.interesting.modules.rawmaterial.vo.XqCutAmountDetailVO;
import com.interesting.modules.rawmaterial.vo.XqPaymentDetailVO;
import com.interesting.modules.reportform.dto.QueryPageSellProfitDTO;
import com.interesting.modules.reportform.vo.SellProfitPageVO;
import com.interesting.modules.reportform.vo.SellProfitPageVO1;
import com.interesting.modules.truck.vo.XqTruckInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 订单表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Mapper
public interface XqOrderMapper extends BaseMapper<XqOrder> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqOrderVO> pageList(@Param("page") Page<XqOrderVO> page, @Param("dto") QueryXqOrderDTO dto, @Param("sortOrder") String sortOrder);

    XqOrderAllVO getOrderHead(@Param("id") String id);

    List<XqOrderProdVO> listOrderProds(@Param("id") String id);

    List<XqOrderComsVO> listOrderComs(@Param("id") String id);

    List<XqOrderComsVO> listOrderComsByCustomer(@Param("customerId") String customerId);

    List<XqOrderRemiVO> listOrderRemi(@Param("id") String id);

    List<XqOrderRawVO> listOrderRaw(@Param("id") String id);

    //List<XqOrderRawFinanceVO> listOrderRawFinance(@Param("id") String id);

    List<XqCommissionOrderVO> listOrderComsDetail(@Param("id") String id);

    List<XqOrderAcsrVO> listOrderAcsr(@Param("id") String id);

    List<XqOrderFretCostVO> listOrderFretCost(@Param("id") String id);

    /**
     * 货运费用信息
     *
     * @param id
     * @param type 1国内 0国外
     * @return
     */
    List<XqOrderFretCostVO> listOrderFretCostWithType(@Param("id") String id, @Param("type") Integer type);

    List<XqOrderFretVO> listOrderFret(@Param("id") String id);

    List<XqOrderInsuranceVO> listOrderInsurance(@Param("id") String id);

    List<XqOrderFretCostVO> listSupplierFretCost(@Param("supplierId") String supplierId);

    List<XqOrderFretCostVO> listSupplierFretOrderCost(@Param("supplierId") String supplierId, @Param("orderId") String orderId);

    List<XqOrderFretCostVO> listCustomerFretCost(@Param("customerId") String customerId,
                                                 @Param("orderId")String orderId);

    List<XqOrderFretCostVO> listWarehouseFretCost(@Param("warehouseId") String warehouseId,
                                                 @Param("orderId")String orderId);

    List<XqOrderFretCostVO> listCustomerFretOrderCost(@Param("customerId") String customerId, @Param("orderId") String orderId);

    List<ListXqFileVO> listOrderFiles(@Param("id") String id);

    List<XqProblemNoteVO> listProblemNotes(@Param("id") String id);

    List<XqPaymentDetailVO> listPaymentDetails(@Param("id") String id);

    List<XqCutAmountDetailVO> listCutAmountDetails(@Param("id") String id);


    List<AccInventoryDetailsVO> listUseOrderAcsrDetails(@Param("id") String id);

    List<AccInventoryDetailsVO> listBackOrderAcsrDetails(String id);

    IPage<RelativeOrderPageVO> pageRelativeOrder(@Param("page") Page<RelativeOrderPageVO> page,
                                                 @Param("dto") QueryPageRelativeOrderDTO dto);

    List<XqTruckInfoVO> listTruckInfo(@Param("id") String id);

    List<XqOrderFretCostVO> listFretCostReturnFee(@Param("id") String id);

    List<XqOrderRemiExpDateVO> listRemiExpDateVOS(@Param("id") String id);

    IPage<SellProfitPageVO> pageSellProfit(@Param("page") Page<SellProfitPageVO> page,
                                           @Param("dto") QueryPageSellProfitDTO dto,
                                           @Param("customerIds")List<String> customerIds);

    List<ListXqOrderExtraCostVO> listOrderExtraCosts(@Param("id") String id);

    List<XqOrderCreditsInsuranceVO> listOrderCreditsInsuranceVOS(@Param("id") String id);

    List<XqOrderFreightNoteVO> listOrderFreightNoteVOS(@Param("id") String id);

    List<XqOrderExportVO> orderExportVO(@Param("orderNum") String orderNum);

    List<XqOrderExportFretVO> orderExportFretVOS(@Param("orderNum") String orderNum);


    List<SellProfitPageVO1> pageSellProfit1(@Param("dto") QueryPageSellProfitDTO dto);


    XqOrderExportVO orderExportVOByGzy(@Param(("orderId")) String orderId);

    XqFreightCostInfo getKaCheFee(@Param("orderId") String orderId, @Param("customerId") String customerId);

    XqFreightCostInfo getFreightCostInfoByAddress(@Param("customerId")String customerId, @Param("deliveryAddress") String deliveryAddress);
}
