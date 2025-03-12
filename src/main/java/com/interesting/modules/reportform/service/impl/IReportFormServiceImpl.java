package com.interesting.modules.reportform.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.CommonUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.common.util.oConvertUtils;
import com.interesting.config.FilterContextHandler;
import com.interesting.config.MyException;
import com.interesting.modules.accmaterial.entity.XqAccessoriesPurchase;
import com.interesting.modules.accmaterial.entity.XqAccessoryCategory;
import com.interesting.modules.accmaterial.entity.XqAccessoryInfo;
import com.interesting.modules.accmaterial.service.IXqAccessoriesPurchaseService;
import com.interesting.modules.accmaterial.service.XqAccessoryCategoryService;
import com.interesting.modules.accmaterial.service.XqAccessoryInfoService;
import com.interesting.modules.accmaterial.vo.XqAccessoriesExportVO;
import com.interesting.modules.expense.entity.XqWarehouseExpenses;
import com.interesting.modules.freightInfo.entity.XqFreightInfo;
import com.interesting.modules.freightInfo.mapper.XqFreightInfoMapper;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.mapper.XqFreightCostInfoMapper;
import com.interesting.modules.freightcost.service.IXqFreightCostInfoService;
import com.interesting.modules.insurance.entity.XqInsuranceExpenses;
import com.interesting.modules.insurance.service.IXqInsuranceExpensesService;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.entity.XqOrderExtraCost;
import com.interesting.modules.order.service.IXqOrderExtraCostService;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.rawmaterial.entity.XqRawMaterialPurchase;
import com.interesting.modules.rawmaterial.mapper.XqRawMaterialPurchaseMapper;
import com.interesting.modules.reportform.dto.*;
import com.interesting.modules.reportform.mapper.ReportFormMapper;
import com.interesting.modules.reportform.service.IReportFormService;
import com.interesting.modules.reportform.vo.*;
import com.interesting.modules.supplier.entity.XqSupplier;
import com.interesting.modules.supplier.mapper.XqSupplierMapper;
import com.interesting.modules.truck.entity.XqTruckInfo;
import com.interesting.modules.truck.service.XqTruckInfoService;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import io.swagger.annotations.ApiModelProperty;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/9/19 9:33
 * @VERSION: V1.0
 */
@Service
public class IReportFormServiceImpl implements IReportFormService {

    @Resource
    private ReportFormMapper reportFormMapper;
    @Resource
    private XqAccessoryInfoService xqAccessoryInfoService;
    @Resource
    private IXqOrderService xqOrderService;
    @Resource
    private IXqFreightCostInfoService xqFreightCostInfoService;

    @Resource
    private XqWarehouseService xqWarehouseService;
    @Resource
    private XqFreightCostInfoMapper xqFreightCostInfoMapper;
    @Resource
    private IXqAccessoriesPurchaseService xqAccessoriesPurchaseService;
    @Autowired
    private XqAccessoryCategoryService xqAccessoryCategoryService;
    @Resource
    private XqSupplierMapper xqSupplierMapper;

    @Resource
    private IXqInsuranceExpensesService xqInsuranceExpensesService;

    @Resource
    private XqFreightInfoMapper xqFreightInfoMapper;

    @Autowired
    private IXqOrderExtraCostService xqOrderExtraCostService;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Resource
    private XqTruckInfoService xqTruckInfoService;

    @Resource
    private IReportFormService reportFormService;

