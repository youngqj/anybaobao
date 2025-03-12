package com.interesting.modules.customer.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.entity.SysDictItem;
import com.interesting.business.system.service.ISysDictItemService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.CommonUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.config.FilterContextHandler;
import com.interesting.config.MyException;
import com.interesting.modules.commissioncompany.service.XqCommissionCompanyService;
import com.interesting.modules.customer.dto.AddCustomerImportDTO;
import com.interesting.modules.customer.dto.AddXqCustomerDTO;
import com.interesting.modules.customer.dto.QueryXqCustomerDTO;
import com.interesting.modules.customer.dto.UpdateXqCustomerDTO;
import com.interesting.modules.customer.entity.XqCustomer;
import com.interesting.modules.customer.mapper.XqCustomerMapper;
import com.interesting.modules.customer.service.IXqCustomerService;
import com.interesting.modules.customer.vo.CustomerIdNameAndAddressVO;
import com.interesting.modules.customer.vo.XqCustomerVO;
import com.interesting.modules.freightcost.dto.AddXqFreightCostInfoDTO;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.service.IXqFreightCostInfoService;
import com.interesting.modules.freightcost.vo.XqFreightCostInfoVO;
import com.interesting.modules.postImport.entity.XqPostImport;
import com.interesting.modules.postImport.mapper.XqPostImportMapper;
import com.interesting.modules.reportform.vo.OrderAscrListVO;
import com.interesting.modules.supplier.entity.XqSupplier;
import org.apache.poi.ss.usermodel.*;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 客户表
 * @Author: interesting-boot
 * @Date: 2023-06-01
 * @Version: V1.0
 */
@Service
public class XqCustomerServiceImpl extends ServiceImpl<XqCustomerMapper, XqCustomer> implements IXqCustomerService {

    @Autowired
    private XqCommissionCompanyService xqCommissionCompanyService;

    @Autowired
    private IXqFreightCostInfoService xqFreightCostInfoService;
    @Autowired
    private ISysDictItemService sysDictItemService;
    @Resource
    private XqCustomerMapper xqCustomerMapper;

    @Resource
    private XqPostImportMapper xqPostImportMapper;
    @Value("${jeecg.path.upload}")
    private String uploadFilePath;

    @Override
    public IPage<XqCustomerVO> pageList(Page<XqCustomerVO> page, QueryXqCustomerDTO dto) {
        return this.baseMapper.pageList(page, dto);
    }

