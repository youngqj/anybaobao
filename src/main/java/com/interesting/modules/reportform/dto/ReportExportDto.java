package com.interesting.modules.reportform.dto;

import com.interesting.modules.reportform.vo.FinanceReportVO;
import com.interesting.modules.reportform.vo.SumFinanceReportVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReportExportDto {

    @ApiModelProperty("自定义列")
    private String exportColumn;
    @ApiModelProperty("财务报表数据")
    private List<FinanceReportVO> list;
    @ApiModelProperty("财务报表合计数据")
    private List<SumFinanceReportVO> vo;
}
