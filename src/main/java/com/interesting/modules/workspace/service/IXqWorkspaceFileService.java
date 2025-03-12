package com.interesting.modules.workspace.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.workspace.dto.InsertDocumentsDTO;
import com.interesting.modules.workspace.dto.ListXqWorkspaceFileDTO;
import com.interesting.modules.workspace.dto.MoveDocumentsDTO;
import com.interesting.modules.workspace.entity.XqWorkspaceFile;
import com.interesting.modules.workspace.vo.ListXqWorkspaceFileVO;

/**
 * 工作区文件表(XqWorkspaceFile)表服务接口
 *
 * @author 郭征宇
 * @since 2023-11-13 10:01:28
 */
public interface IXqWorkspaceFileService extends IService<XqWorkspaceFile> {


    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @param page      分页对象
     * @return 查询结果
     */
    IPage<ListXqWorkspaceFileVO> queryByPage(ListXqWorkspaceFileDTO dto, Page<ListXqWorkspaceFileVO> page);





    void insertDocuments(InsertDocumentsDTO dto);

    boolean moveDocuments(MoveDocumentsDTO dto);

}