    @Override
    public XqCustomerVO getDetailById(String id) {
        XqCustomer byId = this.getById(id);
        XqCustomerVO xqCustomerVO = new XqCustomerVO();
        BeanUtils.copyProperties(byId, xqCustomerVO);

//        // 查询佣金公司
//        List<XqCommissionCompany> list = xqCommissionCompanyService.lambdaQuery()
//                .eq(XqCommissionCompany::getCustomerId, id)
//                .list();
//        List<CommissionCompanyVO> collect = list.stream().map(i -> {
//            CommissionCompanyVO commissionCompanyVO = new CommissionCompanyVO();
//            BeanUtils.copyProperties(i, commissionCompanyVO);
//            return commissionCompanyVO;
//        }).sorted((o1, o2) -> o1.getCreateTime().compareTo(o2.getCreateTime()))
//                .collect(Collectors.toList());
//        xqCustomerVO.setCommissionCompanys(collect);

        List<XqFreightCostInfo> list1 = xqFreightCostInfoService.lambdaQuery()
                .eq(XqFreightCostInfo::getCustomerId, id)
                .isNull(XqFreightCostInfo::getOrderId)
                .list();
        List<XqFreightCostInfoVO> collect1 = list1.stream().map(i -> {
            XqFreightCostInfoVO xqFreightCostInfoVO = new XqFreightCostInfoVO();
            BeanUtils.copyProperties(i, xqFreightCostInfoVO);

            Map<String, SysDictItem> dictItemMap = sysDictItemService.selectMapItemsNameByMainCode("fee_type");
            SysDictItem sysDictItem = dictItemMap.get(xqFreightCostInfoVO.getFeeType());
            xqFreightCostInfoVO.setFeeTypeTxt(sysDictItem.getItemText());

            return xqFreightCostInfoVO;
        }).sorted((o1, o2) -> o1.getCreateTime().compareTo(o2.getCreateTime()))
                .collect(Collectors.toList());
        xqCustomerVO.setFreightCostInfos(collect1);


        return xqCustomerVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveCustomer(AddXqCustomerDTO dto) {
        XqCustomer xqCustomer = new XqCustomer();
        BeanUtils.copyProperties(dto, xqCustomer);
        save(xqCustomer);

//        // 保存佣金公司
//        List<AddCommissionCompanyDTO> commissionCompanys = dto.getCommissionCompanys();
//        List<XqCommissionCompany> collect = commissionCompanys.stream().map(i -> {
//            XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
//            BeanUtils.copyProperties(i, xqCommissionCompany);
//            xqCommissionCompany.setCustomerId(xqCustomer.getId());
//            return xqCommissionCompany;
//        }).collect(Collectors.toList());
//        xqCommissionCompanyService.saveBatch(collect);

        List<AddXqFreightCostInfoDTO> freightCostInfos = dto.getFreightCostInfos();
        if (freightCostInfos != null && freightCostInfos.size() > 0) {
            List<XqFreightCostInfo> collect1 = freightCostInfos.stream().map(i -> {
                XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                BeanUtils.copyProperties(i, xqFreightCostInfo);

                xqFreightCostInfo.setIzDomestic(0);
                xqFreightCostInfo.setCustomerId(xqCustomer.getId());
                xqFreightCostInfo.setId(null);
                if (xqFreightCostInfo.getPrice() == null || xqFreightCostInfo.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                    xqFreightCostInfo.setIzDefaultColor(2);
                } else {
                    xqFreightCostInfo.setIzDefaultColor(0);
                }
                return xqFreightCostInfo;
            }).collect(Collectors.toList());
            xqFreightCostInfoService.saveBatch(collect1);
        }


        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCustomerById(UpdateXqCustomerDTO dto) {
        String id = dto.getId();
        XqCustomer byId = getById(id);
        if (byId == null) {
            throw new InterestingBootException("该记录不存在");
        }
        BeanUtils.copyProperties(dto, byId);
        updateById(byId);

        // 编辑佣金公司
//        List<AddCommissionCompanyDTO> commissionCompanys = dto.getCommissionCompanys();
//        commissionCompanys.forEach(i -> {
//            if (StringUtils.isBlank(i.getId())) {
//                i.setId(UUID.randomUUID().toString());
//            }
//        });
//        Map<String, AddCommissionCompanyDTO> collect =
//                commissionCompanys.stream()
//                        .collect(Collectors.toMap(AddCommissionCompanyDTO::getId, Function.identity()));
//
//        List<XqCommissionCompany> toUpdateComs = new ArrayList<>();
//        List<String> toDeleteIds = new ArrayList<>();
//        List<XqCommissionCompany> toAddComs = new ArrayList<>();
//
//        List<XqCommissionCompany> list = xqCommissionCompanyService.lambdaQuery()
//                .eq(XqCommissionCompany::getCustomerId, id).list();
//
//        for (XqCommissionCompany xqCommissionCompany : list) {
//            AddCommissionCompanyDTO addCommissionCompanyDTO = collect.get(xqCommissionCompany.getId());
//
//            if (addCommissionCompanyDTO != null) {
//                BeanUtils.copyProperties(addCommissionCompanyDTO, xqCommissionCompany);
//                toUpdateComs.add(xqCommissionCompany);
//                collect.remove(xqCommissionCompany.getId());
//            } else {
//                toDeleteIds.add(xqCommissionCompany.getId());
//            }
//        }
//
//        xqCommissionCompanyService.updateBatchById(toUpdateComs);
//        xqCommissionCompanyService.removeByIds(toDeleteIds);
//
//        for (AddCommissionCompanyDTO value : collect.values()) {
//            XqCommissionCompany xqCommissionCompany = new XqCommissionCompany();
//            BeanUtils.copyProperties(value, xqCommissionCompany);
//            xqCommissionCompany.setId(null);
//            xqCommissionCompany.setCustomerId(byId.getId());
//            toAddComs.add(xqCommissionCompany);
//        }
//
//        xqCommissionCompanyService.saveBatch(toAddComs);


        List<AddXqFreightCostInfoDTO> freightCostInfos = dto.getFreightCostInfos();


        if (freightCostInfos != null && freightCostInfos.size() > 0) {
            freightCostInfos.forEach(i -> {
                if (StringUtils.isBlank(i.getId())) {
                    i.setId(UUID.randomUUID().toString());
                }
            });

            Map<String, AddXqFreightCostInfoDTO> collect1 =
                    freightCostInfos.stream()
                            .collect(Collectors.toMap(AddXqFreightCostInfoDTO::getId, Function.identity()));

            List<XqFreightCostInfo> toUpdateCostInfos = new ArrayList<>();
            List<String> toDeleteIds2 = new ArrayList<>();
            List<XqFreightCostInfo> toAddCostInfos = new ArrayList<>();

            List<XqFreightCostInfo> list2 = xqFreightCostInfoService.lambdaQuery()
                    .eq(XqFreightCostInfo::getCustomerId, id).isNull(XqFreightCostInfo::getOrderId).list();

            for (XqFreightCostInfo xqFreightCostInfo : list2) {
                AddXqFreightCostInfoDTO addXqFreightCostInfoDTO = collect1.get(xqFreightCostInfo.getId());

                if (addXqFreightCostInfoDTO != null) {
                    BeanUtils.copyProperties(addXqFreightCostInfoDTO, xqFreightCostInfo);
                    if (xqFreightCostInfo.getPrice() == null || xqFreightCostInfo.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                        xqFreightCostInfo.setIzDefaultColor(2);
                    } else {
                        xqFreightCostInfo.setIzDefaultColor(0);
                    }
                    toUpdateCostInfos.add(xqFreightCostInfo);
                    collect1.remove(xqFreightCostInfo.getId());
                } else {
                    toDeleteIds2.add(xqFreightCostInfo.getId());
                }
            }

            xqFreightCostInfoService.updateBatchById(toUpdateCostInfos);
            xqFreightCostInfoService.removeByIds(toDeleteIds2);

            for (AddXqFreightCostInfoDTO value : collect1.values()) {
                XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                BeanUtils.copyProperties(value, xqFreightCostInfo);
                xqFreightCostInfo.setId(null);
                xqFreightCostInfo.setCustomerId(byId.getId());
                xqFreightCostInfo.setIzDomestic(0);//国外
                toAddCostInfos.add(xqFreightCostInfo);
            }

            xqFreightCostInfoService.saveBatch(toAddCostInfos);
        }

        return true;
    }

    @Override
    public void exportTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        //8
        int num = 12;
        for (int i = 0; i < num; i++) {
            writer.setColumnWidth(i, 30);
            //writer.setRowHeight(i,10);
        }
        writer.addHeaderAlias("cou", "序号*");
        writer.addHeaderAlias("customerName", "客户名称*");
        writer.addHeaderAlias("phone", "联系电话*");
        writer.addHeaderAlias("fccFee", "国外清关费代理商");
        writer.addHeaderAlias("fccCurrency", "国外清关费币种");
        writer.addHeaderAlias("fccPrice", "国外清关费价格");
        writer.addHeaderAlias("ftFee", "国外卡车费代理商");
        writer.addHeaderAlias("ftCurrency", "国外卡车费币种");
        writer.addHeaderAlias("ftPrice", "国外卡车费价格");
        writer.addHeaderAlias("acFee", "额外费用1代理商");
        writer.addHeaderAlias("acCurrency", "额外费用币种");
        writer.addHeaderAlias("acPrice", "额外费用价格");
        writer.setOnlyAlias(true);
        Map<String, Object> item = new HashMap<>();
        item.put("cou", "");
        item.put("customerName", "");
        item.put("phone", "");
        item.put("fccFee", "");
        item.put("fccCurrency", "");
        item.put("fccPrice", "");
        item.put("ftFee", "");
        item.put("ftCurrency", "");
        item.put("ftPrice", "");
        item.put("acFee", "");
        item.put("acCurrency", "");
        item.put("acPrice", "");
        list.add(item);

        //单元格式 文本
        StyleSet style = writer.getStyleSet();
        CellStyle cellStyle = style.getCellStyleForNumber();
        DataFormat format = writer.getWorkbook().createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        writer.setStyleSet(style);
        Font font = writer.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        style.setFont(font, false);//设置字体 大小 //标题是否适用
        font = writer.createFont();
        font.setFontHeightInPoints((short) 13);
        style.setFont(font, true);
        //response为httpservletResponse对象
        String excelName = "客户模板";
        writer.merge(num - 1, excelName);
        writer.write(list, true);
        writer.renameSheet(0, "主要数据");
        // 设置当前行
        writer.setCurrentRow(30);

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(11,
                "所有带 * 的为必填项，货运信息为非必填项）\n" +
                        "> 币种：|人民币:CNY|美元:USD|欧元:EUR|，填写货币编码。例如:CNY\n" +
                        "********** 导入数据时将此说明删除 **********", true
        );

        writer.setRowHeight(30, 180);
        StyleSet styleSet = writer.getStyleSet();
        styleSet.setAlign(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        CellStyle headCellStyle = styleSet.getHeadCellStyle();
        headCellStyle.setWrapText(true);
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((excelName + ".xls").getBytes(), "ISO-8859-1"));
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

    @Override
    public Result<?> importCustomer(MultipartFile file) throws Exception {

        String fileType = file.getContentType();
        if (fileType == null) {
            throw new MyException("未知文件类型");
        }
        Map<String, Object> param = new HashMap<>();
        String result = "";
        String failUrl = null;
        //读取excel数据
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<List<Object>> lists = reader.read(2);
        List<AddCustomerImportDTO> readAll = new ArrayList();

        for (List<Object> row : lists) {
            AddCustomerImportDTO dto = new AddCustomerImportDTO();
            dto.setCou(StringUtils.isNotBlank(row.get(0).toString()) ? Integer.parseInt(row.get(0).toString()) : null);
            dto.setName(row.get(1).toString());
            dto.setPhone(row.get(2).toString());
            List<AddXqFreightCostInfoDTO> freightList = new ArrayList<>();
            AddXqFreightCostInfoDTO freight1 = new AddXqFreightCostInfoDTO();
            freight1.setAgent(row.get(3) == null ? "" : row.get(3).toString());
            freight1.setFeeType("gwqgf");
            freight1.setCurrency(row.get(4) == null ? "" : row.get(4).toString());
            freight1.setPrice(row.get(5).toString().equals("") ? null : new BigDecimal(row.get(5).toString()));
            freightList.add(freight1);
            AddXqFreightCostInfoDTO freight2 = new AddXqFreightCostInfoDTO();
            freight2.setAgent(row.get(6) == null ? "" : row.get(6).toString());
            freight2.setCurrency(row.get(7) == null ? "" : row.get(7).toString());
            freight2.setPrice(row.get(8).toString().equals("") ? null : new BigDecimal(row.get(8).toString()));
            freight2.setFeeType("gwkcf");
            freightList.add(freight2);
            AddXqFreightCostInfoDTO freight3 = new AddXqFreightCostInfoDTO();
            freight3.setAgent(row.get(9) == null ? "" : row.get(9).toString());
            freight3.setCurrency(row.get(10) == null ? "" : row.get(10).toString());
            freight3.setPrice(row.get(11).toString().equals("") ? null : new BigDecimal(row.get(11).toString()));
            freight3.setFeeType("ewfy1");
            freightList.add(freight3);
            dto.setFreightCostInfos(freightList);
            readAll.add(dto);
            //            ****类似一一对应****
        }

        if (CollectionUtil.isEmpty(readAll)) {
            throw new MyException("未找到需要导入的重点人员数据");
        }

        //错误数据
        List<AddCustomerImportDTO> errorS = new ArrayList<>();
        //正确数据
        List<XqSupplier> successS = new ArrayList<>();
        //多线程导入
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 1000,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        for (AddCustomerImportDTO dto : readAll) {
            executorService.execute(() -> {
                String item = "序号" + dto.getCou();
                if (StringUtils.isBlank(dto.getName()) ||
                        StringUtils.isBlank(dto.getPhone())) {
                    dto.setErrorInfo(item + "所有必填内容不能为空");
                    errorS.add(dto);
                    return;
                }
                XqCustomer customer = new XqCustomer();
                customer.setName(dto.getName());
                customer.setPhone(dto.getPhone());
                customer.setIzDelete(0);
                baseMapper.insert(customer);
                //数据模块
                List<XqFreightCostInfo> freightList = new ArrayList<>();
                List<AddXqFreightCostInfoDTO> freightDatas = dto.getFreightCostInfos();
                for (AddXqFreightCostInfoDTO dto1 : freightDatas) {
                    XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                    BeanUtils.copyProperties(dto1, xqFreightCostInfo);
                    xqFreightCostInfo.setIzDomestic(1);
                    xqFreightCostInfo.setCustomerId(customer.getId());
                    xqFreightCostInfo.setId(null);
                    if (xqFreightCostInfo.getPrice() == null || xqFreightCostInfo.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                        xqFreightCostInfo.setIzDefaultColor(2);
                    } else {
                        xqFreightCostInfo.setIzDefaultColor(0);
                    }
                    freightList.add(xqFreightCostInfo);
                }
                xqFreightCostInfoService.saveBatch(freightList);
                //往log里面塞一条(自己先流转给自己供显示流转按钮用)
            });
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (CollectionUtil.isNotEmpty(errorS)) {
            failUrl = getFailList(errorS);
            param.put("fail", errorS.size());
            param.put("data", failUrl);
            result = errorS.stream().map(AddCustomerImportDTO::getErrorInfo).collect(Collectors.joining(","));
            // 导入表记录一条信息
            XqPostImport xqPostImport = new XqPostImport();
            xqPostImport.setImportType("客户导入");
            xqPostImport.setImportPerson(FilterContextHandler.getUserId());
            xqPostImport.setImportTime(new Date());
            xqPostImport.setImportResult(result);
            xqPostImport.setImportCount(readAll.size());
            xqPostImport.setDownloadUrl(failUrl);
            xqPostImportMapper.insert(xqPostImport);
            return Result.error(result, param);
        } else {
            result = "文件导入成功";
        }
        return Result.OK(result, param);
    }

    @Override
    public List<CustomerIdNameAndAddressVO> listCustomer() {

        return xqCustomerMapper.listCustomer();
    }

    @Override
    public void customerExport(Page<XqCustomerVO> page, QueryXqCustomerDTO dto, HttpServletResponse response) {

        IPage<XqCustomerVO> xqCustomerVOIPage = this.baseMapper.pageList(page, dto);
        List<XqCustomerVO> records = xqCustomerVOIPage.getRecords();


        int currentSerialNumber = 0;

        for (XqCustomerVO record : records) {

            currentSerialNumber++;

            record.setSerialNumber(currentSerialNumber);
        }

        if (StringUtils.isBlank(dto.getExportColumn())) {
            dto.setExportColumn("serialNumber,name,abbr,taxNum,contactor,email,phone,address,bankDeposit,bankCardNumber,remark");
        }
        CommonUtils.export(dto.getExportColumn(), records, response, XqCustomerVO.class, null, null, false);

    }


    public String getFailList(List<AddCustomerImportDTO> failList) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");// 格式化为年月
        String date = sf.format(new Date());

        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        //8
        int num = 13;
        for (int i = 0; i < num; i++) {
            writer.setColumnWidth(i, 20);
            //writer.setRowHeight(i,10);
        }
        writer.addHeaderAlias("cou", "序号*");
        writer.addHeaderAlias("customerName", "客户名称*");
        writer.addHeaderAlias("phone", "联系电话*");
        writer.addHeaderAlias("fccFee", "国外清关费代理商");
        writer.addHeaderAlias("fccCurrency", "国外清关费币种");
        writer.addHeaderAlias("fccPrice", "国外清关费价格");
        writer.addHeaderAlias("ftFee", "国外卡车费代理商");
        writer.addHeaderAlias("ftCurrency", "国外卡车费币种");
        writer.addHeaderAlias("ftPrice", "国外卡车费价格");
        writer.addHeaderAlias("acFee", "额外费用1代理商");
        writer.addHeaderAlias("acCurrency", "额外费用1币种");
        writer.addHeaderAlias("acPrice", "额外费用1价格");
        writer.addHeaderAlias("errorInfo", "错误详情");


        writer.setOnlyAlias(true);
        int count = 1;
        if (failList != null && !failList.isEmpty()) {
            for (AddCustomerImportDTO dto : failList) {
                Map<String, Object> item = new HashMap<>();
                item.put("cou", count);
                item.put("customerName", dto.getName());
                item.put("phone", dto.getPhone());
                item.put("fccFee", dto.getFreightCostInfos().get(0).getAgent() == null ? "" : dto.getFreightCostInfos().get(0).getAgent());
                item.put("fccCurrency", dto.getFreightCostInfos().get(0).getCurrency() == null ? "" : dto.getFreightCostInfos().get(0).getCurrency());
                item.put("fccPrice", dto.getFreightCostInfos().get(0).getPrice() == null ? "" : dto.getFreightCostInfos().get(0).getPrice());
                item.put("ftFee", dto.getFreightCostInfos().get(1).getAgent() == null ? "" : dto.getFreightCostInfos().get(1).getAgent());
                item.put("ftCurrency", dto.getFreightCostInfos().get(1).getCurrency() == null ? "" : dto.getFreightCostInfos().get(1).getCurrency());
                item.put("ftPrice", dto.getFreightCostInfos().get(1).getPrice() == null ? "" : dto.getFreightCostInfos().get(1).getPrice());
                item.put("acFee", dto.getFreightCostInfos().get(2).getAgent() == null ? "" : dto.getFreightCostInfos().get(2).getAgent());
                item.put("acCurrency", dto.getFreightCostInfos().get(3).getCurrency() == null ? "" : dto.getFreightCostInfos().get(2).getCurrency());
                item.put("acPrice", dto.getFreightCostInfos().get(3).getPrice() == null ? "" : dto.getFreightCostInfos().get(2).getPrice());
                item.put("errorInfo", dto.getErrorInfo());
                list.add(item);
                count = count + 1;
            }
        }

        StyleSet style = writer.getStyleSet();
        Font font = writer.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        style.setFont(font, false);//设置字体 大小 //标题是否适用
        font = writer.createFont();
        font.setFontHeightInPoints((short) 13);
        style.setFont(font, true);
        Font redFont = writer.createFont();
        redFont.setColor(Font.COLOR_RED);
        CellStyle cellStyle = writer.getCellStyle();
        cellStyle.setFont(redFont);
        writer.setRowStyle(8, cellStyle);
        //response为httpservletResponse对象
        String excelName = "供应商导入失败表-" + System.currentTimeMillis() + ".xls";
        writer.merge(num - 1, excelName);
        writer.write(list, true);
        Calendar cal = Calendar.getInstance();
        String ctxPath = uploadFilePath + "//" + cal.get(Calendar.YEAR) + "//" + (cal.get(Calendar.MONDAY) + 1) + "//" + cal.get(Calendar.DATE) + "//";
        String filePath = ctxPath + "/temp/" + date;
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

}
