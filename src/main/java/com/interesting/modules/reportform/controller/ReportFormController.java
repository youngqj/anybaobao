package com.interesting.modules.reportform.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.common.util.oConvertUtils;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.overseas.service.XqInventoryOverseasService;
import com.interesting.modules.reportform.dto.*;
import com.interesting.modules.reportform.service.IReportFormService;
import com.interesting.modules.reportform.service.XqSellingProfitService;
import com.interesting.modules.reportform.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "报表")
@RestController
@RequestMapping("/report/form")
public class ReportFormController {
    @Autowired
    private IXqOrderService xqOrderService;

    @Autowired
    private XqInventoryOverseasService xqInventoryOverseasService;

    @Resource
    private IReportFormService reportFormService;
    @Autowired
    private XqSellingProfitService xqSellingProfitService;


//    /**
//     * 销售利润表
//     */
//    @AutoLog(value = "销售利润表")
//    @ApiOperation(value = "销售利润表", notes = "销售利润表")
//    @GetMapping(value = "/pageSellProfit")
//    public Result<IPage<SellProfitPageVO>> pageSellProfit(QueryPageSellProfitDTO dto) {
//        IPage<SellProfitPageVO> pageList = xqOrderService.pageSellProfit(dto);
//
//        // 查询销售利润总额
//        List<SellProfitTotalVO> profitTotalVOList = xqOrderService.totalSellProfit(dto);
//
//
//        return Result.OK2(pageList, profitTotalVOList);
//    }



    /**
     * 销售利润表
     */
    @AutoLog(value = "销售利润表(new)")
    @ApiOperation(value = "销售利润表(new)", notes = "销售利润表(new)")
    @GetMapping(value = "/pageSellProfit")
    public Result<IPage<SellProfitPageVO>> pageSellProfitNew(QueryPageSellProfitDTO dto) {
        IPage<SellProfitPageVO> pageList = xqOrderService.pageSellProfitNew(dto);

        // 查询销售利润总额
        List<SellProfitTotalVO> profitTotalVOList = xqOrderService.totalSellProfitNew(dto);


        return Result.OK2(pageList, profitTotalVOList);
    }


    /**
     * 销售利润表 - 初始化
     */
    @AutoLog(value = "销售利润表 - 初始化")
    @ApiOperation(value = "销售利润表 - 初始化", notes = "销售利润表 - 初始化")
    @GetMapping(value = "/initSellProfit")
    public Result<?> initSellProfit() {
        QueryPageSellProfitDTO dto = new QueryPageSellProfitDTO();
        dto.setPageSize(99999);
        log.info("统计销售利润报表 ----------------------- start -------------------------");
        IPage<SellProfitPageVO> profitPageVOIPage = xqOrderService.pageSellProfit(dto);
        if (profitPageVOIPage != null && profitPageVOIPage.getRecords().size() > 0) {
            log.info("统计销售利润报表 ----------------------- end ------------------------- 共："+profitPageVOIPage.getRecords().size());
            boolean flag = xqSellingProfitService.init(profitPageVOIPage.getRecords());
            if(flag){
                return Result.OK();
            }
        }
        return Result.error("初始化失败！");
    }




    /**
     * 销售利润表导出
     */
    @AutoLog(value = "销售利润表导出")
    @ApiOperation(value = "销售利润表导出", notes = "销售利润表导出")
    @GetMapping(value = "/pageSellProfitExport")
    public void pageSellProfitExport(QueryPageSellProfitDTO dto, HttpServletResponse response) {
        xqOrderService.pageSellProfitExport(dto, response);
    }

    /**
     * 仓库成本计算
     */
    @AutoLog(value = "仓库成本计算")
    @ApiOperation(value = "仓库成本计算", notes = "仓库成本计算")
    @GetMapping(value = "/pageWarehouseCost")
    public Result<IPage<WarehouseCostPageVO>> pageWarehouseCost(QueryPageWarehouseDTO dto) {
        IPage<WarehouseCostPageVO> pageList = reportFormService.pageWarehouseCost(dto);
        return Result.OK(pageList);
    }

