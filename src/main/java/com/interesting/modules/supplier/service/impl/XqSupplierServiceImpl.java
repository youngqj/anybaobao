package com.interesting.modules.supplier.service.impl;

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
import com.interesting.modules.customer.vo.XqCustomerVO;
import com.interesting.modules.freightcost.dto.AddXqFreightCostInfoDTO;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.service.IXqFreightCostInfoService;
import com.interesting.modules.freightcost.vo.XqFreightCostInfoVO;
import com.interesting.modules.postImport.entity.XqPostImport;
import com.interesting.modules.postImport.mapper.XqPostImportMapper;
import com.interesting.modules.supplier.dto.AddSupplierImportDTO;
import com.interesting.modules.supplier.dto.AddXqSupplierDTO;
import com.interesting.modules.supplier.dto.QueryXqSupplierDTO;
import com.interesting.modules.supplier.dto.UpdateXqSupplierDTO;
import com.interesting.modules.supplier.entity.XqSupplier;
import com.interesting.modules.supplier.mapper.XqSupplierMapper;
import com.interesting.modules.supplier.service.IXqSupplierService;
import com.interesting.modules.supplier.vo.XqSupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.poi.ss.usermodel.*;
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
 * @Description: 供应商表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Service
public class XqSupplierServiceImpl extends ServiceImpl<XqSupplierMapper, XqSupplier> implements IXqSupplierService {
    @Autowired
    private IXqFreightCostInfoService xqFreightCostInfoService;
    @Autowired
    private ISysDictItemService sysDictItemService;
    @Resource
    private XqPostImportMapper xqPostImportMapper;
    @Value("${jeecg.path.upload}")
    private String uploadFilePath;
    @Override
    public IPage<XqSupplierVO> pageList(Page<XqSupplierVO> page, QueryXqSupplierDTO dto) {
        return this.baseMapper.pageList(page, dto);
    }

