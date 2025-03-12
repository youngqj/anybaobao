package com.interesting.modules.workspace.model;

import com.interesting.modules.workspace.entity.XqWorkspaceCatalog;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class XqWorkspaceFileIdModel {

    private static final long serialVersionUID = 1L;

    // 主键ID
    private String key;

    // 主键ID
    private String value;

    // 部门名称
    private String title;

    List<XqWorkspaceFileIdModel> children = new ArrayList<>();

    /**
     * 将XqWarehouseTypeTreeModel的部分数据放在该对象当中
     *
     * @param treeModel
     * @return
     */
    public XqWorkspaceFileIdModel convert(XqWorkspaceFileTreeModel treeModel) {
        this.key = treeModel.getId();
        this.value = treeModel.getId();
        this.title = treeModel.getCatalogName();
        return this;
    }

    /**
     * 该方法为用户部门的实现类所使用
     *
     * @param xqWorkspaceCatalog
     * @return
     */
    public XqWorkspaceFileIdModel convertByUserDepart(XqWorkspaceCatalog xqWorkspaceCatalog) {
        this.key = xqWorkspaceCatalog.getId();
        this.value = xqWorkspaceCatalog.getId();
        this.title = xqWorkspaceCatalog.getCatalogName();
        return this;
    }

    public List<XqWorkspaceFileIdModel> getChildren() {
        return children;
    }

    public void setChildren(List<XqWorkspaceFileIdModel> children) {
        this.children = children;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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


}