    /**
     * 所有辅料展示
     */
    @AutoLog(value = "所有辅料展示")
    @ApiOperation(value = "所有辅料展示", notes = "所有辅料展示")
    @GetMapping(value = "/orderAcsrPage")
    public Result<IPage<OrderAscrListVO>> orderAcsrPage(QueryOrderAcsrDTO dto) {
        IPage<OrderAscrListVO> pageList = reportFormService.orderAcsrPage(dto);
        List<SumOrderAscrFinanceVO> vo = reportFormService.sumOrderAscrFinance(dto);
        return Result.OK2(pageList, vo);
    }


    /**
     * 辅料导出
     */
    @AutoLog(value = "辅料导出")
    @ApiOperation(value = "辅料导出", notes = "辅料导出")
    @GetMapping(value = "/orderAcsrExport", produces = "application/octet-stream")
    public void orderAcsrExport(QueryOrderAcsrDTO dto, HttpServletResponse response) {
        Page<OrderAscrListVO> page = new Page<>(1, 9999);
        reportFormService.orderAcsrExport(page, dto, response);

    }

    /**
     * 导入辅料信息
     *
     * @return
     */
    @AutoLog(value = "辅料信息导入")
    @ApiOperation(value = "辅料信息导入", notes = "辅料信息导入")
    @PostMapping(value = "/orderAcsrImport")
    public Result<?> orderAcsrImport(@RequestPart MultipartFile file) throws Exception {
        return reportFormService.orderAcsrImport(file);
    }

    /**
     * 仓库成本报表
     */
    @AutoLog(value = "仓库成本报表")
    @ApiOperation(value = "仓库成本报表", notes = "仓库成本报表")
    @GetMapping(value = "/warehouseCostPageVO")
    public Result<IPage<WarehouseCostPageVO>> warehouseCostPageVO(QueryPageWarehouseDTO dto) {
        IPage<WarehouseCostPageVO> pageList = reportFormService.pageWarehouseCost(dto);
        return Result.OK(pageList);
    }


    @AutoLog(value = "收汇报表")
    @ApiOperation(value = "收汇报表", notes = "收汇报表")
    @GetMapping(value = "/remittanceReport")
    public Result<?> remittanceReport(RemittanceReportDTO dto,
                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<PageRemittanceReportVO> page = new Page<>(pageNo, pageSize);
        IPage<PageRemittanceReportVO> pageList = reportFormService.remittanceReport(page, dto);
        List<SumRemittanceFinanceVO> vo = reportFormService.sumRemittanceFinance(dto);

        return Result.OK2(pageList, vo);
    }

    /**
     * 收汇报表
     */
    @AutoLog(value = "收汇报表导出")
    @ApiOperation(value = "收汇报表导出", notes = "收汇报表导出")
    @GetMapping(value = "/remittanceReportExport", produces = "application/octet-stream")
    public void remittanceReportExport(RemittanceReportDTO dto, HttpServletResponse response) {
        Page<PageRemittanceReportVO> page = new Page<>(1, 9999);
        reportFormService.remittanceReportExport(page, dto, response);
    }


    @AutoLog(value = "货代报表-国内费用")
    @ApiOperation(value = "货代报表-国内费用", notes = "货代报表-国内费用", response = FreightReportVO.class)
    @GetMapping(value = "/freightReport")
    public Result<?> freightReport(QueryFreightDTO dto,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<FreightReportVO> page = new Page<>(pageNo, pageSize);
        IPage<FreightReportVO> pageList = reportFormService.freightReport(page, dto);
        List<SumFreightVO> voList = reportFormService.sumFreight(dto);
        return Result.OK2(pageList, voList);
    }

    @AutoLog(value = "货代报表-国内费用导入模板")
    @ApiOperation(value = "货代报表-国内费用导入模板", notes = "货代报表-国内费用导入模板")
    @GetMapping(value = "/freightReportTemplate")
    public void financeReportTemplateExport(QueryFreightDTO dto, HttpServletResponse response) {
        reportFormService.freightReportTemplate(dto, response);

    }

    /**
     * 国内货运费用导入
     *
     * @return
     */
    @AutoLog(value = "国内货运费用导入")
    @ApiOperation(value = "国内货运费用导入", notes = "国内货运费用导入")
    @PostMapping(value = "/freightReportImport")
    public Result<?> freightReportImport(@RequestPart MultipartFile file) throws Exception {
        return reportFormService.freightReportImport(file);
    }