    @Override
    public XqSupplierVO getCustomerById(String id) {
        XqSupplier byId = this.getById(id);
        XqSupplierVO xqSupplierVO = new XqSupplierVO();
        BeanUtils.copyProperties(byId, xqSupplierVO);

        List<XqFreightCostInfo> list1 = xqFreightCostInfoService.lambdaQuery()
                .eq(XqFreightCostInfo::getSupplierId, id)
                .isNull(XqFreightCostInfo::getOrderId)
                .list();
        List<XqFreightCostInfoVO> collect1 = list1.stream().map(i -> {
                    XqFreightCostInfoVO xqFreightCostInfoVO = new XqFreightCostInfoVO();
                    BeanUtils.copyProperties(i, xqFreightCostInfoVO);

                    Map<String, SysDictItem> dictItemMap = sysDictItemService.selectMapItemsNameByMainCode("fee_type");
                    SysDictItem sysDictItem = dictItemMap.get(xqFreightCostInfoVO.getFeeType());
                    if(sysDictItem != null){
                        xqFreightCostInfoVO.setFeeTypeTxt(sysDictItem.getItemText());
                    }


                    return xqFreightCostInfoVO;
                }).sorted((o1, o2) -> o1.getCreateTime().compareTo(o2.getCreateTime()))
                .collect(Collectors.toList());
        xqSupplierVO.setFreightCostInfos(collect1);

        return xqSupplierVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveSupplier(AddXqSupplierDTO dto) {
        XqSupplier xqSupplier = new XqSupplier();
        BeanUtils.copyProperties(dto, xqSupplier);
        this.save(xqSupplier);

        List<AddXqFreightCostInfoDTO> freightCostInfos = dto.getFreightCostInfos();
        if (freightCostInfos != null && freightCostInfos.size() > 0) {
            List<XqFreightCostInfo> collect = freightCostInfos.stream().map(i -> {
                XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                BeanUtils.copyProperties(i, xqFreightCostInfo);
                xqFreightCostInfo.setSupplierId(xqSupplier.getId());
                xqFreightCostInfo.setId(null);
                if (xqFreightCostInfo.getPrice()==null||xqFreightCostInfo.getPrice().compareTo(BigDecimal.ZERO)==0){
                    xqFreightCostInfo.setIzDefaultColor(2);
                }else{
                    xqFreightCostInfo.setIzDefaultColor(0);
                }
                xqFreightCostInfo.setIzDomestic(1);
                return xqFreightCostInfo;
            }).collect(Collectors.toList());
            xqFreightCostInfoService.saveBatch(collect);
        }

        return true;
    }

    @Override
    public boolean updateSupplierById(UpdateXqSupplierDTO dto) {
        String id = dto.getId();
        XqSupplier byId = this.getById(id);
        if (byId == null) {
            throw new InterestingBootException("该记录不存在");
        }
        BeanUtils.copyProperties(dto, byId);
        this.updateById(byId);

        List<AddXqFreightCostInfoDTO> freightCostInfos = dto.getFreightCostInfos();
        if (freightCostInfos != null && freightCostInfos.size() > 0) {
            List<XqFreightCostInfo> collect = freightCostInfos.stream().map(i -> {
                XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                BeanUtils.copyProperties(i, xqFreightCostInfo);
                return xqFreightCostInfo;
            }).collect(Collectors.toList());
            xqFreightCostInfoService.updateBatchById(collect);
        }


//        List<AddXqFreightCostInfoDTO> freightCostInfos = dto.getFreightCostInfos();
//        if (freightCostInfos != null && freightCostInfos.size() > 0) {
//            freightCostInfos.forEach(i -> {
//                if (StringUtils.isBlank(i.getId())) {
//                    i.setId(UUID.randomUUID().toString());
//                }
//            });
//
//
//            Map<String, AddXqFreightCostInfoDTO> collect =
//                    freightCostInfos.stream()
//                            .collect(Collectors.toMap(AddXqFreightCostInfoDTO::getId, Function.identity()));
//
//            List<XqFreightCostInfo> toUpdateInfos = new ArrayList<>();
//            List<String> toDeleteIds = new ArrayList<>();
//            List<XqFreightCostInfo> toAddInfos = new ArrayList<>();
//
//            List<XqFreightCostInfo> list = xqFreightCostInfoService.lambdaQuery()
//                    .eq(XqFreightCostInfo::getSupplierId, id).list();
//
//            for (XqFreightCostInfo xqFreightCostInfo : list) {
//                AddXqFreightCostInfoDTO addXqFreightCostInfoDTO = collect.get(xqFreightCostInfo.getId());
//
//                if (addXqFreightCostInfoDTO != null) {
//                    BeanUtils.copyProperties(addXqFreightCostInfoDTO, xqFreightCostInfo);
//                    if (xqFreightCostInfo.getPrice()==null||xqFreightCostInfo.getPrice().compareTo(BigDecimal.ZERO)==0){
//                        xqFreightCostInfo.setIzDefaultColor(2);
//                    }else{
//                        xqFreightCostInfo.setIzDefaultColor(0);
//                    }
//                    toUpdateInfos.add(xqFreightCostInfo);
//                    collect.remove(xqFreightCostInfo.getId());
//                } else {
//                    toDeleteIds.add(xqFreightCostInfo.getId());
//                }
//            }
//
//            xqFreightCostInfoService.updateBatchById(toUpdateInfos);
//            xqFreightCostInfoService.removeByIds(toDeleteIds);
//
//            for (AddXqFreightCostInfoDTO value : collect.values()) {
//                XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
//                BeanUtils.copyProperties(value, xqFreightCostInfo);
//                xqFreightCostInfo.setId(null);
//                xqFreightCostInfo.setSupplierId(byId.getId());
//                xqFreightCostInfo.setIzDomestic(1);
//                toAddInfos.add(xqFreightCostInfo);
//            }
//
//            xqFreightCostInfoService.saveBatch(toAddInfos);
//        }

        return true;
    }

    @Override
    public void exportTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        //8
        int num = 16;
        for (int i = 0; i < num; i++) {
            writer.setColumnWidth(i, 30);
            //writer.setRowHeight(i,10);
        }
        writer.addHeaderAlias("cou", "序号*");
        writer.addHeaderAlias("supplierName", "供应商名称*");
        writer.addHeaderAlias("supplierType", "供应商类型*");
        writer.addHeaderAlias("phone", "联系电话*");
        writer.addHeaderAlias("dltFee", "国内陆运费代理商");
        writer.addHeaderAlias("dltCurrency", "国内陆运费币种");
        writer.addHeaderAlias("dltPrice", "国内陆运费价格");
        writer.addHeaderAlias("dcdFee", "国内报关费代理商");
        writer.addHeaderAlias("dcdCurrency", "国内报关费币种");
        writer.addHeaderAlias("dcdPrice", "国内报关费价格");
        writer.addHeaderAlias("dmfFee", "国内舱单费代理商");
        writer.addHeaderAlias("dmfCurrency", "国内舱单费币种");
        writer.addHeaderAlias("dmfPrice", "国内舱单费价格");
        writer.addHeaderAlias("dpmFee", "国内港杂费代理商");
        writer.addHeaderAlias("dpmCurrency", "国内港杂费币种");
        writer.addHeaderAlias("dpmPrice", "国内港杂费价格");
        writer.setOnlyAlias(true);
        Map<String, Object> item = new HashMap<>();
        item.put("cou", "");
        item.put("supplierName", "");
        item.put("supplierType", "");
        item.put("phone", "");
        item.put("dltFee", "");
        item.put("dltCurrency", "");
        item.put("dltPrice", "");
        item.put("dcdFee", "");
        item.put("dcdCurrency", "");
        item.put("dcdPrice", "");
        item.put("dmfFee", "");
        item.put("dmfCurrency", "");
        item.put("dmfPrice", "");
        item.put("dpmFee", "");
        item.put("dpmCurrency", "");
        item.put("dpmPrice", "");
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
        String excelName = "供应商模板";
        writer.merge(num - 1, excelName);
        writer.write(list, true);
        writer.renameSheet(0, "主要数据");
        // 设置当前行
        writer.setCurrentRow(30);

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(15,
                "所有带 * 的为必填项。供应商类型是辅料供应商的时候，货运信息为非必填项）\n" +
                        "> 供应商类型：原料供应商填写1、辅料供应商填写2\n" +
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
    public Result<?> importSupplier(MultipartFile file) throws Exception {
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
        List<AddSupplierImportDTO> readAll = new ArrayList();

        for (List<Object> row : lists) {
            AddSupplierImportDTO dto = new AddSupplierImportDTO();
            dto.setCou(StringUtils.isNotBlank(row.get(0).toString())?Integer.parseInt(row.get(0).toString()):null);
            dto.setName(row.get(1).toString());
            dto.setType(row.get(2).toString());
            dto.setPhone(row.get(3).toString());
            List<AddXqFreightCostInfoDTO> freightList=new ArrayList<>();
            if (dto.getType().equals("1")){
//                if (StringUtils.isBlank(row.get(4).toString())||StringUtils.isBlank(row.get(7).toString())||StringUtils.isBlank(row.get(10).toString())){
//                    throw new MyException("供应商类型为原料供应商的时候，代理商信息为必填！");
//                }
                AddXqFreightCostInfoDTO freight1=new AddXqFreightCostInfoDTO();
                freight1.setAgent(row.get(4)==null?"":row.get(4).toString());
                freight1.setFeeType("gnlyf");
                freight1.setCurrency(row.get(5)==null?"":row.get(5).toString());
                freight1.setPrice(row.get(6).toString().equals("")?null:new BigDecimal(row.get(6).toString()));
                freightList.add(freight1);
                AddXqFreightCostInfoDTO freight2=new AddXqFreightCostInfoDTO();
                freight2.setAgent(row.get(7)==null?"":row.get(7).toString());
                freight2.setCurrency(row.get(8)==null?"":row.get(8).toString());
                freight2.setPrice(row.get(9).toString().equals("")?null:new BigDecimal(row.get(9).toString()));
                freight2.setFeeType("gnbgf");
                freightList.add(freight2);
                AddXqFreightCostInfoDTO freight3=new AddXqFreightCostInfoDTO();
                freight3.setAgent(row.get(10)==null?"":row.get(10).toString());
                freight3.setCurrency(row.get(11)==null?"":row.get(11).toString());
                freight3.setPrice(row.get(12).toString().equals("")?null:new BigDecimal(row.get(12).toString()));
                freight3.setFeeType("gncdf");
                freightList.add(freight3);
                AddXqFreightCostInfoDTO freight4=new AddXqFreightCostInfoDTO();
                freight4.setAgent(row.get(13)==null?"":row.get(13).toString());
                freight4.setCurrency(row.get(14)==null?"":row.get(14).toString());
                freight4.setPrice(row.get(15).toString().equals("")?null:new BigDecimal(row.get(15).toString()));
                freight4.setFeeType("gngzf");
                freightList.add(freight4);
                dto.setFreightCostInfos(freightList);
            }
            readAll.add(dto);
            //            ****类似一一对应****
        }

        // List<InsertInvolveImportDTO> readAll = reader.readAll(InsertInvolveImportDTO.class);

        if (CollectionUtil.isEmpty(readAll)) {
            throw new MyException("未找到需要导入的供应商数据");
        }

        //错误数据
        List<AddSupplierImportDTO> errorS = new ArrayList<>();
        //正确数据
        List<XqSupplier> successS = new ArrayList<>();
        //多线程导入
        ExecutorService executorService = new ThreadPoolExecutor(4, 4, 1000,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        for (AddSupplierImportDTO dto : readAll) {
            executorService.execute(() -> {
                String item = "序号" + dto.getCou();
                if (StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getType()) ||
                        StringUtils.isBlank(dto.getPhone())) {
                    dto.setErrorInfo(item + "所有必填内容不能为空");
                    errorS.add(dto);
                    return;
                }
                XqSupplier supplier = new XqSupplier();
                supplier.setName(dto.getName());
                supplier.setType(dto.getType());
                supplier.setPhone(dto.getPhone());
                supplier.setIzDelete(0);
                baseMapper.insert(supplier);
                //数据模块
                List<XqFreightCostInfo> freightList=new ArrayList<>();
                List<AddXqFreightCostInfoDTO> freightDatas=dto.getFreightCostInfos();
                for (AddXqFreightCostInfoDTO dto1 :freightDatas){
                    XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                    BeanUtils.copyProperties(dto1, xqFreightCostInfo);
                    xqFreightCostInfo.setIzDomestic(1);
                    xqFreightCostInfo.setSupplierId(supplier.getId());
                    xqFreightCostInfo.setId(null);
                    if (xqFreightCostInfo.getPrice()==null||xqFreightCostInfo.getPrice().compareTo(BigDecimal.ZERO)==0){
                        xqFreightCostInfo.setIzDefaultColor(2);
                    }else{
                        xqFreightCostInfo.setIzDefaultColor(0);
                    }
                    freightList.add(xqFreightCostInfo);
                }
                xqFreightCostInfoService.saveBatch(freightList);
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
            result = errorS.stream().map(AddSupplierImportDTO::getErrorInfo).collect(Collectors.joining(","));
            // 导入表记录一条信息
            XqPostImport xqPostImport = new XqPostImport();
            xqPostImport.setImportType("供应商导入");
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
        return Result.OK(result, param);    }

    @Override
    public void supplierExport(Page<XqSupplierVO> page, QueryXqSupplierDTO dto, HttpServletResponse response) {
        IPage<XqSupplierVO> xqSupplierVOIPage = this.baseMapper.pageList(page, dto);

        List<XqSupplierVO> records = xqSupplierVOIPage.getRecords();

        int currentSerialNumber = 0;

        for (XqSupplierVO record : records) {

            currentSerialNumber++;

            record.setSerialNumber(currentSerialNumber);
        }

        if (StringUtils.isBlank(dto.getExportColumn())) {
            dto.setExportColumn("serialNumber,name,abbr,taxNum,taxRate,contactor,email,phone,address,bankDeposit,bankCardNumber,remark");
        }
        CommonUtils.export(dto.getExportColumn(), records, response, XqSupplierVO.class, null, null, false);

    }


    public String getFailList(List<AddSupplierImportDTO> failList) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");// 格式化为年月
        String date = sf.format(new Date());

        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        //8
        int num = 17;
        for (int i = 0; i < num; i++) {
            writer.setColumnWidth(i, 20);
            //writer.setRowHeight(i,10);
        }
        writer.addHeaderAlias("cou", "序号*");
        writer.addHeaderAlias("supplierName", "供应商名称*");
        writer.addHeaderAlias("supplierType", "供应商类型*");
        writer.addHeaderAlias("phone", "联系电话*");
        writer.addHeaderAlias("dltFee", "国内陆运费代理商");
        writer.addHeaderAlias("dltCurrency", "国内陆运费币种");
        writer.addHeaderAlias("dltPrice", "国内陆运费价格");
        writer.addHeaderAlias("dcdFee", "国内报关费代理商");
        writer.addHeaderAlias("dcdCurrency", "国内报关费币种");
        writer.addHeaderAlias("dcdPrice", "国内陆运费价格");
        writer.addHeaderAlias("dmfFee", "国内舱单费代理商");
        writer.addHeaderAlias("dmfCurrency", "国内舱单费币种");
        writer.addHeaderAlias("dmfPrice", "国内舱单费价格");
        writer.addHeaderAlias("dpmFee", "国内港杂费代理商");
        writer.addHeaderAlias("dpmCurrency", "国内港杂费币种");
        writer.addHeaderAlias("dpmPrice", "国内港杂费价格");
        writer.addHeaderAlias("errorInfo", "错误详情");


        writer.setOnlyAlias(true);
        int count = 1;
        if (failList != null && !failList.isEmpty()) {
            for (AddSupplierImportDTO dto : failList) {
                Map<String, Object> item = new HashMap<>();
                item.put("cou", count);
                item.put("cou", dto.getName());
                item.put("supplierName",dto.getName());
                item.put("supplierType", dto.getType());
                item.put("phone", dto.getPhone());
                item.put("dltFee", dto.getFreightCostInfos().get(0).getAgent()==null?"":dto.getFreightCostInfos().get(0).getAgent());
                item.put("dltCurrency", dto.getFreightCostInfos().get(0).getCurrency()==null?"":dto.getFreightCostInfos().get(0).getCurrency());
                item.put("dltPrice", dto.getFreightCostInfos().get(0).getPrice()==null?"": dto.getFreightCostInfos().get(0).getPrice());
                item.put("dcdFee", dto.getFreightCostInfos().get(1).getAgent()==null?"":dto.getFreightCostInfos().get(1).getAgent());
                item.put("dcdCurrency", dto.getFreightCostInfos().get(1).getCurrency()==null?"":dto.getFreightCostInfos().get(1).getCurrency());
                item.put("dcdPrice", dto.getFreightCostInfos().get(1).getPrice()==null?"":dto.getFreightCostInfos().get(1).getPrice());
                item.put("dmfFee", dto.getFreightCostInfos().get(2).getAgent()==null?"":dto.getFreightCostInfos().get(2).getAgent());
                item.put("dmfCurrency", dto.getFreightCostInfos().get(3).getCurrency()==null?"":dto.getFreightCostInfos().get(2).getCurrency());
                item.put("dmfPrice",dto.getFreightCostInfos().get(3).getPrice()==null?"":dto.getFreightCostInfos().get(2).getPrice());
                item.put("dpmFee", dto.getFreightCostInfos().get(2).getAgent()==null?"":dto.getFreightCostInfos().get(3).getAgent());
                item.put("dpmCurrency", dto.getFreightCostInfos().get(3).getCurrency()==null?"":dto.getFreightCostInfos().get(3).getCurrency());
                item.put("dpmPrice",dto.getFreightCostInfos().get(3).getPrice()==null?"":dto.getFreightCostInfos().get(3).getPrice());
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
