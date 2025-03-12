package com.interesting.modules.warehouse.service.impl;

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
import com.interesting.common.util.StringUtils;
import com.interesting.config.FilterContextHandler;
import com.interesting.config.MyException;
import com.interesting.modules.accmaterial.dto.QueryXqAccInventoryDTO;
import com.interesting.modules.accmaterial.dto.QueryXqAccInventoryDTO2;
import com.interesting.modules.accmaterial.service.XqAccessoryInfoService;
import com.interesting.modules.accmaterial.vo.AccInventoryPageVO;
import com.interesting.modules.accmaterial.vo.AccInventoryPageVO2;
import com.interesting.modules.customer.entity.XqCustomer;
import com.interesting.modules.freightcost.dto.AddXqFreightCostInfoDTO;
import com.interesting.modules.freightcost.entity.XqFreightCostInfo;
import com.interesting.modules.freightcost.service.IXqFreightCostInfoService;
import com.interesting.modules.freightcost.vo.XqFreightCostInfoVO;
import com.interesting.modules.postImport.entity.XqPostImport;
import com.interesting.modules.postImport.mapper.XqPostImportMapper;
import com.interesting.modules.warehouse.dto.AddWarehouseImportDTO;
import com.interesting.modules.warehouse.dto.InstOrUpdtXqWarehouseDTO;
import com.interesting.modules.warehouse.dto.QueryXqWarehouseDTO;
import com.interesting.modules.warehouse.entity.XqWarehouse;
import com.interesting.modules.warehouse.mapper.XqWarehouseMapper;
import com.interesting.modules.warehouse.service.XqWarehouseService;
import com.interesting.modules.warehouse.vo.WarehouseIdNameAndAddressVO;
import com.interesting.modules.warehouse.vo.XqWarehouseVO;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
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
 * @author 26773
 * @description 针对表【xq_warehouse(仓库管理)】的数据库操作Service实现
 * @createDate 2023-07-04 11:45:24
 */
