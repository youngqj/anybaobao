package com.interesting.modules.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.common.api.vo.Result;
import com.interesting.modules.order.dto.*;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.vo.ShowOrderVO;
import com.interesting.modules.order.vo.XqOrderAllVO;
import com.interesting.modules.order.vo.XqOrderCompleteMsgVO;
import com.interesting.modules.order.vo.XqOrderVO;
import com.interesting.modules.order.vo.sub.XqOrderFretCostVO;
import com.interesting.modules.reportform.dto.QueryPageSellProfitDTO;
import com.interesting.modules.reportform.vo.SellProfitPageVO;
import com.interesting.modules.reportform.vo.SellProfitPageVO1;
import com.interesting.modules.reportform.vo.SellProfitTotalVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 订单表
 * @Author: interesting-boot
 * @Date: 2023-06-01
 * @Version: V1.0
 */
public interface IXqOrderService extends IService<XqOrder> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqOrderVO> pageList(Page<XqOrderVO> page, QueryXqOrderDTO dto);

    XqOrderAllVO queryAllById(String id);

    boolean addAll(AddXqOrderAllDTO dto);

    boolean editAll(EditXqOrderAllDTO dto);

    boolean auditFollowerById(AuditDTO dto);

//    boolean auditFinanceById(AuditDTO dto);

    boolean copyOrder(String id);

    boolean copyAddAll(CopyAddXqOrderAllDTO dto);

    XqOrderCompleteMsgVO queryCompleteState();

    XqOrderCompleteMsgVO getMsgByRole(List<Integer> roleCode, String userId);

    boolean removeDetail(String id);

    XqOrderAllVO copyOrdersViews(String id);

    IPage<SellProfitPageVO> pageSellProfit(QueryPageSellProfitDTO dto);

    void pageSellProfitExport(QueryPageSellProfitDTO dto, HttpServletResponse response);

    boolean auditRepeatById(AuditDTO dto);

    boolean transferOrder(TransferDTO dto);


    Result<?> listDeliveryAddress(String customerId);

    Result<?> listDeliveryAddressByWarehouseId(String warehouseId);

    void doNotRemind(String orderId);

    void doNotRemindPayment(String orderId);

    ShowOrderVO showOrder(String orderId);

    void orderExportVO(String orderId, HttpServletRequest request, HttpServletResponse response);


    List<SellProfitPageVO1> pageSellProfit1(QueryPageSellProfitDTO dto);

    List<SellProfitTotalVO> totalSellProfit(QueryPageSellProfitDTO dto);


    //    List<QueryDataNotInDetailVO> queryRemittanceLessThanTenDays();
    ArrayList<XqOrderFretCostVO> initFretCostVOs();

    IPage<SellProfitPageVO> pageSellProfitNew(QueryPageSellProfitDTO dto);

    List<SellProfitTotalVO> totalSellProfitNew(QueryPageSellProfitDTO dto);

    boolean cancelOrderStatus(String orderNum);

    String getShippingNum(Date etd);
}