    @Override
    public IPage<PageRemittanceReportVO> remittanceReport(Page<PageRemittanceReportVO> page, RemittanceReportDTO dto) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        IPage<PageRemittanceReportVO> pageRemittanceReportVOIPage = reportFormMapper.remittanceReport(page, dto, customerIds);
        List<PageRemittanceReportVO> records = pageRemittanceReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        if (CollectionUtil.isNotEmpty(records)) {
            for (PageRemittanceReportVO record : records) {
                // 收汇总合计
                record.setRemittanceAmountAddFactoringMoney(record.getFactoringMoney().add(record.getRemittanceAmount()));
                // 判断当前订单号是否与上一个一致
                String orderNum = record.getOrderNum();
                if (!currentOrderNumber.equals(orderNum)) {
                    if (orderNum == null) {
                        currentOrderNumber = "";
                    } else {
                        currentOrderNumber = orderNum;
                    }
                    currentSerialNumber++;
                }
                record.setSerialNumber(currentSerialNumber);
            }
        }
        return pageRemittanceReportVOIPage;

    }

    @Override
    public List<SumRemittanceFinanceVO> sumRemittanceFinance(RemittanceReportDTO dto) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        return reportFormMapper.sumRemittanceFinance(dto, customerIds);

    }

    @Override
    public void remittanceReportExport(Page<PageRemittanceReportVO> page, RemittanceReportDTO dto, HttpServletResponse response) {
        // 调用分页
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        IPage<PageRemittanceReportVO> results = reportFormMapper.remittanceReport(page, dto, customerIds);
        List<PageRemittanceReportVO> records = results.getRecords();
        List<SumRemittanceFinanceVO> vo = reportFormMapper.sumRemittanceFinance(dto, customerIds);

        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        String etdYear = "";
        if (CollectionUtil.isNotEmpty(records)) {
            for (PageRemittanceReportVO record : records) {

                // 收汇总合计
                record.setRemittanceAmountAddFactoringMoney(record.getFactoringMoney().add(record.getRemittanceAmount()));
                // 判断当前订单号是否与上一个一致
                String orderNum = record.getOrderNum();
                if (!currentOrderNumber.equals(orderNum)) {
                    if (orderNum == null) {
                        currentOrderNumber = "";
                    } else {
                        currentOrderNumber = orderNum;
                    }
                    currentSerialNumber++;
                }
                record.setSerialNumber(currentSerialNumber);
            }
        }

        CommonUtils.export(dto.getExportColumn(), records, response, PageRemittanceReportVO.class, SumRemittanceFinanceVO.class, vo, true);

    }

    @Override
    public IPage<WarehouseCostPageVO> pageWarehouseCost(QueryPageWarehouseDTO dto) {
        Page<WarehouseCostPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());


        IPage<WarehouseCostPageVO> pageList = reportFormMapper.pageWarehouseCost(page, dto);
        if (pageList != null && pageList.getRecords().size() > 0) {
            for (WarehouseCostPageVO cost : pageList.getRecords()) {
                // 仓库总费用
                BigDecimal warehouseTotalFee = cost.getFirstMonthFee()
                        .add(cost.getSecondMonthFee())
                        .add(cost.getThirdMonthFee())
                        .add(cost.getForthMonthFee())
                        .add(cost.getFifthMonthFee())
                        .add(cost.getDeliveryFee());
                cost.setWarehouseTotalFee(warehouseTotalFee);

                // 仓库费用平均到每磅的价格 仓库总费用/总净重
                BigDecimal warehouseFeePerlbs = warehouseTotalFee.divide(cost.getTotalWeight(), 4, BigDecimal.ROUND_UP);

                cost.setWarehouseFeePerlbs(warehouseFeePerlbs);

                // 含仓储费的成本 成本单价+仓储费用单价
                BigDecimal warehouseFeePrice = warehouseFeePerlbs.add(cost.getNotReceivedPrice());
                cost.setWarehouseFeePrice(warehouseFeePrice);


            }
        }


        return pageList;
    }

    @Override
    public IPage<OrderAscrListVO> orderAcsrPage(QueryOrderAcsrDTO dto) {
        // 多辅料供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }
        // 多原料供应商筛选处理
        String originalSupplierName = dto.getOriginalSupplierName();
        List<String> originalSupplierIds = null;
        if (StringUtils.isNotBlank(originalSupplierName)) {
            String[] split = originalSupplierName.split(",");
            originalSupplierIds = ListUtil.toList(split);
            originalSupplierIds.remove("");
        }
        Page<OrderAscrListVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<OrderAscrListVO> orderAscrListVOIPage = reportFormMapper.orderAcsrPage(page, dto, supplierIds, originalSupplierIds);
        // 序号
        List<OrderAscrListVO> records = orderAscrListVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        for (OrderAscrListVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }
        return orderAscrListVOIPage;

    }

    @Override
    public IPage<FreightReportVO> freightReport(Page<FreightReportVO> page, QueryFreightDTO dto) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        IPage<FreightReportVO> freightReportVOIPage = reportFormMapper.freightReport(page, dto, customerIds);
        // 序号
        List<FreightReportVO> records = freightReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        for (FreightReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }
        return freightReportVOIPage;
    }

    @Override
    public List<SumFreightVO> sumFreight(QueryFreightDTO dto) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        return reportFormMapper.sumFreight(dto, customerIds);
    }

    @Override
    public void freightReportTemplate(QueryFreightDTO dto, HttpServletResponse response) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        List<FreightReportTemplateVO> results = reportFormMapper.freightReportTemplate(dto, customerIds);
        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        Sheet sheet = writer.getSheet();
        // 冻结前四列和第一行
        sheet.createFreezePane(3, 1);

        // 设置行高
        writer.setDefaultRowHeight(20);

        writer.setColumnWidth(0, 40);//客户
        writer.setColumnWidth(1, 20);//PO
        writer.setColumnWidth(2, 15);//开船日期
        writer.setColumnWidth(3, 25);//国内卡车公司
        writer.setColumnWidth(4, 15);//国内卡车费用RMB
        writer.setColumnWidth(5, 20);//国内卡车付款费用RMB
        writer.setColumnWidth(6, 20);//国内卡车付款时间
        writer.setColumnWidth(7, 20);//国内报关公司
        writer.setColumnWidth(8, 15);//国内报关费
        writer.setColumnWidth(9, 20);//国内报关付款金额
        writer.setColumnWidth(10, 20);//国内报关付款时间
        writer.setColumnWidth(11, 20);//海运保险公司
        writer.setColumnWidth(12, 15);//海运保险费USD
        writer.setColumnWidth(13, 20);//海运保险付款费用USD
        writer.setColumnWidth(14, 20);//海运保险付款时间
        writer.setColumnWidth(15, 20);//舱单公司
        writer.setColumnWidth(16, 15);//舱单金额RMB
        writer.setColumnWidth(17, 20);//舱单付款费用RMB
        writer.setColumnWidth(18, 20);//舱单保险付款时间
        writer.setColumnWidth(19, 15);//送货费
        writer.setColumnWidth(20, 15);//送货费金额RMB
        writer.setColumnWidth(21, 20);//送货费付款费用RMB
        writer.setColumnWidth(22, 20);//送货费付款时间
        writer.setColumnWidth(23, 15);//国内额外费用1
        writer.setColumnWidth(24, 22);//国内额外费用1付款金额
        writer.setColumnWidth(25, 22);//国内额外费用1付款日期
        writer.setColumnWidth(26, 15);//国内额外费用2
        writer.setColumnWidth(27, 22);//国内额外费用3付款金额
        writer.setColumnWidth(28, 22);//国内额外费用4付款日期


        writer.addHeaderAlias("customerName", "客户");
        writer.addHeaderAlias("orderNum", "订单号");
        writer.addHeaderAlias("etd", "开船日期");
        writer.addHeaderAlias("gnlyfAgent", "国内陆运费公司");
        writer.addHeaderAlias("gnlyfPrice", "国内陆运费");
        writer.addHeaderAlias("gnlyfFinanceAmount", "国内陆运费付款金额");
        writer.addHeaderAlias("gnlyfFinanceAuditTime", "国内陆运费付款时间");
        writer.addHeaderAlias("gnbgfAgent", "国内报关费公司");
        writer.addHeaderAlias("gnbgfPrice", "国内报关费");
        writer.addHeaderAlias("gnbgfFinanceAmount", "国内报关费付款金额");
        writer.addHeaderAlias("gnbgfFinanceAuditTime", "国内报关费付款时间");
        writer.addHeaderAlias("gnhyfAgent", "海运保险费公司");
        writer.addHeaderAlias("gnhyfPrice", "海运保险费");
        writer.addHeaderAlias("gnhyfFinanceAmount", "海运保险费付款金额");
        writer.addHeaderAlias("gnhyfFinanceAuditTime", "海运保险费付款时间");
        writer.addHeaderAlias("gncdfAgent", "国内舱单费公司");
        writer.addHeaderAlias("gncdfPrice", "国内舱单费金额");
        writer.addHeaderAlias("gncdfFinanceAmount", "国内舱单费付款金额");
        writer.addHeaderAlias("gncdfFinanceAuditTime", "国内舱单费付款时间");
        writer.addHeaderAlias("shfAgent", "送货费公司");
        writer.addHeaderAlias("shfPrice", "送货费金额");
        writer.addHeaderAlias("shfFinanceAmount", "送货费付款金额");
        writer.addHeaderAlias("shfFinanceAuditTime", "送货费付款时间");
        writer.addHeaderAlias("gnewfy1Price", "国内额外费用1");
        writer.addHeaderAlias("gnewfy1FinanceAmount", "国内额外费用1付款金额");
        writer.addHeaderAlias("gnewfy1FinanceAuditTime", "国内额外费用1付款日期");
        writer.addHeaderAlias("gnewfy2Price", "国内额外费用2");
        writer.addHeaderAlias("gnewfy2FinanceAmount", "国内额外费用2付款金额");
        writer.addHeaderAlias("gnewfy2FinanceAuditTime", "国内额外费用2付款日期");

        if (!results.isEmpty()) {
            for (FreightReportTemplateVO vo : results) {
                Map<String, Object> items = new HashMap<>();

                items.put("customerName", vo.getCustomerName());
                items.put("orderNum", vo.getOrderNum());
                items.put("etd", vo.getEtd());
                items.put("gnlyfAgent", vo.getGnlyfAgent());
                items.put("gnlyfPrice", vo.getGnlyfPrice());
                items.put("gnlyfFinanceAmount", vo.getGnlyfFinanceAmount());
                items.put("gnlyfFinanceAuditTime", vo.getGnlyfFinanceAuditTime());
                items.put("gnbgfAgent", vo.getGnbgfAgent());
                items.put("gnbgfPrice", vo.getGnbgfPrice());
                items.put("gnbgfFinanceAmount", vo.getGnbgfFinanceAmount());
                items.put("gnbgfFinanceAuditTime", vo.getGnbgfFinanceAuditTime());
                items.put("gnhyfAgent", vo.getGnhyfAgent());
                items.put("gnhyfPrice", vo.getGnhyfPrice());
                items.put("gnhyfFinanceAmount", vo.getGnhyfFinanceAmount());
                items.put("gnhyfFinanceAuditTime", vo.getGnhyfFinanceAuditTime());
                items.put("gncdfAgent", vo.getGncdfAgent());
                items.put("gncdfPrice", vo.getGncdfPrice());
                items.put("gncdfFinanceAmount", vo.getGncdfFinanceAmount());
                items.put("gncdfFinanceAuditTime", vo.getGncdfFinanceAuditTime());
                items.put("shfAgent", vo.getShfAgent());
                items.put("shfPrice", vo.getShfPrice());
                items.put("shfFinanceAmount", vo.getShfFinanceAmount());
                items.put("shfFinanceAuditTime", vo.getShfFinanceAuditTime());
                items.put("gnewfy1Price", vo.getGnewfy1Price());
                items.put("gnewfy1FinanceAmount", vo.getGnewfy1FinanceAmount());
                items.put("gnewfy1FinanceAuditTime", vo.getGnewfy1FinanceAuditTime());
                items.put("gnewfy2Price", vo.getGnewfy2Price());
                items.put("gnewfy2FinanceAmount", vo.getGnewfy2FinanceAmount());
                items.put("gnewfy2FinanceAuditTime", vo.getGnewfy2FinanceAuditTime());

                list.add(items);
            }

            // 一次性写出内容，使用默认样式
            writer.write(list, true);
            String excelName = "国内货运费导入模板EXCEL";


            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(excelName + ".xls", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new InterestingBootException(e.getMessage());
            }
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
            IoUtil.close(out);

        } else {
            Map<String, Object> items = new HashMap<>();

            items.put("customerName", null);
            items.put("orderNum", null);
            items.put("etd", null);
            items.put("gnlyfAgent", null);
            items.put("gnlyfPrice", null);
            items.put("gnlyfFinanceAmount", null);
            items.put("gnlyfFinanceAuditTime", null);
            items.put("gnbgfAgent", null);
            items.put("gnbgfPrice", null);
            items.put("gnbgfFinanceAmount", null);
            items.put("gnbgfFinanceAuditTime", null);
            items.put("gnhyfAgent", null);
            items.put("gnhyfPrice", null);
            items.put("gnhyfFinanceAmount", null);
            items.put("gnhyfFinanceAuditTime", null);
            items.put("gncdfAgent", null);
            items.put("gncdfPrice", null);
            items.put("gncdfFinanceAmount", null);
            items.put("gncdfFinanceAuditTime", null);
            items.put("shfAgent", null);
            items.put("shfPrice", null);
            items.put("shfFinanceAmount", null);
            items.put("shfFinanceAuditTime", null);
            items.put("gnewfy1Price", null);
            items.put("gnewfy1FinanceAmount", null);
            items.put("gnewfy1FinanceAuditTime", null);
            items.put("gnewfy2Price", null);
            items.put("gnewfy2FinanceAmount", null);
            items.put("gnewfy2FinanceAuditTime", null);
            list.add(items);

            // 一次性写出内容，使用默认样式
            writer.write(list, true);

            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("国内货运报表导入模板EXCEL.xls"));
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }


    }

    @Override
    public void freightReportGWTemplate(QueryGWFreightDTO dto, HttpServletResponse response) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        List<FreightReportGWTemplateVO> results = reportFormMapper.freightGWReportTemplate(dto, customerIds);
        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        Sheet sheet = writer.getSheet();
        // 冻结前四列和第一行
        sheet.createFreezePane(3, 1);
        // 设置行高
        writer.setDefaultRowHeight(20);

        writer.setColumnWidth(0, 40);//客户
        writer.setColumnWidth(1, 15);//PO
        writer.setColumnWidth(2, 15);//开船日期
        writer.setColumnWidth(3, 20);//国内海运公司
        writer.setColumnWidth(4, 15);//国内海运费用RMB
        writer.setColumnWidth(5, 20);//国内海运费付款费用RMB
        writer.setColumnWidth(6, 20);//国内海运费付款时间
        writer.setColumnWidth(7, 20);//国内港杂公司
        writer.setColumnWidth(8, 15);//国内港杂费
        writer.setColumnWidth(9, 20);//国内港杂费付款金额
        writer.setColumnWidth(10, 20);//国内港杂费付款时间
        writer.setColumnWidth(11, 15);//国内额外费用3
        writer.setColumnWidth(12, 22);//额外费用3付款金额
        writer.setColumnWidth(13, 22);//额外费用3付款日期
        writer.setColumnWidth(14, 15);//国内额外费用4
        writer.setColumnWidth(15, 22);//额外费用4付款金额
        writer.setColumnWidth(16, 22);//额外费用4付款日期
        writer.setColumnWidth(17, 15);//国外清关公司
        writer.setColumnWidth(18, 15);//国外清关费USD
        writer.setColumnWidth(19, 20);//国外清关付款费用USD
        writer.setColumnWidth(20, 20);//国外清关付款时间
        writer.setColumnWidth(21, 20);//国外卡车公司
        writer.setColumnWidth(22, 15);//国外卡车费RMB
        writer.setColumnWidth(23, 20);//国外卡车付款费用RMB
        writer.setColumnWidth(24, 20);//国外卡车付款时间
        writer.setColumnWidth(25, 20);//国外额外费用1公司
        writer.setColumnWidth(26, 15);//国外额外费用1RMB
        writer.setColumnWidth(27, 20);//国外额外费用1付款费用RMB
        writer.setColumnWidth(28, 20);//国外额外费用1付款时间
        writer.setColumnWidth(29, 20);//国外额外费用2公司
        writer.setColumnWidth(30, 15);//国外额外费用2RMB
        writer.setColumnWidth(31, 20);//国外额外费用2付款费用RMB
        writer.setColumnWidth(32, 20);//国外额外费用2付款时间
        writer.setColumnWidth(33, 20);//国外额外费用3公司
        writer.setColumnWidth(34, 15);//国外额外费用3RMB
        writer.setColumnWidth(35, 20);//国外额外费用3付款费用RMB
        writer.setColumnWidth(36, 20);//国外额外费用3付款时间
        writer.setColumnWidth(37, 20);//国外额外费用4公司
        writer.setColumnWidth(38, 15);//国外额外费用4RMB
        writer.setColumnWidth(39, 20);//国外额外费用4付款费用RMB
        writer.setColumnWidth(40, 20);//国外额外费用4付款时间
        writer.setColumnWidth(41, 20);//国外额外费用5公司
        writer.setColumnWidth(42, 15);//国外额外费用5RMB
        writer.setColumnWidth(43, 20);//国外额外费用5付款费用RMB
        writer.setColumnWidth(44, 20);//国外额外费用5付款时间
        writer.setColumnWidth(45, 20);//国外额外费用6公司
        writer.setColumnWidth(46, 15);//国外额外费用6RMB
        writer.setColumnWidth(47, 20);//国外额外费用6付款费用RMB
        writer.setColumnWidth(48, 20);//国外额外费用6付款时间


        writer.addHeaderAlias("customerName", "客户");
        writer.addHeaderAlias("orderNum", "PO号");
        writer.addHeaderAlias("etd", "开船日期");
        writer.addHeaderAlias("gnhyfAgent", "国内海运费公司");
        writer.addHeaderAlias("gnhyfPrice", "国内海运费");
        writer.addHeaderAlias("gnhyfFinanceAmount", "国内海运费付款金额");
        writer.addHeaderAlias("gnhyfFinanceAuditTime", "国内海运费付款日期");
        writer.addHeaderAlias("gngzfAgent", "国内港杂费公司");
        writer.addHeaderAlias("gngzfPrice", "国内港杂费");
        writer.addHeaderAlias("gngzfFinanceAmount", "国内港杂费付款金额");
        writer.addHeaderAlias("gngzfFinanceAuditTime", "国内港杂费付款日期");
        writer.addHeaderAlias("gnewfy3Price", "国内额外费用3");
        writer.addHeaderAlias("gnewfy3FinanceAmount", "国内额外费用3付款金额");
        writer.addHeaderAlias("gnewfy3FinanceAuditTime", "国内额外费用3付款日期");
        writer.addHeaderAlias("gnewfy4Price", "国内额外费用4");
        writer.addHeaderAlias("gnewfy4FinanceAmount", "国内额外费用4付款金额");
        writer.addHeaderAlias("gnewfy4FinanceAuditTime", "国内额外费用4付款日期");
        writer.addHeaderAlias("gwqgfAgent", "国外清关费公司");
        writer.addHeaderAlias("gwqgfPrice", "国外清关费");
        writer.addHeaderAlias("gwqgfFinanceAmount", "国外清关费付款金额");
        writer.addHeaderAlias("gwqgfFinanceAuditTime", "国外清关费付款日期");
        writer.addHeaderAlias("gwkcfAgent", "国外卡车费公司");
        writer.addHeaderAlias("gwkcfPrice", "国外卡车费金额");
        writer.addHeaderAlias("gwkcfFinanceAmount", "国外卡车费付款金额");
        writer.addHeaderAlias("gwkcfFinanceAuditTime", "国外卡车费付款日期");
        writer.addHeaderAlias("ewfy1Agent", "额外费用1公司");
        writer.addHeaderAlias("ewfy1Price", "额外费用1金额");
        writer.addHeaderAlias("ewfy1FinanceAmount", "额外费用1付款金额");
        writer.addHeaderAlias("ewfy1FinanceAuditTime", "额外费用1付款日期");
        writer.addHeaderAlias("ewfy2Agent", "额外费用2公司");
        writer.addHeaderAlias("ewfy2Price", "额外费用2金额");
        writer.addHeaderAlias("ewfy2FinanceAmount", "额外费用2付款金额");
        writer.addHeaderAlias("ewfy2FinanceAuditTime", "额外费用2付款日期");
        writer.addHeaderAlias("ewfy3Agent", "额外费用3公司");
        writer.addHeaderAlias("ewfy3Price", "额外费用3金额");
        writer.addHeaderAlias("ewfy3FinanceAmount", "额外费用3付款金额");
        writer.addHeaderAlias("ewfy3FinanceAuditTime", "额外费用3付款日期");
        writer.addHeaderAlias("ewfy4Agent", "额外费用4公司");
        writer.addHeaderAlias("ewfy4Price", "额外费用4金额");
        writer.addHeaderAlias("ewfy4FinanceAmount", "额外费用4付款金额");
        writer.addHeaderAlias("ewfy4FinanceAuditTime", "额外费用4付款日期");
        writer.addHeaderAlias("ewfy5Agent", "额外费用5公司");
        writer.addHeaderAlias("ewfy5Price", "额外费用5金额");
        writer.addHeaderAlias("ewfy5FinanceAmount", "额外费用5付款金额");
        writer.addHeaderAlias("ewfy5FinanceAuditTime", "额外费用5付款日期");
        writer.addHeaderAlias("ewfy6Agent", "额外费用6公司");
        writer.addHeaderAlias("ewfy6Price", "额外费用6金额");
        writer.addHeaderAlias("ewfy6FinanceAmount", "额外费用6付款金额");
        writer.addHeaderAlias("ewfy6FinanceAuditTime", "额外费用6付款日期");

        if (!results.isEmpty()) {
            for (FreightReportGWTemplateVO vo : results) {
                Map<String, Object> items = new HashMap<>();

                items.put("customerName", vo.getCustomerName());
                items.put("orderNum", vo.getOrderNum());
                items.put("etd", vo.getEtd());
                items.put("gnhyfAgent", vo.getGnhyfAgent());
                items.put("gnhyfPrice", vo.getGnhyfPrice());
                items.put("gnhyfFinanceAmount", vo.getGnhyfFinanceAmount());
                items.put("gnhyfFinanceAuditTime", vo.getGnhyfFinanceAuditTime());
                items.put("gngzfAgent", vo.getGngzfAgent());
                items.put("gngzfPrice", vo.getGngzfPrice());
                items.put("gngzfFinanceAmount", vo.getGngzfFinanceAmount());
                items.put("gngzfFinanceAuditTime", vo.getGnhyfFinanceAuditTime());
                items.put("gnewfy3Price", vo.getGnewfy3Price());
                items.put("gnewfy3FinanceAmount", vo.getGnewfy3FinanceAmount());
                items.put("gnewfy3FinanceAuditTime", vo.getGnewfy3FinanceAuditTime());
                items.put("gnewfy4Price", vo.getGnewfy4Price());
                items.put("gnewfy4FinanceAmount", vo.getGnewfy4FinanceAmount());
                items.put("gnewfy4FinanceAuditTime", vo.getGnewfy4FinanceAuditTime());
                items.put("gwqgfAgent", vo.getGwqgfAgent());
                items.put("gwqgfPrice", vo.getGwqgfPrice());
                items.put("gwqgfFinanceAmount", vo.getGwqgfFinanceAmount());
                items.put("gwqgfFinanceAuditTime", vo.getGwqgfFinanceAuditTime());
                items.put("gwkcfAgent", vo.getGwkcfAgent());
                items.put("gwkcfPrice", vo.getGwkcfPrice());
                items.put("gwkcfFinanceAmount", vo.getGwkcfFinanceAmount());
                items.put("gwkcfFinanceAuditTime", vo.getGwkcfFinanceAuditTime());
                items.put("ewfy1Agent", vo.getEwfy1Agent());
                items.put("ewfy1Price", vo.getEwfy1Price());
                items.put("ewfy1FinanceAmount", vo.getEwfy1FinanceAmount());
                items.put("ewfy1FinanceAuditTime", vo.getEwfy1FinanceAuditTime());
                items.put("ewfy2Agent", vo.getEwfy2Agent());
                items.put("ewfy2Price", vo.getEwfy2Price());
                items.put("ewfy2FinanceAmount", vo.getEwfy2FinanceAmount());
                items.put("ewfy2FinanceAuditTime", vo.getEwfy2FinanceAuditTime());
                items.put("ewfy3Agent", vo.getEwfy3Agent());
                items.put("ewfy3Price", vo.getEwfy3Price());
                items.put("ewfy3FinanceAmount", vo.getEwfy3FinanceAmount());
                items.put("ewfy3FinanceAuditTime", vo.getEwfy3FinanceAuditTime());
                items.put("ewfy4Agent", vo.getEwfy4Agent());
                items.put("ewfy4Price", vo.getEwfy4Price());
                items.put("ewfy4FinanceAmount", vo.getEwfy4FinanceAmount());
                items.put("ewfy4FinanceAuditTime", vo.getEwfy4FinanceAuditTime());
                items.put("ewfy5Agent", vo.getEwfy5Agent());
                items.put("ewfy5Price", vo.getEwfy5Price());
                items.put("ewfy5FinanceAmount", vo.getEwfy5FinanceAmount());
                items.put("ewfy5FinanceAuditTime", vo.getEwfy5FinanceAuditTime());
                items.put("ewfy6Agent", vo.getEwfy6Agent());
                items.put("ewfy6Price", vo.getEwfy6Price());
                items.put("ewfy6FinanceAmount", vo.getEwfy6FinanceAmount());
                items.put("ewfy6FinanceAuditTime", vo.getEwfy6FinanceAuditTime());
                list.add(items);
            }

            // 一次性写出内容，使用默认样式
            writer.write(list, true);
            String excelName = "国外货运费导入模板EXCEL";


            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(excelName + ".xls", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new InterestingBootException(e.getMessage());
            }
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
            IoUtil.close(out);

        } else {
            Map<String, Object> items = new HashMap<>();

            items.put("customerName", null);
            items.put("orderNum", null);
            items.put("etd", null);
            items.put("gnhyfAgent", null);
            items.put("gnhyfPrice", null);
            items.put("gnhyfFinanceAmount", null);
            items.put("gnhyfFinanceAuditTime", null);
            items.put("gngzfAgent", null);
            items.put("gngzfPrice", null);
            items.put("gngzfFinanceAmount", null);
            items.put("gngzfFinanceAuditTime", null);
            items.put("gnewfy3Price", null);
            items.put("gnewfy3FinanceAmount", null);
            items.put("gnewfy3FinanceAuditTime", null);
            items.put("gnewfy4Price", null);
            items.put("gnewfy4FinanceAmount", null);
            items.put("gnewfy4FinanceAuditTime", null);
            items.put("gwqgfAgent", null);
            items.put("gwqgfPrice", null);
            items.put("gwqgfFinanceAmount", null);
            items.put("gwqgfFinanceAuditTime", null);
            items.put("gwkcfAgent", null);
            items.put("gwkcfPrice", null);
            items.put("gwkcfFinanceAmount", null);
            items.put("gwkcfFinanceAuditTime", null);
            items.put("ewfy1Agent", null);
            items.put("ewfy1Price", null);
            items.put("ewfy1FinanceAmount", null);
            items.put("ewfy1FinanceAuditTime", null);
            items.put("ewfy2Agent", null);
            items.put("ewfy2Price", null);
            items.put("ewfy2FinanceAmount", null);
            items.put("ewfy2FinanceAuditTime", null);
            items.put("ewfy3Agent", null);
            items.put("ewfy3Price", null);
            items.put("ewfy3FinanceAmount33", null);
            items.put("ewfy3FinanceAuditTime33", null);
            items.put("ewfy4Agent", null);
            items.put("ewfy4Price", null);
            items.put("ewfy4FinanceAmount44", null);
            items.put("ewfy4FinanceAuditTime44", null);
            items.put("ewfy5Agent", null);
            items.put("ewfy5Price", null);
            items.put("ewfy5FinanceAmount", null);
            items.put("ewfy5FinanceAuditTime", null);
            items.put("ewfy6Agent", null);
            items.put("ewfy6Price", null);
            items.put("ewfy6FinanceAmount", null);
            items.put("ewfy6FinanceAuditTime", null);
            list.add(items);

            // 一次性写出内容，使用默认样式
            writer.write(list, true);

            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("国外货运报表导入模板EXCEL.xls"));
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }

    @Override
    public IPage<FreightGWReportVO> freightGWReport(Page<FreightGWReportVO> page, QueryGWFreightDTO dto) {
        // 多客户筛选处理

        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        IPage<FreightGWReportVO> freightGWReportVOIPage = reportFormMapper.freightGWReport(page, dto, customerIds);
        List<FreightGWReportVO> records = freightGWReportVOIPage.getRecords();

        // 序号
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        for (FreightGWReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }
        freightGWReportVOIPage.setRecords(records);
        return freightGWReportVOIPage;
    }

    @Override
    public List<SumFreightGWVO> sumFreightGWReport(QueryGWFreightDTO dto) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        return reportFormMapper.sumFreightGWReport(dto, customerIds);
    }

    @Override
    public IPage<FreightTYReportNewVO> freightTYReportNew(Page<FreightTYReportNewVO> page, QueryTYFreightNewDTO dto) {

        IPage<FreightTYReportNewVO> freightTYReportVOIPage = reportFormMapper.freightTYReportNew(page, dto);
        // 序号
        List<FreightTYReportNewVO> records = freightTYReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        for (FreightTYReportNewVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }
        return freightTYReportVOIPage;
    }

    @Override
    public IPage<FreightTYReportVO> freightTYReport(Page<FreightTYReportVO> page, QueryTYFreightDTO dto) {
        IPage<FreightTYReportVO> freightTYReportVOIPage = reportFormMapper.freightTYReport(page, dto);
        // 序号
        List<FreightTYReportVO> records = freightTYReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        for (FreightTYReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }
        return freightTYReportVOIPage;
    }

    @Override
    public List<SumFreightTYVO> sumFreightTY(QueryTYFreightDTO dto) {
        return reportFormMapper.sumFreightTY(dto);
    }

    @Override
    public IPage<FreightKYReportVO> freightKYReport(Page<FreightKYReportVO> page, QueryFreightKYDTO dto) {
        return reportFormMapper.freightKYReport(page, dto);
    }

    @Override
    public IPage<FreightKCReportVO> freightKCReport(Page<FreightKCReportVO> page, QueryFreightKCDTO dto) {
        IPage<FreightKCReportVO> results = reportFormMapper.freightKCReport(page, dto);
        List<FreightKCReportVO> records = results.getRecords();
        // 序号
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        for (FreightKCReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }
        return results;
    }


    @Override
    public List<SumFreightKCVO> sumFreightKC(QueryFreightKCDTO dto) {
        return reportFormMapper.sumFreightKC(dto);
    }

    @Override
    public void freightKCReportExport(Page<FreightKCReportVO> page, QueryFreightKCDTO dto, HttpServletResponse response) {
        IPage<FreightKCReportVO> results = reportFormMapper.freightKCReport(page, dto);
        List<FreightKCReportVO> records = results.getRecords();
        // 序号
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        for (FreightKCReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }

        List<SumFreightKCVO> sumFreightKCVOS = reportFormMapper.sumFreightKC(dto);

        CommonUtils.export(dto.getExportColumn(), records, response, FreightKCReportVO.class, SumFreightKCVO.class, sumFreightKCVOS, false);
    }


    @Override
    public IPage<FactoryReportVO> factoryReport(Page<FactoryReportVO> page, QueryFactoryDTO dto) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        // 多采购供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }
        IPage<FactoryReportVO> factoryReportVOIPage = reportFormMapper.factoryReport(page, dto, customerIds, supplierIds);
        // 序号
        List<FactoryReportVO> records = factoryReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        FactoryReportVO factoryReportVO = new FactoryReportVO();
        for (FactoryReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                factoryReportVO = record;
                factoryReportVO.setQtPurchaseAmount(factoryReportVO.getQtPurchaseAmount().add(record.getYfPurchaseAmount()).add(record.getSltPurchaseAmount()));
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;

                }
                currentSerialNumber++;
            } else {
                factoryReportVO.setQtPurchaseAmount(factoryReportVO.getQtPurchaseAmount().add(record.getYfPurchaseAmount()).add(record.getSltPurchaseAmount()));
                record.setQtPurchaseAmount(BigDecimal.ZERO);
                record.setYfPurchaseAmount(BigDecimal.ZERO);
                record.setSltPurchaseAmount(BigDecimal.ZERO);
            }
            record.setSerialNumber(currentSerialNumber);
        }
        return factoryReportVOIPage;

    }

    @Override
    public List<SumFactoryFinanceVO> sumfactoryFinance(QueryFactoryDTO dto) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        // 多采购供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }
        return reportFormMapper.sumfactoryFinance(dto, customerIds, supplierIds);

    }

    @Override
    public IPage<SalesStatisticsReportVO> salesStatisticsReport(Page<SalesStatisticsReportVO> page, QuerySalesStatisticsDTO dto) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        // 多原料供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }

        if (StringUtils.isNotBlank(dto.getColumn())) {
            String column = oConvertUtils.camelToUnderline(dto.getColumn());
            dto.setColumn(column);
        }

        IPage<SalesStatisticsReportVO> salesStatisticsReportVOIPage = reportFormMapper.salesStatisticsReport(page, dto, customerIds, supplierIds);
        // 序号
        List<SalesStatisticsReportVO> records = salesStatisticsReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        BigDecimal totalExtraCosts = BigDecimal.ZERO;
        BigDecimal middleManCommission = BigDecimal.ZERO;
        BigDecimal thirdPartImportCommission = BigDecimal.ZERO;
        int recordCount = 1;
        for (SalesStatisticsReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                    recordCount = 1;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
            if (record.getRanking() != 1) {
                record.setCreditInsurancePremium(BigDecimal.ZERO);
                record.setDiscount(BigDecimal.ZERO);

                middleManCommission = middleManCommission.add(record.getMiddleManCommission());
                thirdPartImportCommission = thirdPartImportCommission.add(record.getThirdPartImportCommission());
                record.setUnReceiveAmount(BigDecimal.ZERO);
                record.setRealReceiveAmount(BigDecimal.ZERO);
                record.setFactoringInterest(BigDecimal.ZERO);
                record.setFactoringInterest(BigDecimal.ZERO);
                record.setFactoringMoney1(BigDecimal.ZERO);
                record.setSalesAmountSuddkc(BigDecimal.ZERO);
//                record.setExtraFree(BigDecimal.ZERO);
                record.setAmount(BigDecimal.ZERO);
                record.setOther(BigDecimal.ZERO);
                record.setServiceCharge(BigDecimal.ZERO);
                record.setRemittanceAmount1(BigDecimal.ZERO);
            } else {
                record.setMiddleManCommission(record.getTotalMiddleManCommission().subtract(middleManCommission));
                record.setThirdPartImportCommission(record.getTotalThirdPartImportCommission().subtract(thirdPartImportCommission));
                middleManCommission = BigDecimal.ZERO;
                thirdPartImportCommission = BigDecimal.ZERO;
            }
//            if (orderNum.equals(currentOrderNumber)) {
//                if (recordCount == 1) {
//                    // wn 20240408
////                    XqOrder xqOrder = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, record.getOrderNum()));
////                    List<XqOrderExtraCost> extraCosts = xqOrderExtraCostService.list(new LambdaQueryWrapper<XqOrderExtraCost>().eq(XqOrderExtraCost::getOrderId, xqOrder.getId()));
////                    for (XqOrderExtraCost cost : extraCosts) {
////                        totalExtraCosts = totalExtraCosts.add(cost.getPrice());
////                    }
////                    record.setSalesAmount(record.getSalesAmount().add(totalExtraCosts));
//                    // wn 20240408
//                    totalExtraCosts = BigDecimal.ZERO;
//
//                } else {
//                    record.setCreditInsurancePremium(BigDecimal.ZERO);
//                    record.setDiscount(BigDecimal.ZERO);
//
//                }
//                if (record.getRecordCount() > 1) {
//                    if (record.getRecordCount() != recordCount) {
//                        middleManCommission = middleManCommission.add(record.getMiddleManCommission());
//                        thirdPartImportCommission = thirdPartImportCommission.add(record.getThirdPartImportCommission());
//                        record.setUnReceiveAmount(BigDecimal.ZERO);
//                        record.setRealReceiveAmount(BigDecimal.ZERO);
//                        record.setFactoringInterest(BigDecimal.ZERO);
//                        record.setFactoringInterest(BigDecimal.ZERO);
//                        record.setFactoringMoney1(BigDecimal.ZERO);
//                        record.setSalesAmountSuddkc(BigDecimal.ZERO);
//                        recordCount++;
//                    } else {
//                        record.setMiddleManCommission(record.getTotalMiddleManCommission().subtract(middleManCommission));
//                        record.setThirdPartImportCommission(record.getTotalThirdPartImportCommission().subtract(thirdPartImportCommission));
//                        middleManCommission = BigDecimal.ZERO;
//                        thirdPartImportCommission = BigDecimal.ZERO;
//                    }
//                }
//            }
        }
        return salesStatisticsReportVOIPage;

    }

    @Override
    public List<SumSalesStatisticsVO> sumSalesStatistics(QuerySalesStatisticsDTO dto) {
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        // 多原料供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }
        List<SumSalesStatisticsVO> sumSalesStatisticsVOS = reportFormMapper.sumSalesStatistics(dto, customerIds, supplierIds);


        return sumSalesStatisticsVOS;


    }

    @Override
    public IPage<FinanceReportVO> financeReport(Page<FinanceReportVO> page, QueryFinanceDTO dto) {
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        // 多供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }

        // 多发货方式筛选处理
        String chfs = dto.getChfs();
        List<String> chfsList = null;
        if (StringUtils.isNotBlank(chfs)) {
            String[] split = chfs.split(",");
            chfsList = ListUtil.toList(split);
            chfsList.remove("");
        }
//        if (StringUtils.isNotBlank(dto.getShrqStart()) && StringUtils.isNotBlank(dto.getShrqEnd())) {
//            if (dto.getShrqStart().equals(dto.getShrqEnd())) {
//                dto.setShrqStart(dto.getShrqStart() + " 00:00:00");
//                dto.setShrqEnd(dto.getShrqEnd() + " 23:59:59");
//            }
//        }
//
//        if (StringUtils.isNotBlank(dto.getJxkprqStart()) && StringUtils.isNotBlank(dto.getJxkprqEnd())) {
//            if (dto.getJxkprqStart().equals(dto.getJxkprqEnd())) {
//                dto.setJxkprqStart(dto.getJxkprqStart() + " 00:00:00");
//                dto.setJxkprqEnd(dto.getJxkprqEnd() + " 23:59:59");
//            }
//        }

        IPage<FinanceReportVO> financeReportVOIPage = reportFormMapper.financeReport(page, dto, customerIds, supplierIds, chfsList);


        // 序号
        List<FinanceReportVO> records = financeReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        BigDecimal totalExtraCosts = BigDecimal.ZERO;
        int recordCount = 1;
        for (FinanceReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
            if (orderNum.equals(currentOrderNumber)) {
                if (recordCount == 1) {
                    XqOrder xqOrder = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, record.getOrderNum()));
                    List<XqOrderExtraCost> extraCosts = xqOrderExtraCostService.list(new LambdaQueryWrapper<XqOrderExtraCost>().eq(XqOrderExtraCost::getOrderId, xqOrder.getId()));
                    for (XqOrderExtraCost cost : extraCosts) {
                        totalExtraCosts = totalExtraCosts.add(cost.getPrice());
                    }
                    record.setSalesAmount(record.getSalesAmount().add(totalExtraCosts));
                    totalExtraCosts = BigDecimal.ZERO;
                }
                if (record.getRecordCount() > 1) {
                    if (record.getRecordCount() != recordCount) {
                        recordCount++;
                    }
                }
            }


        }
        return financeReportVOIPage;
    }

    @Override
    public List<SumFinanceReportVO> sumFinance(QueryFinanceDTO dto) {
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        // 多原料供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }

        // 多发货方式筛选处理
        String chfs = dto.getChfs();
        List<String> chfsList = null;
        if (StringUtils.isNotBlank(chfs)) {
            String[] split = chfs.split(",");
            chfsList = ListUtil.toList(split);
            chfsList.remove("");
        }

        List<SumFinanceReportVO> sumFinance = reportFormMapper.sumFinanceReport(dto, customerIds, supplierIds, chfsList);
        return sumFinance;

    }

    @Override
    public List<SumOrderAscrFinanceVO> sumOrderAscrFinance(QueryOrderAcsrDTO dto) {
        // 多辅料供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }
        // 多原料供应商筛选处理
        String originalSupplierName = dto.getOriginalSupplierName();
        List<String> originalSupplierIds = null;
        if (StringUtils.isNotBlank(originalSupplierName)) {
            String[] split = originalSupplierName.split(",");
            originalSupplierIds = ListUtil.toList(split);
            originalSupplierIds.remove("");
        }
        return reportFormMapper.sumOrderAcsrPage(dto, supplierIds, originalSupplierIds);
    }

    //1
    @Override
    public void orderAcsrExport(Page<OrderAscrListVO> page, QueryOrderAcsrDTO dto, HttpServletResponse response) {
        // 多辅料供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }
        // 多原料供应商筛选处理
        String originalSupplierName = dto.getOriginalSupplierName();
        List<String> originalSupplierIds = null;
        if (StringUtils.isNotBlank(originalSupplierName)) {
            String[] split = originalSupplierName.split(",");
            originalSupplierIds = ListUtil.toList(split);
            originalSupplierIds.remove("");
        }
        IPage<OrderAscrListVO> results = reportFormMapper.orderAcsrPage(page, dto, supplierIds, originalSupplierIds);
        List<OrderAscrListVO> records = results.getRecords();

        // 序号
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        String etdYear = "";
        for (OrderAscrListVO record : records) {

            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }

        CommonUtils.export(dto.getExportColumn(), records, response, OrderAscrListVO.class, null, null, false);


//        List<SumOrderAscrFinanceVO> sumOrderAscrFinanceVOS = reportFormMapper.sumOrderAcsrPage(dto);
//
//
//        // 通过工具类创建writer
//        ExcelWriter writer = ExcelUtil.getWriter();
//        // 是否是第一页
//        AtomicBoolean a = new AtomicBoolean(true);
//        // 根据供应商分成不同的列表
//        Map<String, List<OrderAscrListVO>> collect = records.stream().collect(Collectors.groupingBy(OrderAscrListVO::getSupplierName));
//        collect.forEach((supplier, dataS) -> {
//            // 序号
//            String currentOrderNumber = UUID.randomUUID().toString();
//            int currentSerialNumber = 0;
//            for (OrderAscrListVO record : dataS) {
//                // 判断当前订单号是否与上一个一致
//                String orderNum = record.getOrderNum();
//                if (!currentOrderNumber.equals(orderNum)) {
//                    if (orderNum == null) {
//                        currentOrderNumber = "";
//                    } else {
//                        currentOrderNumber = orderNum;
//                    }
//                    currentSerialNumber++;
//                }
//                record.setSerialNumber(currentSerialNumber);
//            }
//            // 写入页签
//            if (a.get()) {
//                writer.renameSheet(supplier);
//                a.set(false);
//            }
//            writer.setSheet(supplier);
//
//            List<Map<String, Object>> list = new ArrayList<>();
//
//            writer.setColumnWidth(0, 5);
//            writer.setColumnWidth(1, 20);
//            writer.setColumnWidth(2, 20);
//            writer.setColumnWidth(3, 20);
//            writer.setColumnWidth(4, 20);
//            writer.setColumnWidth(5, 25);
//            writer.setColumnWidth(6, 20);
//            writer.setColumnWidth(7, 20);
//            writer.setColumnWidth(8, 20);
//            writer.setColumnWidth(9, 20);
//            writer.setColumnWidth(10, 20);
//            writer.setColumnWidth(11, 20);
//            writer.setColumnWidth(12, 20);
//            writer.setColumnWidth(13, 20);
//            writer.setColumnWidth(14, 20);
//            writer.setColumnWidth(15, 20);
//            writer.setColumnWidth(16, 20);
//
//            writer.addHeaderAlias("serialNumber", "序号");
//            writer.addHeaderAlias("supplierName", "辅料供应商");
//            writer.addHeaderAlias("purchaseContractNo", "采购合同号");
//            writer.addHeaderAlias("orderNum", "订单号");
//            writer.addHeaderAlias("accessoryName", "辅料名称");
//            writer.addHeaderAlias("size", "尺寸");
//            writer.addHeaderAlias("materialSpecification", "材质规格");
//            writer.addHeaderAlias("categoryName", "辅料分类");
//            writer.addHeaderAlias("orderQuantity", "订单数量");
//            writer.addHeaderAlias("currency", "币种");
//            writer.addHeaderAlias("unitPrice", "单价");
//            writer.addHeaderAlias("purchaseAmount", "采购金额");
//            writer.addHeaderAlias("taxRate", "税率");
//            writer.addHeaderAlias("taxIncludedAmount", "含税金额");
//            writer.addHeaderAlias("deliveryFactory", "送货工厂");
//            writer.addHeaderAlias("backNum", "退料");
//            writer.addHeaderAlias("paymentAmount", "付款金额");
//
//
//            for (OrderAscrListVO vo : dataS) {
//                Map<String, Object> items = new HashMap<>();
//                // 日期时间格式化器，用于将日期时间转换为字符串
//                items.put("serialNumber", vo.getSerialNumber());
//                items.put("supplierName", vo.getSupplierName() == null ? "" : vo.getSupplierName());
//                items.put("purchaseContractNo", vo.getPurchaseContractNo() == null ? "" : vo.getPurchaseContractNo());
//                items.put("orderNum", vo.getOrderNum() == null ? "" : vo.getOrderNum());
//                items.put("accessoryName", vo.getAccessoryName() == null ? "" : vo.getAccessoryName());
//                items.put("size", vo.getSize() == null ? "" : vo.getSize());
//                items.put("materialSpecification", vo.getMaterialSpecification() == null ? "" : vo.getMaterialSpecification());
//                items.put("categoryName", vo.getCategoryName() == null ? "" : vo.getCategoryName());
//                items.put("orderQuantity", vo.getOrderQuantity() == null ? "" : vo.getOrderQuantity());
//                items.put("currency", vo.getCurrency() == null ? "" : vo.getCurrency());
//                items.put("unitPrice", vo.getUnitPrice() == null ? "" : vo.getUnitPrice());
//                items.put("purchaseAmount", vo.getPurchaseAmount() == null ? "" : vo.getPurchaseAmount());
//                items.put("taxRate", vo.getTaxRate() == null ? "" : vo.getTaxRate());
//                items.put("taxIncludedAmount", vo.getTaxIncludedAmount() == null ? "" : vo.getTaxIncludedAmount());
//
//                items.put("backNum", "");
//                items.put("paymentAmount", "");
//
//                list.add(items);
//            }
//
//            // 一次性写出内容，使用默认样式
//            writer.write(list, true);
//
//        });
//
//        String excelName = "辅料采购导出EXCEL";
//
//        try {
//            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(excelName + ".xls", "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            throw new InterestingBootException(e.getMessage());
//        }
//        ServletOutputStream out = null;
//        try {
//            out = response.getOutputStream();
//            writer.flush(out, true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            writer.close();
//        }
//        IoUtil.close(out);


    }


    @Override
    public Result<?> orderAcsrImport(MultipartFile file) throws Exception {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 定义日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 格式化日期为指定格式的字符串
        String formattedDate = currentDate.format(formatter);
        //先拿到所有list
        String fileType = file.getContentType();
        if (fileType == null) {
            throw new MyException("未知文件类型");
        }
        Map<String, Object> param = new HashMap<>();
        //读取excel数据
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<List<Object>> lists = reader.read(1);
        List<AddOrderAscrImportDTO> readAll = new ArrayList();
        List<String> orderNums = new ArrayList<>();

        for (List<Object> row : lists) {
            if (row.get(1).toString().contains(formattedDate)) {
                if (row.get(2) == null) {
                    break;
                }
                orderNums.add(row.get(2).toString());
            }
        }
        List<String> orderNumDis = orderNums.stream().distinct().collect(Collectors.toList());
        for (String str : orderNumDis) {
            for (List<Object> row : lists) {
                if (row.get(2).toString().equals(str)) {
                    AddOrderAscrImportDTO dto = new AddOrderAscrImportDTO();
                    dto.setSupplierName(row.get(0) == null || row.get(0).equals("") ? "" : row.get(0).toString());
                    dto.setPurchaseContractNo(row.get(1) == null || row.get(1).equals("") ? "" : row.get(1).toString());
                    dto.setOrderNum(row.get(2) == null || row.get(2).equals("") ? "" : row.get(2).toString());
                    dto.setAccessoryName(row.get(3) == null || row.get(3).equals("") ? "" : row.get(3).toString());
                    dto.setSize(row.get(4) == null || row.get(4).equals("") ? "无" : row.get(4).toString());
                    dto.setMaterialSpecification(row.get(5) == null || row.get(5).equals("") ? "无" : row.get(5).toString());
                    dto.setCategoryName(row.get(6) == null || row.get(6).equals("") ? "" : row.get(6).toString());
                    dto.setOrderQuantity(row.get(7) == null || row.get(7).equals("") ? 0 : Integer.parseInt(row.get(7).toString()));
//                dto.setUseNum(row.get(8) == null || row.get(8).equals("") ? 0 : Integer.parseInt(row.get(8).toString() == "" ? "0" : row.get(8).toString()));
//                dto.setBackNum(row.get(9) == null || row.get(9).equals("") ? 0 : Integer.parseInt(row.get(9).toString() == "" ? "0" : row.get(9).toString()));
                    dto.setCurrency(row.get(8) == null || row.get(8).equals("") ? "" : row.get(8).toString());
                    dto.setUnitPrice(row.get(9) == null || row.get(9).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(9).toString()));
                    dto.setPurchaseAmount(row.get(10) == null || row.get(10).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(10).toString()));
                    dto.setTaxRate(row.get(11) == null || row.get(11).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(11).toString()));
                    dto.setTaxIncludedAmount(row.get(12) == null || row.get(12).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(12).toString()));

                    readAll.add(dto);
                }
            }

            //            ****类似一一对应****
        }
        //
        //先把获取的实体类根据orderNum分成map
        Map<String, List<AddOrderAscrImportDTO>> orderNumMap = new LinkedHashMap<>();
        for (AddOrderAscrImportDTO dto : readAll) {
            String orderNum = dto.getOrderNum();
            // 如果 orderNumMap 中已存在该 OrderNum 的键，则将当前对象添加到对应的列表中
            if (orderNumMap.containsKey(orderNum)) {
                List<AddOrderAscrImportDTO> orderNumList = orderNumMap.get(orderNum);
                orderNumList.add(dto);
            } else {
                // 如果 orderNumMap 中不存在该 OrderNum 的键，则创建新的列表并将当前对象添加到列表中
                List<AddOrderAscrImportDTO> orderNumList = new ArrayList<>();
                orderNumList.add(dto);
                orderNumMap.put(orderNum, orderNumList);
            }
        }
        int i = 0;
        String successOrderNum = "";
        String errorOrderNum = "";
        for (Map.Entry<String, List<AddOrderAscrImportDTO>> entry : orderNumMap.entrySet()) {
            String orderNum = entry.getKey();
            /* 新增辅料信息，有则直接插入id，没有则新增辅料信息再插入id */
            XqOrder xqOrder = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, orderNum));
            List<AddOrderAscrImportDTO> orderNumList = entry.getValue();

            //先把新的拿出来放到map里面
            Map<String, AddOrderAscrImportDTO> addComsMap = new LinkedHashMap<>();
            for (AddOrderAscrImportDTO dto : orderNumList) {
                addComsMap.put(dto.getPurchaseContractNo(), dto);
            }
            List<XqAccessoriesPurchase> toUpdateComs = new ArrayList<>();
            List<String> toDeleteIds = new ArrayList<>();
            List<XqAccessoriesPurchase> toAddComs = new ArrayList<>();
            if (xqOrder != null) {
                List<XqAccessoriesPurchase> originalComs = xqAccessoriesPurchaseService
                        .list(new LambdaQueryWrapper<XqAccessoriesPurchase>().eq(XqAccessoriesPurchase::getOrderId, xqOrder.getId()));

                for (XqAccessoriesPurchase originalCom : originalComs) {
                    AddOrderAscrImportDTO addCom = addComsMap.get(originalCom.getPurchaseContractNo());
                    if (addCom != null) {
                        // 更新记录
                        XqSupplier xqSupplier = xqSupplierMapper.getByName(addCom.getSupplierName());
                        if (xqSupplier != null) {
                            originalCom.setSupplierId(xqSupplier.getId());
                        } else {
                            //新增
                            XqSupplier xqSupplier1 = new XqSupplier();
                            xqSupplier1.setName(addCom.getSupplierName());
                            xqSupplier1.setType("2");
                            xqSupplierMapper.insert(xqSupplier1);
                            originalCom.setSupplierId(xqSupplier1.getId());
                        }
                        String accessoryName = addCom.getAccessoryName().trim();
                        String size = addCom.getSize().trim();
                        String msf = addCom.getMaterialSpecification().trim();
                        XqAccessoryInfo one = xqAccessoryInfoService.getByCondition(accessoryName, size, msf);
                        if (one == null) {
//                            XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
//                            xqAccessoryInfo.setAccessoryName(accessoryName);
//                            xqAccessoryInfo.setSize(size);
//                            xqAccessoryInfo.setMaterialSpecification(msf);
//                            //找到辅料分类
//                            if (StringUtils.isBlank(addCom.getCategoryName())) {
//                                throw new InterestingBootException("订单号" + orderNum + "的" + addCom.getCategoryName() + "分类名称不存在");
//                            }
//                            if (xqAccessoryCategoryService.lambdaQuery()
//                                    .eq(XqAccessoryCategory::getName, addCom.getCategoryName())
//                                    .count() == 0) {
//                                XqAccessoryCategory xqAccessoryCategory = new XqAccessoryCategory();
//                                xqAccessoryCategory.setName(addCom.getCategoryName());
//                                xqAccessoryCategory.setPid("0");
//                                xqAccessoryCategoryService.save(xqAccessoryCategory);
//                                xqAccessoryInfo.setCategoryId(xqAccessoryCategory.getId());
//                            } else {
//                                XqAccessoryCategory xqAccessoryCategory = xqAccessoryCategoryService.getOne(new LambdaQueryWrapper<XqAccessoryCategory>().eq(XqAccessoryCategory::getName, addCom.getCategoryName()));
//                                xqAccessoryInfo.setCategoryId(xqAccessoryCategory.getId());
//                            }
//                            xqAccessoryInfoService.save(xqAccessoryInfo);
//                            originalCom.setAccessoryId(xqAccessoryInfo.getId());
                            throw new InterestingBootException("未找到辅料-" + accessoryName + "," + size + "," + msf + "");
                        } else {
                            originalCom.setAccessoryId(one.getId());
                        }
                        originalCom.setPurchaseContractNo(addCom.getPurchaseContractNo());
                        originalCom.setSortNum(i);
                        originalCom.setOrderQuantity(addCom.getOrderQuantity());
                        originalCom.setCurrency(addCom.getCurrency());
                        originalCom.setUnitPrice(addCom.getUnitPrice());
                        originalCom.setPurchaseAmount(addCom.getPurchaseAmount());
                        originalCom.setTaxRate(addCom.getTaxRate());
                        originalCom.setTaxIncludedAmount(addCom.getTaxIncludedAmount());

                        toUpdateComs.add(originalCom);
                        //
                        addComsMap.remove(originalCom.getPurchaseContractNo());
                    } else {
                        // 删除记录
                        toDeleteIds.add(originalCom.getId());
                    }
                }

                xqAccessoriesPurchaseService.updateBatchById(toUpdateComs);
                xqAccessoriesPurchaseService.removeByIds(toDeleteIds);

                for (AddOrderAscrImportDTO dto : addComsMap.values()) {
                    XqAccessoriesPurchase xqAccessoriesPurchase = new XqAccessoriesPurchase();
                    String accessoryName = dto.getAccessoryName().trim();
                    String size = dto.getSize().trim();
                    String msf = dto.getMaterialSpecification().trim();
                    XqAccessoryInfo one = xqAccessoryInfoService.getByCondition(accessoryName, size, msf);
                    if (one == null) {

//
//                        XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
//                        xqAccessoryInfo.setAccessoryName(accessoryName);
//                        xqAccessoryInfo.setSize(size);
//                        xqAccessoryInfo.setMaterialSpecification(msf);
//                        //找到辅料分类
//                        if (StringUtils.isBlank(dto.getCategoryName())) {
//                            throw new InterestingBootException("订单号" + orderNum + "的" + dto.getCategoryName() + "分类名称不存在");
//                        }
//                        if (xqAccessoryCategoryService.lambdaQuery()
//                                .eq(XqAccessoryCategory::getName, dto.getCategoryName())
//                                .count() == 0) {
//                            XqAccessoryCategory xqAccessoryCategory = new XqAccessoryCategory();
//                            xqAccessoryCategory.setName(dto.getCategoryName());
//                            xqAccessoryCategory.setPid("0");
//                            xqAccessoryCategoryService.save(xqAccessoryCategory);
//                            xqAccessoryInfo.setCategoryId(xqAccessoryCategory.getId());
//                        } else {
//                            XqAccessoryCategory xqAccessoryCategory = xqAccessoryCategoryService.getOne(new LambdaQueryWrapper<XqAccessoryCategory>().eq(XqAccessoryCategory::getName, dto.getCategoryName()));
//                            xqAccessoryInfo.setCategoryId(xqAccessoryCategory.getId());
//                        }
//
//                        xqAccessoryInfoService.save(xqAccessoryInfo);
//                        xqAccessoriesPurchase.setAccessoryId(xqAccessoryInfo.getId());
                        throw new InterestingBootException("未找到辅料-" + accessoryName + "," + size + "," + msf + "");
                    } else {
                        xqAccessoriesPurchase.setAccessoryId(one.getId());
                        if (StringUtils.isBlank(one.getCategoryId())) {
                            XqAccessoryCategory xqAccessoryCategory = xqAccessoryCategoryService.getOne(new LambdaQueryWrapper<XqAccessoryCategory>().eq(XqAccessoryCategory::getName, dto.getCategoryName()));
                            one.setCategoryId(xqAccessoryCategory.getId());
                            xqAccessoryInfoService.updateById(one);
                        }
                    }
                    xqAccessoriesPurchase.setOrderId(xqOrder.getId());
                    XqSupplier xqSupplier = xqSupplierMapper.getByName(dto.getSupplierName());
                    if (xqSupplier != null) {
                        xqAccessoriesPurchase.setSupplierId(xqSupplier.getId());
                    } else {
                        //新增
                        XqSupplier xqSupplier1 = new XqSupplier();
                        xqSupplier1.setName(dto.getSupplierName());
                        xqSupplier1.setType("2");
                        xqSupplierMapper.insert(xqSupplier1);
                        xqAccessoriesPurchase.setSupplierId(xqSupplier1.getId());
                    }


                    xqAccessoriesPurchase.setPurchaseContractNo(dto.getPurchaseContractNo());
                    xqAccessoriesPurchase.setSortNum(i);
                    xqAccessoriesPurchase.setOrderQuantity(dto.getOrderQuantity());
                    xqAccessoriesPurchase.setCurrency(dto.getCurrency());
                    xqAccessoriesPurchase.setUnitPrice(dto.getUnitPrice());
                    xqAccessoriesPurchase.setPurchaseAmount(dto.getPurchaseAmount());
                    xqAccessoriesPurchase.setTaxRate(dto.getTaxRate());
                    xqAccessoriesPurchase.setTaxIncludedAmount(dto.getTaxIncludedAmount());
                    toAddComs.add(xqAccessoriesPurchase);
                    i = i + 1;
                }
                successOrderNum = successOrderNum + orderNum + "|";
            } else {
                i = 0;
                errorOrderNum = errorOrderNum + orderNum + "|";
            }
            xqAccessoriesPurchaseService.saveBatch(toAddComs);
        }
        if (CollectionUtil.isEmpty(readAll)) {
            throw new MyException("未找到需要导入的当天时间" + formattedDate + "辅料数据");
        }
        if (successOrderNum.equals("")) {
            successOrderNum = "无订单导入成功";
        } else {
            successOrderNum = "其中订单号" + successOrderNum + "导入成功";
        }
        if (errorOrderNum.equals("")) {
            errorOrderNum = "无错误订单";
        } else {
            errorOrderNum = "订单号" + errorOrderNum + "未找到订单";
        }
        return Result.OK("" + formattedDate + "日期的数据情况如下，" + successOrderNum + "," + errorOrderNum + "。");

    }

    @Override
    public Result<?> orderAcsrImportByOrderNum(String orderNum, MultipartFile file) throws Exception {
        //先拿到所有list
        String fileType = file.getContentType();
        if (fileType == null) {
            throw new MyException("未知文件类型");
        }
        Map<String, Object> param = new HashMap<>();
        //读取excel数据
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        // 获取所有 Sheet 页的数据
        List<List<Object>> allRows = new ArrayList<>();
        List<Object> allrows = new ArrayList<>();

        // 遍历所有 Sheet 页
        for (int sheetIndex = 0; sheetIndex < reader.getSheetCount(); sheetIndex++) {
            // 读取当前 Sheet 页的数据
            reader.setSheet(sheetIndex);
            for (int rowIndex = 2; rowIndex < reader.getRowCount(); rowIndex++) {
                List<Object> row = reader.readRow(rowIndex);
                if (row.size() > 0) {
                    if (row.get(2) == null || row.get(2).equals("")) {
                        break;
                    }
                    // 将当前 Sheet 页的数据添加到总数据列表中
                    allRows.add(row);
                }
            }

        }
        List<AddOrderAscrImportDTO> readAll = new ArrayList();

        for (List<Object> row : allRows) {
            if (row.get(2) == null) {
                break;
            }
            if (row.get(2).toString().equals(orderNum)) {
                AddOrderAscrImportDTO dto = new AddOrderAscrImportDTO();
                dto.setSupplierName(row.get(0) == null || row.get(0).equals("") ? "" : row.get(0).toString());
                dto.setPurchaseContractNo(row.get(1) == null || row.get(1).equals("") ? "" : row.get(1).toString());
                dto.setOrderNum(row.get(2) == null || row.get(2).equals("") ? "" : row.get(2).toString());
                dto.setAccessoryName(row.get(3) == null || row.get(3).equals("") ? "" : row.get(3).toString());
                dto.setSize(row.get(4) == null || row.get(4).equals("") ? "无" : row.get(4).toString());
                dto.setMaterialSpecification(row.get(5) == null || row.get(5).equals("") ? "无" : row.get(5).toString());
                dto.setCategoryName(row.get(6) == null || row.get(6).equals("") ? "" : row.get(6).toString());
                dto.setOrderQuantity(row.get(7) == null || row.get(7).equals("") ? 0 : Integer.parseInt(row.get(7).toString().contains(".") ? row.get(7).toString().substring(0, row.get(7).toString().lastIndexOf(".")) : row.get(7).toString()));
//                dto.setUseNum(row.get(8) == null || row.get(8).equals("") ? 0 : Integer.parseInt(row.get(8).toString() == "" ? "0" : row.get(8).toString()));
//                dto.setBackNum(row.get(9) == null || row.get(9).equals("") ? 0 : Integer.parseInt(row.get(9).toString() == "" ? "0" : row.get(9).toString()));
                dto.setCurrency(row.get(8) == null || row.get(8).equals("") ? "" : row.get(8).toString());
                dto.setUnitPrice(row.get(9) == null || row.get(9).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(9).toString()));
                dto.setPurchaseAmount(row.get(10) == null || row.get(10).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(10).toString()));
                dto.setTaxRate(row.get(11) == null || row.get(11).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(11).toString()));
                dto.setTaxIncludedAmount(row.get(12) == null || row.get(12).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(12).toString()));

                readAll.add(dto);
            }
            //            ****类似一一对应****
        }
        int i = 0;
        String successOrderNum = "";
        XqOrder xqOrder = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, orderNum));

        //先把新的拿出来放到map里面
        Map<String, AddOrderAscrImportDTO> addComsMap = new LinkedHashMap<>();
        for (AddOrderAscrImportDTO dto : readAll) {
            addComsMap.put(dto.getPurchaseContractNo(), dto);
        }
        List<XqAccessoriesPurchase> toUpdateComs = new ArrayList<>();
        List<String> toDeleteIds = new ArrayList<>();
        List<XqAccessoriesPurchase> toAddComs = new ArrayList<>();
        if (xqOrder != null && readAll.size() > 0) {
            List<XqAccessoriesPurchase> originalComs = xqAccessoriesPurchaseService
                    .list(new LambdaQueryWrapper<XqAccessoriesPurchase>().eq(XqAccessoriesPurchase::getOrderId, xqOrder.getId()));

            for (XqAccessoriesPurchase originalCom : originalComs) {
                AddOrderAscrImportDTO addCom = addComsMap.get(originalCom.getPurchaseContractNo());
                if (addCom != null) {
                    // 更新记录
                    //BeanUtils.copyProperties(addCom, originalCom);
                    XqSupplier xqSupplier = xqSupplierMapper.getByName(addCom.getSupplierName());
                    if (xqSupplier != null) {
                        originalCom.setSupplierId(xqSupplier.getId());
                    } else {
                        //新增
                        XqSupplier xqSupplier1 = new XqSupplier();
                        xqSupplier1.setName(addCom.getSupplierName());
                        xqSupplier1.setType("2");
                        xqSupplierMapper.insert(xqSupplier1);
                        originalCom.setSupplierId(xqSupplier1.getId());
                    }
                    String accessoryName = addCom.getAccessoryName().trim();
                    String size = addCom.getSize().trim();
                    String msf = addCom.getMaterialSpecification().trim();
                    XqAccessoryInfo one = xqAccessoryInfoService.getByCondition(accessoryName, size, msf);
                    if (one == null) {
//                        XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
//                        xqAccessoryInfo.setAccessoryName(accessoryName);
//                        xqAccessoryInfo.setSize(size);
//                        xqAccessoryInfo.setMaterialSpecification(msf);
//                        //找到辅料分类
//                        if (StringUtils.isBlank(addCom.getCategoryName())) {
//                            throw new InterestingBootException("订单号" + orderNum + "的" + addCom.getCategoryName() + "分类名称不存在");
//                        }
//                        if (xqAccessoryCategoryService.lambdaQuery()
//                                .eq(XqAccessoryCategory::getName, addCom.getCategoryName())
//                                .count() == 0) {
//                            XqAccessoryCategory xqAccessoryCategory = new XqAccessoryCategory();
//                            xqAccessoryCategory.setName(addCom.getCategoryName());
//                            xqAccessoryCategory.setPid("0");
//                            xqAccessoryCategoryService.save(xqAccessoryCategory);
//                            xqAccessoryInfo.setCategoryId(xqAccessoryCategory.getId());
//                        } else {
//                            XqAccessoryCategory xqAccessoryCategory = xqAccessoryCategoryService.getOne(new LambdaQueryWrapper<XqAccessoryCategory>().eq(XqAccessoryCategory::getName, addCom.getCategoryName()));
//                            xqAccessoryInfo.setCategoryId(xqAccessoryCategory.getId());
//                        }
//                        xqAccessoryInfoService.save(xqAccessoryInfo);
//                        originalCom.setAccessoryId(xqAccessoryInfo.getId());
                        throw new InterestingBootException("未找到辅料-" + accessoryName + "," + size + "," + msf + "");
                    } else {
                        originalCom.setAccessoryId(one.getId());
                        if (StringUtils.isBlank(one.getCategoryId())) {
                            XqAccessoryCategory xqAccessoryCategory = xqAccessoryCategoryService.getOne(new LambdaQueryWrapper<XqAccessoryCategory>().eq(XqAccessoryCategory::getName, addCom.getCategoryName()));
                            one.setCategoryId(xqAccessoryCategory.getId());
                            xqAccessoryInfoService.updateById(one);
                        }
                    }
                    originalCom.setPurchaseContractNo(addCom.getPurchaseContractNo());
                    originalCom.setSortNum(i);
                    originalCom.setOrderQuantity(addCom.getOrderQuantity());
                    originalCom.setCurrency(addCom.getCurrency());
                    originalCom.setUnitPrice(addCom.getUnitPrice());
                    originalCom.setPurchaseAmount(addCom.getPurchaseAmount());
                    originalCom.setTaxRate(addCom.getTaxRate());
                    originalCom.setTaxIncludedAmount(addCom.getTaxIncludedAmount());
                    toUpdateComs.add(originalCom);
                    addComsMap.remove(originalCom.getPurchaseContractNo());
                } else {
                    // 删除记录
                    toDeleteIds.add(originalCom.getId());
                }
            }
            xqAccessoriesPurchaseService.updateBatchById(toUpdateComs);
            xqAccessoriesPurchaseService.removeByIds(toDeleteIds);
            for (AddOrderAscrImportDTO dto : addComsMap.values()) {

                i = i + 1;
                XqAccessoriesPurchase xqAccessoriesPurchase = new XqAccessoriesPurchase();
                String accessoryName = dto.getAccessoryName().trim();
                String size = dto.getSize().trim();
                String msf = dto.getMaterialSpecification().trim();
                XqAccessoryInfo one = xqAccessoryInfoService.getByCondition(accessoryName, size, msf);
                if (one == null) {
//                    XqAccessoryInfo xqAccessoryInfo = new XqAccessoryInfo();
//                    xqAccessoryInfo.setAccessoryName(accessoryName);
//                    xqAccessoryInfo.setSize(size);
//                    xqAccessoryInfo.setMaterialSpecification(msf);
//                    //找到辅料分类
//                    if (StringUtils.isBlank(dto.getCategoryName())) {
//                        throw new InterestingBootException("订单号" + orderNum + "的" + dto.getCategoryName() + "分类名称不存在");
//                    }
//                    if (xqAccessoryCategoryService.lambdaQuery()
//                            .eq(XqAccessoryCategory::getName, dto.getCategoryName())
//                            .count() == 0) {
//                        XqAccessoryCategory xqAccessoryCategory = new XqAccessoryCategory();
//                        xqAccessoryCategory.setName(dto.getCategoryName());
//                        xqAccessoryCategory.setPid("0");
//                        xqAccessoryCategoryService.save(xqAccessoryCategory);
//                        xqAccessoryInfo.setCategoryId(xqAccessoryCategory.getId());
//                    } else {
//                        XqAccessoryCategory xqAccessoryCategory = xqAccessoryCategoryService.getOne(new LambdaQueryWrapper<XqAccessoryCategory>().eq(XqAccessoryCategory::getName, dto.getCategoryName()));
//                        xqAccessoryInfo.setCategoryId(xqAccessoryCategory.getId());
//                    }
//                    xqAccessoryInfoService.save(xqAccessoryInfo);
//                    xqAccessoriesPurchase.setAccessoryId(xqAccessoryInfo.getId());
                    throw new InterestingBootException("未找到辅料-" + accessoryName + "," + size + "," + msf + "");
                } else {
                    xqAccessoriesPurchase.setAccessoryId(one.getId());
                }
                xqAccessoriesPurchase.setOrderId(xqOrder.getId());
                XqSupplier xqSupplier = xqSupplierMapper.getByName(dto.getSupplierName());
                if (xqSupplier != null) {
                    xqAccessoriesPurchase.setSupplierId(xqSupplier.getId());
                } else {
                    //新增
                    XqSupplier xqSupplier1 = new XqSupplier();
                    xqSupplier1.setName(dto.getSupplierName());
                    xqSupplier1.setType("2");
                    xqSupplierMapper.insert(xqSupplier1);
                    xqAccessoriesPurchase.setSupplierId(xqSupplier1.getId());
                }
                xqAccessoriesPurchase.setPurchaseContractNo(dto.getPurchaseContractNo());
                xqAccessoriesPurchase.setSortNum(i);
                xqAccessoriesPurchase.setOrderQuantity(dto.getOrderQuantity());
                xqAccessoriesPurchase.setCurrency(dto.getCurrency());
                xqAccessoriesPurchase.setUnitPrice(dto.getUnitPrice());
                xqAccessoriesPurchase.setPurchaseAmount(dto.getPurchaseAmount());
                xqAccessoriesPurchase.setTaxRate(dto.getTaxRate());
                xqAccessoriesPurchase.setTaxIncludedAmount(dto.getTaxIncludedAmount());
                toAddComs.add(xqAccessoriesPurchase);
            }
            successOrderNum = "1";
        } else {
            i = 0;
            successOrderNum = "0";
        }
        xqAccessoriesPurchaseService.saveBatch(toAddComs);
        if (successOrderNum.equals("1")) {
            return Result.OK("订单号" + orderNum + "辅料采购导入成功");
        } else {
            return Result.error("订单号" + orderNum + "辅料采购导入失败EXCEL中未找到" + orderNum + "的订单");
        }
    }

    @Override
    public void freightReportExport(Page<FreightReportVO> page, QueryFreightDTO dto, HttpServletResponse response) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        IPage<FreightReportVO> results = reportFormMapper.freightReport(page, dto, customerIds);
        List<FreightReportVO> records = results.getRecords();
        // 序号
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        String etdYear = "";
        for (FreightReportVO record : records) {

            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }


        List<SumFreightVO> voList = reportFormService.sumFreight(dto);

        CommonUtils.export(dto.getExportColumn(), records, response, FreightReportVO.class, SumFreightVO.class, voList, false);

    }

    @Override
    public void factoryReportExport(Page<FactoryReportVO> page, QueryFactoryDTO dto, HttpServletResponse response) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        // 多采购供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }
        IPage<FactoryReportVO> results = reportFormMapper.factoryReport(page, dto, customerIds, supplierIds);
        List<FactoryReportVO> records = results.getRecords();
        // 合计
        List<SumFactoryFinanceVO> vo = reportFormMapper.sumfactoryFinance(dto, customerIds, supplierIds);

        // 序号
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        String etdYear = "";
        for (FactoryReportVO record : records) {
            // 判断当前订单号是否与上一个一致

            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            } else {
                record.setQtPurchaseAmount(BigDecimal.ZERO);
            }

            record.setSerialNumber(currentSerialNumber);
        }

        CommonUtils.export(dto.getExportColumn(), records, response, FactoryReportVO.class, SumFactoryFinanceVO.class, vo, true);

    }

    @Override
    public void salesStatisticsReportExport(Page<SalesStatisticsReportVO> page, QuerySalesStatisticsDTO dto, HttpServletResponse response) {

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


//
//        String customerName = dto.getCustomerName();
//        List<String> customerIds = null;
//        if (StringUtils.isNotBlank(customerName)) {
//            String[] split = customerName.split(",");
//            customerIds = ListUtil.toList(split);
//            customerIds.remove("");
//        }
//        // 多原料供应商筛选处理
//        String supplierName = dto.getSupplierName();
//        List<String> supplierIds = null;
//        if (StringUtils.isNotBlank(supplierName)) {
//            String[] split = supplierName.split(",");
//            supplierIds = ListUtil.toList(split);
//            supplierIds.remove("");
//        }
//        IPage<SalesStatisticsReportVO> results = reportFormMapper.salesStatisticsReport(page, dto, customerIds, supplierIds);
//        List<SumSalesStatisticsVO> sumSalesStatisticsVOS = reportFormMapper.sumSalesStatistics(dto, customerIds, supplierIds);
//        List<SalesStatisticsReportVO> records = results.getRecords();
//        // 序号
//        String currentOrderNumber = UUID.randomUUID().toString();
//        int currentSerialNumber = 0;
//        String etdYear = "";
//        int recordCount = 1;
//        BigDecimal totalExtraCosts = BigDecimal.ZERO;
//        for (SalesStatisticsReportVO record : records) {
//            // 判断当前订单号是否与上一个一致
//            String orderNum = record.getOrderNum();
//            if (!currentOrderNumber.equals(orderNum)) {
//                if (orderNum == null) {
//                    currentOrderNumber = "";
//                } else {
//                    currentOrderNumber = orderNum;
//                    recordCount = 1;
//                }
//                currentSerialNumber++;
//            }
//            record.setSerialNumber(currentSerialNumber);
//            if (orderNum.equals(currentOrderNumber)) {
//                if (recordCount == 1) {
//                    XqOrder xqOrder = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, record.getOrderNum()));
//                    List<XqOrderExtraCost> extraCosts = xqOrderExtraCostService.list(new LambdaQueryWrapper<XqOrderExtraCost>().eq(XqOrderExtraCost::getOrderId, xqOrder.getId()));
//                    for (XqOrderExtraCost cost : extraCosts) {
//                        totalExtraCosts = totalExtraCosts.add(cost.getPrice());
//                    }
//                    record.setSalesAmount(record.getSalesAmount().add(totalExtraCosts));
//                    totalExtraCosts = BigDecimal.ZERO;
//                }
//                if (record.getRecordCount() > 1) {
//                    if (record.getRecordCount() != recordCount) {
//                        record.setUnReceiveAmount(BigDecimal.ZERO);
//                        record.setRealReceiveAmount(BigDecimal.ZERO);
//                        record.setFactoringInterest(BigDecimal.ZERO);
//                        record.setFactoringInterest(BigDecimal.ZERO);
//                        record.setFactoringMoney1(BigDecimal.ZERO);
//                        record.setSalesAmountSuddkc(BigDecimal.ZERO);
//                        recordCount++;
//                    }
//                }
//            }
//        }
//        BigDecimal bigDecimal = new BigDecimal(0);
//        for (SumSalesStatisticsVO sumSalesStatisticsVO : sumSalesStatisticsVOS) {
//            bigDecimal = bigDecimal.add(sumSalesStatisticsVO.getTotalCreditInsurancePremium());
//            sumSalesStatisticsVO.setTotalCreditInsurancePremium(new BigDecimal(0));
//        }
//        for (SumSalesStatisticsVO sumSalesStatisticsVO : sumSalesStatisticsVOS) {
//            if (sumSalesStatisticsVO.getCurrency().equals("USD")) {
//                sumSalesStatisticsVO.setTotalCreditInsurancePremium(bigDecimal);
//                break;
//            }
//        }
        CommonUtils.export(dto.getExportColumn(), pageList.getRecords(), response, SalesStatisticsReportVO.class, SumSalesStatisticsVO.class, vo, true);
    }

    @Override
    public void financeReportExport(Page<FinanceReportVO> page, QueryFinanceDTO dto, HttpServletResponse response) {
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }

        // 多发货方式筛选处理
        String chfs = dto.getChfs();
        List<String> chfsList = null;
        if (StringUtils.isNotBlank(chfs)) {
            String[] split = chfs.split(",");
            chfsList = ListUtil.toList(split);
            chfsList.remove("");
        }

        // 多供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }

        IPage<FinanceReportVO> financeReportVOIPage = reportFormMapper.financeReport(page, dto, customerIds, supplierIds, chfsList);
        // 序号
        List<FinanceReportVO> records = financeReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        int recordCount = 0;
        BigDecimal totalExtraCosts = BigDecimal.ZERO;
        String etdYear = "";
        for (FinanceReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
            if (orderNum.equals(currentOrderNumber)) {
                if (recordCount == 1) {
                    XqOrder xqOrder = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, record.getOrderNum()));
                    List<XqOrderExtraCost> extraCosts = xqOrderExtraCostService.list(new LambdaQueryWrapper<XqOrderExtraCost>().eq(XqOrderExtraCost::getOrderId, xqOrder.getId()));
                    for (XqOrderExtraCost cost : extraCosts) {
                        totalExtraCosts = totalExtraCosts.add(cost.getPrice());
                    }
                    record.setSalesAmount(record.getSalesAmount().add(totalExtraCosts));
                    totalExtraCosts = BigDecimal.ZERO;
                }
                if (record.getRecordCount() > 1) {
                    if (record.getRecordCount() != recordCount) {
                        recordCount++;
                    }
                }
            }


        }

        List<SumFinanceReportVO> vo = reportFormService.sumFinance(dto);

        CommonUtils.export(dto.getExportColumn(), records, response, FinanceReportVO.class, SumFinanceReportVO.class, vo, false);

    }

    @Override
    public void financeReportExportBySelect(ReportExportDto dto, HttpServletResponse response) {
        CommonUtils.export(dto.getExportColumn(), dto.getList(), response, FinanceReportVO.class, SumFinanceReportVO.class, dto.getVo(), false);
    }

    @Override
    public void orderAcsrExportByOrderNum(String orderNum, HttpServletResponse response) {
        List<XqAccessoriesExportVO> xqAccessoriesPurchaseList = xqAccessoriesPurchaseService.listByExport(orderNum);
        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.setColumnWidth(0, 20);
        writer.setColumnWidth(1, 20);
        writer.setColumnWidth(2, 20);
        writer.setColumnWidth(3, 20);
        writer.setColumnWidth(4, 25);
        writer.setColumnWidth(5, 20);
        writer.setColumnWidth(6, 20);
        writer.setColumnWidth(7, 20);
        writer.setColumnWidth(8, 20);
        writer.setColumnWidth(9, 20);
        writer.setColumnWidth(10, 20);
        writer.setColumnWidth(11, 20);
        writer.setColumnWidth(12, 20);
        writer.setColumnWidth(13, 20);
        writer.setColumnWidth(14, 20);
        writer.setColumnWidth(15, 20);
        writer.setColumnWidth(16, 20);
        writer.setColumnWidth(17, 20);


        writer.addHeaderAlias("cou", "序号");
        writer.addHeaderAlias("productName", "产品名称");
        writer.addHeaderAlias("packaging", "包装方式");
        writer.addHeaderAlias("packagingUnit", "包装方式单位");
        writer.addHeaderAlias("layoutRequirements", "版面要求");
        writer.addHeaderAlias("supplierName", "辅料供应商");
        writer.addHeaderAlias("purchaseContractNo", "采购合同号");
        writer.addHeaderAlias("accessoryName", "辅料名称");
        writer.addHeaderAlias("size", "尺寸");
        writer.addHeaderAlias("materialSpecification", "材质规格");
        writer.addHeaderAlias("orderQuantity", "订单数量");
        writer.addHeaderAlias("currency", "币种");
        writer.addHeaderAlias("unitPrice", "采购单价");
        writer.addHeaderAlias("purchaseAmount", "采购总金额");
        writer.addHeaderAlias("taxRate", "税率");
        writer.addHeaderAlias("taxIncludeAmount", "含税金额");
        writer.addHeaderAlias("purchaseNote", "采购备注栏");

        int i = 0;

        if (xqAccessoriesPurchaseList != null && !xqAccessoriesPurchaseList.isEmpty()) {
            for (XqAccessoriesExportVO vo : xqAccessoriesPurchaseList) {
                Map<String, Object> items = new HashMap<>();
                items.put("cou", i);
                items.put("productName", vo.getProductName() == null ? "" : vo.getProductName());
                items.put("packaging", vo.getPackaging() == null ? "" : vo.getPackaging());
                items.put("packagingUnit", vo.getPackagingUnit() == null ? "" : vo.getPackagingUnit());
                items.put("layoutRequirements", vo.getLayoutRequirements() == null ? "" : vo.getLayoutRequirements());
                items.put("supplierName", vo.getSupplierName() == null ? "" : vo.getSupplierName());
                items.put("purchaseContractNo", vo.getPurchaseContractNo() == null ? "" : vo.getPurchaseContractNo());
                items.put("accessoryName", vo.getAccessoryName() == null ? "" : vo.getAccessoryName());
                items.put("size", vo.getSize() == null ? "" : vo.getSize());
                items.put("materialSpecification", vo.getMaterialSpecification() == null ? "" : vo.getMaterialSpecification());
                items.put("orderQuantity", vo.getOrderQuantity() == null ? 0 : vo.getOrderQuantity());
                items.put("currency", vo.getCurrency() == null ? "" : vo.getCurrency());
                items.put("unitPrice", vo.getUnitPrice() == null ? 0 : vo.getUnitPrice());
                items.put("purchaseAmount", vo.getPurchaseAmount() == null ? 0 : vo.getPurchaseAmount());
                items.put("taxRate", vo.getTaxRate() == null ? 0 : vo.getTaxRate());
                items.put("taxIncludeAmount", vo.getTaxIncludedAmount() == null ? "" : vo.getTaxIncludedAmount());
                items.put("purchaseNote", vo.getPurchaseNote() == null ? "" : vo.getPurchaseNote());
                list.add(items);
                i = i + 1;
            }
            String excelName = "辅料导出EXCEL";
            // 一次性写出内容，使用默认样式
            writer.write(list, true);
            writer.disableDefaultStyle();
            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(excelName + ".xls", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new InterestingBootException(e.getMessage());
            }
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
            IoUtil.close(out);

        } else {
            Map<String, Object> items = new HashMap<>();

            items.put("cou", i);
            items.put("productName", null);
            items.put("packaging", null);
            items.put("packagingUnit", null);
            items.put("layoutRequirements", null);
            items.put("supplierName", null);
            items.put("purchaseContractNo", null);
            items.put("accessoryName", null);
            items.put("size", null);
            items.put("materialSpecification", null);
            items.put("orderQuantity", null);
            items.put("currency", null);
            items.put("unitPrice", null);
            items.put("purchaseAmount", null);
            items.put("taxRate", null);
            items.put("taxIncludeAmount", null);
            items.put("purchaseNote", null);
            list.add(items);
            // 一次性写出内容，使用默认样式
            writer.write(list, true);

            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("辅料导出EXCEL.xls"));
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }

    @Override
    public boolean editFreightReport(EditFreightDTO dto) {
        XqOrder xo = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, dto.getOrderNum()).last("limit 1"));
        if (xo == null) {
            throw new InterestingBootException("未找到订单");
        }
        if (dto.getGnhyfFinanceAmount() != null || dto.getGnhyfFinanceAuditTime() != null) {
//            XqFreightCostInfo info =xqFreightCostInfoService.getOne(new LambdaQueryWrapper<XqFreightCostInfo>()
//                    .eq(XqFreightCostInfo::getOrderId,xo.getId()).eq(XqFreightCostInfo::getFeeType,"gnhyf").last("limit 1"));
//            if (info!=null){
//                info.setPayMoney(dto.getGnhyfFinanceAmount());
//                info.setPayTime(dto.getGnhyfFinanceAuditTime());
//                xqFreightCostInfoService.updateById(info);
//            }
            //海运保险费
            XqInsuranceExpenses xqInsuranceExpenses = xqInsuranceExpensesService.getOne(new LambdaQueryWrapper<XqInsuranceExpenses>()
                    .eq(XqInsuranceExpenses::getOrderId, xo.getId()).last("limit 1"));
            if (xqInsuranceExpenses != null) {
                xqInsuranceExpenses.setPayMoney(dto.getGnhyfFinanceAmount());
                xqInsuranceExpenses.setPayTime(dto.getGnhyfFinanceAuditTime());
                xqInsuranceExpensesService.updateById(xqInsuranceExpenses);
            }

        }
        if (dto.getGnbgfFinanceAmount() != null || dto.getGnbgfFinanceAuditTime() != null) {
            XqFreightCostInfo info = xqFreightCostInfoService.getOne(new LambdaQueryWrapper<XqFreightCostInfo>()
                    .eq(XqFreightCostInfo::getOrderId, xo.getId()).eq(XqFreightCostInfo::getFeeType, "gnbgf").last("limit 1"));
            if (info != null) {
                info.setPayMoney(dto.getGnbgfFinanceAmount());
                info.setPayTime(dto.getGnbgfFinanceAuditTime());
                xqFreightCostInfoService.updateById(info);
            }
        }
        if (dto.getGncdfFinanceAmount() != null || dto.getGncdfFinanceAuditTime() != null) {
            XqFreightCostInfo info = xqFreightCostInfoService.getOne(new LambdaQueryWrapper<XqFreightCostInfo>()
                    .eq(XqFreightCostInfo::getOrderId, xo.getId()).eq(XqFreightCostInfo::getFeeType, "gncdf").last("limit 1"));
            if (info != null) {
                info.setPayMoney(dto.getGnhyfFinanceAmount());
                info.setPayTime(dto.getGnhyfFinanceAuditTime());
                xqFreightCostInfoService.updateById(info);
            }
        }
        if (dto.getGnlyfFinanceAmount() != null || dto.getGnlyfFinanceAuditTime() != null) {
            XqFreightCostInfo info = xqFreightCostInfoService.getOne(new LambdaQueryWrapper<XqFreightCostInfo>()
                    .eq(XqFreightCostInfo::getOrderId, xo.getId()).eq(XqFreightCostInfo::getFeeType, "gnlyf").last("limit 1"));
            if (info != null) {
                info.setPayMoney(dto.getGnlyfFinanceAmount());
                info.setPayTime(dto.getGnlyfFinanceAuditTime());
                xqFreightCostInfoService.updateById(info);
            }
        }
        return true;
    }

    @Override
    public boolean editGWFreightReport(EditGWFreightDTO dto) {
        XqOrder xo = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, dto.getOrderNum()).last("limit 1"));
        if (xo == null) {
            throw new InterestingBootException("未找到订单");
        }
        if (dto.getGngzfFinanceAmount() != null || dto.getGngzfFinanceAuditTime() != null) {
            XqFreightCostInfo info = xqFreightCostInfoService.getOne(new LambdaQueryWrapper<XqFreightCostInfo>()
                    .eq(XqFreightCostInfo::getOrderId, xo.getId()).eq(XqFreightCostInfo::getFeeType, "gngzf").last("limit 1"));
            if (info != null) {
                info.setPayMoney(dto.getGnhyfFinanceAmount());
                info.setPayTime(dto.getGnhyfFinanceAuditTime());
                xqFreightCostInfoService.updateById(info);
            }
        }
        if (dto.getGnhyfFinanceAmount() != null || dto.getGnhyfFinanceAuditTime() != null) {
            XqFreightCostInfo info = xqFreightCostInfoService.getOne(new LambdaQueryWrapper<XqFreightCostInfo>()
                    .eq(XqFreightCostInfo::getOrderId, xo.getId()).eq(XqFreightCostInfo::getFeeType, "gnhyf").last("limit 1"));
            if (info != null) {
                info.setPayMoney(dto.getGnhyfFinanceAmount());
                info.setPayTime(dto.getGnhyfFinanceAuditTime());
                xqFreightCostInfoService.updateById(info);
            }
        }
        if (dto.getGwqgfFinanceAmount() != null || dto.getGwqgfFinanceAuditTime() != null) {
            XqFreightCostInfo info = xqFreightCostInfoService.getOne(new LambdaQueryWrapper<XqFreightCostInfo>()
                    .eq(XqFreightCostInfo::getOrderId, xo.getId()).eq(XqFreightCostInfo::getFeeType, "gncdf").last("limit 1"));
            if (info != null) {
                info.setPayMoney(dto.getGnhyfFinanceAmount());
                info.setPayTime(dto.getGnhyfFinanceAuditTime());
                xqFreightCostInfoService.updateById(info);
            }
        }
