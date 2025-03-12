package com.interesting.business.system.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.interesting.common.system.base.entity.InterestingEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 用户通告阅读标记表
 * @Author: interesting-boot
 * @Date:  2019-02-21
 * @Version: V1.0
 */
@Data
@TableName("sys_announcement_send")
public class SysAnnouncementSend implements Serializable {
    private static final long serialVersionUID = 1L;
	/**通告id*/
	private String anntId;
	/**用户id*/
	private String userId;
	/**阅读状态（0未读，1已读）*/
	private String readFlag;
	/**阅读时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date readTime;
	/** ID */
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "ID")
	private String id;
	/** 创建人 */
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/** 更新人 */
	@ApiModelProperty(value = "更新人")

	private String updateBy;
	/** 更新时间 */
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
}
