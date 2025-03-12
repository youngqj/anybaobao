package com.interesting.modules.order.vo.sub;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class XqOrderRemiExpDateVO {
    private String id;

    /**
     * 收款到期日
     */
    @ApiModelProperty(value = "收款到期日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date remittanceExpireDate;

    @ApiModelProperty("金额")
    private BigDecimal price;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