@Service
public class XqWarehouseServiceImpl extends ServiceImpl<XqWarehouseMapper, XqWarehouse>
        implements XqWarehouseService {

    @Autowired
    private XqAccessoryInfoService xqAccessoryInfoService;
    @Resource
    private XqPostImportMapper xqPostImportMapper;
    @Value("${jeecg.path.upload}")
    private String uploadFilePath;

    @Resource
    private XqWarehouseMapper xqWarehouseMapper;
    @Autowired
    private IXqFreightCostInfoService xqFreightCostInfoService;

    @Override
    public IPage<XqWarehouseVO> warehousePage(QueryXqWarehouseDTO dto) {
        Page<XqWarehouseVO> xqWarehouseVOPage = new Page<>(dto.getPageNo(), dto.getPageSize());
        return this.baseMapper.warehousePage(xqWarehouseVOPage, dto);
    }

    @Override
    public IPage<AccInventoryPageVO> listAccInventoryPage(QueryXqAccInventoryDTO dto) {
        Page<AccInventoryPageVO> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<AccInventoryPageVO> pageList = this.baseMapper.listAccInventoryPage(page, dto);
        pageList.getRecords().forEach(i -> {
            i.setId(UUID.randomUUID().toString().replace("-", ""));
        });
        return pageList;
    }

    @Override
    public IPage<AccInventoryPageVO2> listAccInventoryPage2(QueryXqAccInventoryDTO2 dto) {
        Page<AccInventoryPageVO2> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        IPage<AccInventoryPageVO2> pageList = this.baseMapper.listAccInventoryPage2(page, dto);
        pageList.getRecords().forEach(i -> {
            i.setId(UUID.randomUUID().toString().replace("-", ""));
        });
        return pageList;
    }


    @Override
    public void exportTemplate(HttpServletResponse response) throws UnsupportedEncodingException {
        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        //8
        int num = 4;
        for (int i = 0; i < num; i++) {
            writer.setColumnWidth(i, 30);
            //writer.setRowHeight(i,10);
        }
        writer.addHeaderAlias("cou", "序号*");
        writer.addHeaderAlias("name", "仓库名称*");
        writer.addHeaderAlias("number", "仓库编号");
        writer.addHeaderAlias("type", "仓库类型*");
        writer.setOnlyAlias(true);
        Map<String, Object> item = new HashMap<>();
        item.put("cou", "");
        item.put("name", "");
        item.put("number", "");
        item.put("type", "");
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
        String excelName = "仓库模板";
        writer.merge(num - 1, excelName);
        writer.write(list, true);
        writer.renameSheet(0, "主要数据");
        // 设置当前行
        writer.setCurrentRow(30);

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(3,
                "所有带 * 的为必填项\n" +
                        "> 仓库类型：国内辅料仓填写1、海外仓填写2\n" +
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
    @Transactional(rollbackFor = Exception.class)
    public Result<?> importWarehouse(MultipartFile file) throws Exception {
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
        List<AddWarehouseImportDTO> readAll = new ArrayList();

        for (List<Object> row : lists) {
            AddWarehouseImportDTO dto = new AddWarehouseImportDTO();
            dto.setCou(StringUtils.isNotBlank(row.get(0).toString()) ? Integer.parseInt(row.get(0).toString()) : null);
            dto.setName(row.get(1).toString());
            dto.setNumber(row.get(2).toString());
            dto.setType(row.get(3).toString());

            readAll.add(dto);
        }

        // List<InsertInvolveImportDTO> readAll = reader.readAll(InsertInvolveImportDTO.class);

        if (CollectionUtil.isEmpty(readAll)) {
            throw new MyException("未找到需要导入的仓库数据");
        }

        //错误数据
        List<AddWarehouseImportDTO> errorS = new ArrayList<>();
        //正确数据
        List<XqWarehouse> successS = new ArrayList<>();
        //多线程导入
        ExecutorService executorService = new ThreadPoolExecutor(4, 4, 1000,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        for (AddWarehouseImportDTO dto : readAll) {
            executorService.execute(() -> {
                String item = "序号" + dto.getCou();
                if (StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getType())) {
                    dto.setErrorInfo(item + "所有必填内容不能为空");
                    errorS.add(dto);
                    return;
                }
                XqWarehouse xqWarehouse = new XqWarehouse();
                xqWarehouse.setName(dto.getName());
                xqWarehouse.setSerialNum(dto.getNumber());
                xqWarehouse.setWarehouseType(dto.getType());
                xqWarehouse.setIzDelete(0);
                baseMapper.insert(xqWarehouse);
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
            result = errorS.stream().map(AddWarehouseImportDTO::getErrorInfo).collect(Collectors.joining(","));
            // 导入表记录一条信息
            XqPostImport xqPostImport = new XqPostImport();
            xqPostImport.setImportType("仓库导入");
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
    public List<WarehouseIdNameAndAddressVO> listWarehouse() {

        return xqWarehouseMapper.listWarehouse();

    }

    @Override
    public List<String> getWarehouseName() {
        return xqWarehouseMapper.getWarehouseName();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addWarehouse(InstOrUpdtXqWarehouseDTO dto) {
        XqWarehouse xqWarehouse = new XqWarehouse();
        BeanUtils.copyProperties(dto, xqWarehouse);
        xqWarehouse.setId(null);
        save(xqWarehouse);

        List<AddXqFreightCostInfoDTO> freightCostInfos = dto.getFreightCostInfos();
        if (freightCostInfos != null && freightCostInfos.size() > 0) {
            List<XqFreightCostInfo> collect1 = freightCostInfos.stream().map(i -> {
                XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                BeanUtils.copyProperties(i, xqFreightCostInfo);

                xqFreightCostInfo.setIzDomestic(0);
                xqFreightCostInfo.setWarehouseId(xqWarehouse.getId());
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
    public Boolean updateWarehouseById(InstOrUpdtXqWarehouseDTO dto) {
        String id = dto.getId();
        XqWarehouse byId = getById(id);
        if (byId == null) {
            throw new InterestingBootException("该记录不存在");
        }
        BeanUtils.copyProperties(dto, byId);
        updateById(byId);

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
            LinkedList<XqFreightCostInfo> toAddCostInfos = new LinkedList<>();

            List<XqFreightCostInfo> list2 = xqFreightCostInfoService.lambdaQuery()
                    .eq(XqFreightCostInfo::getWarehouseId, id).isNull(XqFreightCostInfo::getOrderId).list();

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

            for (AddXqFreightCostInfoDTO value : freightCostInfos) {
                if (ObjectUtils.isEmpty(collect1.get(value.getId()))) {
                    continue;
                }
                XqFreightCostInfo xqFreightCostInfo = new XqFreightCostInfo();
                BeanUtils.copyProperties(value, xqFreightCostInfo);
                xqFreightCostInfo.setId(null);
                xqFreightCostInfo.setWarehouseId(byId.getId());
                xqFreightCostInfo.setIzDomestic(0);//国外
                toAddCostInfos.add(xqFreightCostInfo);
            }

            xqFreightCostInfoService.saveBatch(toAddCostInfos);
        }

        return true;
    }

    @Autowired
    private ISysDictItemService sysDictItemService;

    @Override
    public XqWarehouseVO getDetailById(String id) {
        XqWarehouseVO xqWarehouseVO = new XqWarehouseVO();
        xqWarehouseVO = this.baseMapper.getDetailById(id);

        List<XqFreightCostInfo> list1 = xqFreightCostInfoService.lambdaQuery()
                .eq(XqFreightCostInfo::getWarehouseId, id)
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
        xqWarehouseVO.setFreightCostInfos(collect1);

        return xqWarehouseVO;
    }


    public String getFailList(List<AddWarehouseImportDTO> failList) {
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
        writer.addHeaderAlias("name", "仓库名称*");
        writer.addHeaderAlias("number", "仓库编号");
        writer.addHeaderAlias("type", "仓库类型*");
        writer.addHeaderAlias("errorInfo", "错误详情");


        writer.setOnlyAlias(true);
        int count = 1;
        if (failList != null && !failList.isEmpty()) {
            for (AddWarehouseImportDTO dto : failList) {
                Map<String, Object> item = new HashMap<>();
                item.put("cou", count);
                item.put("cou", dto.getName());
                item.put("name", dto.getName());
                item.put("number", dto.getNumber());
                item.put("type", dto.getType());
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
        String excelName = "仓库导入失败表-" + System.currentTimeMillis() + ".xls";
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




