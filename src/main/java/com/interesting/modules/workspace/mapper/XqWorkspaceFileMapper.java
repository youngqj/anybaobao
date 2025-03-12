package com.interesting.modules.workspace.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.workspace.dto.ListXqWorkspaceFileDTO;
import com.interesting.modules.workspace.entity.XqWorkspaceFile;
import com.interesting.modules.workspace.vo.ListXqWorkspaceFileVO;

/**
 * 工作区文件表(XqWorkspaceFile)表数据库访问层
 *
 * @author 郭征宇
 * @since 2023-11-13 10:01:28
 */
public interface XqWorkspaceFileMapper extends BaseMapper<XqWorkspaceFile> {


    
    
    IPage<ListXqWorkspaceFileVO> queryByPage(ListXqWorkspaceFileDTO dto, Page<ListXqWorkspaceFileVO> page);
    

}

