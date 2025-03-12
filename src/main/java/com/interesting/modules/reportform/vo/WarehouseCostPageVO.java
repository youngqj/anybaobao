package com.interesting.modules.reportform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.interesting.config.BigDecimalSerializer2;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WarehouseCostPageVO {

    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "LOT")
    private String warehouseLot;

    @ApiModelProperty(value = "PO号")
    private String orderNum;

    @ApiModelProperty(value = "实际提完时间")
    private String realFinishTime;

    /**订单号*/
    @ApiModelProperty(value = "是否提完")
    private Integer izFinished;


    @ApiModelProperty(value = "托盘数量")
    private Integer palletNum;


    @ApiModelProperty(value = "每箱磅数（包装方式）")
    private String packing;

    @ApiModelProperty(value = "总净重(磅数)")
    private BigDecimal totalWeight;

    /**
     * 预计出库时间
     */
    @ApiModelProperty(value = "预计出库时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")

    private Date expectedDeliveryTime;
    /**
     *
     */
    @ApiModelProperty(value = "入库时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;


    /**
     * 仓储时间
     */
    @ApiModelProperty(value = "仓储时间")
    private String monthDiff;

    /**
     * 未入库成本单价
     */
    @ApiModelProperty(value = "入库数量")
//    @JsonSerialize(using = BigDecimalSerializer2.class)
    private Integer enterNum;


    /**
     * 未入库成本单价
     */
    @ApiModelProperty(value = "未入库成本单价")
    private BigDecimal notReceivedPrice;

    /**
     * 单箱毛重
     */

    @ApiModelProperty(value = "单箱毛重")
    private BigDecimal weightPerLb = BigDecimal.ZERO;

    /**
     * 总毛重
     */

    @ApiModelProperty(value = "总毛重")
    private BigDecimal totalBoxesWeight = BigDecimal.ZERO;



//    /**入库操作费*/
//    @JsonSerialize(using = BigDecimalSerializer2.class)
//    @ApiModelProperty(value = "入库操作费")
//    private BigDecimal operationFee = BigDecimal.ZERO;

    /**
     * 第一个月仓储费
     */
    @ApiModelProperty(value = "第一个月仓储费", required = true)
    private BigDecimal firstMonthFee = BigDecimal.ZERO;

    /**第二个月仓储费*/
    @ApiModelProperty(value = "第二个月仓储费")
    private BigDecimal secondMonthFee = BigDecimal.ZERO;

    /**第三个月仓储费*/
    @ApiModelProperty(value = "第三个月仓储费")
    private BigDecimal thirdMonthFee = BigDecimal.ZERO;

    /** 第四个月仓储费 */
    @ApiModelProperty(value = "第四个月仓储费")
    private BigDecimal forthMonthFee = BigDecimal.ZERO;

    /** 第五到十二个月仓储费 */
    @ApiModelProperty(value = "第五到十二个月仓储费")
    private BigDecimal fifthMonthFee = BigDecimal.ZERO;

    /**
     * 超出月份
     */
    @ApiModelProperty(value = "超出月份")
    private Integer overMonth;

//    /** 超出月份总仓储费 */
//    @ApiModelProperty(value = "超出月份总仓储费")
//    @JsonSerialize(using = BigDecimalSerializer2.class)
//    private BigDecimal overMonthFee = BigDecimal.ZERO;

    /** 出库费 */
    @ApiModelProperty(value = "出库费")
    private BigDecimal deliveryFee = BigDecimal.ZERO;

    /** 仓库总费用 */
    @ApiModelProperty(value = "仓库总费用")
    private BigDecimal warehouseTotalFee = BigDecimal.ZERO;

    /**仓库费用平均到每磅的价格*/
    @ApiModelProperty(value = "仓库费用平均到每磅的价格")
    private BigDecimal warehouseFeePerlbs= BigDecimal.ZERO;

//    /**佣金*/
//    @ApiModelProperty(value = "佣金")
//    @JsonSerialize(using = BigDecimalSerializer2.class)
//    private BigDecimal commission = BigDecimal.ZERO;

    /** 辅料采购金额（辅料合计1） */
    @ApiModelProperty(value = "含仓储费的成本")
    private BigDecimal warehouseFeePrice = BigDecimal.ZERO;


}
