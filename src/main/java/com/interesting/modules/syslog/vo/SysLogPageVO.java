package com.interesting.modules.syslog.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.interesting.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SysLogPageVO {
    /**
     * id
     */

    private String id;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 耗时
     */
    @ApiModelProperty(value = "耗时")
    private Long costTime;

    /**
     * IP
     */
    @ApiModelProperty(value = "IP")
    private String ip;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    /**
     * 请求类型
     */
    @ApiModelProperty(value = "请求类型")
    private String requestType;

    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    private String requestUrl;

    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法")
    private String method;

    /**
     * 操作人用户名称
     */
    @ApiModelProperty(value = "操作人用户名称")
    private String username;
    /**
     * 操作人用户账户
     */
    @ApiModelProperty(value = "操作人用户账户")
    private String userid;
    /**
     * 操作详细日志
     */
    @ApiModelProperty(value = "操作详细日志")
    private String logContent;

    /**
     * 日志类型（1登录日志，2操作日志）
     */
    @ApiModelProperty(value = "日志类型（1登录日志，2操作日志）")
    private Integer logType;

    /**
     * 操作类型（1查询，2添加，3修改，4删除,5导入，6导出）
     */
    @ApiModelProperty(value = "操作类型（1查询，2添加，3修改，4删除,5导入，6导出）")
    private Integer operateType;
}