    @AutoLog(value = "货代报表-国外费用导入模板")
    @ApiOperation(value = "货代报表-国外费用导入模板", notes = "货代报表-国外费用导入模板")
    @GetMapping(value = "/freightReportGWTemplate")
    public void financeReportGWTemplateExport(QueryGWFreightDTO dto, HttpServletResponse response) {
        reportFormService.freightReportGWTemplate(dto, response);

    }

    /**
     * 国外货运费用导入
     *
     * @return
     */
    @AutoLog(value = "国外货运费用导入")
    @ApiOperation(value = "国外货运费用导入", notes = "国外货运费用导入")
    @PostMapping(value = "/freightReportGWImport")
    public Result<?> freightReportGWImport(@RequestPart MultipartFile file) throws Exception {
        return reportFormService.freightReportGWImport(file);
    }

    /**
     * 货代报表
     */
    @AutoLog(value = "货代报表导出-国内")
    @ApiOperation(value = "货代报表导出-国内", notes = "货代报表导出-国内")
    @GetMapping(value = "/freightReportExport", produces = "application/octet-stream")
    public void freightReportExport(QueryFreightDTO dto, HttpServletResponse response) {
        Page<FreightReportVO> page = new Page<>(1, 9999);
        reportFormService.freightReportExport(page, dto, response);
    }


    @AutoLog(value = "货代报表-国外费用")
    @ApiOperation(value = "货代报表-国外费用", notes = "货代报表-国外费用", response = FreightGWReportVO.class)
    @GetMapping(value = "/freightGWReport")
    public Result<?> freightGWReport(QueryGWFreightDTO dto,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<FreightGWReportVO> page = new Page<>(pageNo, pageSize);
        IPage<FreightGWReportVO> pageList = reportFormService.freightGWReport(page, dto);
        List<SumFreightGWVO> list = reportFormService.sumFreightGWReport(dto);
        return Result.OK2(pageList, list);
    }

    /**
     * 货代报表
     */
    @AutoLog(value = "货代报表导出-国外")
    @ApiOperation(value = "货代报表导出-国外", notes = "货代报表导出-国外")
    @GetMapping(value = "/freightGWReportExport", produces = "application/octet-stream")
    public void freightGWReportExport(QueryGWFreightDTO dto, HttpServletResponse response) {
        Page<FreightGWReportVO> page = new Page<>(1, 9999);
        reportFormService.freightGWReportExport(page, dto, response);
    }


    @AutoLog(value = "货代报表-退运费用")
    @ApiOperation(value = "货代报表-退运费用", notes = "货代报表-退运费用", response = FreightTYReportVO.class)
    @GetMapping(value = "/freightTYReport")
    public Result<?> freightTYReport(QueryTYFreightDTO dto,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<FreightTYReportVO> page = new Page<>(pageNo, pageSize);
        IPage<FreightTYReportVO> pageList = reportFormService.freightTYReport(page, dto);
        List<SumFreightTYVO> vo = reportFormService.sumFreightTY(dto);
        return Result.OK2(pageList, vo);
    }

    @AutoLog(value = "货代报表-退运费用（new）")
    @ApiOperation(value = "货代报表-退运费用（new）", notes = "货代报表-退运费用（new）", response = FreightTYReportNewVO.class)
    @GetMapping(value = "/freightTYReportNew")
    public Result<?> freightTYReportNew(QueryTYFreightNewDTO dto,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<FreightTYReportNewVO> page = new Page<>(pageNo, pageSize);

        if (StringUtils.isNotBlank(dto.getColumn())) {
            String column = oConvertUtils.camelToUnderline(dto.getColumn());
            dto.setColumn(column);
        }
        IPage<FreightTYReportNewVO> pageList = reportFormService.freightTYReportNew(page, dto);
        List<SumFreightTYNewVO> vo = reportFormService.sumFreightTYNew(dto);
        return Result.OK2(pageList, vo);
    }

