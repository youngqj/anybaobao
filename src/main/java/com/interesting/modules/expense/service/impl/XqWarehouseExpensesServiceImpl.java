package com.interesting.modules.expense.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.*;
import com.interesting.config.FilterContextHandler;
import com.interesting.modules.expense.dto.QueryExpensesDTO;
import com.interesting.modules.expense.dto.WarehouseExpensesVO;
import com.interesting.modules.expense.entity.XqWarehouseExpenses;
import com.interesting.modules.expense.mapper.XqWarehouseExpensesMapper;
import com.interesting.modules.expense.service.XqWarehouseExpensesService;
import com.interesting.modules.expense.vo.WarehouseTotalAmountVO;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.postImport.entity.XqPostImport;
import com.interesting.modules.postImport.mapper.XqPostImportMapper;
import com.interesting.modules.warehouse.entity.XqWarehouse;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/3/12 9:02
 * @Project: trade-manage
 * @Description:
 */

@Service
public class XqWarehouseExpensesServiceImpl extends ServiceImpl<XqWarehouseExpensesMapper, XqWarehouseExpenses> implements XqWarehouseExpensesService {
    @Resource
    private XqPostImportMapper xqPostImportMapper;
    @Value("${jeecg.path.upload}")
    private String uploadFilePath;
    @Value("${downloadExcel}")
    private String downloadExcel;
    @Autowired
    private XqWarehouseService xqWarehouseService;
    @Autowired
    private IXqOrderService xqOrderService;

    /**
     * 下载仓库费用登记模板
     *
     * @param response
     */
    @Override
    public void expenseTemplateExport(HttpServletResponse response) {
        // 获取所有海外仓库名称
        List<XqWarehouse> warehouses = xqWarehouseService.lambdaQuery()
                .eq(XqWarehouse::getWarehouseType, 2)
                .list();


        if (warehouses.size() > 0) {
            // 创建 ExcelWriter 对象
            // 通过工具类创建writer
            ExcelWriter writer = ExcelUtil.getWriter();
            // 是否是第一页
            AtomicBoolean a = new AtomicBoolean(true);
            warehouses.forEach(vo -> {

                // 写入页签
                if (a.get()) {
                    writer.renameSheet(vo.getName());
                    a.set(false);
                }
                writer.setSheet(vo.getName());

                List<Map<String, Object>> list = new ArrayList<>();
                writer.setColumnWidth(0, 30);
                writer.setColumnWidth(1, 30);
                writer.setColumnWidth(2, 20);
                writer.setColumnWidth(3, 15);
                writer.setColumnWidth(4, 40);
                writer.setColumnWidth(5, 25);
                writer.setColumnWidth(6, 40);
                writer.setColumnWidth(7, 40);
                writer.setColumnWidth(8, 40);
                writer.setColumnWidth(9, 10);
                writer.setColumnWidth(10, 15);
                writer.setColumnWidth(11, 20);
                writer.setColumnWidth(12, 20);
                writer.setColumnWidth(13, 40);


                writer.addHeaderAlias("orderNum", "订单号");
                writer.addHeaderAlias("productName", "产品名称");
                writer.addHeaderAlias("invoiceNum", "发票号");
                writer.addHeaderAlias("expense", "费用USD");
                writer.addHeaderAlias("invoiceDate", "发票日期(格式：2024-03-13)");
                writer.addHeaderAlias("remark", "费用名称");
                writer.addHeaderAlias("dueDate", "到期付款日(格式：2024-03-13)");
                writer.addHeaderAlias("applyForDate", "申请时间(格式：2024-03-13)");
                writer.addHeaderAlias("paymentDate", "实际付款日(格式：2024-03-13)");
                writer.addHeaderAlias("izSettle", "状态");
                writer.addHeaderAlias("paymentAmount", "付款金额");
                writer.addHeaderAlias("remark2", "备注");
                writer.addHeaderAlias("auditAmount", "财务审核金额");
                writer.addHeaderAlias("auditDate", "财务审核日期(格式：2024-03-13)");


                Map<String, Object> items = new HashMap<>();

                items.put("orderNum", "");
                items.put("productName", "");
                items.put("invoiceNum", "");
                items.put("expense", "");
                items.put("invoiceDate", "");
                items.put("remark", "");
                items.put("dueDate", "");
                items.put("applyForDate", "");
                items.put("paymentDate", "");
                items.put("izSettle", "");
                items.put("paymentAmount", "");
                items.put("remark2", "");
                items.put("auditAmount", "");
                items.put("auditDate", "");

                list.add(items);

                // 一次性写出内容，使用默认样式
                writer.write(list, true);

            });

            String excelName = "仓库费用登记模板";

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

        }


    }

