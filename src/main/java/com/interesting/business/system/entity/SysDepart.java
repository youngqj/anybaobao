package com.interesting.business.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.interesting.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 部门表
 * <p>
 *
 * @Author Steve
 * @Since 2019-01-22
 */
@Data
@TableName("sys_depart")
public class SysDepart implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 父机构ID
     */
    @ApiModelProperty(value = "父ID")
    private String parentId;
    /**
     * 公司id
     */
    private String enterpriseInfoId;
    /**
     * 机构/部门名称
     */
    @ApiModelProperty(value = "部门名称/机构名称")
    @NotNull(message = "部门名称不为空")
    private String departName;
    /**
     * 英文名
     */
    private String departNameEn;
    /**
     * 缩写
     */
    private String departNameAbbr;
    /**
     * 排序
     */
    @ApiModelProperty(value = "序号")
    private Integer departOrder;
    /**
     * 描述
     */
    private String description;
    /**
     * 机构类别 1公司，2组织机构，2岗位
     */
    @ApiModelProperty(value = "机构类型对应字典code：org_category")
    private String orgCategory;
    /**
     * 机构类型
     */
    private String orgType;
    /**
     * 机构编码
     */
    private String orgCode;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "联系电话")
    private String mobile;
    /**
     * 传真
     */
    private String fax;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注说明")
    private String memo;
    /**
     * 状态（1启用，0不启用）
     */
    @Dict(dicCode = "depart_status")
    private String status;
    /**
     * 删除状态（0，正常，1已删除）
     */
    private String izDelete;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 重写equals方法
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SysDepart depart = (SysDepart) o;
        return Objects.equals(id, depart.id) &&
                Objects.equals(parentId, depart.parentId) &&
                Objects.equals(departName, depart.departName) &&
                Objects.equals(departNameEn, depart.departNameEn) &&
                Objects.equals(departNameAbbr, depart.departNameAbbr) &&
                Objects.equals(departOrder, depart.departOrder) &&
                Objects.equals(description, depart.description) &&
                Objects.equals(orgCategory, depart.orgCategory) &&
                Objects.equals(orgType, depart.orgType) &&
                Objects.equals(orgCode, depart.orgCode) &&
                Objects.equals(mobile, depart.mobile) &&
                Objects.equals(fax, depart.fax) &&
                Objects.equals(address, depart.address) &&
                Objects.equals(memo, depart.memo) &&
                Objects.equals(status, depart.status) &&
                Objects.equals(izDelete, depart.izDelete) &&
                Objects.equals(createBy, depart.createBy) &&
                Objects.equals(createTime, depart.createTime) &&
                Objects.equals(updateBy, depart.updateBy) &&
                Objects.equals(updateTime, depart.updateTime);
    }

    /**
     * 重写hashCode方法
     */
    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, parentId, departName,
                departNameEn, departNameAbbr, departOrder, description, orgCategory,
                orgType, orgCode, mobile, fax, address, memo, status,
                izDelete, createBy, createTime, updateBy, updateTime);
    }
}
