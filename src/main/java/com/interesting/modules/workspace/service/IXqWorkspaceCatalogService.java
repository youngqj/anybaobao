package com.interesting.modules.workspace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.workspace.dto.XqWorkspaceCatalogDTO;
import com.interesting.modules.workspace.entity.XqWorkspaceCatalog;
import com.interesting.modules.workspace.model.XqWorkspaceFileTreeModel;

import java.util.List;

/**
 * 工作区目录表(XqWorkspaceCatalog)表服务接口
 *
 * @author 郭征宇
 * @since 2023-11-13 10:01:12
 */
public interface IXqWorkspaceCatalogService extends IService<XqWorkspaceCatalog> {



    /**
     * 新增数据
     *
     * @param dto 
     * @return boolean
     */
    boolean insert(XqWorkspaceCatalogDTO dto);

    /**
     * 修改数据
     *
     * @param dto
     * @return boolean
     */
    boolean update(XqWorkspaceCatalogDTO dto);


    List<XqWorkspaceFileTreeModel> getCatalogTree(String roleId);

    List<XqWorkspaceFileTreeModel> getCatalogTreeByAirport(String roleId);

    boolean moveCategory(String id,String pId);

}
