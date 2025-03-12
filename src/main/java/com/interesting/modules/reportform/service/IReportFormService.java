package com.interesting.modules.reportform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.modules.reportform.dto.*;
import com.interesting.modules.reportform.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/19 9:33
 * @VERSION: V1.0
 */
public interface IReportFormService {

    IPage<PageRemittanceReportVO> remittanceReport(Page<PageRemittanceReportVO> page, RemittanceReportDTO dto);

    List<SumRemittanceFinanceVO> sumRemittanceFinance(RemittanceReportDTO dto);
    void remittanceReportExport(Page<PageRemittanceReportVO> page, RemittanceReportDTO dto, HttpServletResponse response);

    IPage<WarehouseCostPageVO> pageWarehouseCost(QueryPageWarehouseDTO dto);

    IPage<OrderAscrListVO> orderAcsrPage(QueryOrderAcsrDTO dto);

    IPage<FreightReportVO> freightReport(Page<FreightReportVO> page, QueryFreightDTO dto);

    List<SumFreightVO> sumFreight(QueryFreightDTO dto);

    void freightReportTemplate(QueryFreightDTO dto, HttpServletResponse response);

    void freightReportGWTemplate(QueryGWFreightDTO dto, HttpServletResponse response);

    IPage<FreightGWReportVO> freightGWReport(Page<FreightGWReportVO> page, QueryGWFreightDTO dto);

    List<SumFreightGWVO> sumFreightGWReport(QueryGWFreightDTO dto);

    void freightGWReportExport(Page<FreightGWReportVO> page, QueryGWFreightDTO dto, HttpServletResponse response);

    IPage<FreightTYReportVO> freightTYReport(Page<FreightTYReportVO> page, QueryTYFreightDTO dto);

    IPage<FreightTYReportNewVO> freightTYReportNew(Page<FreightTYReportNewVO> page, QueryTYFreightNewDTO dto);

    List<SumFreightTYVO> sumFreightTY(QueryTYFreightDTO dto);

    IPage<FreightKYReportVO> freightKYReport(Page<FreightKYReportVO> page, QueryFreightKYDTO dto);

    IPage<FreightKCReportVO> freightKCReport(Page<FreightKCReportVO> page, QueryFreightKCDTO dto);

    List<SumFreightKCVO> sumFreightKC(QueryFreightKCDTO dto);
    void freightKCReportExport(Page<FreightKCReportVO> page, QueryFreightKCDTO dto, HttpServletResponse response);

    IPage<FactoryReportVO> factoryReport(Page<FactoryReportVO> page, QueryFactoryDTO dto);
    List<SumFactoryFinanceVO> sumfactoryFinance(QueryFactoryDTO dto);

    IPage<SalesStatisticsReportVO> salesStatisticsReport(Page<SalesStatisticsReportVO> page, QuerySalesStatisticsDTO dto);

    List<SumSalesStatisticsVO> sumSalesStatistics(QuerySalesStatisticsDTO dto);

    IPage<FinanceReportVO> financeReport(Page<FinanceReportVO> page, QueryFinanceDTO dto);

    List<SumFinanceReportVO> sumFinance(QueryFinanceDTO dto);

    List<SumOrderAscrFinanceVO> sumOrderAscrFinance(QueryOrderAcsrDTO dto);



    void orderAcsrExport(Page<OrderAscrListVO> page, QueryOrderAcsrDTO dto, HttpServletResponse response);

    Result<?> orderAcsrImport(MultipartFile file) throws Exception;

    Result<?> orderAcsrImportByOrderNum(String orderNum, MultipartFile file) throws Exception;

    void freightReportExport(Page<FreightReportVO> page, QueryFreightDTO dto, HttpServletResponse response);

    void factoryReportExport(Page<FactoryReportVO> page, QueryFactoryDTO dto, HttpServletResponse response);

    void salesStatisticsReportExport(Page<SalesStatisticsReportVO> page, QuerySalesStatisticsDTO dto, HttpServletResponse response);

    void financeReportExport(Page<FinanceReportVO> page, QueryFinanceDTO dto, HttpServletResponse response);

    void financeReportExportBySelect(ReportExportDto dto, HttpServletResponse response);

    void orderAcsrExportByOrderNum(String orderNum, HttpServletResponse response);

    boolean editFreightReport(EditFreightDTO dto);

    boolean editGWFreightReport(EditGWFreightDTO dto);


    Result<?> financeImport(MultipartFile file) throws Exception;
    void freightTYReportExport(Page<FreightTYReportVO> page, QueryTYFreightDTO dto, HttpServletResponse response);
    void freightTYReportExportNew(Page<FreightTYReportNewVO> page, QueryTYFreightNewDTO dto, HttpServletResponse response);
    void financeTemplateExport(QueryFinanceDTO dto, HttpServletResponse response);
    void financeReportTemplateExportBySelect(List<FinanceTemplateVO> results, HttpServletResponse response);

    Result<?> freightReportImport(MultipartFile file) throws Exception;

    Result<?> freightReportGWImport(MultipartFile file) throws Exception;


    void freightKCReportTemplate(QueryFreightKCDTO dto, HttpServletResponse response);

    Result<?> freightKCReportImport(MultipartFile file);

    Map<String, Object> getWarehouseProfit(QueryTiHuoLiRunDTO dto);

    List<TiHuoLiRunTotalVO> getWarehouseProfitByYear(String year);

    List<PayableVO> getPayableList(PayableDTO dto);

    void payableReportExport(PayableDTO dto, HttpServletResponse response);

    List<SumFreightTYNewVO> sumFreightTYNew(QueryTYFreightNewDTO dto);
}
