package com.interesting.modules.files.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.BeanUtils;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.files.dto.InstOrUpdtXqFilesDTO;
import com.interesting.modules.files.dto.ListXqFileDTO;
import com.interesting.modules.files.entity.XqFiles;
import com.interesting.modules.files.service.XqFilesService;
import com.interesting.modules.files.mapper.XqFilesMapper;
import com.interesting.modules.files.vo.ListXqFileVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
* @author 26773
* @description 针对表【xq_files(附件表)】的数据库操作Service实现
* @createDate 2023-06-21 17:13:29
*/
@Service
public class XqFilesServiceImpl extends ServiceImpl<XqFilesMapper, XqFiles>
    implements XqFilesService{

    @Override
    public IPage<ListXqFileVO> pageList(Page<ListXqFileVO> page, ListXqFileDTO dto) {
        return this.baseMapper.pageList(page, dto);

    }

    @Override
    public IPage<ListXqFileVO> pageFileByNodeId(Page<ListXqFileVO> page, String nodeId,
                                                String username,
                                                String fileName) {
        return this.baseMapper.pageFileByNodeId(page, nodeId, username, fileName);

    }

    @Override
    public boolean addFileToTree(InstOrUpdtXqFilesDTO dto) {
        XqFiles xqFiles = new XqFiles();
        BeanUtils.copyProperties(dto, xqFiles);
        xqFiles.setId(null);
        return this.save(xqFiles);
    }

    @Override
    public boolean moveBatchFiles(String ids, String nodeId) {
        return this.lambdaUpdate().set(XqFiles::getNodeId, nodeId)
                .in(XqFiles::getId, Arrays.asList(ids.split(","))).update();
    }

    @Override
    public boolean addFileToTreeByList(List<InstOrUpdtXqFilesDTO> dtos) {

        for (InstOrUpdtXqFilesDTO dto : dtos) {
            if (("操作失败，上传失败").equals(dto.getFileUrl())) {
                continue;
            }
            XqFiles currentFile = this.getOne(new LambdaQueryWrapper<XqFiles>().eq(XqFiles::getFileName, dto.getFileName()).eq(XqFiles::getNodeId, dto.getNodeId()).last("limit 1"));
            if (currentFile != null) {
                throw new InterestingBootException("文件名：" + dto.getFileName() + "在当前目录已存在。不能上传");
            }
            XqFiles xqFiles = new XqFiles();
            BeanUtils.copyProperties(dto, xqFiles);
            xqFiles.setId(null);
            this.save(xqFiles);
        }
        return true;
    }
}




