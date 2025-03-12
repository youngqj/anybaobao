package com.interesting.modules.files.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.files.dto.InstOrUpdtXqFilesDTO;
import com.interesting.modules.files.dto.ListXqFileDTO;
import com.interesting.modules.files.entity.XqFiles;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.files.vo.ListXqFileVO;

import java.util.List;

/**
* @author 26773
* @description 针对表【xq_files(附件表)】的数据库操作Service
* @createDate 2023-06-21 17:13:29
*/
public interface XqFilesService extends IService<XqFiles> {

    IPage<ListXqFileVO> pageList(Page<ListXqFileVO> page, ListXqFileDTO dto);

    IPage<ListXqFileVO> pageFileByNodeId(Page<ListXqFileVO> page,
                                         String nodeId,
                                         String username,
                                         String fileName);

    boolean addFileToTree(InstOrUpdtXqFilesDTO dto);

    boolean moveBatchFiles(String ids, String nodeId);

    boolean addFileToTreeByList(List<InstOrUpdtXqFilesDTO> dtos);

}