    @Override
    public Result<?> importExpenseTemplate(MultipartFile file) {
        String fileType = file.getContentType();
        if (fileType == null) {
            throw new InterestingBootException("未知文件类型");
        }
        List<WarehouseExpensesVO> warehouseExpensesList = null;
        try {
            // 封装导入数据
            warehouseExpensesList = importExpenses(file.getInputStream());

            // 校验数据
            List<WarehouseExpensesVO> failList = handle(warehouseExpensesList);

            // 失败文件
            if (failList.size() > 0) {

                Map<String, Object> param = new HashMap<>();
                // 失败数据返回下载链接
                String failUrl = getFailList(failList);

                param.put("fail", failList.size());
                param.put("data", downloadExcel + failUrl);

                // 导入表记录一条信息
                XqPostImport xqPostImport = new XqPostImport();
                xqPostImport.setImportType("仓库费用登记");
                xqPostImport.setImportPerson(FilterContextHandler.getUserId());
                xqPostImport.setImportTime(new Date());
                xqPostImport.setImportResult("导入失败");
                xqPostImport.setImportCount(failList.size());
                xqPostImport.setDownloadUrl(failUrl);
                xqPostImportMapper.insert(xqPostImport);
                return Result.error("导入失败！", param);
            }

            return Result.OK();

        } catch (Exception e) {
            return Result.error(e.getMessage());
        }


    }

    @Override
    public IPage<WarehouseExpensesVO> pageList(Page<WarehouseExpensesVO> page, QueryExpensesDTO dto) {
        if (StringUtils.isNotBlank(dto.getColumn())) {
            String column = oConvertUtils.camelToUnderline(dto.getColumn());
            dto.setColumn(column);
        }
        return baseMapper.getWarehouseList(page, dto);
    }

    @Override
    public List<WarehouseTotalAmountVO>  getWarehouseTotalAmount(QueryExpensesDTO dto) {
        List<WarehouseTotalAmountVO> list = new ArrayList<>();
        WarehouseTotalAmountVO vo = baseMapper.getWarehouseTotalAmount(dto);
        if(vo == null){
            vo = new WarehouseTotalAmountVO();
        }
        vo.setNotPaymentAmountTotal(vo.getExpenseTotal().subtract(vo.getPaymentAmountTotal()));
        list.add(vo);
//        Map<String, BigDecimal> map = new HashMap<>();
//        map.put("auditAmountTotal", vo == null ? BigDecimal.ZERO : vo.getAuditAmountTotal());
//        map.put("expenseTotal", vo == null ? BigDecimal.ZERO : vo.getExpenseTotal());
//        map.put("paymentAmountTotal", vo == null ? BigDecimal.ZERO : vo.getPaymentAmountTotal());
        return list;
    }