//        if (dto.getGnlyfFinanceAmount()!=null||dto.getGnlyfFinanceAuditTime()!=null){
//            XqFreightCostInfo info =xqFreightCostInfoService.getOne(new LambdaQueryWrapper<XqFreightCostInfo>()
//                    .eq(XqFreightCostInfo::getOrderId,xo.getId()).eq(XqFreightCostInfo::getFeeType,"gnlyf").last("limit 1"));
//            if (info!=null){
//                info.setPayMoney(dto.getGnlyfFinanceAmount());
//                info.setPayTime(dto.getGnlyfFinanceAuditTime());
//                xqFreightCostInfoService.updateById(info);
//            }
//        }
        return true;
    }

    @Override
    public Result<?> financeImport(MultipartFile file) throws Exception {
        //先拿到所有list
        String fileType = file.getContentType();
        if (fileType == null) {
            throw new MyException("未知文件类型");
        }
        Map<String, Object> param = new HashMap<>();
        //读取excel数据
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<List<Object>> lists = reader.read(1);
        List<FinanceTemplateDTO> readAll = new ArrayList();

        for (List<Object> row : lists) {
            FinanceTemplateDTO dto = new FinanceTemplateDTO();
//            dto.setSerialNumber(row.get(0) == null || row.get(0).equals("") ? null : row.get(0));
            dto.setEtd(row.get(1) == null || row.get(1).equals("") ? "" : row.get(1).toString());
            dto.setOrderNum(row.get(2) == null || row.get(2).equals("") ? "" : row.get(2).toString());
            dto.setSupplierName(row.get(3) == null || row.get(3).equals("") ? "" : row.get(3).toString());
            dto.setProductName(row.get(4) == null || row.get(4).equals("") ? "" : row.get(4).toString());
            dto.setWeight(row.get(5) == null || row.get(5).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(5).toString()));
            dto.setUnitPrice(row.get(6) == null || row.get(6).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(6).toString()));
            dto.setPurchaseAmount(row.get(8) == null || row.get(8).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(8).toString()));
            dto.setKpzl(row.get(9) == null || row.get(9).equals("") ? "" : row.get(9).toString());
            dto.setJxkprq(row.get(10) == null || row.get(10).equals("") ? null : row.get(10).toString());
            dto.setJxfph(row.get(11) == null || row.get(11).equals("") ? "" : row.get(11).toString());
            dto.setFpje(row.get(12) == null || row.get(12).equals("") ? null : new BigDecimal(row.get(12).toString()));
            dto.setFpse(row.get(13) == null || row.get(13).equals("") ? null : new BigDecimal(row.get(13).toString()));
            dto.setExportDeclarationNum(row.get(14) == null || row.get(14).equals("") ? "" : row.get(14).toString());
            dto.setCkrq(row.get(15) == null || row.get(15).equals("") ? null : row.get(15).toString());
            dto.setHsCode(row.get(16) == null || row.get(16).equals("") ? "" : row.get(16).toString());
            dto.setBgjeCfr(row.get(17) == null || row.get(17).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(17).toString()));
            dto.setHyf(row.get(18) == null || row.get(18).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(18).toString()));
            dto.setBgjeFob(row.get(19) == null || row.get(19).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(19).toString()));
            dto.setKphl(row.get(20) == null || row.get(20).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(20).toString()));
            dto.setCkfpje(row.get(21) == null || row.get(21).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(21).toString()));
            dto.setCkfprq(row.get(22) == null || row.get(22).equals("") ? null : row.get(22).toString());
            dto.setCkfphm(row.get(23) == null || row.get(23).equals("") ? "" : row.get(23).toString());