    /**
     * 货代报表
     */
    @AutoLog(value = "货代报表导出-退运(new)")
    @ApiOperation(value = "货代报表导出-退运(new)", notes = "货代报表导出-退运(new)")
    @GetMapping(value = "/freightTYReportExportNew", produces = "application/octet-stream")
    public void freightTYReportExportNew(QueryTYFreightNewDTO dto, HttpServletResponse response) {
        Page<FreightTYReportNewVO> page = new Page<>(1, 9999);
        reportFormService.freightTYReportExportNew(page, dto, response);
    }

    /**
     * 货代报表
     */
    @AutoLog(value = "货代报表导出-退运")
    @ApiOperation(value = "货代报表导出-退运", notes = "货代报表导出-退运")
    @GetMapping(value = "/freightTYReportExport", produces = "application/octet-stream")
    public void freightTYReportExport(QueryTYFreightDTO dto, HttpServletResponse response) {
        Page<FreightTYReportVO> page = new Page<>(1, 9999);
        reportFormService.freightTYReportExport(page, dto, response);
    }


    @AutoLog(value = "货代报表-空运费用")
    @ApiOperation(value = "货代报表-空运费用", notes = "货代报表-空运费用", response = FreightKYReportVO.class)
    @GetMapping(value = "/freightKYReport")
    public Result<?> freightKYReport(QueryFreightKYDTO dto,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<FreightKYReportVO> page = new Page<>(pageNo, pageSize);
        IPage<FreightKYReportVO> pageList = reportFormService.freightKYReport(page, dto);
        return Result.OK(pageList);
    }

    @AutoLog(value = "货代报表-卡车费用")
    @ApiOperation(value = "货代报表-卡车费用", notes = "货代报表-卡车费用")
    @GetMapping(value = "/freightKCReport")
    public Result<?> freightKCReport(QueryFreightKCDTO dto,
                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<FreightKCReportVO> page = new Page<>(pageNo, pageSize);
        IPage<FreightKCReportVO> pageList = reportFormService.freightKCReport(page, dto);
        List<SumFreightKCVO> vo = reportFormService.sumFreightKC(dto);
        return Result.OK2(pageList, vo);
    }

    @AutoLog(value = "货代报表-卡车费用导入模板")
    @ApiOperation(value = "货代报表-卡车费用导入模板", notes = "货代报表-卡车费用导入模板")
    @GetMapping(value = "/freightKCReportTemplate", produces = "application/octet-stream")
    public void freightKCReportTemplateExport(QueryFreightKCDTO dto, HttpServletResponse response) {
        reportFormService.freightKCReportTemplate(dto, response);

    }

    /**
     * 国内货运费用导入
     *
     * @return
     */
    @AutoLog(value = "卡车费用导入")
    @ApiOperation(value = "卡车费用导入", notes = "卡车费用导入")
    @PostMapping(value = "/freightKCReportImport")
    public Result<?> freightKCReportImport(@RequestPart MultipartFile file) throws Exception {
        return reportFormService.freightKCReportImport(file);
    }


    /**
     * 货代报表
     */
    @AutoLog(value = "货代报表导出-卡车")
    @ApiOperation(value = "货代报表导出-卡车", notes = "货代报表导出-卡车")
    @GetMapping(value = "/freightKCReportExport", produces = "application/octet-stream")
    public void freightKCReportExport(QueryFreightKCDTO dto, HttpServletResponse response) {
        Page<FreightKCReportVO> page = new Page<>(1, 9999);
        reportFormService.freightKCReportExport(page, dto, response);
    }

    @AutoLog(value = "货代报表-国内编辑")
    @ApiOperation(value = "货代报表-国内编辑", notes = "货代报表-国内编辑")
    @PostMapping(value = "/updateFreightReport")
    public Result<?> updateFreightReport(@RequestBody EditFreightDTO dto) {
        boolean res = reportFormService.editFreightReport(dto);
        return res == true ? Result.OK("编辑成功!") : Result.error("编辑失败!");
    }

    @AutoLog(value = "货代报表-国外编辑")
    @ApiOperation(value = "货代报表-国外编辑", notes = "货代报表-国外编辑")
    @PostMapping(value = "/updateFreightGWReport")
    public Result<?> updateFreightGWReport(@RequestBody EditGWFreightDTO dto) {
        boolean res = reportFormService.editGWFreightReport(dto);
        return res == true ? Result.OK("编辑成功!") : Result.error("编辑失败!");
    }