    /**
     * 失败文件写入服务器
     *
     * @param failList
     * @return
     */
    public String getFailList(List<WarehouseExpensesVO> failList) {

        Map<String, List<WarehouseExpensesVO>> collect = failList.stream().collect(Collectors.groupingBy(WarehouseExpensesVO::getWarehouseId));

        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");// 格式化为年月
        String date = sf.format(new Date());


        // 获取所有海外仓库名称
        Set<String> warehouses = collect.keySet();


        if (warehouses.size() > 0) {
            // 创建 ExcelWriter 对象
            // 通过工具类创建writer
            ExcelWriter writer = ExcelUtil.getWriter();
            // 是否是第一页
            AtomicBoolean a = new AtomicBoolean(true);
            warehouses.forEach(vo -> {

                // 写入页签
                if (a.get()) {
                    writer.renameSheet(vo);
                    a.set(false);
                }
                writer.setSheet(vo);

                List<Map<String, Object>> list = new ArrayList<>();

                writer.setColumnWidth(0, 30);
                writer.setColumnWidth(1, 30);
                writer.setColumnWidth(2, 20);
                writer.setColumnWidth(3, 15);
                writer.setColumnWidth(4, 40);
                writer.setColumnWidth(5, 25);
                writer.setColumnWidth(6, 40);
                writer.setColumnWidth(7, 40);
                writer.setColumnWidth(8, 40);
                writer.setColumnWidth(9, 10);
                writer.setColumnWidth(10, 15);
                writer.setColumnWidth(11, 20);
                writer.setColumnWidth(12, 20);
                writer.setColumnWidth(13, 40);
                writer.setColumnWidth(14, 30);


                writer.addHeaderAlias("orderNum", "订单号");
                writer.addHeaderAlias("productName", "产品名称");
                writer.addHeaderAlias("invoiceNum", "发票号");
                writer.addHeaderAlias("expense", "费用USD");
                writer.addHeaderAlias("invoiceDate", "发票日期(格式：2024-03-13)");
                writer.addHeaderAlias("remark", "费用名称");
                writer.addHeaderAlias("dueDate", "到期付款日(格式：2024-03-13)");
                writer.addHeaderAlias("applyForDate", "申请时间(格式：2024-03-13)");
                writer.addHeaderAlias("paymentDate", "实际付款日(格式：2024-03-13)");
                writer.addHeaderAlias("izSettle", "状态");
                writer.addHeaderAlias("paymentAmount", "付款金额");
                writer.addHeaderAlias("remark2", "备注");
                writer.addHeaderAlias("auditAmount", "财务审核金额");
                writer.addHeaderAlias("auditDate", "财务审核日期(格式：2024-03-13)");
                writer.addHeaderAlias("failMsg", "失败原因");

                List<WarehouseExpensesVO> expensesDTOList = collect.get(vo);

                expensesDTOList.forEach(e -> {
                    Map<String, Object> items = new HashMap<>();

                    items.put("orderNum", e.getOrderNum());
                    items.put("productName", e.getProductName());
                    items.put("invoiceNum", e.getInvoiceNum());
                    items.put("expense", e.getExpense());
                    items.put("invoiceDate", e.getInvoiceDate());
                    items.put("remark", e.getRemark());
                    items.put("dueDate", e.getDueDate());
                    items.put("applyForDate", e.getApplyForDate());
                    items.put("paymentDate", e.getPaymentDate());
                    items.put("izSettle", e.getIzSettle());
                    items.put("paymentAmount", e.getPaymentAmount());
                    items.put("remark2", e.getRemark2());
                    items.put("auditAmount", e.getAuditAmount());
                    items.put("auditDate", e.getAuditDate());
                    items.put("failMsg", e.getFailMsg());

                    list.add(items);
                });


                // 一次性写出内容，使用默认样式
                writer.write(list, true);

            });
            String excelName = System.currentTimeMillis() + ".xls";

            Calendar cal = Calendar.getInstance();
            String ctxPath = uploadFilePath + "/" + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONDAY) + 1) + "/" + cal.get(Calendar.DATE) + "/";
            String filePath = ctxPath + "temp/" + date;
            File localFile = new File(filePath);
            if (!localFile.exists()) {
                localFile.mkdirs();
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(filePath + "/" + excelName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            writer.flush(fos, true);
            return filePath + "/" + excelName;


        }
        return null;


    }

