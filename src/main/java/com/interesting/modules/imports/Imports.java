package com.interesting.modules.imports;

import cn.hutool.core.io.IoUtil;
import com.interesting.modules.order.mapper.XqOrderMapper;
import com.interesting.modules.order.vo.XqOrderExportFretVO;
import com.interesting.modules.order.vo.XqOrderExportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导出(Imports)表控制层
 *
 * @author 王安麒
 * @since 2023-09-18 10:17:10
 */

@Api(tags = "导出")
@RestController
@RequestMapping("Imports")
@Slf4j
public class Imports  {
    @Resource
    XqOrderMapper xqOrderMapper;

    @ApiOperation(value = "导出EXCEL", httpMethod = "GET")
    @GetMapping(value = "/exportInvoiceExcel", produces = "application/octet-stream")
    public void exportInvoiceExcel(@RequestParam("orderNum") String orderNum, HttpServletResponse response) throws Exception {

        List<XqOrderExportVO> xqOrderExportVO = xqOrderMapper.orderExportVO(orderNum);
        List<XqOrderExportFretVO> xqOrderExportFretVOS = xqOrderMapper.orderExportFretVOS(orderNum);



        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();

        // 创建工作表
        Sheet sheet = workbook.createSheet("INVOICE");

        // 创建行和单元格 ,使用集合来存储单元格对象
        List<Cell> cellss = new ArrayList<>();
        for (int row = 0; row <= 35; row++) {
            Row rows = sheet.createRow(row);
            for (int cell = 0; cell <= 4; cell++) {
                Cell cells = rows.createCell(cell);
                cells.getRow().setHeightInPoints(15);
                cellss.add(cells);
            }
        }

        //获取所有单元格
        Cell cell1 = cellss.get(0);
        Cell cell2 = cellss.get(1);
        Cell cell3 = cellss.get(2);
        Cell cell4 = cellss.get(3);
        Cell cell5 = cellss.get(4);
        Cell cell6 = cellss.get(5);
        Cell cell7 = cellss.get(6);
        Cell cell8 = cellss.get(7);
        Cell cell9 = cellss.get(8);
        Cell cell10 = cellss.get(9);
        Cell cell11 = cellss.get(10);
        Cell cell12 = cellss.get(11);
        Cell cell13 = cellss.get(12);
        Cell cell14 = cellss.get(13);
        Cell cell15 = cellss.get(14);
        Cell cell16 = cellss.get(15);
        Cell cell17 = cellss.get(16);
        Cell cell18 = cellss.get(17);
        Cell cell19 = cellss.get(18);
        Cell cell20 = cellss.get(19);
        Cell cell21 = cellss.get(20);
        Cell cell22 = cellss.get(21);
        Cell cell23 = cellss.get(22);
        Cell cell24 = cellss.get(23);
        Cell cell25 = cellss.get(24);
        Cell cell26 = cellss.get(25);
        Cell cell27 = cellss.get(26);
        Cell cell28 = cellss.get(27);
        Cell cell29 = cellss.get(28);
        Cell cell30 = cellss.get(29);
        Cell cell31 = cellss.get(30);
        Cell cell32 = cellss.get(31);
        Cell cell33 = cellss.get(32);
        Cell cell34 = cellss.get(33);
        Cell cell35 = cellss.get(34);
        Cell cell36 = cellss.get(35);
        Cell cell37 = cellss.get(36);
        Cell cell38 = cellss.get(37);
        Cell cell39 = cellss.get(38);
        Cell cell40 = cellss.get(39);
        Cell cell41 = cellss.get(40);
        Cell cell42 = cellss.get(41);
        Cell cell43 = cellss.get(42);
        Cell cell44 = cellss.get(43);
        Cell cell45 = cellss.get(44);
        Cell cell46 = cellss.get(45);
        Cell cell47 = cellss.get(46);
        Cell cell48 = cellss.get(47);
        Cell cell49 = cellss.get(48);
        Cell cell50 = cellss.get(49);
        Cell cell51 = cellss.get(50);
        Cell cell52 = cellss.get(51);
        Cell cell53 = cellss.get(52);
        Cell cell54 = cellss.get(53);
        Cell cell55 = cellss.get(54);
        Cell cell56 = cellss.get(55);
        Cell cell57 = cellss.get(56);
        Cell cell58 = cellss.get(57);
        Cell cell59 = cellss.get(58);
        Cell cell60 = cellss.get(59);
        Cell cell61 = cellss.get(60);
        Cell cell62 = cellss.get(61);
        Cell cell63 = cellss.get(62);
        Cell cell64 = cellss.get(63);
        Cell cell65 = cellss.get(64);
        Cell cell66 = cellss.get(65);
        Cell cell67 = cellss.get(66);
        Cell cell68 = cellss.get(67);
        Cell cell69 = cellss.get(68);
        Cell cell70 = cellss.get(69);
        Cell cell71 = cellss.get(70);
        Cell cell72 = cellss.get(71);
        Cell cell73 = cellss.get(72);
        Cell cell74 = cellss.get(73);
        Cell cell75 = cellss.get(74);
        Cell cell76 = cellss.get(75);
        Cell cell77 = cellss.get(76);
        Cell cell78 = cellss.get(77);
        Cell cell79 = cellss.get(78);
        Cell cell80 = cellss.get(79);
        Cell cell81 = cellss.get(80);
        Cell cell82 = cellss.get(81);
        Cell cell83 = cellss.get(82);
        Cell cell84 = cellss.get(83);
        Cell cell85 = cellss.get(84);
        Cell cell86 = cellss.get(85);
        Cell cell87 = cellss.get(86);
        Cell cell88 = cellss.get(87);
        Cell cell89 = cellss.get(88);
        Cell cell90 = cellss.get(89);
        Cell cell91 = cellss.get(90);
        Cell cell92 = cellss.get(91);
        Cell cell93 = cellss.get(92);
        Cell cell94 = cellss.get(93);
        Cell cell95 = cellss.get(94);
        Cell cell96 = cellss.get(95);
        Cell cell97 = cellss.get(96);
        Cell cell98 = cellss.get(97);
        Cell cell99 = cellss.get(98);
        Cell cell100 = cellss.get(99);
        Cell cell101 = cellss.get(100);
        Cell cell102 = cellss.get(101);
        Cell cell103 = cellss.get(102);
        Cell cell104 = cellss.get(103);
        Cell cell105 = cellss.get(104);
        Cell cell106 = cellss.get(105);
        Cell cell107 = cellss.get(106);
        Cell cell108 = cellss.get(107);
        Cell cell109 = cellss.get(108);
        Cell cell110 = cellss.get(109);
        Cell cell111 = cellss.get(110);
        Cell cell112 = cellss.get(111);
        Cell cell113 = cellss.get(112);
        Cell cell114 = cellss.get(113);
        Cell cell115 = cellss.get(114);
        Cell cell116 = cellss.get(115);
        Cell cell117 = cellss.get(116);
        Cell cell118 = cellss.get(117);
        Cell cell119 = cellss.get(118);
        Cell cell120 = cellss.get(119);
        Cell cell121 = cellss.get(120);
        Cell cell122 = cellss.get(121);
        Cell cell123 = cellss.get(122);
        Cell cell124 = cellss.get(123);
        Cell cell125 = cellss.get(124);
        Cell cell126 = cellss.get(125);
        Cell cell127 = cellss.get(126);
        Cell cell128 = cellss.get(127);
        Cell cell129 = cellss.get(128);
        Cell cell130 = cellss.get(129);
        Cell cell131 = cellss.get(130);
        Cell cell132 = cellss.get(131);
        Cell cell133 = cellss.get(132);
        Cell cell134 = cellss.get(133);
        Cell cell135 = cellss.get(134);
        Cell cell136 = cellss.get(135);
        Cell cell137 = cellss.get(136);
        Cell cell138 = cellss.get(137);
        Cell cell139 = cellss.get(138);
        Cell cell140 = cellss.get(139);
        Cell cell141 = cellss.get(140);
        Cell cell142 = cellss.get(141);
        Cell cell143 = cellss.get(142);
        Cell cell144 = cellss.get(143);
        Cell cell145 = cellss.get(144);
        Cell cell146 = cellss.get(145);
        Cell cell147 = cellss.get(146);
        Cell cell148 = cellss.get(147);
        Cell cell149 = cellss.get(148);
        Cell cell150 = cellss.get(149);
        Cell cell151 = cellss.get(150);
        Cell cell152 = cellss.get(151);
        Cell cell153 = cellss.get(152);
        Cell cell154 = cellss.get(153);
        Cell cell155 = cellss.get(154);
        Cell cell156 = cellss.get(155);
        Cell cell157 = cellss.get(156);
        Cell cell158 = cellss.get(157);
        Cell cell159 = cellss.get(158);
        Cell cell160 = cellss.get(159);
        Cell cell161 = cellss.get(160);
        Cell cell162 = cellss.get(161);
        Cell cell163 = cellss.get(162);
        Cell cell164 = cellss.get(163);
        Cell cell165 = cellss.get(164);
        Cell cell166 = cellss.get(165);
        Cell cell167 = cellss.get(166);
        Cell cell168 = cellss.get(167);
        Cell cell169 = cellss.get(168);
        Cell cell170 = cellss.get(169);
        Cell cell171 = cellss.get(170);
        Cell cell172 = cellss.get(171);
        Cell cell173 = cellss.get(172);
        Cell cell174 = cellss.get(173);
        Cell cell175 = cellss.get(174);
        Cell cell176 = cellss.get(175);
        Cell cell177 = cellss.get(176);
        Cell cell178 = cellss.get(177);
        Cell cell179 = cellss.get(178);
        Cell cell180 = cellss.get(179);

        // 合并单元格
        /** 0 ~ 18行*/
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        sheet.addMergedRegion(new CellRangeAddress(1, 5, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 9, 3, 4));
        sheet.addMergedRegion(new CellRangeAddress(6, 10, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(11, 15, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(10, 11, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(10, 11, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(12, 13, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(12, 13, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(14, 15, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(14, 15, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(16, 17, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(16, 17, 3, 4));
        sheet.addMergedRegion(new CellRangeAddress(18, 18, 0, 1));
        //修改第20行的高度
        Row row20 = sheet.getRow(19);
        if (row20 != null) {
            short rowHeight = 3 * 256; // 要设置的行高
            row20.setHeight(rowHeight);
        }

        //修改第33行的高度
        Row row33 = sheet.getRow(32);
        if (row33 != null) {
            short rowHeight = 10 * 256; // 要设置的行高
            row33.setHeight(rowHeight);
        }

        //修改第35行的高度
        Row row35 = sheet.getRow(34);
        if (row35 != null) {
            short rowHeight = 8 * 256; // 要设置的行高
            row35.setHeight(rowHeight);
        }
        String fret = "";
        if (xqOrderExportFretVOS.size() > 0) {
            for (XqOrderExportFretVO vo : xqOrderExportFretVOS) {
                fret += vo.getAgentName() + "\n";
            }
        }

        //单元格赋值
        cell1.setCellValue("COMMERCIAL INVOICE");
        cell6.setCellValue("Issuer:\n GREEN GARDEN PRODUCE LLC.\n" +
                "113 BARKSDALE PROFESSIONAL CENTER, NEWARK\n" +
                "DELAWARE 19711, USA\n" +
                "Office:+1 202-888-3699\n");
        cell31.setCellValue("To:\n" + xqOrderExportVO.get(0).getCustomer() + "");
        cell54.setCellValue("Date:\n" + xqOrderExportVO.get(0).getInvoiceDate() == null ? "" : xqOrderExportVO.get(0).getInvoiceDate() + "");
        cell55.setCellValue("Invoice:\n" + xqOrderExportVO.get(0).getInvoiceNo() + "");
        cell56.setCellValue("Notify Party\n" + fret);
        cell64.setCellValue("ETD:\n" + xqOrderExportVO.get(0).getEtd() == null ? "" : xqOrderExportVO.get(0).getEtd() + "");
        cell65.setCellValue("ETA:\n" + xqOrderExportVO.get(0).getEta() == null ? "" : xqOrderExportVO.get(0).getEta() + "");
        cell74.setCellValue("Container#:\n" + xqOrderExportVO.get(0).getContainerNo() + "");
        cell75.setCellValue("B/L#:\n" + xqOrderExportVO.get(0).getBillOfLading() + "");
        cell81.setCellValue("Transport Details:\n" + xqOrderExportVO.get(0).getTransportDetails() + "");
        cell84.setCellValue("Vessel Name/Voyage Number:\n" + xqOrderExportVO.get(0).getVoyageNumber() + "");
        cell91.setCellValue("Description of Goods");
        cell93.setCellValue("Quantity");
        cell94.setCellValue("Unit Price");
        cell95.setCellValue("Amount");
        cell96.setCellValue("(英文品名)");
        cell101.setCellValue("包装方式");
        cell103.setCellValue("总箱数");
        cell104.setCellValue("销售单价");
        cell105.setCellValue("销售总额");
        cell116.setCellValue("(订单号) ERP59219579");
        cell131.setCellValue("Total:");
        cell136.setCellValue("Total Amount:");
        cell146.setCellValue("Terms of Payment:");
        cell156.setCellValue("PAYEE BANKING DETAILS:");
        cell176.setCellValue("Thank you for your business!");

        // 设置首行字体
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        font1.setBold(true);
        font1.setFontName("Arial");
        font1.setFontHeightInPoints((short) 12);
        style1.setFont(font1);
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        cell1.setCellStyle(style1);
        cell2.setCellStyle(style1);
        cell3.setCellStyle(style1);
        cell4.setCellStyle(style1);
        cell5.setCellStyle(style1);

        // 设置左右框加粗，字体加粗
        CellStyle style2 = workbook.createCellStyle();
        Font font2 = workbook.createFont();
        font2.setBold(true);
        font2.setFontName("Arial");
        font2.setFontHeightInPoints((short) 11);
        style2.setFont(font2);
        style2.setAlignment(HorizontalAlignment.LEFT);
        style2.setVerticalAlignment(VerticalAlignment.TOP);
        style2.setBorderBottom(BorderStyle.NONE);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setWrapText(true);
        cell6.setCellStyle(style2);
        cell8.setCellStyle(style2);
        cell31.setCellStyle(style2);
        cell32.setCellStyle(style2);
        cell33.setCellStyle(style2);
        cell56.setCellStyle(style2);
        cell57.setCellStyle(style2);
        cell58.setCellStyle(style2);
        cell54.setCellStyle(style2);
        cell55.setCellStyle(style2);
        cell74.setCellStyle(style2);
        cell75.setCellStyle(style2);
        cell81.setCellStyle(style2);
        cell82.setCellStyle(style2);
        cell83.setCellStyle(style2);
        cell84.setCellStyle(style2);
        cell85.setCellStyle(style2);
        cell91.setCellStyle(style2);
        cell92.setCellStyle(style2);
        cell93.setCellStyle(style2);
        cell94.setCellStyle(style2);
        cell95.setCellStyle(style2);
        cell131.setCellStyle(style2);
        cell132.setCellStyle(style2);
        cell136.setCellStyle(style2);
        cell137.setCellStyle(style2);
        cell138.setCellStyle(style2);
        cell139.setCellStyle(style2);
        cell140.setCellStyle(style2);
        cell146.setCellStyle(style2);
        cell147.setCellStyle(style2);
        cell148.setCellStyle(style2);
        cell149.setCellStyle(style2);
        cell150.setCellStyle(style2);
        cell156.setCellStyle(style2);
        cell157.setCellStyle(style2);
        cell158.setCellStyle(style2);
        cell159.setCellStyle(style2);
        cell160.setCellStyle(style2);
        cell166.setCellStyle(style2);
        cell167.setCellStyle(style2);
        cell168.setCellStyle(style2);
        cell169.setCellStyle(style2);
        cell170.setCellStyle(style2);

        // 设置中间加粗字体
        CellStyle style5 = workbook.createCellStyle();
        Font font3 = workbook.createFont();
        font3.setFontName("Arial");
        font3.setBold(true);
        font3.setFontHeightInPoints((short) 11);
        style5.setFont(font3);
        style5.setAlignment(HorizontalAlignment.CENTER);
        style5.setVerticalAlignment(VerticalAlignment.TOP);
        style5.setBorderBottom(BorderStyle.NONE);
        style5.setBorderTop(BorderStyle.THIN);
        style5.setBorderLeft(BorderStyle.THIN);
        style5.setBorderRight(BorderStyle.THIN);
        style5.setWrapText(true);
        cell91.setCellStyle(style5);
        cell92.setCellStyle(style5);
        cell93.setCellStyle(style5);
        cell94.setCellStyle(style5);
        cell95.setCellStyle(style5);
        cell98.setCellStyle(style5);
        cell99.setCellStyle(style5);
        cell100.setCellStyle(style5);


        //设置中间字体不加粗
        CellStyle style6 = workbook.createCellStyle();
        style6.setAlignment(HorizontalAlignment.CENTER);
        style6.setVerticalAlignment(VerticalAlignment.TOP);
        style6.setBorderBottom(BorderStyle.NONE);
        style6.setBorderTop(BorderStyle.NONE);
        style6.setBorderLeft(BorderStyle.THIN);
        style6.setBorderRight(BorderStyle.THIN);
        style6.setWrapText(true);
        Font font6 = workbook.createFont();
        font6.setFontName("Arial");
        font6.setFontHeightInPoints((short) 11);
        style6.setFont(font6);
        cell103.setCellStyle(style6);
        cell104.setCellStyle(style6);
        cell105.setCellStyle(style6);
        cell108.setCellStyle(style6);
        cell109.setCellStyle(style6);
        cell110.setCellStyle(style6);
        cell113.setCellStyle(style6);
        cell114.setCellStyle(style6);
        cell115.setCellStyle(style6);
        cell118.setCellStyle(style6);
        cell119.setCellStyle(style6);
        cell120.setCellStyle(style6);
        cell123.setCellStyle(style6);
        cell124.setCellStyle(style6);
        cell125.setCellStyle(style6);
        cell128.setCellStyle(style6);
        cell129.setCellStyle(style6);
        cell130.setCellStyle(style6);

        //设置中间字体不加粗,有边框
        CellStyle style7 = workbook.createCellStyle();
        style7.setAlignment(HorizontalAlignment.CENTER);
        style7.setVerticalAlignment(VerticalAlignment.TOP);
        style7.setBorderBottom(BorderStyle.NONE);
        style7.setWrapText(true);
        style7.setBorderTop(BorderStyle.THIN);
        style7.setBorderLeft(BorderStyle.THIN);
        style7.setBorderRight(BorderStyle.THIN);
        Font font5 = workbook.createFont();
        font5.setFontName("Arial");
        font5.setFontHeightInPoints((short) 11);
        style7.setFont(font5);
        cell133.setCellStyle(style7);
        cell134.setCellStyle(style7);
        cell135.setCellStyle(style7);

        // 设置左右框加粗，字不加粗
        CellStyle style3 = workbook.createCellStyle();
        style3.setVerticalAlignment(VerticalAlignment.TOP);
        style3.setAlignment(HorizontalAlignment.LEFT);
        style3.setBorderBottom(BorderStyle.NONE);
        style3.setBorderTop(BorderStyle.NONE);
        style3.setBorderLeft(BorderStyle.THIN);
        style3.setWrapText(true);
        style3.setBorderRight(BorderStyle.THIN);
        Font font4 = workbook.createFont();
        font4.setFontName("Arial");
        font4.setFontHeightInPoints((short) 11);
        style3.setFont(font4);
        cell11.setCellStyle(style3);
        cell13.setCellStyle(style3);
        cell16.setCellStyle(style3);
        cell18.setCellStyle(style3);
        cell21.setCellStyle(style3);
        cell23.setCellStyle(style3);
        cell26.setCellStyle(style3);
        cell28.setCellStyle(style3);
        cell36.setCellStyle(style3);
        cell38.setCellStyle(style3);
        cell41.setCellStyle(style3);
        cell43.setCellStyle(style3);
        cell46.setCellStyle(style3);
        cell48.setCellStyle(style3);
        cell51.setCellStyle(style3);
        cell53.setCellStyle(style3);
        cell61.setCellStyle(style3);
        cell63.setCellStyle(style3);
        cell66.setCellStyle(style3);
        cell68.setCellStyle(style3);
        cell71.setCellStyle(style3);
        cell73.setCellStyle(style3);
        cell76.setCellStyle(style3);
        cell78.setCellStyle(style3);
        cell59.setCellStyle(style3);
        cell60.setCellStyle(style3);
        cell69.setCellStyle(style3);
        cell70.setCellStyle(style3);
        cell79.setCellStyle(style3);
        cell80.setCellStyle(style3);
        cell86.setCellStyle(style3);
        cell87.setCellStyle(style3);
        cell88.setCellStyle(style3);
        cell89.setCellStyle(style3);
        cell90.setCellStyle(style3);
        cell101.setCellStyle(style3);
        cell102.setCellStyle(style3);
        cell106.setCellStyle(style3);
        cell107.setCellStyle(style3);
        cell111.setCellStyle(style3);
        cell112.setCellStyle(style3);
        cell121.setCellStyle(style3);
        cell122.setCellStyle(style3);
        cell126.setCellStyle(style3);
        cell127.setCellStyle(style3);
        cell141.setCellStyle(style3);
        cell145.setCellStyle(style3);
        cell151.setCellStyle(style3);
        cell152.setCellStyle(style3);
        cell153.setCellStyle(style3);
        cell154.setCellStyle(style3);
        cell155.setCellStyle(style3);
        cell161.setCellStyle(style3);
        cell162.setCellStyle(style3);
        cell163.setCellStyle(style3);
        cell164.setCellStyle(style3);
        cell165.setCellStyle(style3);


        // 设置边框
        CellStyle style4 = workbook.createCellStyle();
        style4.setAlignment(HorizontalAlignment.CENTER);
        style4.setVerticalAlignment(VerticalAlignment.TOP);
        style4.setBorderBottom(BorderStyle.THIN);
        style4.setBorderTop(BorderStyle.THIN);
        style4.setBorderLeft(BorderStyle.THIN);
        style4.setBorderRight(BorderStyle.THIN);
        style4.setWrapText(true);
        Font font7 = workbook.createFont();
        font7.setBold(true);
        font7.setFontName("Arial");
        font7.setFontHeightInPoints((short) 11);
        style4.setFont(font7);
        cell49.setCellStyle(style4);
        cell10.setCellStyle(style4);
        cell15.setCellStyle(style4);
        cell20.setCellStyle(style4);
        cell25.setCellStyle(style4);
        cell30.setCellStyle(style4);
        cell35.setCellStyle(style4);
        cell40.setCellStyle(style4);
        cell45.setCellStyle(style4);
        cell50.setCellStyle(style4);
        cell176.setCellStyle(style4);
        cell177.setCellStyle(style4);
        cell178.setCellStyle(style4);
        cell179.setCellStyle(style4);
        cell180.setCellStyle(style4);

        //额外设置订单号字体，和英文品名，简单介绍字体
        CellStyle style8 = workbook.createCellStyle();
        style8.setVerticalAlignment(VerticalAlignment.TOP);
        style8.setAlignment(HorizontalAlignment.LEFT);
        style8.setBorderBottom(BorderStyle.NONE);
        style8.setBorderTop(BorderStyle.NONE);
        style8.setBorderLeft(BorderStyle.THIN);
        style8.setBorderRight(BorderStyle.THIN);
        style8.setWrapText(true);
        Font font8 = workbook.createFont();
        font8.setFontName("Arial");
        font8.setBold(true);
        font8.setFontHeightInPoints((short) 12);
        style8.setFont(font8);
        cell116.setCellStyle(style8);
        cell117.setCellStyle(style8);

        CellStyle style9 = workbook.createCellStyle();
        style9.setVerticalAlignment(VerticalAlignment.TOP);
        style9.setAlignment(HorizontalAlignment.LEFT);
        style9.setBorderBottom(BorderStyle.NONE);
        style9.setBorderTop(BorderStyle.THIN);
        style9.setBorderLeft(BorderStyle.THIN);
        style9.setWrapText(true);
        style9.setBorderRight(BorderStyle.THIN);
        Font font9 = workbook.createFont();
        font9.setFontName("Arial");
        font9.setFontHeightInPoints((short) 10);
        style9.setFont(font9);
        cell96.setCellStyle(style9);
        cell97.setCellStyle(style9);
        cell171.setCellStyle(style9);
        cell172.setCellStyle(style9);
        cell173.setCellStyle(style9);
        cell174.setCellStyle(style9);
        cell175.setCellStyle(style9);

        CellStyle style10 = workbook.createCellStyle();
        Font font10 = workbook.createFont();
        font10.setBold(true);
        font10.setFontName("Arial");
        font10.setColor(Font.COLOR_RED);
        font10.setFontHeightInPoints((short) 11);
        style10.setWrapText(true);
        style10.setFont(font10);
        style10.setAlignment(HorizontalAlignment.LEFT);
        style10.setVerticalAlignment(VerticalAlignment.TOP);
        style10.setBorderBottom(BorderStyle.NONE);
        style10.setBorderTop(BorderStyle.THIN);
        style10.setBorderLeft(BorderStyle.THIN);
        style10.setBorderRight(BorderStyle.THIN);
        cell64.setCellStyle(style10);
        cell65.setCellStyle(style10);


        // 设置列宽
        sheet.setColumnWidth(0,30 * 256);
        sheet.setColumnWidth(1,15 * 256);
        sheet.setColumnWidth(2,15 * 256);
        sheet.setColumnWidth(3,20 * 256);
        sheet.setColumnWidth(4,20 * 256);


        // 创建PACKING LIST工作表
        Sheet sheet1 = workbook.createSheet("PACKING LIST");

        // 创建行和单元格 ,使用集合来存储单元格对象
        List<Cell> packingCells = new ArrayList<>();
        for (int row = 0; row <= 33; row++) {
            Row rows = sheet1.createRow(row);
            for (int cell = 0; cell <= 4; cell++) {
                Cell cells = rows.createCell(cell);
                packingCells.add(cells);
            }
        }

        //获取所有单元格
        Cell packCell1 = packingCells.get(0);
        Cell packCell2 = packingCells.get(1);
        Cell packCell3 = packingCells.get(2);
        Cell packCell4 = packingCells.get(3);
        Cell packCell5 = packingCells.get(4);
        Cell packCell6 = packingCells.get(5);
        Cell packCell7 = packingCells.get(6);
        Cell packCell8 = packingCells.get(7);
        Cell packCell9 = packingCells.get(8);
        Cell packCell10 = packingCells.get(9);
        Cell packCell11 = packingCells.get(10);
        Cell packCell12 = packingCells.get(11);
        Cell packCell13 = packingCells.get(12);
        Cell packCell14 = packingCells.get(13);
        Cell packCell15 = packingCells.get(14);
        Cell packCell16 = packingCells.get(15);
        Cell packCell17 = packingCells.get(16);
        Cell packCell18 = packingCells.get(17);
        Cell packCell19 = packingCells.get(18);
        Cell packCell20 = packingCells.get(19);
        Cell packCell21 = packingCells.get(20);
        Cell packCell22 = packingCells.get(21);
        Cell packCell23 = packingCells.get(22);
        Cell packCell24 = packingCells.get(23);
        Cell packCell25 = packingCells.get(24);
        Cell packCell26 = packingCells.get(25);
        Cell packCell27 = packingCells.get(26);
        Cell packCell28 = packingCells.get(27);
        Cell packCell29 = packingCells.get(28);
        Cell packCell30 = packingCells.get(29);
        Cell packCell31 = packingCells.get(30);
        Cell packCell32 = packingCells.get(31);
        Cell packCell33 = packingCells.get(32);
        Cell packCell34 = packingCells.get(33);
        Cell packCell35 = packingCells.get(34);
        Cell packCell36 = packingCells.get(35);
        Cell packCell37 = packingCells.get(36);
        Cell packCell38 = packingCells.get(37);
        Cell packCell39 = packingCells.get(38);
        Cell packCell40 = packingCells.get(39);
        Cell packCell41 = packingCells.get(40);
        Cell packCell42 = packingCells.get(41);
        Cell packCell43 = packingCells.get(42);
        Cell packCell44 = packingCells.get(43);
        Cell packCell45 = packingCells.get(44);
        Cell packCell46 = packingCells.get(45);
        Cell packCell47 = packingCells.get(46);
        Cell packCell48 = packingCells.get(47);
        Cell packCell49 = packingCells.get(48);
        Cell packCell50 = packingCells.get(49);
        Cell packCell51 = packingCells.get(50);
        Cell packCell52 = packingCells.get(51);
        Cell packCell53 = packingCells.get(52);
        Cell packCell54 = packingCells.get(53);
        Cell packCell55 = packingCells.get(54);
        Cell packCell56 = packingCells.get(55);
        Cell packCell57 = packingCells.get(56);
        Cell packCell58 = packingCells.get(57);
        Cell packCell59 = packingCells.get(58);
        Cell packCell60 = packingCells.get(59);
        Cell packCell61 = packingCells.get(60);
        Cell packCell62 = packingCells.get(61);
        Cell packCell63 = packingCells.get(62);
        Cell packCell64 = packingCells.get(63);
        Cell packCell65 = packingCells.get(64);
        Cell packCell66 = packingCells.get(65);
        Cell packCell67 = packingCells.get(66);
        Cell packCell68 = packingCells.get(67);
        Cell packCell69 = packingCells.get(68);
        Cell packCell70 = packingCells.get(69);
        Cell packCell71 = packingCells.get(70);
        Cell packCell72 = packingCells.get(71);
        Cell packCell73 = packingCells.get(72);
        Cell packCell74 = packingCells.get(73);
        Cell packCell75 = packingCells.get(74);
        Cell packCell76 = packingCells.get(75);
        Cell packCell77 = packingCells.get(76);
        Cell packCell78 = packingCells.get(77);
        Cell packCell79 = packingCells.get(78);
        Cell packCell80 = packingCells.get(79);
        Cell packCell81 = packingCells.get(80);
        Cell packCell82 = packingCells.get(81);
        Cell packCell83 = packingCells.get(82);
        Cell packCell84 = packingCells.get(83);
        Cell packCell85 = packingCells.get(84);
        Cell packCell86 = packingCells.get(85);
        Cell packCell87 = packingCells.get(86);
        Cell packCell88 = packingCells.get(87);
        Cell packCell89 = packingCells.get(88);
        Cell packCell90 = packingCells.get(89);
        Cell packCell91 = packingCells.get(90);
        Cell packCell92 = packingCells.get(91);
        Cell packCell93 = packingCells.get(92);
        Cell packCell94 = packingCells.get(93);
        Cell packCell95 = packingCells.get(94);
        Cell packCell96 = packingCells.get(95);
        Cell packCell97 = packingCells.get(96);
        Cell packCell98 = packingCells.get(97);
        Cell packCell99 = packingCells.get(98);
        Cell packCell100 = packingCells.get(99);
        Cell packCell101 = packingCells.get(100);
        Cell packCell102 = packingCells.get(101);
        Cell packCell103 = packingCells.get(102);
        Cell packCell104 = packingCells.get(103);
        Cell packCell105 = packingCells.get(104);
        Cell packCell106 = packingCells.get(105);
        Cell packCell107 = packingCells.get(106);
        Cell packCell108 = packingCells.get(107);
        Cell packCell109 = packingCells.get(108);
        Cell packCell110 = packingCells.get(109);
        Cell packCell111 = packingCells.get(110);
        Cell packCell112 = packingCells.get(111);
        Cell packCell113 = packingCells.get(112);
        Cell packCell114 = packingCells.get(113);
        Cell packCell115 = packingCells.get(114);
        Cell packCell116 = packingCells.get(115);
        Cell packCell117 = packingCells.get(116);
        Cell packCell118 = packingCells.get(117);
        Cell packCell119 = packingCells.get(118);
        Cell packCell120 = packingCells.get(119);
        Cell packCell121 = packingCells.get(120);
        Cell packCell122 = packingCells.get(121);
        Cell packCell123 = packingCells.get(122);
        Cell packCell124 = packingCells.get(123);
        Cell packCell125 = packingCells.get(124);
        Cell packCell126 = packingCells.get(125);
        Cell packCell127 = packingCells.get(126);
        Cell packCell128 = packingCells.get(127);
        Cell packCell129 = packingCells.get(128);
        Cell packCell130 = packingCells.get(129);
        Cell packCell131 = packingCells.get(130);
        Cell packCell132 = packingCells.get(131);
        Cell packCell133 = packingCells.get(132);
        Cell packCell134 = packingCells.get(133);
        Cell packCell135 = packingCells.get(134);
        Cell packCell136 = packingCells.get(135);
        Cell packCell137 = packingCells.get(136);
        Cell packCell138 = packingCells.get(137);
        Cell packCell139 = packingCells.get(138);
        Cell packCell140 = packingCells.get(139);
        Cell packCell141 = packingCells.get(140);
        Cell packCell142 = packingCells.get(141);
        Cell packCell143 = packingCells.get(142);
        Cell packCell144 = packingCells.get(143);
        Cell packCell145 = packingCells.get(144);
        Cell packCell146 = packingCells.get(145);
        Cell packCell147 = packingCells.get(146);
        Cell packCell148 = packingCells.get(147);
        Cell packCell149 = packingCells.get(148);
        Cell packCell150 = packingCells.get(149);
        Cell packCell151 = packingCells.get(150);
        Cell packCell152 = packingCells.get(151);
        Cell packCell153 = packingCells.get(152);
        Cell packCell154 = packingCells.get(153);
        Cell packCell155 = packingCells.get(154);
        Cell packCell156 = packingCells.get(155);
        Cell packCell157 = packingCells.get(156);
        Cell packCell158 = packingCells.get(157);
        Cell packCell159 = packingCells.get(158);
        Cell packCell160 = packingCells.get(159);
        Cell packCell161 = packingCells.get(160);
        Cell packCell162 = packingCells.get(161);
        Cell packCell163 = packingCells.get(162);
        Cell packCell164 = packingCells.get(163);
        Cell packCell165 = packingCells.get(164);
        Cell packCell166 = packingCells.get(165);
        Cell packCell167 = packingCells.get(166);
        Cell packCell168 = packingCells.get(167);
        Cell packCell169 = packingCells.get(168);
        Cell packCell170 = packingCells.get(169);

        // 合并单元格
        sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        sheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(4, 4, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(5, 5, 0, 2));

        sheet1.addMergedRegion(new CellRangeAddress(6, 6, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(7, 7, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(8, 8, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(9, 9, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(10,10, 0, 2));

        sheet1.addMergedRegion(new CellRangeAddress(11, 11, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(12, 12, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(13, 13, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(14, 14, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(15, 15, 0, 2));

        sheet1.addMergedRegion(new CellRangeAddress(16, 16, 0, 2));
        sheet1.addMergedRegion(new CellRangeAddress(17, 17, 0, 2));

        sheet1.addMergedRegion(new CellRangeAddress(1, 9, 3, 4));

        sheet1.addMergedRegion(new CellRangeAddress(16, 16, 3, 4));
        sheet1.addMergedRegion(new CellRangeAddress(17, 17, 3, 4));

        sheet1.addMergedRegion(new CellRangeAddress(30, 30, 0, 4));
        sheet1.addMergedRegion(new CellRangeAddress(31, 31, 0, 4));
        sheet1.addMergedRegion(new CellRangeAddress(32, 32, 0, 4));
        sheet1.addMergedRegion(new CellRangeAddress(33, 33, 0, 4));

        //修改第20行的高度
        Row packRow20 = sheet1.getRow(19);
        if (packRow20 != null) {
            short rowHeight = 3 * 256; // 要设置的行高
            packRow20.setHeight(rowHeight);
        }

        //修改第30行的高度
        Row packRow30 = sheet1.getRow(29);
        if (packRow30 != null) {
            short rowHeight = 3 * 256; // 要设置的行高
            packRow30.setHeight(rowHeight);
        }

        //修改第32行的高度
        Row packRow32 = sheet1.getRow(31);
        if (packRow32 != null) {
            short rowHeight = 5 * 256; // 要设置的行高
            packRow32.setHeight(rowHeight);
        }

        //单元格赋值
        packCell1.setCellValue("PACKING LIST");
        packCell6.setCellValue("Issuer:");
        packCell31.setCellValue("To:");
        packCell54.setCellValue("Date:");
        packCell55.setCellValue("Invoice:");
        packCell56.setCellValue("Notify Party");
        packCell64.setCellValue("ETD:");
        packCell65.setCellValue("ETA:");
        packCell74.setCellValue("Container#:");
        packCell75.setCellValue("B/L#:");
        packCell81.setCellValue("Transport Details");
        packCell84.setCellValue("Vessel Name/Voyage Number:");
        packCell91.setCellValue("Specifications");
        packCell92.setCellValue("Package");
        packCell93.setCellValue("N.W.");
        packCell94.setCellValue("G.W.");
        packCell95.setCellValue("Volume");
        packCell96.setCellValue("(英文品名)");
        packCell101.setCellValue("包装方式");
        packCell103.setCellValue("净重");
        packCell104.setCellValue("毛重");
        packCell105.setCellValue("体积");
        packCell116.setCellValue("(订单号) ERP59219579");
        packCell141.setCellValue("Total:");
        packCell151.setCellValue("Marks & Numbers");
        packCell156.setCellValue("N/M");
        packCell166.setCellValue("Thank you for your business!");

        // 设置首行字体
        CellStyle packStyle1 = workbook.createCellStyle();
        Font packFont1 = workbook.createFont();
        packFont1.setBold(true);
        packFont1.setFontName("Arial");
        packFont1.setFontHeightInPoints((short) 12);
        packStyle1.setFont(packFont1);
        packStyle1.setAlignment(HorizontalAlignment.CENTER);
        packStyle1.setBorderBottom(BorderStyle.THIN);
        packStyle1.setBorderTop(BorderStyle.THIN);
        packStyle1.setBorderLeft(BorderStyle.THIN);
        packStyle1.setBorderRight(BorderStyle.THIN);
        packCell1.setCellStyle(packStyle1);
        packCell2.setCellStyle(packStyle1);
        packCell3.setCellStyle(packStyle1);
        packCell4.setCellStyle(packStyle1);
        packCell5.setCellStyle(packStyle1);

        // 设置左右框加粗，字体加粗
        CellStyle packStyle2 = workbook.createCellStyle();
        Font packFont2 = workbook.createFont();
        packFont2.setBold(true);
        packFont2.setFontName("Arial");
        packFont2.setFontHeightInPoints((short) 11);
        packStyle2.setFont(packFont2);
        packStyle2.setAlignment(HorizontalAlignment.LEFT);
        packStyle2.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle2.setBorderBottom(BorderStyle.NONE);
        packStyle2.setBorderTop(BorderStyle.THIN);
        packStyle2.setBorderLeft(BorderStyle.THIN);
        packStyle2.setBorderRight(BorderStyle.THIN);
        packCell6.setCellStyle(packStyle2);
        packCell8.setCellStyle(packStyle2);
        packCell31.setCellStyle(packStyle2);
        packCell32.setCellStyle(packStyle2);
        packCell33.setCellStyle(packStyle2);
        packCell56.setCellStyle(packStyle2);
        packCell57.setCellStyle(packStyle2);
        packCell58.setCellStyle(packStyle2);
        packCell54.setCellStyle(packStyle2);
        packCell55.setCellStyle(packStyle2);
        packCell74.setCellStyle(packStyle2);
        packCell75.setCellStyle(packStyle2);
        packCell81.setCellStyle(packStyle2);
        packCell82.setCellStyle(packStyle2);
        packCell83.setCellStyle(packStyle2);
        packCell84.setCellStyle(packStyle2);
        packCell85.setCellStyle(packStyle2);
        packCell91.setCellStyle(packStyle2);
        packCell92.setCellStyle(packStyle2);
        packCell93.setCellStyle(packStyle2);
        packCell94.setCellStyle(packStyle2);
        packCell95.setCellStyle(packStyle2);
        packCell131.setCellStyle(packStyle2);
        packCell132.setCellStyle(packStyle2);
        packCell136.setCellStyle(packStyle2);
        packCell137.setCellStyle(packStyle2);
        packCell138.setCellStyle(packStyle2);
        packCell139.setCellStyle(packStyle2);
        packCell140.setCellStyle(packStyle2);
        packCell146.setCellStyle(packStyle2);
        packCell147.setCellStyle(packStyle2);
        packCell166.setCellStyle(packStyle2);
        packCell167.setCellStyle(packStyle2);
        packCell168.setCellStyle(packStyle2);
        packCell169.setCellStyle(packStyle2);
        packCell170.setCellStyle(packStyle2);
        packCell141.setCellStyle(packStyle2);
        packCell151.setCellStyle(packStyle2);
        packCell152.setCellStyle(packStyle2);
        packCell153.setCellStyle(packStyle2);
        packCell154.setCellStyle(packStyle2);
        packCell155.setCellStyle(packStyle2);

        // 设置中间加粗字体 有边框
        CellStyle packStyle5 = workbook.createCellStyle();
        Font packFont3 = workbook.createFont();
        packFont3.setFontName("Arial");
        packFont3.setBold(true);
        packFont3.setFontHeightInPoints((short) 11);
        packStyle5.setFont(packFont3);
        packStyle5.setAlignment(HorizontalAlignment.CENTER);
        packStyle5.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle5.setBorderBottom(BorderStyle.NONE);
        packStyle5.setBorderTop(BorderStyle.THIN);
        packStyle5.setBorderLeft(BorderStyle.THIN);
        packStyle5.setBorderRight(BorderStyle.THIN);
        packCell91.setCellStyle(packStyle5);
        packCell92.setCellStyle(packStyle5);
        packCell93.setCellStyle(packStyle5);
        packCell94.setCellStyle(packStyle5);
        packCell95.setCellStyle(packStyle5);
        packCell98.setCellStyle(packStyle5);
        packCell99.setCellStyle(packStyle5);
        packCell100.setCellStyle(packStyle5);
        packCell143.setCellStyle(packStyle5);
        packCell144.setCellStyle(packStyle5);
        packCell145.setCellStyle(packStyle5);

        // 设置中间加粗字体无边框
        CellStyle packStyle11 = workbook.createCellStyle();
        Font packFont11 = workbook.createFont();
        packFont11.setFontName("Arial");
        packFont11.setBold(true);
        packFont11.setFontHeightInPoints((short) 11);
        packStyle11.setFont(packFont11);
        packStyle11.setAlignment(HorizontalAlignment.CENTER);
        packStyle11.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle11.setBorderBottom(BorderStyle.NONE);
        packStyle11.setBorderTop(BorderStyle.NONE);
        packStyle11.setBorderLeft(BorderStyle.THIN);
        packStyle11.setBorderRight(BorderStyle.THIN);
        packCell148.setCellStyle(packStyle11);
        packCell149.setCellStyle(packStyle11);
        packCell150.setCellStyle(packStyle11);



        //设置中间字体不加粗
        CellStyle packStyle6 = workbook.createCellStyle();
        packStyle6.setAlignment(HorizontalAlignment.CENTER);
        packStyle6.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle6.setBorderBottom(BorderStyle.NONE);
        packStyle6.setBorderTop(BorderStyle.NONE);
        packStyle6.setBorderLeft(BorderStyle.THIN);
        packStyle6.setBorderRight(BorderStyle.THIN);
        Font packFont6 = workbook.createFont();
        packFont6.setFontName("Arial");
        packFont6.setFontHeightInPoints((short) 11);
        packStyle6.setFont(packFont6);
        packCell103.setCellStyle(packStyle6);
        packCell104.setCellStyle(packStyle6);
        packCell105.setCellStyle(packStyle6);
        packCell108.setCellStyle(packStyle6);
        packCell109.setCellStyle(packStyle6);
        packCell110.setCellStyle(packStyle6);
        packCell113.setCellStyle(packStyle6);
        packCell114.setCellStyle(packStyle6);
        packCell115.setCellStyle(packStyle6);
        packCell118.setCellStyle(packStyle6);
        packCell119.setCellStyle(packStyle6);
        packCell120.setCellStyle(packStyle6);
        packCell123.setCellStyle(packStyle6);
        packCell124.setCellStyle(packStyle6);
        packCell125.setCellStyle(packStyle6);
        packCell128.setCellStyle(packStyle6);
        packCell129.setCellStyle(packStyle6);
        packCell130.setCellStyle(packStyle6);

        //设置中间字体不加粗,有边框
        CellStyle packStyle7 = workbook.createCellStyle();
        packStyle7.setAlignment(HorizontalAlignment.CENTER);
        packStyle7.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle7.setBorderBottom(BorderStyle.NONE);
        packStyle7.setBorderTop(BorderStyle.THIN);
        packStyle7.setBorderLeft(BorderStyle.THIN);
        packStyle7.setBorderRight(BorderStyle.THIN);
        Font packFont5 = workbook.createFont();
        packFont5.setFontName("Arial");
        packFont5.setFontHeightInPoints((short) 11);
        packStyle7.setFont(packFont5);
        packCell133.setCellStyle(packStyle7);
        packCell134.setCellStyle(packStyle7);
        packCell135.setCellStyle(packStyle7);
        packCell142.setCellStyle(packStyle7);

        //设置中间字体不加粗,无边框
        CellStyle packStyle12 = workbook.createCellStyle();
        packStyle12.setAlignment(HorizontalAlignment.CENTER);
        packStyle12.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle12.setBorderBottom(BorderStyle.NONE);
        packStyle12.setBorderTop(BorderStyle.NONE);
        packStyle12.setBorderLeft(BorderStyle.THIN);
        packStyle12.setBorderRight(BorderStyle.THIN);
        Font packFont12 = workbook.createFont();
        packFont12.setFontName("Arial");
        packFont12.setFontHeightInPoints((short) 11);
        packStyle12.setFont(packFont12);
        packCell147.setCellStyle(packStyle12);

        // 设置左右框加粗，字不加粗
        CellStyle packStyle3 = workbook.createCellStyle();
        packStyle3.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle3.setAlignment(HorizontalAlignment.LEFT);
        packStyle3.setBorderBottom(BorderStyle.NONE);
        packStyle3.setBorderTop(BorderStyle.NONE);
        packStyle3.setBorderLeft(BorderStyle.THIN);
        packStyle3.setBorderRight(BorderStyle.THIN);
        Font packFont4 = workbook.createFont();
        packFont4.setFontName("Arial");
        packFont4.setFontHeightInPoints((short) 11);
        packStyle3.setFont(packFont4);
        packCell11.setCellStyle(packStyle3);
        packCell13.setCellStyle(packStyle3);
        packCell16.setCellStyle(packStyle3);
        packCell18.setCellStyle(packStyle3);
        packCell21.setCellStyle(packStyle3);
        packCell23.setCellStyle(packStyle3);
        packCell26.setCellStyle(packStyle3);
        packCell28.setCellStyle(packStyle3);
        packCell36.setCellStyle(packStyle3);
        packCell38.setCellStyle(packStyle3);
        packCell41.setCellStyle(packStyle3);
        packCell43.setCellStyle(packStyle3);
        packCell46.setCellStyle(packStyle3);
        packCell48.setCellStyle(packStyle3);
        packCell51.setCellStyle(packStyle3);
        packCell53.setCellStyle(packStyle3);
        packCell61.setCellStyle(packStyle3);
        packCell63.setCellStyle(packStyle3);
        packCell66.setCellStyle(packStyle3);
        packCell68.setCellStyle(packStyle3);
        packCell71.setCellStyle(packStyle3);
        packCell73.setCellStyle(packStyle3);
        packCell76.setCellStyle(packStyle3);
        packCell78.setCellStyle(packStyle3);
        packCell59.setCellStyle(packStyle3);
        packCell60.setCellStyle(packStyle3);
        packCell69.setCellStyle(packStyle3);
        packCell70.setCellStyle(packStyle3);
        packCell79.setCellStyle(packStyle3);
        packCell80.setCellStyle(packStyle3);
        packCell86.setCellStyle(packStyle3);
        packCell87.setCellStyle(packStyle3);
        packCell88.setCellStyle(packStyle3);
        packCell89.setCellStyle(packStyle3);
        packCell90.setCellStyle(packStyle3);
        packCell101.setCellStyle(packStyle3);
        packCell102.setCellStyle(packStyle3);
        packCell106.setCellStyle(packStyle3);
        packCell107.setCellStyle(packStyle3);
        packCell111.setCellStyle(packStyle3);
        packCell112.setCellStyle(packStyle3);
        packCell121.setCellStyle(packStyle3);
        packCell122.setCellStyle(packStyle3);
        packCell126.setCellStyle(packStyle3);
        packCell127.setCellStyle(packStyle3);
        packCell131.setCellStyle(packStyle3);
        packCell132.setCellStyle(packStyle3);
        packCell133.setCellStyle(packStyle3);
        packCell134.setCellStyle(packStyle3);
        packCell135.setCellStyle(packStyle3);
        packCell136.setCellStyle(packStyle3);
        packCell137.setCellStyle(packStyle3);
        packCell138.setCellStyle(packStyle3);
        packCell139.setCellStyle(packStyle3);
        packCell140.setCellStyle(packStyle3);
        packCell146.setCellStyle(packStyle3);
        packCell156.setCellStyle(packStyle3);
        packCell157.setCellStyle(packStyle3);
        packCell158.setCellStyle(packStyle3);
        packCell159.setCellStyle(packStyle3);
        packCell160.setCellStyle(packStyle3);


        // 设置边框
        CellStyle packStyle4 = workbook.createCellStyle();
        packStyle4.setAlignment(HorizontalAlignment.CENTER);
        packStyle4.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle4.setBorderBottom(BorderStyle.THIN);
        packStyle4.setBorderTop(BorderStyle.THIN);
        packStyle4.setBorderLeft(BorderStyle.THIN);
        packStyle4.setBorderRight(BorderStyle.THIN);
        Font packFont7 = workbook.createFont();
        packFont7.setBold(true);
        packFont7.setFontName("Arial");
        packFont7.setFontHeightInPoints((short) 11);
        packStyle4.setFont(packFont7);
        packCell49.setCellStyle(packStyle4);
        packCell10.setCellStyle(packStyle4);
        packCell15.setCellStyle(packStyle4);
        packCell20.setCellStyle(packStyle4);
        packCell25.setCellStyle(packStyle4);
        packCell30.setCellStyle(packStyle4);
        packCell35.setCellStyle(packStyle4);
        packCell40.setCellStyle(packStyle4);
        packCell45.setCellStyle(packStyle4);
        packCell50.setCellStyle(packStyle4);
        packCell166.setCellStyle(packStyle4);
        packCell167.setCellStyle(packStyle4);
        packCell168.setCellStyle(packStyle4);
        packCell169.setCellStyle(packStyle4);
        packCell170.setCellStyle(packStyle4);

        //额外设置订单号字体，和英文品名，简单介绍字体
        CellStyle packStyle8 = workbook.createCellStyle();
        packStyle8.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle8.setAlignment(HorizontalAlignment.LEFT);
        packStyle8.setBorderBottom(BorderStyle.NONE);
        packStyle8.setBorderTop(BorderStyle.NONE);
        packStyle8.setBorderLeft(BorderStyle.THIN);
        packStyle8.setBorderRight(BorderStyle.THIN);
        Font packFont8 = workbook.createFont();
        packFont8.setFontName("Arial");
        packFont8.setBold(true);
        packFont8.setFontHeightInPoints((short) 12);
        packStyle8.setFont(packFont8);
        packCell116.setCellStyle(packStyle8);
        packCell117.setCellStyle(packStyle8);

        CellStyle packStyle9 = workbook.createCellStyle();
        packStyle9.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle9.setAlignment(HorizontalAlignment.LEFT);
        packStyle9.setBorderBottom(BorderStyle.NONE);
        packStyle9.setBorderTop(BorderStyle.THIN);
        packStyle9.setBorderLeft(BorderStyle.THIN);
        packStyle9.setBorderRight(BorderStyle.THIN);
        Font packFont9 = workbook.createFont();
        packFont9.setFontName("Arial");
        packFont9.setFontHeightInPoints((short) 10);
        packStyle9.setFont(packFont9);
        packCell96.setCellStyle(packStyle9);
        packCell97.setCellStyle(packStyle9);

        CellStyle packStyle10 = workbook.createCellStyle();
        Font packFont10 = workbook.createFont();
        packFont10.setBold(true);
        packFont10.setFontName("Arial");
        packFont10.setColor(Font.COLOR_RED);
        packFont10.setFontHeightInPoints((short) 11);
        packStyle10.setFont(packFont10);
        packStyle10.setAlignment(HorizontalAlignment.LEFT);
        packStyle10.setVerticalAlignment(VerticalAlignment.TOP);
        packStyle10.setBorderBottom(BorderStyle.NONE);
        packStyle10.setBorderTop(BorderStyle.THIN);
        packStyle10.setBorderLeft(BorderStyle.THIN);
        packStyle10.setBorderRight(BorderStyle.THIN);
        packCell64.setCellStyle(packStyle10);
        packCell65.setCellStyle(packStyle10);
        packCell161.setCellStyle(packStyle10);
        packCell162.setCellStyle(packStyle10);
        packCell163.setCellStyle(packStyle10);
        packCell164.setCellStyle(packStyle10);
        packCell165.setCellStyle(packStyle10);

        // 设置列宽
        sheet1.setColumnWidth(0,30 * 256);
        sheet1.setColumnWidth(1,15 * 256);
        sheet1.setColumnWidth(2,15 * 256);
        sheet1.setColumnWidth(3,20 * 256);
        sheet1.setColumnWidth(4,20 * 256);



        // 创建工作表
        Sheet sheet2 = workbook.createSheet("PICTURE");

        // 创建行和单元格 ,使用集合来存储单元格对象
        List<Cell> picCell = new ArrayList<>();
        for (int row = 0; row <= 6; row++) {
            Row rows = sheet2.createRow(row);
            for (int cell = 0; cell <= 1; cell++) {
                Cell cells = rows.createCell(cell);
                picCell.add(cells);
            }
        }

        //获取所有单元格
        Cell picCell1 = picCell.get(0);
        Cell picCell2 = picCell.get(1);
        Cell picCell3 = picCell.get(2);
        Cell picCell4 = picCell.get(3);
        Cell picCell5 = picCell.get(4);
        Cell picCell6 = picCell.get(5);
        Cell picCell7 = picCell.get(6);
        Cell picCell8 = picCell.get(7);
        Cell picCell9 = picCell.get(8);
        Cell picCell10 = picCell.get(9);
        Cell picCell11 = picCell.get(10);
        Cell picCell12 = picCell.get(11);
        Cell picCell13 = picCell.get(12);
        Cell picCell14 = picCell.get(13);

        // 合并单元格
        sheet2.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet2.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));

        // 设置列宽
        sheet2.setColumnWidth(0,50 * 256);
        sheet2.setColumnWidth(1,50 * 256);

        // 设置值
        picCell2.setCellValue("Pictures Details");
        picCell4.setCellValue("订单号");
        picCell13.setCellValue("Thank you for your business!");

        //设置对应行宽
        Row row1 = sheet2.getRow(0);
        if (row1 != null) {
            short rowHeight = 8 * 256;
            row1.setHeight(rowHeight);
        }

        Row row3 = sheet2.getRow(2);
        if (row3 != null) {
            short rowHeight = 16 * 256;
            row3.setHeight(rowHeight);
        }

        Row row4 = sheet2.getRow(3);
        if (row4 != null) {
            short rowHeight = 16 * 256;
            row4.setHeight(rowHeight);
        }

        Row row5 = sheet2.getRow(4);
        if (row5 != null) {
            short rowHeight = 16 * 256;
            row5.setHeight(rowHeight);
        }

        // 默认其他样式
        CellStyle pcellStyle = workbook.createCellStyle();
        Font pFont = workbook.createFont();
        pFont.setFontName("Arial");
        pFont.setFontHeightInPoints((short) 12);
        pcellStyle.setFont(pFont);
        pcellStyle.setVerticalAlignment(VerticalAlignment.TOP);
        pcellStyle.setBorderTop(BorderStyle.THIN);
        pcellStyle.setBorderLeft(BorderStyle.THIN);
        pcellStyle.setBorderRight(BorderStyle.THIN);
        pcellStyle.setBorderBottom(BorderStyle.THIN);
        picCell1.setCellStyle(pcellStyle);
        picCell3.setCellStyle(pcellStyle);
        picCell5.setCellStyle(pcellStyle);
        picCell6.setCellStyle(pcellStyle);
        picCell7.setCellStyle(pcellStyle);
        picCell8.setCellStyle(pcellStyle);
        picCell9.setCellStyle(pcellStyle);
        picCell10.setCellStyle(pcellStyle);
        picCell11.setCellStyle(pcellStyle);
        picCell12.setCellStyle(pcellStyle);

        //订单字体样式
        CellStyle pcellStyle1 = workbook.createCellStyle();
        Font pFont1 = workbook.createFont();
        pFont1.setFontName("Arial Black");
        pFont1.setFontHeightInPoints((short) 26);
        pcellStyle1.setFont(pFont1);
        pcellStyle1.setAlignment(HorizontalAlignment.CENTER);
        pcellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        pcellStyle1.setBorderTop(BorderStyle.THIN);
        pcellStyle1.setBorderLeft(BorderStyle.THIN);
        pcellStyle1.setBorderRight(BorderStyle.THIN);
        pcellStyle1.setBorderBottom(BorderStyle.THIN);
        picCell2.setCellStyle(pcellStyle1);

        //订单号样式
        CellStyle pcellStyle2 = workbook.createCellStyle();
        Font pFont2 = workbook.createFont();
        pFont2.setFontName("宋体");
        pFont2.setFontHeightInPoints((short) 16);
        pcellStyle2.setFont(pFont2);
        pcellStyle2.setAlignment(HorizontalAlignment.RIGHT);
        pcellStyle2.setBorderTop(BorderStyle.THIN);
        pcellStyle2.setBorderLeft(BorderStyle.THIN);
        pcellStyle2.setBorderRight(BorderStyle.THIN);
        pcellStyle2.setBorderBottom(BorderStyle.THIN);
        picCell4.setCellStyle(pcellStyle2);

        // 设置边框
        CellStyle pstyle = workbook.createCellStyle();
        pstyle.setAlignment(HorizontalAlignment.CENTER);
        pstyle.setVerticalAlignment(VerticalAlignment.TOP);
        pstyle.setBorderBottom(BorderStyle.THIN);
        pstyle.setBorderTop(BorderStyle.THIN);
        pstyle.setBorderLeft(BorderStyle.THIN);
        pstyle.setBorderRight(BorderStyle.THIN);
        Font pfont7 = workbook.createFont();
        pfont7.setBold(true);
        pfont7.setFontName("Arial");
        pfont7.setFontHeightInPoints((short) 11);
        pstyle.setFont(pfont7);
        picCell13.setCellStyle(pstyle);
        picCell14.setCellStyle(pstyle);


        String excelName = "Invoice Packing List Pictures";

        try {
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + java.net.URLEncoder.encode(excelName + ".xlsx", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream out = null;
        try  {
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
        IoUtil.close(out);
    }

//
//    public class ExportCellImageExample {
//        public static void main(String[] args) {
//            try (Workbook workbook = new XSSFWorkbook()) {
//                Sheet sheet = workbook.createSheet("Sheet1");
//
//                // 在指定单元格插入图片
//                InputStream imageInputStream = ExportCellImageExample.class.getResourceAsStream("image.jpg");
//                byte[] imageBytes = IOUtils.toByteArray(imageInputStream);
//                int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
//                imageInputStream.close();
//
//                CreationHelper creationHelper = workbook.getCreationHelper();
//                Drawing drawing = sheet.createDrawingPatriarch();
//                ClientAnchor anchor = creationHelper.createClientAnchor();
//                anchor.setCol1(0); // 图片起始列
//                anchor.setRow1(0); // 图片起始行
//
//                // 设置图片的大小
//                Picture picture = drawing.createPicture(anchor, pictureIdx);
//                picture.resize();
//
//                // 将工作簿保存到文件
//                try (OutputStream fileOut = new FileOutputStream("output.xlsx")) {
//                    workbook.write(fileOut);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}