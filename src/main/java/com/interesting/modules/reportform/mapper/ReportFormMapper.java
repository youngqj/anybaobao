package com.interesting.modules.reportform.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.reportform.dto.*;
import com.interesting.modules.reportform.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @DESCRIPTION:/getWarehouseProfit
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/19 9:37
 * @VERSION: V1.0
 */
public interface ReportFormMapper {


    IPage<PageRemittanceReportVO> remittanceReport(@Param("page") Page<PageRemittanceReportVO> page, @Param("dto") RemittanceReportDTO dto, @Param("customerIds") List<String> customerIds);

    List<SumRemittanceFinanceVO> sumRemittanceFinance(@Param("dto") RemittanceReportDTO dto, @Param("customerIds") List<String> customerIds);

    IPage<WarehouseCostPageVO> pageWarehouseCost(@Param("page") Page<WarehouseCostPageVO> page, @Param("dto") QueryPageWarehouseDTO dto);

    IPage<OrderAscrListVO> orderAcsrPage(@Param("page") Page<OrderAscrListVO> page, @Param("dto") QueryOrderAcsrDTO dto, @Param("supplierIds") List<String> supplierIds, @Param("originalSupplierIds") List<String> originalSupplierIds);

    List<SumOrderAscrFinanceVO> sumOrderAcsrPage(@Param("dto") QueryOrderAcsrDTO dto, @Param("supplierIds") List<String> supplierIds, @Param("originalSupplierIds") List<String> originalSupplierIds);

    IPage<FreightReportVO> freightReport(@Param("page") Page<FreightReportVO> page, @Param("dto") QueryFreightDTO dto, @Param("customerIds") List<String> customerIds);

    List<SumFreightVO> sumFreight(@Param("dto") QueryFreightDTO dto, @Param("customerIds") List<String> customerIds);

    IPage<FreightGWReportVO> freightGWReport(@Param("page") Page<FreightGWReportVO> page, @Param("dto") QueryGWFreightDTO dto, @Param("customerIds") List<String> customerIds);

    List<SumFreightGWVO> sumFreightGWReport(@Param("dto") QueryGWFreightDTO dto, @Param("customerIds") List<String> customerIds);


    IPage<FreightTYReportVO> freightTYReport(@Param("page") Page<FreightTYReportVO> page, @Param("dto") QueryTYFreightDTO dto);

    IPage<FreightKYReportVO> freightKYReport(@Param("page") Page<FreightKYReportVO> page, @Param("dto") QueryFreightKYDTO dto);

    IPage<FreightKCReportVO> freightKCReport(@Param("page") Page<FreightKCReportVO> page, @Param("dto") QueryFreightKCDTO dto);

    IPage<FactoryReportVO> factoryReport(@Param("page") Page<FactoryReportVO> page, @Param("dto") QueryFactoryDTO dto, @Param("customerIds") List<String> customerIds, @Param("supplierIds") List<String> supplierIds);

    IPage<SalesStatisticsReportVO> salesStatisticsReport(@Param("page") Page<SalesStatisticsReportVO> page,
                                                         @Param("dto") QuerySalesStatisticsDTO dto,
                                                         @Param("customerIds") List<String> customerIds,
                                                         @Param("supplierIds") List<String> supplierIds);

    IPage<FinanceReportVO> financeReport(@Param("page") Page<FinanceReportVO> page,
                                         @Param("dto") QueryFinanceDTO dto,
                                         @Param("customerIds") List<String> customerIds,
                                         @Param("supplierIds") List<String> supplierIds,
                                         @Param("chfsList") List<String> chfsList);


    List<SumFactoryFinanceVO> sumfactoryFinance(@Param("dto") QueryFactoryDTO dto, @Param("customerIds") List<String> customerIds, @Param("supplierIds") List<String> supplierIds);

    List<SumSalesStatisticsVO> sumSalesStatistics(@Param("dto") QuerySalesStatisticsDTO dto, @Param("customerIds") List<String> customerIds, @Param("supplierIds") List<String> supplierIds);

    // BigDecimal totalCreditInsurancePremium(@Param("dto") QuerySalesStatisticsDTO dto, @Param("customerIds") List<String> customerIds, @Param("supplierIds") List<String> supplierIds);

    List<SumFinanceReportVO> sumFinanceReport(@Param("dto") QueryFinanceDTO dto, @Param("customerIds") List<String> customerIds, @Param("supplierIds") List<String> supplierIds,@Param("chfsList") List<String> chfsList);

    List<FinanceTemplateVO> financeTemplateExport(@Param("dto") QueryFinanceDTO dto,
                                                  @Param("customerIds") List<String> customerIds,
                                                  @Param("supplierIds") List<String> supplierIds,
                                                  @Param("chfsList") List<String> chfsList);

    List<FreightReportTemplateVO> freightReportTemplate(@Param("dto") QueryFreightDTO dto, @Param("customerIds") List<String> customerIds);

    List<FreightReportGWTemplateVO> freightGWReportTemplate(@Param("dto") QueryGWFreightDTO dto, @Param("customerIds") List<String> customerIds);


    List<SumFreightKCVO> sumFreightKC(@Param("dto") QueryFreightKCDTO dto);

    List<SumFreightTYVO> sumFreightTY(@Param("dto") QueryTYFreightDTO dto);

    List<TiHuoLiRunVO> getWarehouseProfit(@Param("dto") QueryTiHuoLiRunDTO dto);

    BigDecimal getWarehouseFreeByDate(@Param("warehouseId") String warehouseId,@Param("bolTimeStart") String bolTimeStart, @Param("bolTimeEnd") String bolTimeEnd);


    List<TiHuoLiRunTotalVO> getWarehouseProfitByYear(String year);

    List<TiHuoLiRunMonthVO> getWarehouseFeeByYear(String year);

    List<PayableVO> getPayableList(@Param("dto") PayableDTO dto);

    IPage<FreightTYReportNewVO> freightTYReportNew(@Param("page") Page<FreightTYReportNewVO> page,@Param("dto") QueryTYFreightNewDTO dto);

    List<SumFreightTYNewVO> sumFreightTYNew(@Param("dto") QueryTYFreightNewDTO dto);
}
