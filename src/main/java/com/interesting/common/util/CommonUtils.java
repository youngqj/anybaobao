package com.interesting.common.util;


import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import cn.hutool.poi.excel.style.StyleUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.interesting.common.annotation.Excel;
import com.interesting.common.constant.DataBaseConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.modules.reportform.vo.PayableVO;
import com.interesting.modules.reportform.vo.SellProfitPageVO;
import com.interesting.modules.reportform.vo.SumVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CommonUtils {

    //中文正则
    private static Pattern ZHONGWEN_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    /**
     * 判断文件名是否带盘符，重新处理
     *
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName) {
        //判断是否带有盘符信息
        // Check for Unix-style path
        int unixSep = fileName.lastIndexOf('/');
        // Check for Windows-style path
        int winSep = fileName.lastIndexOf('\\');
        // Cut off at latest possible point
        int pos = (winSep > unixSep ? winSep : unixSep);
        if (pos != -1) {
            // Any sort of path separator found...
            fileName = fileName.substring(pos + 1);
        }
        //替换上传文件名字的特殊字符
        fileName = fileName.replace("=", "").replace(",", "").replace("&", "").replace("#", "");
        //替换上传文件名字中的空格
        fileName = fileName.replaceAll("\\s", "");
        return fileName;
    }

    // java 判断字符串里是否包含中文字符
    public static boolean ifContainChinese(String str) {
        if (str.getBytes().length == str.length()) {
            return false;
        } else {
            Matcher m = ZHONGWEN_PATTERN.matcher(str);
            if (m.find()) {
                return true;
            }
            return false;
        }
    }

    /**
     * 当前系统数据库类型
     */
    private static String DB_TYPE = "";

    public static String getDatabaseType() {
        if (oConvertUtils.isNotEmpty(DB_TYPE)) {
            return DB_TYPE;
        }
        DataSource dataSource = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
        try {
            return getDatabaseTypeByDataSource(dataSource);
        } catch (SQLException e) {
            //e.printStackTrace();
            log.warn(e.getMessage());
            return "";
        }
    }

    /**
     * 获取数据库类型
     *
     * @param dataSource
     * @return
     * @throws SQLException
     */
    private static String getDatabaseTypeByDataSource(DataSource dataSource) throws SQLException {
        if ("".equals(DB_TYPE)) {
            Connection connection = dataSource.getConnection();
            try {
                DatabaseMetaData md = connection.getMetaData();
                String dbType = md.getDatabaseProductName().toLowerCase();
                if (dbType.indexOf("mysql") >= 0) {
                    DB_TYPE = DataBaseConstant.DB_TYPE_MYSQL;
                } else if (dbType.indexOf("oracle") >= 0 || dbType.indexOf("dm") >= 0) {
                    DB_TYPE = DataBaseConstant.DB_TYPE_ORACLE;
                } else if (dbType.indexOf("sqlserver") >= 0 || dbType.indexOf("sql server") >= 0) {
                    DB_TYPE = DataBaseConstant.DB_TYPE_SQLSERVER;
                } else if (dbType.indexOf("postgresql") >= 0) {
                    DB_TYPE = DataBaseConstant.DB_TYPE_POSTGRESQL;
                } else {
                    throw new InterestingBootException("数据库类型:[" + dbType + "]不识别!");
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                connection.close();
            }
        }
        return DB_TYPE;

    }


//    public static Map<String, String> getFiledName(Class<?> o) {
//        Field[] fields = o.getDeclaredFields();
//        LinkedHashMap<String, String> map = new LinkedHashMap<>();
//        for (int i = 0; i < fields.length; i++) {
//            ApiModelProperty nameAnno = fields[i].getAnnotation(ApiModelProperty.class);
//            map.put(fields[i].getName(), nameAnno.value());
//        }
//        return map;
//    }

    public static Map<String, Excel> getFiledName(Class<?> o) {
        Field[] fields = o.getDeclaredFields();
        LinkedHashMap<String, Excel> map = new LinkedHashMap<>();
        for (int i = 0; i < fields.length; i++) {
            Excel nameAnno = fields[i].getAnnotation(Excel.class);
            if (nameAnno != null) {
                map.put(fields[i].getName(), nameAnno);
            }

        }
        return map;
    }

    @SneakyThrows
    public static void export(String exportColumns, List<?> records, HttpServletResponse response, Class<?> c, Class<? extends SumVO> sumVO, List<? extends SumVO> addUpList, Boolean hasCurrency) {

        // 判断是否选择了至少一列
        ArrayList<String> exportColumnList = new ArrayList<>();
        if (null == exportColumns) {
            throw new InterestingBootException("导出失败，请至少选择一列导出");
        }
        String[] split = exportColumns.split(",");
        Collections.addAll(exportColumnList, split);
        exportColumnList.remove("");
        if (exportColumnList.size() == 0) throw new InterestingBootException("导出失败，请至少选择一列导出");



        // 拿到自定义导出列的列名
        Map<String, Excel> filedName = CommonUtils.getFiledName(c);
        // 创建实际被写入的数据集合
        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        // 设置默认宽度20


        // 设置行高
        writer.setDefaultRowHeight(20);
        // 冻结第一行
        writer.setFreezePane(0, 1);


        for (int i = 0; i < exportColumnList.size(); i++) {
            String s = exportColumnList.get(i);
            Excel excel = filedName.get(s);
            if (excel != null) {
                writer.addHeaderAlias(s, excel.name());
                writer.setColumnWidth(i, excel.width());
            }

        }

        // 填充
        if (!records.isEmpty()) {
            int num = 1;
            for (Object vo : records) {
                Map<String, Object> items = new HashMap<>();

                // 在循环内创建一个 boolean 变量用于标记是否需要设置蓝色字体
                boolean isBlue = vo instanceof PayableVO && ((PayableVO) vo).getType() == 2;


                for (String s : exportColumnList) {

                    Object invoke = c.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1)).invoke(vo);
                    if (invoke != null && invoke.getClass() == Date.class) {
                        invoke = JSONObject.toJSONStringWithDateFormat(invoke, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat).replaceAll("\"", "");

                    }
                    if ("financeStatus".equals(s) && invoke != null) {
                        invoke = (int)invoke==1 ? "未付款" : "已付款";
                    }
                    if ("remmitStatus".equals(s) && invoke != null) {
                        invoke = (int)invoke==1 ? "未收汇" : (int)invoke==2 ? "部分收汇" : "全额收汇";
                    }


//                    // 设置单元格样式
//                    if (isBlue) {
//                        Row row = writer.getOrCreateRow(num); // 获取或创建行
//                        // 创建单元格
//                        Cell cell = row.createCell(num);
//                        cell.setCellValue(String.valueOf(invoke)); // 设置单元格值
//
//                        CellStyle blueCellStyle = writer.getWorkbook().createCellStyle();
//                        Font blueFont = writer.getWorkbook().createFont();
//                        blueFont.setColor(Font.COLOR_RED); // 设置字体颜色为蓝色
//                        blueCellStyle.setFont(blueFont);
//                        cell.setCellStyle(blueCellStyle);
//                    }

//                    if (isBlue) {
//                        if("dqyfkCNY".equals(s) ||"dqyfkUSD".equals(s) || "zyfkCNY".equals(s) ||"zyfkUSD".equals(s) ){
//                            items.put(s, invoke);
//                        }else {
//                            items.put(s, " ");
//
//                        }
//
//                    }else {
//                        items.put(s, invoke);
//                    }

                    items.put(s, invoke);
                }
                list.add(items);

                num++;

            }
            writer.write(list, true);
            writer.passRows(1);



            // 合计
            if (sumVO != null) {
                // 创建实际被写入的数据集合
                List<Map<String, Object>> list2 = new ArrayList<>();
                Map<String, Excel> filedName1 = CommonUtils.getFiledName(sumVO);
                Set<String> strings = filedName1.keySet();
//                writer.addHeaderAlias("A", " ");
                // 单独写入币种
                if (hasCurrency) {
                    writer.addHeaderAlias("currency", "币种");
                }
//                int num = 1;
                for (String string : strings) {
                    writer.addHeaderAlias(string, filedName1.get(string).name());
//                    writer.setColumnWidth(num, filedName1.get(string).width());
//                    num++;
                }


                for (SumVO vo : addUpList) {
                    Map<String, Object> items = new HashMap<>();

                    for (String s : strings) {
//                        items.put("A", " ");
                        if (hasCurrency) {
                            items.put("currency", vo.getCurrency());
                        }
                        items.put(s, sumVO.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1)).invoke(vo));
                    }
                    list2.add(items);
                }
                writer.write(list2, true);

            }


        } else {
            Map<String, Object> items = new HashMap<>();
            for (String s : exportColumnList) {
                items.put(s, null);
            }
            list.add(items);

            // 一次性写出内容，使用默认样式
            writer.write(list, true);
        }

        String excelName = "自定义导出EXCEL";
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

    /**
     *  销售利润表导出专用
     * @param exportColumns
     * @param records
     * @param response
     * @param c
     * @param sumVO
     * @param addUpList
     * @param hasCurrency
     */
    @SneakyThrows
    public static void export2(String exportColumns, List<?> records, HttpServletResponse response, Class<?> c, Class<? extends SumVO> sumVO, List<? extends SumVO> addUpList, Boolean hasCurrency) {

        // 判断是否选择了至少一列
        ArrayList<String> exportColumnList = new ArrayList<>();
        if (null == exportColumns) {
            throw new InterestingBootException("导出失败，请至少选择一列导出");
        }
        String[] split = exportColumns.split(",");
        Collections.addAll(exportColumnList, split);
        exportColumnList.remove("");
        if (exportColumnList.size() == 0) throw new InterestingBootException("导出失败，请至少选择一列导出");



        // 拿到自定义导出列的列名
        Map<String, Excel> filedName = CommonUtils.getFiledName(c);
        // 创建实际被写入的数据集合
        List<Map<String, Object>> list = new ArrayList<>();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter();
        // 设置默认宽度20


        // 设置行高
        writer.setDefaultRowHeight(20);
        // 冻结第一行
        writer.setFreezePane(0, 1);


        for (int i = 0; i < exportColumnList.size(); i++) {
            String s = exportColumnList.get(i);
            Excel excel = filedName.get(s);
            if (excel != null) {
                writer.addHeaderAlias(s, excel.name());
                writer.setColumnWidth(i, excel.width());
            }

        }

        // 填充
        if (!records.isEmpty()) {
            int num = 1;
            Map<String, CellStyle> cellStyleMap = new HashMap<>();
            for (Object vo : records) {
                Map<String, Object> items = new HashMap<>();

                // 在循环内创建一个 boolean 变量用于标记是否需要设置蓝色字体
                boolean isBlue = vo instanceof PayableVO && ((PayableVO) vo).getType() == 2;

                for (String s : exportColumnList) {

                    Object invoke = c.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1)).invoke(vo);
                    if (invoke != null && invoke.getClass() == Date.class) {
                        invoke = JSONObject.toJSONStringWithDateFormat(invoke, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat).replaceAll("\"", "");

                    }
                    if ("financeStatus".equals(s) && invoke != null) {
                        invoke = (int)invoke==1 ? "未付款" : "已付款";
                    }


//                    // 设置单元格样式
//                    if (isBlue) {
//                        Row row = writer.getOrCreateRow(num); // 获取或创建行
//                        // 创建单元格
//                        Cell cell = row.createCell(num);
//                        cell.setCellValue(String.valueOf(invoke)); // 设置单元格值
//
//                        CellStyle blueCellStyle = writer.getWorkbook().createCellStyle();
//                        Font blueFont = writer.getWorkbook().createFont();
//                        blueFont.setColor(Font.COLOR_RED); // 设置字体颜色为蓝色
//                        blueCellStyle.setFont(blueFont);
//                        cell.setCellStyle(blueCellStyle);
//                    }

//                    if (isBlue) {
//                        if("dqyfkCNY".equals(s) ||"dqyfkUSD".equals(s) || "zyfkCNY".equals(s) ||"zyfkUSD".equals(s) ){
//                            items.put(s, invoke);
//                        }else {
//                            items.put(s, " ");
//
//                        }
//
//                    }else {
//                        items.put(s, invoke);
//                    }

                    items.put(s, invoke);
                }
                list.add(items);

                num++;

            }
            writer.write(list, true);
            writer.passRows(1);

            if (SellProfitPageVO.class == c) {
                int num1 = 1;
                for (Object vo : records) {
                    int num2 = 0;
                    for (String s : exportColumnList) {

                        Object invoke = c.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1)).invoke(vo);

                        if ("domesticShippingFee".equals(s) && invoke != null) {
                            Object color = c.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1) + "Color").invoke(vo);

                            Row row = writer.getOrCreateRow(num1); // 获取或创建行
                            // 创建单元格
                            Cell cell = row.createCell(num2);
                            cell.setCellValue(String.valueOf(invoke)); // 设置单元格值
                            if (!ObjectUtils.isEmpty(color) && BigDecimal.ZERO.compareTo((BigDecimal) invoke) != 0) {
                                if ((int)color == 0) {
                                    CellStyle redCellStyle = cellStyleMap.computeIfAbsent("redCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        Font font = writer.getWorkbook().createFont();
                                        font.setColor(IndexedColors.RED.getIndex()); // 设置字体颜色
                                        cellStyle.setFont(font);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(redCellStyle);
                                }
                                else if ((int)color == 1) {
                                    CellStyle blueCellStyle = cellStyleMap.computeIfAbsent("blueCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        Font font = writer.getWorkbook().createFont();
                                        font.setColor(IndexedColors.BLUE.getIndex()); // 设置字体颜色
                                        cellStyle.setFont(font);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(blueCellStyle);
                                }
                                else {
                                    CellStyle normalCellStyle = cellStyleMap.computeIfAbsent("normalCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(normalCellStyle);
                                }
                            }
                            else {
                                CellStyle normalCellStyle = cellStyleMap.computeIfAbsent("normalCellStyle", key -> {
                                    CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                    // 内容水平居中
                                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                    // 内容垂直居中
                                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                    // 设置边框
                                    cellStyle.setBorderBottom(BorderStyle.THIN);
                                    cellStyle.setBorderLeft(BorderStyle.THIN);
                                    cellStyle.setBorderRight(BorderStyle.THIN);
                                    return cellStyle;
                                });
                                cell.setCellStyle(normalCellStyle);
                            }
                        }

                        if ("customsClearanceTaxFee".equals(s) && invoke != null) {
                            Object color = c.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1) + "Color").invoke(vo);

                            Row row = writer.getOrCreateRow(num1); // 获取或创建行
                            // 创建单元格
                            Cell cell = row.createCell(num2);
                            cell.setCellValue(String.valueOf(invoke)); // 设置单元格值
                            if (!ObjectUtils.isEmpty(color) && BigDecimal.ZERO.compareTo((BigDecimal) invoke) != 0) {
                                if ((int)color == 0) {
                                    CellStyle redCellStyle = cellStyleMap.computeIfAbsent("redCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        Font font = writer.getWorkbook().createFont();
                                        font.setColor(IndexedColors.RED.getIndex()); // 设置字体颜色
                                        cellStyle.setFont(font);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(redCellStyle);
                                }
                                else if ((int)color == 1) {
                                    CellStyle blueCellStyle = cellStyleMap.computeIfAbsent("blueCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        Font font = writer.getWorkbook().createFont();
                                        font.setColor(IndexedColors.BLUE.getIndex()); // 设置字体颜色
                                        cellStyle.setFont(font);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(blueCellStyle);
                                }
                                else {
                                    CellStyle normalCellStyle = cellStyleMap.computeIfAbsent("normalCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(normalCellStyle);
                                }
                            }
                            else {
                                CellStyle normalCellStyle = cellStyleMap.computeIfAbsent("normalCellStyle", key -> {
                                    CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                    // 内容水平居中
                                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                    // 内容垂直居中
                                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                    // 设置边框
                                    cellStyle.setBorderBottom(BorderStyle.THIN);
                                    cellStyle.setBorderLeft(BorderStyle.THIN);
                                    cellStyle.setBorderRight(BorderStyle.THIN);
                                    return cellStyle;
                                });
                                cell.setCellStyle(normalCellStyle);
                            }
                        }

                        if ("foreignCustomsClearanceFee".equals(s) && invoke != null) {
                            Object color = c.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1) + "Color").invoke(vo);

                            Row row = writer.getOrCreateRow(num1); // 获取或创建行
                            // 创建单元格
                            Cell cell = row.createCell(num2);
                            cell.setCellValue(String.valueOf(invoke)); // 设置单元格值
                            if (!ObjectUtils.isEmpty(color) && BigDecimal.ZERO.compareTo((BigDecimal) invoke) != 0) {
                                if ((int)color == 0) {
                                    CellStyle redCellStyle = cellStyleMap.computeIfAbsent("redCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        Font font = writer.getWorkbook().createFont();
                                        font.setColor(IndexedColors.RED.getIndex()); // 设置字体颜色
                                        cellStyle.setFont(font);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(redCellStyle);
                                }
                                else if ((int)color == 1) {
                                    CellStyle blueCellStyle = cellStyleMap.computeIfAbsent("blueCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        Font font = writer.getWorkbook().createFont();
                                        font.setColor(IndexedColors.BLUE.getIndex()); // 设置字体颜色
                                        cellStyle.setFont(font);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(blueCellStyle);
                                }
                                else {
                                    CellStyle normalCellStyle = cellStyleMap.computeIfAbsent("normalCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(normalCellStyle);
                                }
                            }
                            else {
                                CellStyle normalCellStyle = cellStyleMap.computeIfAbsent("normalCellStyle", key -> {
                                    CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                    // 内容水平居中
                                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                    // 内容垂直居中
                                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                    // 设置边框
                                    cellStyle.setBorderBottom(BorderStyle.THIN);
                                    cellStyle.setBorderLeft(BorderStyle.THIN);
                                    cellStyle.setBorderRight(BorderStyle.THIN);
                                    return cellStyle;
                                });
                                cell.setCellStyle(normalCellStyle);
                            }
                        }

                        if ("foreignTruckFee".equals(s) && invoke != null) {
                            Object color = c.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1) + "Color").invoke(vo);

                            Row row = writer.getOrCreateRow(num1); // 获取或创建行
                            // 创建单元格
                            Cell cell = row.createCell(num2);
                            cell.setCellValue(String.valueOf(invoke)); // 设置单元格值
                            if (!ObjectUtils.isEmpty(color) && BigDecimal.ZERO.compareTo((BigDecimal) invoke) != 0) {
                                if ((int)color == 0) {
                                    CellStyle redCellStyle = cellStyleMap.computeIfAbsent("redCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        Font font = writer.getWorkbook().createFont();
                                        font.setColor(IndexedColors.RED.getIndex()); // 设置字体颜色
                                        cellStyle.setFont(font);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(redCellStyle);
                                }
                                else if ((int)color == 1) {
                                    CellStyle blueCellStyle = cellStyleMap.computeIfAbsent("blueCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        Font font = writer.getWorkbook().createFont();
                                        font.setColor(IndexedColors.BLUE.getIndex()); // 设置字体颜色
                                        cellStyle.setFont(font);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(blueCellStyle);
                                }
                                else {
                                    CellStyle normalCellStyle = cellStyleMap.computeIfAbsent("normalCellStyle", key -> {
                                        CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                        // 内容水平居中
                                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                        // 内容垂直居中
                                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                        // 设置边框
                                        cellStyle.setBorderBottom(BorderStyle.THIN);
                                        cellStyle.setBorderLeft(BorderStyle.THIN);
                                        cellStyle.setBorderRight(BorderStyle.THIN);
                                        return cellStyle;
                                    });
                                    cell.setCellStyle(normalCellStyle);
                                }
                            }
                            else {
                                CellStyle normalCellStyle = cellStyleMap.computeIfAbsent("normalCellStyle", key -> {
                                    CellStyle cellStyle = writer.getWorkbook().createCellStyle();
                                    // 内容水平居中
                                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                                    // 内容垂直居中
                                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                                    // 设置边框
                                    cellStyle.setBorderBottom(BorderStyle.THIN);
                                    cellStyle.setBorderLeft(BorderStyle.THIN);
                                    cellStyle.setBorderRight(BorderStyle.THIN);
                                    return cellStyle;
                                });
                                cell.setCellStyle(normalCellStyle);
                            }
                        }
                        num2++;
                    }
                    num1++;
                }
            }

//            // 合计
//            if (sumVO != null) {
//                // 创建实际被写入的数据集合
//                List<Map<String, Object>> list2 = new ArrayList<>();
//                Map<String, Excel> filedName1 = CommonUtils.getFiledName(sumVO);
//                Set<String> strings = filedName1.keySet();
////                writer.addHeaderAlias("A", " ");
//                // 单独写入币种
//                if (hasCurrency) {
//                    writer.addHeaderAlias("currency", "币种");
//                }
////                int num = 1;
//                for (String string : strings) {
//                    writer.addHeaderAlias(string, filedName1.get(string).name());
////                    writer.setColumnWidth(num, filedName1.get(string).width());
////                    num++;
//                }
//
//
//                for (SumVO vo : addUpList) {
//                    Map<String, Object> items = new HashMap<>();
//
//                    for (String s : strings) {
////                        items.put("A", " ");
//                        if (hasCurrency) {
//                            items.put("currency", vo.getCurrency());
//                        }
//                        items.put(s, sumVO.getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1)).invoke(vo));
//                    }
//                    list2.add(items);
//                }
//                writer.write(list2, true);
//
//            }


        } else {
            Map<String, Object> items = new HashMap<>();
            for (String s : exportColumnList) {
                items.put(s, null);
            }
            list.add(items);

            // 一次性写出内容，使用默认样式
            writer.write(list, true);
        }

        String excelName = "自定义导出EXCEL";
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

