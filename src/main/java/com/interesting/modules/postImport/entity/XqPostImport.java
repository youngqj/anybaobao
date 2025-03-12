package com.interesting.modules.postImport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 导入记录
 *
 * @Date:   2021-12-06
 * @Version: V1.0
 */
@Data
@TableName("xq_post_import")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="xq_post_import对象", description="导入记录")
public class XqPostImport implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**导入类型*/
    @ApiModelProperty(value = "导入类型")
    private String importType;
	/**导入人*/
    @ApiModelProperty(value = "导入人")
    private String importPerson;
	/**导入时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "导入时间")
    private Date importTime;
	/**导入结果*/
    @ApiModelProperty(value = "导入结果")
    private String importResult;
	/**导入数据量*/
    @ApiModelProperty(value = "导入数据量")
    private Integer importCount;

	@ApiModelProperty(value = "下载路径")
    private String downloadUrl;
}