    @AutoLog(value = "工厂统计报表")
    @ApiOperation(value = "工厂统计报表", notes = "工厂统计报表", response = FactoryReportVO.class)
    @GetMapping(value = "/factoryReport")
    public Result<IPage<FactoryReportVO>> factoryReport(QueryFactoryDTO dto,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<FactoryReportVO> page = new Page<>(pageNo, pageSize);
        IPage<FactoryReportVO> pageList = reportFormService.factoryReport(page, dto);
        List<SumFactoryFinanceVO> vo = reportFormService.sumfactoryFinance(dto);
        return Result.OK2(pageList, vo);
    }

    /**
     * 工厂统计报表导出
     */
    @AutoLog(value = "工厂统计报表导出")
    @ApiOperation(value = "工厂统计报表导出", notes = "工厂统计报表导出")
    @GetMapping(value = "/factoryReportExport")
    public void factoryReportExport(QueryFactoryDTO dto, HttpServletResponse response) {
        Page<FactoryReportVO> page = new Page<>(1, 9999);
        reportFormService.factoryReportExport(page, dto, response);
    }

    @AutoLog(value = "销售统计报表")
    @ApiOperation(value = "销售统计报表", notes = "销售统计报表", response = SalesStatisticsReportVO.class)
    @GetMapping(value = "/salesStatisticsReport")
    public Result<IPage<SalesStatisticsReportVO>> salesStatisticsReport(QuerySalesStatisticsDTO dto,
                                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<SalesStatisticsReportVO> page = new Page<>(pageNo, pageSize);
        IPage<SalesStatisticsReportVO> pageList = reportFormService.salesStatisticsReport(page, dto);
        List<SumSalesStatisticsVO> vo = reportFormService.sumSalesStatistics(dto);
        BigDecimal bigDecimal = new BigDecimal(0);
        boolean res = false;
        for (SumSalesStatisticsVO sumSalesStatisticsVO : vo) {
            if (sumSalesStatisticsVO.getCurrency().equals("USD")) {
                res = true;
            }
            bigDecimal = bigDecimal.add(sumSalesStatisticsVO.getTotalCreditInsurancePremium());
        }
        for (SumSalesStatisticsVO sumSalesStatisticsVO : vo) {
            if (sumSalesStatisticsVO.getCurrency().equals("USD")) {
                sumSalesStatisticsVO.setTotalCreditInsurancePremium(bigDecimal);
            }
        }

        if (!res) {
            SumSalesStatisticsVO vo1 = new SumSalesStatisticsVO();
            vo1.setCurrency("USD");
            vo1.setTotalCreditInsurancePremium(bigDecimal);
            vo1.setTotalFactoringInterest(BigDecimal.ZERO);
            vo1.setTotalRealReceiveAmount(BigDecimal.ZERO);
            vo1.setTotalUnReceiveAmount(BigDecimal.ZERO);
            vo.add(vo1);
        }
        for (SumSalesStatisticsVO sumSalesStatisticsVO : vo) {
            if (!sumSalesStatisticsVO.getCurrency().equals("USD")) {
                sumSalesStatisticsVO.setTotalCreditInsurancePremium(BigDecimal.ZERO);
            }
        }
//        Map<String,BigDecimal> map =new HashMap<>();
//        map.put("totalCreditInsurancePremium",bigDecimal);
        return Result.OK2(pageList, vo);
    }

    /**
     * 销售统计报表导出
     */
    @AutoLog(value = "销售统计报表导出")
    @ApiOperation(value = "销售统计报表导出", notes = "销售统计报表导出")
    @GetMapping(value = "/salesStatisticsReportExport")
    public void salesStatisticsReportExport(QuerySalesStatisticsDTO dto, HttpServletResponse response) {
        Page<SalesStatisticsReportVO> page = new Page<>(1, 9999);
        reportFormService.salesStatisticsReportExport(page, dto, response);
    }


    @AutoLog(value = "财务报表")
    @ApiOperation(value = "财务报表", notes = "财务报表", response = FinanceReportVO.class)
    @GetMapping(value = "/financeReport")
    public Result<IPage<FinanceReportVO>> financeReport(QueryFinanceDTO dto,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<FinanceReportVO> page = new Page<>(pageNo, pageSize);
        IPage<FinanceReportVO> pageList = reportFormService.financeReport(page, dto);
        List<SumFinanceReportVO> vo = reportFormService.sumFinance(dto);
        return Result.OK2(pageList, vo);
    }