//            dto.setTsl(row.get(22) == null || row.get(22).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(22).toString()));
            dto.setTssj(row.get(24) == null || row.get(24).equals("") ? null : row.get(24).toString());
//            dto.setTsje(row.get(24) == null || row.get(24).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(24).toString()));
            dto.setSdtssj(row.get(25) == null || row.get(25).equals("") ? null : row.get(25).toString());
            dto.setShje(row.get(26) == null || row.get(26).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(26).toString()));
            dto.setShsj(row.get(27) == null || row.get(27).equals("") ? null : row.get(27).toString());
            dto.setShje2(row.get(28) == null || row.get(28).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(28).toString()));
            dto.setShsj2(row.get(29) == null || row.get(29).equals("") ? null : row.get(29).toString());
            dto.setShje3(row.get(30) == null || row.get(30).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(30).toString()));
            dto.setShsj3(row.get(31) == null || row.get(31).equals("") ? null : row.get(31).toString());
            dto.setShyh(row.get(32) == null || row.get(32).equals("") ? "" : row.get(32).toString());
            dto.setCkfptt(row.get(33) == null || row.get(33).equals("") ? "" : row.get(33).toString());
            dto.setLayoutRequirements(row.get(34) == null || row.get(34).equals("") ? "" : row.get(34).toString());
            dto.setSalesCurrency(row.get(35) == null || row.get(35).equals("") ? "" : row.get(35).toString());
            dto.setChfs(row.get(36) == null || row.get(36).equals("") ? "" : row.get(36).toString());
            readAll.add(dto);
        }
        List<FinanceTemplateVO> results = reportFormMapper.financeTemplateExport(null, null, null, null);

        Map<String, FinanceTemplateVO> expenseMap = results.stream()
                .collect(Collectors.toMap(FinanceTemplateVO::getKey, p -> p));


        List<XqRawMaterialPurchase> rawList = new ArrayList<>();
        List<XqFreightInfo> freList = new ArrayList<>();
        for (FinanceTemplateDTO dto : readAll) {
//            Optional<FinanceTemplateVO> vo = results.stream().filter(template ->
//                    template.getOrderNum().equals(dto.getOrderNum())
//                            && template.getProductName().equals(dto.getProductName())
//                            && template.getSupplierName().equals(dto.getSupplierName())
//                            && template.getWeight().compareTo(dto.getWeight()) == 0
//                            && template.getUnitPrice().compareTo(dto.getUnitPrice()) == 0
//                            && template.getLayoutRequirements().equals(dto.getLayoutRequirements())
//                            && template.getPurchaseAmount().compareTo(dto.getPurchaseAmount()) == 0).findFirst();


            FinanceTemplateVO vo = expenseMap.get(dto.getKey());
            if (vo == null) {
                continue;
            }

            XqRawMaterialPurchase raw = new XqRawMaterialPurchase();
            BeanUtils.copyProperties(vo, raw);
            BeanUtils.copyProperties(dto, raw);
            raw.setId(vo.getRawPurchaseId());
            raw.setBgjeFob(raw.getBgjeCfr().subtract(raw.getHyf()));
            raw.setCkfpje(raw.getBgjeFob().multiply(raw.getKphl()));
            if (raw.getFpse().compareTo(BigDecimal.ZERO) == 0) {
                raw.setTsl(BigDecimal.ZERO);

            } else {
                raw.setTsl(raw.getFpse().divide(raw.getFpje(), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)));

            }
            raw.setTsje(raw.getFpse());
            rawList.add(raw);
            if (StringUtils.isNotBlank(dto.getExportDeclarationNum())) {
                XqOrder xqOrder = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, dto.getOrderNum()));
                if (xqOrder != null) {
                    XqFreightInfo xqFreightInfo = xqFreightInfoMapper.selectOne(new LambdaQueryWrapper<XqFreightInfo>().eq(XqFreightInfo::getOrderId, xqOrder.getId()));
                    if (xqFreightInfo != null) {
                        xqFreightInfo.setExportDeclarationNum(dto.getExportDeclarationNum());
                        freList.add(xqFreightInfo);
                    }
                }

            }


        }

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            XqRawMaterialPurchaseMapper mapper = sqlSession.getMapper(XqRawMaterialPurchaseMapper.class);
            XqFreightInfoMapper mapper1 = sqlSession.getMapper(XqFreightInfoMapper.class);

            for (XqRawMaterialPurchase raw : rawList) {
                mapper.updateImport(raw);
            }
            for (XqFreightInfo fret : freList) {
                mapper1.updateImport(fret);
            }
            sqlSession.commit();
        }
        return Result.OK("财务报表导入成功");
    }

    @Override
    public void freightTYReportExport(Page<FreightTYReportVO> page, QueryTYFreightDTO dto, HttpServletResponse response) {
        IPage<FreightTYReportVO> freightTYReportVOIPage = reportFormMapper.freightTYReport(page, dto);
        List<SumFreightTYVO> sumFreightTYVOS = this.sumFreightTY(dto);
        // 序号
        List<FreightTYReportVO> records = freightTYReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        String etdYear = "";
        for (FreightTYReportVO record : records) {

            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {

                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }

        CommonUtils.export(dto.getExportColumn(), records, response, FreightTYReportVO.class, SumFreightTYVO.class, sumFreightTYVOS, false);
    }

    @Override
    public void freightTYReportExportNew(Page<FreightTYReportNewVO> page, QueryTYFreightNewDTO dto, HttpServletResponse response) {
        IPage<FreightTYReportNewVO> freightTYReportVOIPage = reportFormMapper.freightTYReportNew(page, dto);
        List<SumFreightTYNewVO> sumFreightTYVOS = this.sumFreightTYNew(dto);
        // 序号
        List<FreightTYReportNewVO> records = freightTYReportVOIPage.getRecords();
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        String etdYear = "";
        for (FreightTYReportNewVO record : records) {

            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {

                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }

        CommonUtils.export(dto.getExportColumn(), records, response, FreightTYReportNewVO.class, SumFreightTYNewVO.class, sumFreightTYVOS, true);
    }

    @Override
    public void financeTemplateExport(QueryFinanceDTO dto, HttpServletResponse response) {
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        // 多供应商筛选处理
        String supplierName = dto.getSupplierName();
        List<String> supplierIds = null;
        if (StringUtils.isNotBlank(supplierName)) {
            String[] split = supplierName.split(",");
            supplierIds = ListUtil.toList(split);
            supplierIds.remove("");
        }

        // 多发货方式筛选处理
        String chfs = dto.getChfs();
        List<String> chfsList = null;
        if (StringUtils.isNotBlank(chfs)) {
            String[] split = chfs.split(",");
            chfsList = ListUtil.toList(split);
            chfsList.remove("");
        }

        List<FinanceTemplateVO> results = reportFormMapper.financeTemplateExport(dto, customerIds, supplierIds, chfsList);


        // 序号

        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
//        int recordCount = 0;
//        BigDecimal totalExtraCosts = BigDecimal.ZERO;
//        String etdYear = "";
        for (FinanceTemplateVO record : results) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);

        }

        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        Sheet sheet = writer.getSheet();
        // 设置行高
        writer.setDefaultRowHeight(20);
        // 冻结前四列和第一行
        sheet.createFreezePane(6, 1);


        writer.setColumnWidth(0, 10);//序号
        writer.setColumnWidth(1, 12);// 开船日期
        writer.setColumnWidth(2, 12);//订单号
        writer.setColumnWidth(3, 28);//供应商
        writer.setColumnWidth(4, 24);//中文名
        writer.setColumnWidth(5, 10);//采购数量MT
        writer.setColumnWidth(6, 10);//采购单价
        writer.setColumnWidth(7, 10);//币种
        writer.setColumnWidth(8, 15);//采购总金额
        writer.setColumnWidth(9, 10);//开票资料
        writer.setColumnWidth(10, 12);//进项开票日期
        writer.setColumnWidth(11, 20);//进项发票号
        writer.setColumnWidth(12, 13);//发票金额
        writer.setColumnWidth(13, 13);//发票税额
        writer.setColumnWidth(14, 20);//报关单号码
        writer.setColumnWidth(15, 12);//出口日期
        writer.setColumnWidth(16, 12);//HS编码
        writer.setColumnWidth(17, 13);//报关金额CFR
        writer.setColumnWidth(18, 13);//海运费
        writer.setColumnWidth(19, 13);//报关金额FOB
        writer.setColumnWidth(20, 10);//开票汇率
        writer.setColumnWidth(21, 20);//出口发票金额CNY
        writer.setColumnWidth(22, 12);//出口发票日期
        writer.setColumnWidth(23, 20);//出口发票号码
//        writer.setColumnWidth(22, 20);//退税率
        writer.setColumnWidth(24, 12);//退税时间
        writer.setColumnWidth(25, 12);//收到退税时间
//        writer.setColumnWidth(24, 30);//退税金额
        writer.setColumnWidth(26, 15);//首次收汇金额
        writer.setColumnWidth(27, 15);//首次收汇时间
        writer.setColumnWidth(28, 15);//二次收汇金额
        writer.setColumnWidth(29, 15);//二次收汇时间
        writer.setColumnWidth(30, 15);//末次收汇金额
        writer.setColumnWidth(31, 15);//末次收汇时间
        writer.setColumnWidth(32, 15);//收汇银行
        writer.setColumnWidth(33, 16);//出口发票抬头
        writer.setColumnWidth(34, 42); // 版面要求
        writer.setColumnWidth(35, 12); // 销售币种
        writer.setColumnWidth(36, 15); // 出货方式

        writer.addHeaderAlias("serialNumber", "序号");
        writer.addHeaderAlias("etd", "开船日期");
        writer.addHeaderAlias("orderNum", "订单号");
        writer.addHeaderAlias("supplierName", "供应商");
        writer.addHeaderAlias("productName", "中文名");
        writer.addHeaderAlias("weight", "采购数量");
        writer.addHeaderAlias("unitPrice", "采购单价");
        writer.addHeaderAlias("currency", "采购币种");
        writer.addHeaderAlias("purchaseAmount", "采购总金额");
        writer.addHeaderAlias("kpzl", "开票资料");
        writer.addHeaderAlias("jxkprq", "进项开票日期");
        writer.addHeaderAlias("jxfph", "进项发票号");
        writer.addHeaderAlias("fpje", "发票金额");
        writer.addHeaderAlias("fpse", "发票税额");
        writer.addHeaderAlias("exportDeclarationNum", "报关单号码");
        writer.addHeaderAlias("ckrq", "出口日期");
        writer.addHeaderAlias("hsCode", "HS编码");
        writer.addHeaderAlias("bgjeCfr", "报关金额CFR");
        writer.addHeaderAlias("hyf", "海运费");
        writer.addHeaderAlias("bgjeFob", "报关金额FOB");
        writer.addHeaderAlias("kphl", "开票汇率");
        writer.addHeaderAlias("ckfpje", "出口发票金额CNY");
        writer.addHeaderAlias("ckfprq", "出口发票日期");
        writer.addHeaderAlias("ckfphm", "出口发票号码");
//        writer.addHeaderAlias("tsl", "退税率");
        writer.addHeaderAlias("tssj", "退税办理时间");
        writer.addHeaderAlias("sdtssj", "收到退税时间");
//        writer.addHeaderAlias("tsje", "退税金额");
        writer.addHeaderAlias("shje", "首次收汇金额");
        writer.addHeaderAlias("shsj", "首次收汇日期");
        writer.addHeaderAlias("shje2", "二次收汇金额");
        writer.addHeaderAlias("shsj2", "二次收汇日期");
        writer.addHeaderAlias("shje3", "末次收汇金额");
        writer.addHeaderAlias("shsj3", "末次收汇日期");
        writer.addHeaderAlias("shyh", "收汇银行");
        writer.addHeaderAlias("ckfptt", "出口发票抬头");
        writer.addHeaderAlias("layoutRequirements", "版面要求");
        writer.addHeaderAlias("salesCurrency", "销售币种");
        writer.addHeaderAlias("chfs", "出货方式");

        if (!results.isEmpty()) {
            for (FinanceTemplateVO vo : results) {
                Map<String, Object> items = new HashMap<>();

                items.put("serialNumber", vo.getSerialNumber());
                items.put("etd", StringUtils.isNotBlank(vo.getEtd()) ? vo.getEtd().substring(0, 11) : "");
                items.put("orderNum", vo.getOrderNum());
                items.put("supplierName", vo.getSupplierName());
                items.put("productName", vo.getProductName());
                items.put("weight", vo.getWeight());
                items.put("unitPrice", vo.getUnitPrice());
                items.put("purchaseAmount", vo.getPurchaseAmount());
                items.put("currency", vo.getCurrency());
                items.put("kpzl", vo.getKpzl());
                items.put("jxkprq", StringUtils.isNotBlank(vo.getJxkprq()) ? vo.getJxkprq().substring(0, 11) : "");
                items.put("jxfph", vo.getJxfph());
                items.put("fpje", vo.getFpje());
                items.put("fpse", vo.getFpse());
                items.put("exportDeclarationNum", vo.getExportDeclarationNum());
                items.put("ckrq", StringUtils.isNotBlank(vo.getCkrq()) ? vo.getCkrq().substring(0, 11) : "");
                items.put("hsCode", vo.getHsCode());
                items.put("bgjeCfr", vo.getBgjeCfr());
                items.put("hyf", vo.getHyf());
                items.put("bgjeFob", vo.getBgjeFob());
                items.put("kphl", vo.getKphl());
                items.put("ckfpje", vo.getCkfpje());
                items.put("ckfprq", StringUtils.isNotBlank(vo.getCkfprq()) ? vo.getCkfprq().substring(0, 11) : "");
                items.put("ckfphm", vo.getCkfphm());
//                items.put("tsl", vo.getTsl());
                items.put("tssj", StringUtils.isNotBlank(vo.getTssj()) ? vo.getTssj().substring(0, 11) : "");
                items.put("sdtssj", StringUtils.isNotBlank(vo.getSdtssj()) ? vo.getSdtssj().substring(0, 11) : "");
//                items.put("tsje", vo.getTsje());
                items.put("shje", vo.getShje().compareTo(BigDecimal.ZERO) == 0 ? null : vo.getShje());
                items.put("shsj", StringUtils.isNotBlank(vo.getShsj()) ? vo.getShsj().substring(0, 11) : "");
                items.put("shje2", vo.getShje2().compareTo(BigDecimal.ZERO) == 0 ? null : vo.getShje2());
                items.put("shsj2", StringUtils.isNotBlank(vo.getShsj2()) ? vo.getShsj2().substring(0, 11) : "");
                items.put("shje3", vo.getShje3().compareTo(BigDecimal.ZERO) == 0 ? null : vo.getShje3());
                items.put("shsj3", StringUtils.isNotBlank(vo.getShsj3()) ? vo.getShsj3().substring(0, 11) : "");
                items.put("shyh", vo.getShyh());
                items.put("ckfptt", vo.getCkfptt());
                items.put("layoutRequirements", vo.getLayoutRequirements());
                items.put("salesCurrency", vo.getSalesCurrency());
                items.put("chfs", vo.getChfs());
                list.add(items);
            }

            // 一次性写出内容，使用默认样式
            writer.write(list, true);
            String excelName = "财务报表导入模板EXCEL";


            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(excelName + ".xls", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new InterestingBootException(e.getMessage());
            }
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
            IoUtil.close(out);

        } else {
            Map<String, Object> items = new HashMap<>();

            items.put("serialNumber", null);
            items.put("etd", null);
            items.put("orderNum", null);
            items.put("supplierName", null);
            items.put("productName", null);
            items.put("weight", null);
            items.put("unitPrice", null);
            items.put("purchaseAmount", null);
            items.put("currency", null);
            items.put("kpzl", null);
            items.put("jxkprq", null);
            items.put("jxfph", null);
            items.put("fpje", null);
            items.put("fpse", null);
            items.put("exportDeclarationNum", null);
            items.put("ckrq", null);
            items.put("hsCode", null);
            items.put("bgjeCfr", null);
            items.put("hyf", null);
            items.put("bgjeFob", null);
            items.put("kphl", null);
            items.put("ckfpje", null);
            items.put("ckfprq", null);
            items.put("ckfphm", null);
//            items.put("tsl", null);
            items.put("tssj", null);
            items.put("sdtssj", null);
//            items.put("tsje", null);
            items.put("shje", null);
            items.put("shsj", null);
            items.put("shje2", null);
            items.put("shsj2", null);
            items.put("shje3", null);
            items.put("shsj3", null);
            items.put("shyh", null);
            items.put("ckfptt", null);
            items.put("salesCurrency", null);
            items.put("chfs", null);
            list.add(items);

            // 一次性写出内容，使用默认样式
            writer.write(list, true);

            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("财务报表导入模板EXCEL.xls"));
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }

    @Override
    public void financeReportTemplateExportBySelect(List<FinanceTemplateVO> results, HttpServletResponse response) {
        // 序号

        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
//        int recordCount = 0;
//        BigDecimal totalExtraCosts = BigDecimal.ZERO;
//        String etdYear = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        for (FinanceTemplateVO record : results) {
            // 判断当前订单号是否与上一个一致
            try {
                if (StringUtils.isNotBlank(record.getEtd())) {
                    Date date = dateFormat.parse(record.getEtd());
                    record.setEtd(dateFormat2.format(date));
                }
                if (StringUtils.isNotBlank(record.getJxkprq())) {
                    Date date = dateFormat.parse(record.getJxkprq());
                    record.setJxkprq(dateFormat2.format(date));
                }
                if (StringUtils.isNotBlank(record.getCkrq())) {
                    Date date = dateFormat.parse(record.getCkrq());
                    record.setCkrq(dateFormat2.format(date));
                }
                if (StringUtils.isNotBlank(record.getCkfprq())) {
                    Date date = dateFormat.parse(record.getCkfprq());
                    record.setCkfprq(dateFormat2.format(date));
                }
                if (StringUtils.isNotBlank(record.getTssj())) {
                    Date date = dateFormat.parse(record.getTssj());
                    record.setTssj(dateFormat2.format(date));
                }
                if (StringUtils.isNotBlank(record.getSdtssj())) {
                    Date date = dateFormat.parse(record.getSdtssj());
                    record.setSdtssj(dateFormat2.format(date));
                }
                if (StringUtils.isNotBlank(record.getShsj())) {
                    Date date = dateFormat.parse(record.getShsj());
                    record.setShsj(dateFormat2.format(date));
                }
                if (StringUtils.isNotBlank(record.getShsj2())) {
                    Date date = dateFormat.parse(record.getShsj2());
                    record.setShsj2(dateFormat2.format(date));
                }
                if (StringUtils.isNotBlank(record.getShsj3())) {
                    Date date = dateFormat.parse(record.getShsj3());
                    record.setShsj3(dateFormat2.format(date));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        Sheet sheet = writer.getSheet();
        // 设置行高
        writer.setDefaultRowHeight(20);
        // 冻结前四列和第一行
        sheet.createFreezePane(6, 1);


        writer.setColumnWidth(0, 10);//序号
        writer.setColumnWidth(1, 12);// 开船日期
        writer.setColumnWidth(2, 12);//订单号
        writer.setColumnWidth(3, 28);//供应商
        writer.setColumnWidth(4, 24);//中文名
        writer.setColumnWidth(5, 10);//采购数量MT
        writer.setColumnWidth(6, 10);//采购单价
        writer.setColumnWidth(7, 10);//币种
        writer.setColumnWidth(8, 15);//采购总金额
        writer.setColumnWidth(9, 10);//开票资料
        writer.setColumnWidth(10, 12);//进项开票日期
        writer.setColumnWidth(11, 20);//进项发票号
        writer.setColumnWidth(12, 13);//发票金额
        writer.setColumnWidth(13, 13);//发票税额
        writer.setColumnWidth(14, 20);//报关单号码
        writer.setColumnWidth(15, 12);//出口日期
        writer.setColumnWidth(16, 12);//HS编码
        writer.setColumnWidth(17, 13);//报关金额CFR
        writer.setColumnWidth(18, 13);//海运费
        writer.setColumnWidth(19, 13);//报关金额FOB
        writer.setColumnWidth(20, 10);//开票汇率
        writer.setColumnWidth(21, 20);//出口发票金额CNY
        writer.setColumnWidth(22, 12);//出口发票日期
        writer.setColumnWidth(23, 20);//出口发票号码
//        writer.setColumnWidth(22, 20);//退税率
        writer.setColumnWidth(24, 12);//退税时间
        writer.setColumnWidth(25, 12);//收到退税时间
//        writer.setColumnWidth(24, 30);//退税金额
        writer.setColumnWidth(26, 15);//首次收汇金额
        writer.setColumnWidth(27, 15);//首次收汇时间
        writer.setColumnWidth(28, 15);//二次收汇金额
        writer.setColumnWidth(29, 15);//二次收汇时间
        writer.setColumnWidth(30, 15);//末次收汇金额
        writer.setColumnWidth(31, 15);//末次收汇时间
        writer.setColumnWidth(32, 15);//收汇银行
        writer.setColumnWidth(33, 16);//出口发票抬头
        writer.setColumnWidth(34, 42); // 版面要求
        writer.setColumnWidth(35, 12); // 销售币种
        writer.setColumnWidth(36, 15); // 出货方式

        writer.addHeaderAlias("serialNumber", "序号");
        writer.addHeaderAlias("etd", "开船日期");
        writer.addHeaderAlias("orderNum", "订单号");
        writer.addHeaderAlias("supplierName", "供应商");
        writer.addHeaderAlias("productName", "中文名");
        writer.addHeaderAlias("weight", "采购数量");
        writer.addHeaderAlias("unitPrice", "采购单价");
        writer.addHeaderAlias("currency", "采购币种");
        writer.addHeaderAlias("purchaseAmount", "采购总金额");
        writer.addHeaderAlias("kpzl", "开票资料");
        writer.addHeaderAlias("jxkprq", "进项开票日期");
        writer.addHeaderAlias("jxfph", "进项发票号");
        writer.addHeaderAlias("fpje", "发票金额");
        writer.addHeaderAlias("fpse", "发票税额");
        writer.addHeaderAlias("exportDeclarationNum", "报关单号码");
        writer.addHeaderAlias("ckrq", "出口日期");
        writer.addHeaderAlias("hsCode", "HS编码");
        writer.addHeaderAlias("bgjeCfr", "报关金额CFR");
        writer.addHeaderAlias("hyf", "海运费");
        writer.addHeaderAlias("bgjeFob", "报关金额FOB");
        writer.addHeaderAlias("kphl", "开票汇率");
        writer.addHeaderAlias("ckfpje", "出口发票金额CNY");
        writer.addHeaderAlias("ckfprq", "出口发票日期");
        writer.addHeaderAlias("ckfphm", "出口发票号码");
//        writer.addHeaderAlias("tsl", "退税率");
        writer.addHeaderAlias("tssj", "退税办理时间");
        writer.addHeaderAlias("sdtssj", "收到退税时间");
//        writer.addHeaderAlias("tsje", "退税金额");
        writer.addHeaderAlias("shje", "首次收汇金额");
        writer.addHeaderAlias("shsj", "首次收汇日期");
        writer.addHeaderAlias("shje2", "二次收汇金额");
        writer.addHeaderAlias("shsj2", "二次收汇日期");
        writer.addHeaderAlias("shje3", "末次收汇金额");
        writer.addHeaderAlias("shsj3", "末次收汇日期");
        writer.addHeaderAlias("shyh", "收汇银行");
        writer.addHeaderAlias("ckfptt", "出口发票抬头");
        writer.addHeaderAlias("layoutRequirements", "版面要求");
        writer.addHeaderAlias("salesCurrency", "销售币种");
        writer.addHeaderAlias("chfs", "出货方式");

        if (!results.isEmpty()) {
            for (FinanceTemplateVO vo : results) {
                Map<String, Object> items = new HashMap<>();

                items.put("serialNumber", vo.getSerialNumber());
                items.put("etd", vo.getEtd());
                items.put("orderNum", vo.getOrderNum());
                items.put("supplierName", vo.getSupplierName());
                items.put("productName", vo.getProductName());
                items.put("weight", vo.getWeight());
                items.put("unitPrice", vo.getUnitPrice());
                items.put("purchaseAmount", vo.getPurchaseAmount());
                items.put("currency", vo.getCurrency());
                items.put("kpzl", vo.getKpzl());
                items.put("jxkprq", vo.getJxkprq());
                items.put("jxfph", vo.getJxfph());
                items.put("fpje", vo.getFpje());
                items.put("fpse", vo.getFpse());
                items.put("exportDeclarationNum", vo.getExportDeclarationNum());
                items.put("ckrq", vo.getCkrq());
                items.put("hsCode", vo.getHsCode());
                items.put("bgjeCfr", vo.getBgjeCfr());
                items.put("hyf", vo.getHyf());
                items.put("bgjeFob", vo.getBgjeFob());
                items.put("kphl", vo.getKphl());
                items.put("ckfpje", vo.getCkfpje());
                items.put("ckfprq", vo.getCkfprq());
                items.put("ckfphm", vo.getCkfphm());
//                items.put("tsl", vo.getTsl());
                items.put("tssj", vo.getTssj());
                items.put("sdtssj", vo.getSdtssj());
//                items.put("tsje", vo.getTsje());
                items.put("shje", vo.getShje().compareTo(BigDecimal.ZERO) == 0 ? null : vo.getShje());
                items.put("shsj", vo.getShsj());
                items.put("shje2", vo.getShje2().compareTo(BigDecimal.ZERO) == 0 ? null : vo.getShje2());
                items.put("shsj2", vo.getShsj2());
                items.put("shje3", vo.getShje3().compareTo(BigDecimal.ZERO) == 0 ? null : vo.getShje3());
                items.put("shsj3", vo.getShsj3());
                items.put("shyh", vo.getShyh());
                items.put("ckfptt", vo.getCkfptt());
                items.put("layoutRequirements", vo.getLayoutRequirements());
                items.put("salesCurrency", vo.getSalesCurrency());
                items.put("chfs", vo.getChfs());
                list.add(items);
            }

            // 一次性写出内容，使用默认样式
            writer.write(list, true);
            String excelName = "财务报表导入模板EXCEL";


            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(excelName + ".xls", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new InterestingBootException(e.getMessage());
            }
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
            IoUtil.close(out);

        } else {
            Map<String, Object> items = new HashMap<>();

            items.put("serialNumber", null);
            items.put("etd", null);
            items.put("orderNum", null);
            items.put("supplierName", null);
            items.put("productName", null);
            items.put("weight", null);
            items.put("unitPrice", null);
            items.put("purchaseAmount", null);
            items.put("currency", null);
            items.put("kpzl", null);
            items.put("jxkprq", null);
            items.put("jxfph", null);
            items.put("fpje", null);
            items.put("fpse", null);
            items.put("exportDeclarationNum", null);
            items.put("ckrq", null);
            items.put("hsCode", null);
            items.put("bgjeCfr", null);
            items.put("hyf", null);
            items.put("bgjeFob", null);
            items.put("kphl", null);
            items.put("ckfpje", null);
            items.put("ckfprq", null);
            items.put("ckfphm", null);
//            items.put("tsl", null);
            items.put("tssj", null);
            items.put("sdtssj", null);
//            items.put("tsje", null);
            items.put("shje", null);
            items.put("shsj", null);
            items.put("shje2", null);
            items.put("shsj2", null);
            items.put("shje3", null);
            items.put("shsj3", null);
            items.put("shyh", null);
            items.put("ckfptt", null);
            items.put("salesCurrency", null);
            items.put("chfs", null);
            list.add(items);

            // 一次性写出内容，使用默认样式
            writer.write(list, true);

            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode("财务报表导入模板EXCEL.xls"));
            ServletOutputStream out = null;
            try {
                out = response.getOutputStream();
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.close();
            }
        }
    }

    @Override
    public Result<?> freightReportImport(MultipartFile file) throws Exception {
        //先拿到所有list
        String fileType = file.getContentType();
        if (fileType == null) {
            throw new MyException("未知文件类型");
        }
        Map<String, Object> param = new HashMap<>();
        //读取excel数据
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<List<Object>> lists = reader.read(1);
        List<FreightReportTemplateDTO> readAll = new ArrayList();

        for (List<Object> row : lists) {
            FreightReportTemplateDTO dto = new FreightReportTemplateDTO();
            dto.setCustomerName(row.get(0) == null || row.get(0).equals("") ? "" : row.get(0).toString());
            dto.setOrderNum(row.get(1) == null || row.get(1).equals("") ? "" : row.get(1).toString());
            dto.setEtd(row.get(2) == null || row.get(2).equals("") ? "" : row.get(2).toString());
            dto.setGnlyfAgent(row.get(3) == null || row.get(3).equals("") ? "" : row.get(3).toString());
            dto.setGnlyfPrice(row.get(4) == null || row.get(4).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(4).toString()));
            dto.setGnlyfFinanceAmount(row.get(5) == null || row.get(5).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(5).toString()));
            dto.setGnlyfFinanceAuditTime(row.get(6) == null || row.get(6).equals("") ? null : getDate(row.get(6).toString()));
            dto.setGnbgfAgent(row.get(7) == null || row.get(7).equals("") ? "" : row.get(7).toString());
            dto.setGnbgfPrice(row.get(8) == null || row.get(8).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(8).toString()));
            dto.setGnbgfFinanceAmount(row.get(9) == null || row.get(9).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(9).toString()));
            dto.setGnbgfFinanceAuditTime(row.get(10) == null || row.get(10).equals("") ? null : getDate(row.get(10).toString()));
            dto.setGnhyfAgent(row.get(11) == null || row.get(11).equals("") ? null : row.get(11).toString());
            dto.setGnhyfPrice(row.get(12) == null || row.get(12).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(12).toString()));
            dto.setGnhyfFinanceAmount(row.get(13) == null || row.get(13).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(13).toString()));
            dto.setGnhyfFinanceAuditTime(row.get(14) == null || row.get(14).equals("") ? null : getDate(row.get(14).toString()));
            dto.setGncdfAgent(row.get(15) == null || row.get(15).equals("") ? "" : row.get(15).toString());
            dto.setGncdfPrice(row.get(16) == null || row.get(16).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(16).toString()));
            dto.setGncdfFinanceAmount(row.get(17) == null || row.get(17).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(17).toString()));
            dto.setGncdfFinanceAuditTime(row.get(18) == null || row.get(18).equals("") ? null : getDate(row.get(18).toString()));
            dto.setShfAgent(row.get(19) == null || row.get(19).equals("") ? "" : row.get(19).toString());
            dto.setShfPrice(row.get(20) == null || row.get(20).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(20).toString()));
            dto.setShfFinanceAmount(row.get(21) == null || row.get(21).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(21).toString()));
            dto.setShfFinanceAuditTime(row.get(22) == null || row.get(22).equals("") ? null : getDate(row.get(22).toString()));
            dto.setGnewfy1Price(row.get(23) == null || row.get(23).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(23).toString()));
            dto.setGnewfy1FinanceAmount(row.get(24) == null || row.get(24).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(24).toString()));
            dto.setGnewfy1FinanceAuditTime(row.get(25) == null || row.get(25).equals("") ? null : getDate(row.get(25).toString()));
            dto.setGnewfy2Price(row.get(26) == null || row.get(26).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(26).toString()));
            dto.setGnewfy2FinanceAmount(row.get(27) == null || row.get(27).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(27).toString()));
            dto.setGnewfy2FinanceAuditTime(row.get(28) == null || row.get(28).equals("") ? null : getDate(row.get(28).toString()));

            readAll.add(dto);
        }

        List<XqFreightCostInfo> freList = new ArrayList<>();

        List<XqInsuranceExpenses> insuranceList = new ArrayList<>();
        for (FreightReportTemplateDTO dto : readAll) {
            XqOrder xqOrder = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, dto.getOrderNum()));
            if (xqOrder != null) {
                if (dto.getGnlyfFinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGnlyfFinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType(xqOrder.getId(), "gnlyf");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGnlyfFinanceAuditTime());
                        if (dto.getGnlyfFinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGncdfPrice());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }

                if (dto.getGnbgfFinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGnbgfFinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType(xqOrder.getId(), "gnbgf");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGnbgfFinanceAuditTime());
                        if (dto.getGnbgfFinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGncdfPrice());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }

                if (dto.getGncdfFinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGncdfFinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType(xqOrder.getId(), "gncdf");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getPrice());
                        costInfo.setPayTime(dto.getGncdfFinanceAuditTime());
                        if (dto.getGncdfFinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGncdfPrice());
                            costInfo.setPaidAmount(costInfo.getPrice());
                        }
                        freList.add(costInfo);
                    }
                }

                if (dto.getShfFinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getShfFinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType(xqOrder.getId(), "shf");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getShfFinanceAuditTime());
                        if (dto.getShfFinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getShfPrice());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }
                // 国内额外费用1
                if (dto.getGnewfy1FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGnewfy1FinanceAuditTime() != null) {

                    XqFreightCostInfo costInfo = xqFreightCostInfoService.lambdaQuery()
                            .eq(XqFreightCostInfo::getFeeType, "ewfy1")
                            .eq(XqFreightCostInfo::getIzDomestic, 1)
                            .eq(XqFreightCostInfo::getOrderId, xqOrder.getId())
                            .last("limit 1")
                            .one();

                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGnewfy1FinanceAuditTime());
                        if (dto.getGnewfy1FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGnewfy1Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }

                // 国内额外费用2
                if (dto.getGnewfy2FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGnewfy2FinanceAuditTime() != null) {

                    XqFreightCostInfo costInfo = xqFreightCostInfoService.lambdaQuery()
                            .eq(XqFreightCostInfo::getFeeType, "ewfy2")
                            .eq(XqFreightCostInfo::getIzDomestic, 1)
                            .eq(XqFreightCostInfo::getOrderId, xqOrder.getId())
                            .last("limit 1")
                            .one();

                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGnewfy2FinanceAuditTime());
                        if (dto.getGnewfy2FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGnewfy2Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }

                if (dto.getGnhyfFinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGnhyfFinanceAuditTime() != null) {
                    XqInsuranceExpenses insurance = xqInsuranceExpensesService.getOne(new LambdaQueryWrapper<XqInsuranceExpenses>().eq(XqInsuranceExpenses::getOrderId, xqOrder.getId()));
                    if (insurance != null) {
                        insurance.setPayMoney(insurance.getFinanceConfirmAmount());
                        insurance.setPayTime(dto.getGnhyfFinanceAuditTime());
                        if (dto.getGnhyfFinanceAuditTime() != null) {
                            insurance.setPayableAmount(dto.getGnhyfPrice());
                            insurance.setPaidAmount(insurance.getFinanceConfirmAmount());
                        }
                        insuranceList.add(insurance);
                    }
                }


            }
        }

        xqFreightCostInfoService.updateBatchById(freList);
        xqInsuranceExpensesService.updateBatchById(insuranceList);
        return Result.OK("国内费用报表导入成功");
    }


    // String 转日期
    private static Date getDate(String dateString) {

        String[] dateFormats = {"yyyy-MM-dd", "yyyy/MM/dd"};
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        for (String format : dateFormats) {
            sdf.applyPattern(format);
            try {
                date = sdf.parse(dateString);
                break; // 如果成功解析了日期，就不再尝试其他格式
            } catch (ParseException e) {
                // 如果解析失败，继续尝试下一个格式
                continue;
            }
        }

        if (date != null) {
            System.out.println(date); // 输出日期对象
        } else {
            System.out.println("日期解析失败");
        }

        return date;
    }

    @Override
    public Result<?> freightReportGWImport(MultipartFile file) throws Exception {
        //先拿到所有list
        String fileType = file.getContentType();
        if (fileType == null) {
            throw new MyException("未知文件类型");
        }
        Map<String, Object> param = new HashMap<>();
        //读取excel数据
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<List<Object>> lists = reader.read(1);
        List<FreightReportGWTemplateDTO> readAll = new ArrayList();

        for (List<Object> row : lists) {
            FreightReportGWTemplateDTO dto = new FreightReportGWTemplateDTO();
            dto.setCustomerName(row.get(0) == null || row.get(0).equals("") ? "" : row.get(0).toString());
            dto.setOrderNum(row.get(1) == null || row.get(1).equals("") ? "" : row.get(1).toString());
            dto.setEtd(row.get(2) == null || row.get(2).equals("") ? "" : row.get(2).toString());
            dto.setGnhyfAgent(row.get(3) == null || row.get(3).equals("") ? "" : row.get(3).toString());
            dto.setGnhyfPrice(row.get(4) == null || row.get(4).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(4).toString()));
            dto.setGnhyfFinanceAmount(row.get(5) == null || row.get(5).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(5).toString()));
            dto.setGnhyfFinanceAuditTime(row.get(6) == null || row.get(6).equals("") ? null : getDate(row.get(6).toString()));
            dto.setGngzfAgent(row.get(7) == null || row.get(7).equals("") ? "" : row.get(7).toString());
            dto.setGngzfPrice(row.get(8) == null || row.get(8).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(8).toString()));
            dto.setGngzfFinanceAmount(row.get(9) == null || row.get(9).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(9).toString()));
            dto.setGngzfFinanceAuditTime(row.get(10) == null || row.get(10).equals("") ? null : getDate(row.get(10).toString()));
            dto.setGnewfy3Price(row.get(11) == null || row.get(11).equals("") ? null : new BigDecimal(row.get(11).toString()));
            dto.setGnewfy3FinanceAmount(row.get(12) == null || row.get(12).equals("") ? null : new BigDecimal(row.get(12).toString()));
            dto.setGnewfy3FinanceAuditTime(row.get(13) == null || row.get(13).equals("") ? null : getDate(row.get(13).toString()));
            dto.setGnewfy4Price(row.get(14) == null || row.get(14).equals("") ? null : new BigDecimal(row.get(14).toString()));
            dto.setGnewfy4FinanceAmount(row.get(15) == null || row.get(15).equals("") ? null : new BigDecimal(row.get(15).toString()));
            dto.setGnewfy4FinanceAuditTime(row.get(16) == null || row.get(16).equals("") ? null : getDate(row.get(16).toString()));

            dto.setGwqgfAgent(row.get(17) == null || row.get(17).equals("") ? null : row.get(17).toString());
            dto.setGwqgfPrice(row.get(18) == null || row.get(18).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(18).toString()));
            dto.setGwqgfFinanceAmount(row.get(19) == null || row.get(19).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(19).toString()));
            dto.setGwqgfFinanceAuditTime(row.get(20) == null || row.get(20).equals("") ? null : getDate(row.get(20).toString()));
            dto.setGwkcfAgent(row.get(21) == null || row.get(21).equals("") ? "" : row.get(21).toString());
            dto.setGwkcfPrice(row.get(22) == null || row.get(22).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(22).toString()));
            dto.setGwkcfFinanceAmount(row.get(23) == null || row.get(23).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(23).toString()));
            dto.setGwkcfFinanceAuditTime(row.get(24) == null || row.get(24).equals("") ? null : getDate(row.get(24).toString()));
            dto.setEwfy1Agent(row.get(25) == null || row.get(25).equals("") ? "" : row.get(25).toString());
            dto.setEwfy1Price(row.get(26) == null || row.get(26).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(26).toString()));
            dto.setEwfy1FinanceAmount(row.get(27) == null || row.get(27).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(27).toString()));
            dto.setEwfy1FinanceAuditTime(row.get(28) == null || row.get(28).equals("") ? null : getDate(row.get(28).toString()));
            dto.setEwfy2Agent(row.get(29) == null || row.get(29).equals("") ? "" : row.get(29).toString());
            dto.setEwfy2Price(row.get(30) == null || row.get(30).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(30).toString()));
            dto.setEwfy2FinanceAmount(row.get(31) == null || row.get(31).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(31).toString()));
            dto.setEwfy2FinanceAuditTime(row.get(32) == null || row.get(32).equals("") ? null : getDate(row.get(32).toString()));
            dto.setEwfy3Agent(row.get(33) == null || row.get(33).equals("") ? "" : row.get(33).toString());
            dto.setEwfy3Price(row.get(34) == null || row.get(34).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(34).toString()));
            dto.setEwfy3FinanceAmount(row.get(35) == null || row.get(35).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(35).toString()));
            dto.setEwfy3FinanceAuditTime(row.get(36) == null || row.get(36).equals("") ? null : getDate(row.get(36).toString()));
            dto.setEwfy4Agent(row.get(37) == null || row.get(37).equals("") ? "" : row.get(37).toString());
            dto.setEwfy4Price(row.get(38) == null || row.get(38).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(38).toString()));
            dto.setEwfy4FinanceAmount(row.get(39) == null || row.get(39).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(39).toString()));
            dto.setEwfy4FinanceAuditTime(row.get(40) == null || row.get(40).equals("") ? null : getDate(row.get(40).toString()));
            dto.setEwfy5Agent(row.get(41) == null || row.get(41).equals("") ? "" : row.get(41).toString());
            dto.setEwfy5Price(row.get(42) == null || row.get(42).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(42).toString()));
            dto.setEwfy5FinanceAmount(row.get(43) == null || row.get(43).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(43).toString()));
            dto.setEwfy5FinanceAuditTime(row.get(44) == null || row.get(44).equals("") ? null : getDate(row.get(44).toString()));
            dto.setEwfy6Agent(row.get(45) == null || row.get(45).equals("") ? "" : row.get(45).toString());
            dto.setEwfy6Price(row.get(46) == null || row.get(46).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(46).toString()));
            dto.setEwfy6FinanceAmount(row.get(47) == null || row.get(47).equals("") ? BigDecimal.ZERO : new BigDecimal(row.get(47).toString()));
            dto.setEwfy6FinanceAuditTime(row.get(48) == null || row.get(48).equals("") ? null : getDate(row.get(48).toString()));
            readAll.add(dto);
        }

        List<XqFreightCostInfo> freList = new ArrayList<>();
        for (FreightReportGWTemplateDTO dto : readAll) {
            XqOrder xqOrder = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getOrderNum, dto.getOrderNum()));
            if (xqOrder != null) {
                if (dto.getGnhyfFinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGnhyfFinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType(xqOrder.getId(), "gnhyf");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGnhyfFinanceAuditTime());
                        if (dto.getGnhyfFinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGnhyfPrice());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }

                if (dto.getGngzfFinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGngzfFinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType(xqOrder.getId(), "gngzf");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGngzfFinanceAuditTime());
                        if (dto.getGngzfFinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGngzfPrice());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }

                if (dto.getGwqgfFinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGwqgfFinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType(xqOrder.getId(), "gwqgf");
                    XqFreightCostInfo costInfo1 = xqFreightCostInfoMapper.getByType(xqOrder.getId(), "qggs");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGwqgfFinanceAuditTime());
                        if (dto.getGwqgfFinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGwqgfPrice());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                    if (costInfo1 != null) {
                        costInfo1.setPayMoney(costInfo1.getFinanceAmount());
                        costInfo1.setPayTime(dto.getGwqgfFinanceAuditTime());
                        if (dto.getGwqgfFinanceAuditTime() != null) {
                            costInfo1.setPayableAmount(dto.getGwqgfPrice());
                            costInfo1.setPaidAmount(costInfo1.getFinanceAmount());
                        }
                        freList.add(costInfo1);
                    }
                }

                if (dto.getGwkcfFinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGwkcfFinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoService.lambdaQuery()
                            .eq(XqFreightCostInfo::getOrderId, xqOrder.getId())
                            .eq(XqFreightCostInfo::getIzReturnFee, 0)
                            .likeRight(XqFreightCostInfo::getFeeType, "gwkcf")
                            .last("limit 1")
                            .one();
//                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType(xqOrder.getId(), "gwkcf");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGwkcfFinanceAuditTime());
                        if (dto.getGwkcfFinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGwkcfPrice());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }

                if (dto.getEwfy1FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getEwfy1FinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType1(xqOrder.getId(), "ewfy1");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getEwfy1FinanceAuditTime());
                        if (dto.getEwfy1FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getEwfy1Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }

                if (dto.getEwfy2FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getEwfy2FinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType1(xqOrder.getId(), "ewfy2");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getEwfy2FinanceAuditTime());
                        if (dto.getEwfy2FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getEwfy2Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }
                if (dto.getEwfy3FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getEwfy3FinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType1(xqOrder.getId(), "ewfy3");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getEwfy3FinanceAuditTime());
                        if (dto.getEwfy3FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getEwfy3Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }
                if (dto.getEwfy4FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getEwfy4FinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType1(xqOrder.getId(), "ewfy4");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getEwfy4FinanceAuditTime());
                        if (dto.getEwfy4FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getEwfy1Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }
                if (dto.getEwfy5FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getEwfy5FinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType1(xqOrder.getId(), "ewfy5");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getEwfy5FinanceAuditTime());
                        if (dto.getEwfy5FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getEwfy5Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }
                if (dto.getEwfy6FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getEwfy6FinanceAuditTime() != null) {
                    XqFreightCostInfo costInfo = xqFreightCostInfoMapper.getByType1(xqOrder.getId(), "ewfy6");
                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getEwfy6FinanceAuditTime());
                        if (dto.getEwfy6FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getEwfy6Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }

                // 国内额外费用3
                if (dto.getGnewfy3FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGnewfy3FinanceAuditTime() != null) {

                    XqFreightCostInfo costInfo = xqFreightCostInfoService.lambdaQuery()
                            .eq(XqFreightCostInfo::getFeeType, "ewfy3")
                            .eq(XqFreightCostInfo::getIzDomestic, 1)
                            .eq(XqFreightCostInfo::getOrderId, xqOrder.getId())
                            .last("limit 1")
                            .one();

                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGnewfy3FinanceAuditTime());
                        if (dto.getGnewfy3FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGnewfy3Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }


                // 国内额外费用4
                if (dto.getGnewfy4FinanceAmount().compareTo(BigDecimal.ZERO) > 0 || dto.getGnewfy4FinanceAuditTime() != null) {

                    XqFreightCostInfo costInfo = xqFreightCostInfoService.lambdaQuery()
                            .eq(XqFreightCostInfo::getFeeType, "ewfy4")
                            .eq(XqFreightCostInfo::getIzDomestic, 1)
                            .eq(XqFreightCostInfo::getOrderId, xqOrder.getId())
                            .last("limit 1")
                            .one();

                    if (costInfo != null) {
                        costInfo.setPayMoney(costInfo.getFinanceAmount());
                        costInfo.setPayTime(dto.getGnewfy4FinanceAuditTime());
                        if (dto.getGnewfy4FinanceAuditTime() != null) {
                            costInfo.setPayableAmount(dto.getGnewfy4Price());
                            costInfo.setPaidAmount(costInfo.getFinanceAmount());
                        }
                        freList.add(costInfo);
                    }
                }


            }
        }

        xqFreightCostInfoService.updateBatchById(freList);
        return Result.OK("国外费用报表导入成功");
    }

    @Override
    public void freightKCReportTemplate(QueryFreightKCDTO dto, HttpServletResponse response) {
        Page<FreightKCReportVO> page = new Page<>(1, 9999);
        IPage<FreightKCReportVO> results = reportFormMapper.freightKCReport(page, dto);
        List<FreightKCReportVO> records = results.getRecords();
        // 序号
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        for (FreightKCReportVO record : records) {
            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }

        String conlumn = "serialNumber,orderNum,customerOrderNum,customerName,truckCompany,truckFee,payDate";
        CommonUtils.export(conlumn, records, response, FreightKCReportVO.class, null, null, false);

    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Result<?> freightKCReportImport(MultipartFile file) {
        //先拿到所有list
        String fileType = file.getContentType();
        if (fileType == null) {
            throw new InterestingBootException("未知文件类型");
        }
        // 获取输入流
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        // 拿出所有sheet页中的值
        if (reader.getSheetCount() == 0) throw new InterestingBootException("导入失败，该文件中没有数据");
        List<List<Object>> allList = new ArrayList<>();
        for (int i = 0; i < reader.getSheetCount(); i++) {
            reader.setSheet(i);
            allList = reader.read(1);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 拿出其中付款日期不为空的数据
        List<List<Object>> datas = new ArrayList<>();
        for (List<Object> d : allList) {
            Object orderNum = d.get(1);
            Object customerOrderNum = d.get(2);
            Object payDate = d.get(6);
            if (StringUtils.isNotBlank((String) orderNum) && StringUtils.isNotBlank((String) customerOrderNum) && StringUtils.isNotBlank(payDate.toString())) {
                datas.add(d);
            }
        }
        // 如果结果不为空

        if (datas.size() != 0) {
            // 一次性根据orderNum查询所有的Id
            List<String> orderNums = new ArrayList<>();
            for (List<Object> data : datas) {
                String s = (String) data.get(1);
                String[] split = s.split(",");
                orderNums.addAll(Arrays.asList(split));
            }

            Map<String, String> idNumMap = xqOrderService.lambdaQuery()
                    .in(XqOrder::getOrderNum, orderNums)
                    .select(XqOrder::getId, XqOrder::getOrderNum)
                    .list().stream().collect(Collectors.toMap(XqOrder::getOrderNum, XqOrder::getId));

            // 校验数据
            for (List<Object> data : datas) {
                // 取出订单号和客户订单号
                String s = (String) data.get(1);
                String[] split = s.split(",");
                for (String s1 : split) {
                    // 订单号不存在抛异常
                    if (StringUtils.isBlank(idNumMap.get(s1)))
                        throw new InterestingBootException("订单号" + s1 + "对应的订单不存在");
                }
//                // 客户订单号为空抛异常
//                if (StringUtils.isBlank((String) data.get(2)))
//                    throw new InterestingBootException("订单号" + s + "这一行的客户订单号未填写");
            }

            for (List<Object> data : datas) {
                // 解析付款日期
                Date payDate = sdf.parse(data.get(6).toString());

                String s = (String) data.get(1);
                String[] split = s.split(",");
                for (String s1 : split) {

                    String customerOrderNum = (String) data.get(2);
                    String poId = idNumMap.get(s1);
                    XqTruckInfo one = xqTruckInfoService.lambdaQuery().eq(XqTruckInfo::getOrderId, poId).eq(XqTruckInfo::getCustomerOrderNum, customerOrderNum).one();
                    if (one == null)
                        throw new InterestingBootException("不存在订单号为" + s1 + "，客户订单号为" + customerOrderNum + "的卡车费用记录");

                    // 根据订单号和客户订单号查询卡车记录
                    xqTruckInfoService.lambdaUpdate()
                            .eq(XqTruckInfo::getId, one.getId())
                            .set(XqTruckInfo::getPayDate, payDate)
                            .set(one.getTruckFee() != null, XqTruckInfo::getPayedPrice, one.getTruckFee())
                            .set(XqTruckInfo::getUpdateTime, new Date())
                            .set(XqTruckInfo::getUpdateBy, FilterContextHandler.getUserInfo().getUsername())
                            .update();
                }
            }


        }
        return Result.OK("卡车费用导入成功");

    }

    /**
     * 仓库利润列表查询
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> getWarehouseProfit(QueryTiHuoLiRunDTO dto) {
        List<TiHuoLiRunVO> warehouseProfit = reportFormMapper.getWarehouseProfit(dto);

        // 数据处理
        Map<String, Object> result = new LinkedHashMap<>();

        Map<String, List<TiHuoLiRunVO>> collect = warehouseProfit.stream().collect(Collectors.groupingBy(TiHuoLiRunVO::getWarehouseName));


        // 提货数量
        Integer exitNumTotal = 0;
        // 总价
        BigDecimal exitMoneyTotal = BigDecimal.ZERO;
        // 总成本
        BigDecimal zcbTotal = BigDecimal.ZERO;
        // 毛利
        BigDecimal mlTotal = BigDecimal.ZERO;
        // 总榜数
        BigDecimal zbsTotal = BigDecimal.ZERO;
        // 仓库费用
        BigDecimal warehouseFreeTotal = BigDecimal.ZERO;

        // 佣金
        BigDecimal commissiontotal = BigDecimal.ZERO;
        // 卡车额外费用
        BigDecimal kcewfytotal = BigDecimal.ZERO;
        // 中信保费用
        BigDecimal creditInsurancePremiumConverttotal = BigDecimal.ZERO;
        // 保理利息
        BigDecimal factoringInteresttotal = BigDecimal.ZERO;
        //额外费用
        BigDecimal extraFreeTotal = BigDecimal.ZERO;

        for (String vo : collect.keySet()) {

            List<TiHuoLiRunVO> voList = collect.get(vo);
            if (voList != null && voList.size() > 0) {
                Map<String, List<TiHuoLiRunVO>> collect1 = voList.stream().collect(Collectors.groupingBy(TiHuoLiRunVO::getSourceRepNum));
                for (String vo1 : collect1.keySet()) {
                    List<TiHuoLiRunVO> voList1 = collect1.get(vo1);
                    if (voList1 != null && voList1.size() > 0) {
                        Map<String, List<TiHuoLiRunVO>> collect2 = voList1.stream().collect(Collectors.groupingBy(TiHuoLiRunVO::getCustomerOrderNum));
                        for (String vo2 : collect2.keySet()) {
                            List<TiHuoLiRunVO> voList2 = collect2.get(vo2);
                            for (int i = 0; i < voList2.size(); i++) {
                                if (i != 0) {
                                    voList2.get(i).setKcewfy(new BigDecimal(0));
                                }
                            }
                        }
                    }
                }

                List<Object> data = new ArrayList<>();
                // 提货数量
                Integer exitNumSubtotal = 0;
                // 总价
                BigDecimal exitMoneySubtotal = BigDecimal.ZERO;
                // 总成本
                BigDecimal zcbSubtotal = BigDecimal.ZERO;
                // 毛利
                BigDecimal mlSubtotal = BigDecimal.ZERO;
                // 总榜数
                BigDecimal zbsSubtotal = BigDecimal.ZERO;
                // 佣金
                BigDecimal commissionSubtotal = BigDecimal.ZERO;
                // 卡车额外费用
                BigDecimal kcewfySubtotal = BigDecimal.ZERO;
                // 中信保费用
                BigDecimal creditInsurancePremiumConvertSubtotal = BigDecimal.ZERO;
                // 保理利息
                BigDecimal factoringInterestSubtotal = BigDecimal.ZERO;
                //额外费用
                BigDecimal extraFreeSubTotal = BigDecimal.ZERO;

                // 保理利息相同订单只取一次
//                String customerOrderNum = null;
                // 小计
                for (TiHuoLiRunVO v : voList) {
//                    if (v.getCustomerOrderNum()!= null && v.getCustomerOrderNum().equals(customerOrderNum)) {
//                        v.setFactoringInterest(BigDecimal.ZERO);
//                    }else {
//                        customerOrderNum = v.getCustomerOrderNum();
//                    }
                    exitNumSubtotal += v.getExitNum();
                    exitMoneySubtotal = exitMoneySubtotal.add(v.getExitMoney());
                    zcbSubtotal = zcbSubtotal.add(v.getZcb());
                    mlSubtotal = mlSubtotal.add(v.getMl());
                    zbsSubtotal = zbsSubtotal.add(v.getZbs());
                    commissionSubtotal = commissionSubtotal.add(v.getCommission());
                    kcewfySubtotal = kcewfySubtotal.add(v.getKcewfy());
                    creditInsurancePremiumConvertSubtotal = creditInsurancePremiumConvertSubtotal.add(v.getCreditInsurancePremiumConvert());
                    factoringInterestSubtotal = factoringInterestSubtotal.add(v.getFactoringInterest());
                    extraFreeSubTotal = extraFreeSubTotal.add(v.getExtraFree());
                }


                WarehouseTotalVO warehouseTotalVO = new WarehouseTotalVO();
                // 查询仓库费用
                BigDecimal warehouseFree = reportFormMapper.getWarehouseFreeByDate(voList.get(0).getWarehouseId(), dto.getBolTimeStart(), dto.getBolTimeEnd());
                warehouseTotalVO.setWarehouseFree(warehouseFree);
                warehouseTotalVO.setExitNum(exitNumSubtotal);
                warehouseTotalVO.setExitMoney(exitMoneySubtotal);
                warehouseTotalVO.setZcb(zcbSubtotal);
                warehouseTotalVO.setMl(mlSubtotal);
                warehouseTotalVO.setZbs(zbsSubtotal);
                warehouseTotalVO.setCommissiontotal(commissionSubtotal);
                warehouseTotalVO.setKcewfy(kcewfySubtotal);
                warehouseTotalVO.setCreditInsurancePremiumConvert(creditInsurancePremiumConvertSubtotal);
                warehouseTotalVO.setFactoringInterest(factoringInterestSubtotal);
                warehouseTotalVO.setExtraFree(extraFreeSubTotal);

                // 总计
                exitNumTotal += exitNumSubtotal;
                exitMoneyTotal = exitMoneyTotal.add(exitMoneySubtotal);
                zcbTotal = zcbTotal.add(zcbSubtotal);
                mlTotal = mlTotal.add(mlSubtotal);
                zbsTotal = zbsTotal.add(zbsSubtotal);
                warehouseFreeTotal = warehouseFreeTotal.add(warehouseFree);
                commissiontotal = commissiontotal.add(commissionSubtotal);
                kcewfytotal = kcewfytotal.add(kcewfySubtotal);
                creditInsurancePremiumConverttotal = creditInsurancePremiumConverttotal.add(creditInsurancePremiumConvertSubtotal);
                factoringInteresttotal = factoringInteresttotal.add(factoringInterestSubtotal);
                extraFreeTotal = extraFreeTotal.add(extraFreeSubTotal);


                // 仓库利润明细
                data.add(voList);
                // 仓库小计
                data.add(warehouseTotalVO);

                result.put(vo, data);
            }
        }

        WarehouseTotalVO warehouseTotalVO = new WarehouseTotalVO();

        warehouseTotalVO.setExitNum(exitNumTotal);
        warehouseTotalVO.setExitMoney(exitMoneyTotal);
        warehouseTotalVO.setZcb(zcbTotal);
        warehouseTotalVO.setMl(mlTotal);
        warehouseTotalVO.setZbs(zbsTotal);
        warehouseTotalVO.setWarehouseFree(warehouseFreeTotal);
        warehouseTotalVO.setCommissiontotal(commissiontotal);
        warehouseTotalVO.setKcewfy(kcewfytotal);
        warehouseTotalVO.setCreditInsurancePremiumConvert(creditInsurancePremiumConverttotal);
        warehouseTotalVO.setFactoringInterest(factoringInteresttotal);
        warehouseTotalVO.setExtraFree(extraFreeTotal);

        result.put("total", warehouseTotalVO);

        return result;
    }


    /**
     * 仓库利润年度报表
     *
     * @param year
     * @return
     */
    @Override
    public List<TiHuoLiRunTotalVO> getWarehouseProfitByYear(String year) {

        // 查询基础信息
        List<TiHuoLiRunTotalVO> warehouseProfitByYear = reportFormMapper.getWarehouseProfitByYear(year);
        // 查询各仓库每月的额外费用
        List<TiHuoLiRunMonthVO> warehouseFeeList = reportFormMapper.getWarehouseFeeByYear(year);
        Map<String, TiHuoLiRunMonthVO> collect = warehouseFeeList.stream().collect(Collectors.toMap(TiHuoLiRunMonthVO::getWarehouseName, v -> v));

        // 获取出库订单的仓库名称
        List<String> warehouseList = xqWarehouseService.getWarehouseName();


        List<WarehouseHuoZhiVO> list1 = new ArrayList();
        List<WarehouseHuoZhiVO> list2 = new ArrayList();
        List<WarehouseHuoZhiVO> list3 = new ArrayList();
        List<WarehouseHuoZhiVO> list4 = new ArrayList();
        List<WarehouseHuoZhiVO> list5 = new ArrayList();
        List<WarehouseHuoZhiVO> list6 = new ArrayList();
        List<WarehouseHuoZhiVO> list7 = new ArrayList();
        List<WarehouseHuoZhiVO> list8 = new ArrayList();
        List<WarehouseHuoZhiVO> list9 = new ArrayList();
        List<WarehouseHuoZhiVO> list10 = new ArrayList();
        List<WarehouseHuoZhiVO> list11 = new ArrayList();
        List<WarehouseHuoZhiVO> list12 = new ArrayList();
        Map<String, List<WarehouseHuoZhiVO>> map = new HashMap<>();

        if (warehouseList != null && warehouseList.size() > 0) {


            for (String s : warehouseList) {
                TiHuoLiRunMonthVO tiHuoLiRunMonthVO = collect.get(s);

                WarehouseHuoZhiVO vo1 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo2 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo3 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo4 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo5 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo6 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo7 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo8 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo9 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo10 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo11 = new WarehouseHuoZhiVO();
                WarehouseHuoZhiVO vo12 = new WarehouseHuoZhiVO();
                if (tiHuoLiRunMonthVO != null) {
                    vo1.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo1.setNum(tiHuoLiRunMonthVO.getJanuary());
                    vo2.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo2.setNum(tiHuoLiRunMonthVO.getFebruary());
                    vo3.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo3.setNum(tiHuoLiRunMonthVO.getMarch());
                    vo4.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo4.setNum(tiHuoLiRunMonthVO.getApril());
                    vo5.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo5.setNum(tiHuoLiRunMonthVO.getMay());
                    vo6.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo6.setNum(tiHuoLiRunMonthVO.getJune());
                    vo7.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo7.setNum(tiHuoLiRunMonthVO.getJuly());
                    vo8.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo8.setNum(tiHuoLiRunMonthVO.getAugust());
                    vo9.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo9.setNum(tiHuoLiRunMonthVO.getSeptember());
                    vo10.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo10.setNum(tiHuoLiRunMonthVO.getOctober());
                    vo11.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo11.setNum(tiHuoLiRunMonthVO.getNovember());
                    vo12.setWarehouseName(tiHuoLiRunMonthVO.getWarehouseName());
                    vo12.setNum(tiHuoLiRunMonthVO.getDecember());

                } else {
                    vo1.setWarehouseName(s);
                    vo2.setWarehouseName(s);
                    vo3.setWarehouseName(s);
                    vo4.setWarehouseName(s);
                    vo5.setWarehouseName(s);
                    vo6.setWarehouseName(s);
                    vo7.setWarehouseName(s);
                    vo8.setWarehouseName(s);
                    vo9.setWarehouseName(s);
                    vo10.setWarehouseName(s);
                    vo11.setWarehouseName(s);
                    vo12.setWarehouseName(s);
                }
                list1.add(vo1);
                list2.add(vo2);
                list3.add(vo3);
                list4.add(vo4);
                list5.add(vo5);
                list6.add(vo6);
                list7.add(vo7);
                list8.add(vo8);
                list9.add(vo9);
                list10.add(vo10);
                list11.add(vo11);
                list12.add(vo12);
            }


            map.put("1", list1);
            map.put("2", list2);
            map.put("3", list3);
            map.put("4", list4);
            map.put("5", list5);
            map.put("6", list6);
            map.put("7", list7);
            map.put("8", list8);
            map.put("9", list9);
            map.put("10", list10);
            map.put("11", list11);
            map.put("12", list12);

        }

        if (warehouseProfitByYear != null && warehouseProfitByYear.size() > 0) {
            for (TiHuoLiRunTotalVO vo : warehouseProfitByYear) {

                List<WarehouseHuoZhiVO> warehouseHuoZhiVOS = map.get(vo.getMonth());
                // 统计额外费用
                BigDecimal feeTotal = BigDecimal.ZERO;

                if (warehouseHuoZhiVOS != null && warehouseHuoZhiVOS.size() > 0) {
                    for (WarehouseHuoZhiVO warehouseHuoZhiVO : warehouseHuoZhiVOS) {
                        feeTotal = feeTotal.add(warehouseHuoZhiVO.getNum());
                    }
                }

                vo.setWarehouseNames(warehouseHuoZhiVOS);
                vo.setFeeTotal(feeTotal);
                // 计算纯利润 = 毛利 - 仓库费用 - 额外费用
                BigDecimal clr = vo.getMl().subtract(vo.getWarehouseFree()).subtract(feeTotal);
                vo.setNetProfit(clr);
            }
        }
        return warehouseProfitByYear;
    }

    @Override
    public List<PayableVO> getPayableList(PayableDTO dto) {
        // 多出货方式筛选处理
        String chfs = dto.getChfs();
        List<String> chfsList = null;
        if (StringUtils.isNotBlank(chfs)) {
            String[] split = chfs.split(",");
            chfsList = ListUtil.toList(split);
            chfsList.remove("");
            dto.setChfsList(chfsList);
        }


        if (StringUtils.isNotBlank(dto.getColumn())) {
            String column = oConvertUtils.camelToUnderline(dto.getColumn());
            dto.setColumn(column);
        }
        return reportFormMapper.getPayableList(dto);
    }

    @Override
    public void payableReportExport(PayableDTO dto, HttpServletResponse response) {

        // 序号
        List<PayableVO> voList = reportFormService.getPayableList(dto);
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        int recordCount = 0;
        BigDecimal totalExtraCosts = BigDecimal.ZERO;
        String etdYear = "";

        BigDecimal dqyfkCNY = BigDecimal.ZERO;
        BigDecimal dqyfkUSD = BigDecimal.ZERO;
        BigDecimal zyfkCNY = BigDecimal.ZERO;
        BigDecimal zyfkUSD = BigDecimal.ZERO;
        BigDecimal ykkCNY = BigDecimal.ZERO;
        BigDecimal ykkUSD = BigDecimal.ZERO;

        for (PayableVO record : voList) {
            // 判断当前订单号是否与上一个一致
            if (record.getType() == 1) {
                dqyfkCNY = dqyfkCNY.add(record.getDqyfkCNY());
                dqyfkUSD = dqyfkUSD.add(record.getDqyfkUSD());
                zyfkCNY = zyfkCNY.add(record.getZyfkCNY());
                zyfkUSD = zyfkUSD.add(record.getZyfkUSD());
            } else {
                ykkCNY = ykkCNY.add(record.getZyfkCNY());
                ykkUSD = ykkUSD.add(record.getZyfkUSD());

            }
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);

        }
        List<YfkTotalOV> vos = new ArrayList<>();

        YfkTotalOV vo = new YfkTotalOV();
        vo.setDqyfkCNY(dqyfkCNY);
        vo.setDqyfkUSD(dqyfkUSD);
        vo.setZyfkCNY(zyfkCNY);
        vo.setZyfkUSD(zyfkUSD);
        vo.setYkkCNY(ykkCNY);
        vo.setYkkUSD(ykkUSD);
        vos.add(vo);

        CommonUtils.export(dto.getExportColumn(), voList, response, PayableVO.class, YfkTotalOV.class, vos, false);


    }

    @Override
    public List<SumFreightTYNewVO> sumFreightTYNew(QueryTYFreightNewDTO dto) {
        return reportFormMapper.sumFreightTYNew(dto);
    }


    @SneakyThrows
    @Override
    public void freightGWReportExport(Page<FreightGWReportVO> page, QueryGWFreightDTO dto, HttpServletResponse response) {
        // 多客户筛选处理
        String customerName = dto.getCustomerName();
        List<String> customerIds = null;
        if (StringUtils.isNotBlank(customerName)) {
            String[] split = customerName.split(",");
            customerIds = ListUtil.toList(split);
            customerIds.remove("");
        }
        // 拿到数据
        IPage<FreightGWReportVO> results = reportFormMapper.freightGWReport(page, dto, customerIds);
        List<FreightGWReportVO> records = results.getRecords();
        // 序号
        String currentOrderNumber = UUID.randomUUID().toString();
        int currentSerialNumber = 0;
        for (FreightGWReportVO record : records) {

            // 判断当前订单号是否与上一个一致
            String orderNum = record.getOrderNum();
            if (!currentOrderNumber.equals(orderNum)) {
                if (orderNum == null) {
                    currentOrderNumber = "";
                } else {
                    currentOrderNumber = orderNum;
                }
                currentSerialNumber++;
            }
            record.setSerialNumber(currentSerialNumber);
        }


        // 合计
        List<SumFreightGWVO> list = reportFormService.sumFreightGWReport(dto);
        CommonUtils.export(dto.getExportColumn(), records, response, FreightGWReportVO.class, SumFreightGWVO.class, list, false);

    }


}
