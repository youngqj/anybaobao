package com.interesting.modules.workspace.model;

import com.interesting.modules.workspace.entity.XqWorkspaceCatalog;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @DESCRIPTION:
 * @AUTHOR: 郭征宇
 * @DATE: 2023/4/12 9:49
 * @VERSION: V1.0
 */

public class XqWorkspaceFileTreeModel {

    private static final long serialVersionUID = 1L;
    /**
     * 对应XqWarehouse中的id字段,前端数据树中的key
     */
    private String key;

    /**
     * 对应XqWarehouse中的id字段,前端数据树中的value
     */
    private String value;

    /**
     * 对应name字段,前端数据树中的title
     */
    private String title;


    private Boolean isLeaf;

    @ApiModelProperty("主键")
    private String id;

    /**
     * 父id  默认为0 即根元素
     */
    @ApiModelProperty("父id  默认为0 即根元素")
    private String pId;

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private String roleId;

    /**
     * 文件夹名
     */
    @ApiModelProperty("目录名")
    private String catalogName;


    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除0正常 1已删除")
    private Integer izDelete;


    @ApiModelProperty("子分类")
    private List<XqWorkspaceFileTreeModel> children = new ArrayList<>();


    public XqWorkspaceFileTreeModel(XqWorkspaceCatalog xqWorkspaceCatalog) {

        this.key = xqWorkspaceCatalog.getId();
        this.value = xqWorkspaceCatalog.getId();
        this.roleId = xqWorkspaceCatalog.getRoleId();
        this.title = xqWorkspaceCatalog.getCatalogName();
        this.id = xqWorkspaceCatalog.getId();
        this.pId = xqWorkspaceCatalog.getPId();
        this.catalogName = xqWorkspaceCatalog.getCatalogName();
        this.createBy = xqWorkspaceCatalog.getCreateBy();
        this.createTime = xqWorkspaceCatalog.getCreateTime();
        this.updateBy = xqWorkspaceCatalog.getUpdateBy();
        this.updateTime = xqWorkspaceCatalog.getUpdateTime();
        this.izDelete = xqWorkspaceCatalog.getIzDelete();
    }


    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isleaf) {
        this.isLeaf = isleaf;
    }

    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<XqWorkspaceFileTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<XqWorkspaceFileTreeModel> children) {
        if (children == null) {
            this.isLeaf = true;
        }
        this.children = children;
    }


    public String getRoleId(){return roleId;}



    public String getPId() {
        return pId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setPId(String parentId) {
        this.pId = parentId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }



    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public Integer getIzDelete() {
        return izDelete;
    }

    public void setIzDelete(Integer izDelete) {
        this.izDelete = izDelete;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public XqWorkspaceFileTreeModel() {
    }

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
        XqWorkspaceFileTreeModel model = (XqWorkspaceFileTreeModel) o;
        return Objects.equals(id, model.id) &&
                Objects.equals(pId, model.pId) &&
                Objects.equals(roleId,model.roleId)&&
                Objects.equals(catalogName, model.catalogName) &&
                Objects.equals(izDelete, model.izDelete) &&
                Objects.equals(createBy, model.createBy) &&
                Objects.equals(createTime, model.createTime) &&
                Objects.equals(updateBy, model.updateBy) &&
                Objects.equals(updateTime, model.updateTime) &&
                Objects.equals(children, model.children);
    }

    /**
     * 重写hashCode方法
     */
    @Override
    public int hashCode() {

        return Objects.hash(id, pId,roleId, catalogName, izDelete, createBy, createTime, updateBy, updateTime,
                children);
    }
}