    /**
     * 处理导入数据
     *
     * @param dtoList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<WarehouseExpensesVO> handle(List<WarehouseExpensesVO> dtoList) {

        List<WarehouseExpensesVO> failList = new ArrayList<>();
        List<XqWarehouseExpenses> result = new ArrayList<>();
        List<XqWarehouseExpenses> updateList = new ArrayList<>();

        // 查询现有仓库信息
        List<XqWarehouse> warehouses = xqWarehouseService.lambdaQuery()
                .eq(XqWarehouse::getWarehouseType, 2)
                .list();

        // 查询订单信息
        List<XqOrder> list = xqOrderService.lambdaQuery().list();
        Map<String, List<XqOrder>> orderMap = list.stream().collect(Collectors.groupingBy(XqOrder::getOrderNum));

        Map<String, String> warehouseMap = warehouses.stream().collect(Collectors.toMap(XqWarehouse::getName, XqWarehouse::getId));

        // 查询数据库仓库费用登记
        List<XqWarehouseExpenses> expenses = this.lambdaQuery().list();

        Map<String, XqWarehouseExpenses> expenseMap = expenses.stream()
                .collect(Collectors.toMap(XqWarehouseExpenses::getKey, p->p));

        // 验证数据是否可以插入数据库
        for (WarehouseExpensesVO dto : dtoList) {
            String warehouseName = dto.getWarehouseId();

            String warehouseId = warehouseMap.get(dto.getWarehouseId());
            if (warehouseId == null) {
                // 加入失败队列
                dto.setFailMsg("仓库名称不存在！");
                failList.add(dto);
                continue;
            }
            dto.setWarehouseId(warehouseId);
            String key = warehouseId + "-" + dto.getOrderNum() + "-" + dto.getProductName() + "-" + dto.getInvoiceNum();
            XqWarehouseExpenses warehouseExpenses = expenseMap.get(key);



            if (warehouseExpenses != null) {
                BeanUtils.copyProperties(dto,warehouseExpenses);


                if (StringUtils.isNotBlank(dto.getDueDate())) {
                    try {
                        Date date = getDate(dto.getDueDate());
                        warehouseExpenses.setDueDate(date);
                    } catch (ParseException e) {
//                        builder.append("到期付款日格式错误").append(",");
                    }
                }

                if (StringUtils.isNotBlank(dto.getAuditDate())) {
                    try {
                        Date date = getDate(dto.getAuditDate());
                        warehouseExpenses.setAuditDate(date);
                    } catch (ParseException e) {
//                        builder.append("财务审核日期格式错误").append(",");
                    }
                }
                if (StringUtils.isNotBlank(dto.getInvoiceDate())) {
                    try {
                        Date date = getDate(dto.getInvoiceDate());
                        warehouseExpenses.setInvoiceDate(date);
                    } catch (ParseException e) {
//                        builder.append("发票日期格式错误").append(",");
                    }
                }

                if (StringUtils.isNotBlank(dto.getPaymentDate())) {
                    try {
                        Date date = getDate(dto.getPaymentDate());
                        warehouseExpenses.setPaymentDate(date);
                    } catch (ParseException e) {
//                        builder.append("实际付款日格式错误").append(",");
                    }
                }

                if (StringUtils.isNotBlank(dto.getApplyForDate())) {
                    try {
                        Date date = getDate(dto.getApplyForDate());
                        warehouseExpenses.setApplyForDate(date);
                    } catch (ParseException e) {
//                        builder.append("申请日期格式错误").append(",");
                    }
                }


                warehouseExpenses.setIzSettle(getIntegerByString(dto.getIzSettle()));
                // 更新导入时间
                warehouseExpenses.setCreateTime(new Date());
                updateList.add(warehouseExpenses);
                continue;
            }
            // 检查订单号是否存在
            List<XqOrder> xqOrders = orderMap.get(dto.getOrderNum());

            if (xqOrders == null || xqOrders.size() == 0) {
                dto.setFailMsg("订单号不存在！");
                dto.setWarehouseId(warehouseName);
                failList.add(dto);
                continue;
            }

            XqWarehouseExpenses xqWarehouseExpenses = BeanCopyUtils.copyBean(dto, XqWarehouseExpenses.class);

            StringBuilder builder = new StringBuilder();

            if (StringUtils.isNotBlank(dto.getDueDate())) {
                try {
                    Date date = getDate(dto.getDueDate());
                    xqWarehouseExpenses.setDueDate(date);
                } catch (ParseException e) {
                    builder.append("到期付款日格式错误").append(",");
                }
            }

            if (StringUtils.isNotBlank(dto.getAuditDate())) {
                try {
                    Date date = getDate(dto.getAuditDate());
                    xqWarehouseExpenses.setAuditDate(date);
                } catch (ParseException e) {
                    builder.append("财务审核日期格式错误").append(",");
                }
            }
            if (StringUtils.isNotBlank(dto.getInvoiceDate())) {
                try {
                    Date date = getDate(dto.getInvoiceDate());
                    xqWarehouseExpenses.setInvoiceDate(date);
                } catch (ParseException e) {
                    builder.append("发票日期格式错误").append(",");
                }
            }

            if (StringUtils.isNotBlank(dto.getPaymentDate())) {
                try {
                    Date date = getDate(dto.getPaymentDate());
                    xqWarehouseExpenses.setPaymentDate(date);
                } catch (ParseException e) {
                    builder.append("实际付款日格式错误").append(",");
                }
            }

            if (StringUtils.isNotBlank(dto.getApplyForDate())) {
                try {
                    Date date = getDate(dto.getApplyForDate());
                    xqWarehouseExpenses.setApplyForDate(date);
                } catch (ParseException e) {
                    builder.append("申请日期格式错误").append(",");
                }
            }

            if (builder.length() > 0) {
                String failMsg = builder.substring(0, builder.lastIndexOf(","));
                dto.setFailMsg(failMsg);
                dto.setWarehouseId(warehouseName);
                failList.add(dto);
                continue;
            }

            xqWarehouseExpenses.setIzSettle(getIntegerByString(dto.getIzSettle()));

            result.add(xqWarehouseExpenses);

        }

        // 保存数据库
        if (result.size() > 0) {
            saveBatch(result);
        }

        // 修改数据
        if(updateList.size() > 0){
            updateBatchById(updateList);
        }


        return failList;

    }

    /**
     * 封装数据
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public List<WarehouseExpensesVO> importExpenses(InputStream inputStream) throws IOException {
        List<WarehouseExpensesVO> expensesList = new ArrayList<>();

        Map<String,WarehouseExpensesVO> map = new HashMap<>();


        //读取excel数据
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 遍历所有 Sheet 页
        for (int sheetIndex = 0; sheetIndex < reader.getSheetCount(); sheetIndex++) {
            // 读取当前 Sheet 页的数据
            reader.setSheet(sheetIndex);
            String sheetName = reader.getSheet().getSheetName();
            for (int rowIndex = 1; rowIndex < reader.getRowCount(); rowIndex++) {
                List<Object> row = reader.readRow(rowIndex);
                if (row.size() > 0) {
                    WarehouseExpensesVO expensesDTO = new WarehouseExpensesVO();

                    expensesDTO.setOrderNum(row.get(0) == null || "".equals(row.get(0)) ? null : row.get(0).toString());
                    expensesDTO.setProductName(row.get(1) == null || "".equals(row.get(1)) ? null : row.get(1).toString());
                    expensesDTO.setInvoiceNum(row.get(2) == null || "".equals(row.get(2)) ? null : row.get(2).toString());
                    expensesDTO.setExpense(row.get(3) == null || "".equals(row.get(3)) ? null : new BigDecimal(row.get(3).toString()));
                    expensesDTO.setInvoiceDate(row.get(4) == null || "".equals(row.get(4)) ? null : row.get(4).toString());
                    expensesDTO.setRemark(row.get(5) == null || "".equals(row.get(5)) ? null : row.get(5).toString());
                    expensesDTO.setDueDate(row.get(6) == null || "".equals(row.get(6)) ? null : row.get(6).toString());
                    expensesDTO.setApplyForDate(row.get(7) == null || "".equals(row.get(7)) ? null : row.get(7).toString());
                    expensesDTO.setPaymentDate(row.get(8) == null || "".equals(row.get(8)) ? null : row.get(8).toString());
                    expensesDTO.setIzSettle(row.get(9) == null || "".equals(row.get(9)) ? null : row.get(9).toString());
                    expensesDTO.setPaymentAmount(row.get(10) == null || "".equals(row.get(10)) ? null : new BigDecimal(row.get(10).toString()));
                    expensesDTO.setRemark2(row.get(11) == null || "".equals(row.get(11)) ? null : row.get(11).toString());
                    expensesDTO.setAuditAmount(row.get(12) == null || "".equals(row.get(12)) ? null : new BigDecimal(row.get(12).toString()));
                    expensesDTO.setAuditDate(row.get(13) == null || "".equals(row.get(13)) ? null : row.get(13).toString());

                    // 设置工作表名称到DTO对象
                    expensesDTO.setWarehouseId(sheetName);
                    String key = expensesDTO.getWarehouseId() + "-" + expensesDTO.getOrderNum() + "-" + expensesDTO.getProductName() + "-" + expensesDTO.getInvoiceNum();
                    map.put(key,expensesDTO);

                }
            }

        }


        inputStream.close();
        reader.close();

        List<WarehouseExpensesVO> collect = map.entrySet().stream().map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return collect;
    }


    private Integer getIntegerByString(String cell) {
        if (cell != null) {
            return "结清".equals(cell) ? 1 : 0;
        }
        return 0;
    }

    final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    private Date getDate(String str) throws ParseException {
        return format.parse(str);
    }

}