    /**
     * 导入财务报表
     *
     * @return
     */
    @AutoLog(value = "导入财务报表")
    @ApiOperation(value = "导入财务报表", notes = "导入财务报表")
    @PostMapping(value = "/financeImport")
    public Result<?> financeImport(@RequestPart MultipartFile file) throws Exception {
        return reportFormService.financeImport(file);
    }


    /**
     * 导出财务报表模板
     */
    @AutoLog(value = "导出财务报表模板")
    @ApiOperation(value = "导出财务报表模板", notes = "导出财务报表模板")
    @GetMapping(value = "/financeReportTemplateExport")
    public void financeReportTemplateExport(QueryFinanceDTO dto, HttpServletResponse response) {
        reportFormService.financeTemplateExport(dto, response);
    }

    @AutoLog(value = "导出财务报表模板")
    @ApiOperation(value = "导出财务报表模板", notes = "导出财务报表模板")
    @PostMapping(value = "/financeReportTemplateExportBySelect")
    public void financeReportTemplateExportBySelect(@RequestBody List<FinanceTemplateVO> results, HttpServletResponse response) {
        reportFormService.financeReportTemplateExportBySelect(results, response);
    }


    /**
     * 财务报表导出
     */
    @AutoLog(value = "财务报表导出")
    @ApiOperation(value = "财务报表导出", notes = "财务报表导出")
    @GetMapping(value = "/financeReportExport")
    public void financeReportExport(QueryFinanceDTO dto, HttpServletResponse response) throws ParseException {
        Page<FinanceReportVO> page = new Page<>(1, 9999);
        reportFormService.financeReportExport(page, dto, response);
    }

    /**
     * 财务报表导出
     */
    @AutoLog(value = "财务报表导出")
    @ApiOperation(value = "财务报表导出", notes = "财务报表导出")
    @PostMapping(value = "/financeReportExportBySelect")
    public void financeReportExportBySelect(@RequestBody ReportExportDto dto, HttpServletResponse response) {
        reportFormService.financeReportExportBySelect(dto, response);
    }


    /**
     * 仓库提货利润列表
     */
    @AutoLog(value = "仓库提货利润列表")
    @ApiOperation(value = "仓库提货利润列表", notes = "仓库提货利润列表",response = TiHuoLiRunVO.class)
    @GetMapping(value = "/getWarehouseProfit")
    public Result<?>  getWarehouseProfit(QueryTiHuoLiRunDTO dto){

        Map<String, Object> voList =  reportFormService.getWarehouseProfit(dto);
        return Result.OK(voList);
    }



    /**
     * 根据年份查询仓库利润统计
     */
    @AutoLog(value = "根据年份查询仓库利润统计")
    @ApiOperation(value = "根据年份查询仓库利润统计", notes = "根据年份查询仓库利润统计",response = TiHuoLiRunTotalVO.class)
    @GetMapping(value = "/getWarehouseProfitByYear")
    public Result<?>  getWarehouseProfitByYear( @ApiParam("年份 格式：2024") @RequestParam("year") String year){

        List<TiHuoLiRunTotalVO> voList =  reportFormService.getWarehouseProfitByYear(year);
        return Result.OK(voList);
    }


    /**
     * 到期应付款
     */
    @AutoLog(value = "到期应付款")
    @ApiOperation(value = "到期应付款", notes = "到期应付款",response = PayableVO.class)
    @GetMapping(value = "/getPayableList")
    public Result<?>  getPayableList(PayableDTO dto){

        List<PayableVO> voList =  reportFormService.getPayableList(dto);
        return Result.OK(voList);
    }


    /**
     * 到期应付款导出
     */
    @AutoLog(value = "到期应付款导出")
    @ApiOperation(value = "到期应付款导出", notes = "到期应付款导出")
    @GetMapping(value = "/payableReportExport", produces = "application/octet-stream")
    public void payableReportExport(PayableDTO dto, HttpServletResponse response) {

        reportFormService.payableReportExport(dto, response);
    }








}
