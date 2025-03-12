package com.interesting.modules.workspace.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.modules.workspace.dto.InsertDocumentsDTO;
import com.interesting.modules.workspace.dto.ListXqWorkspaceFileDTO;
import com.interesting.modules.workspace.dto.MoveDocumentsDTO;
import com.interesting.modules.workspace.entity.XqWorkspaceCatalog;
import com.interesting.modules.workspace.entity.XqWorkspaceFile;
import com.interesting.modules.workspace.mapper.XqWorkspaceFileMapper;
import com.interesting.modules.workspace.service.IXqWorkspaceCatalogService;
import com.interesting.modules.workspace.service.IXqWorkspaceFileService;
import com.interesting.modules.workspace.vo.ListXqWorkspaceFileVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作区文件表(XqWorkspaceFile)表服务实现类
 *
 * @author 郭征宇
 * @since 2023-11-13 10:01:28
 */
@Service
public class XqWorkspaceFileServiceImpl extends ServiceImpl<XqWorkspaceFileMapper, XqWorkspaceFile> implements IXqWorkspaceFileService {
    @Resource
    private XqWorkspaceFileMapper xqWorkspaceFileMapper;
    @Resource
    private IXqWorkspaceCatalogService xqWorkspaceCatalogService;


    /**
     * 分页查询
     *
     * @param dto  筛选条件
     * @param page 分页对象
     * @return 查询结果
     */
    @Override
    public IPage<ListXqWorkspaceFileVO> queryByPage(ListXqWorkspaceFileDTO dto, Page<ListXqWorkspaceFileVO> page) {

        return xqWorkspaceFileMapper.queryByPage(dto, page);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDocuments(InsertDocumentsDTO dto) {

        String catalogId = dto.getCatalogId();
        // 检验传入的目录id是否存在，如果是多角色下还需要拼接角色id
        XqWorkspaceCatalog one = xqWorkspaceCatalogService.lambdaQuery().eq(XqWorkspaceCatalog::getId, catalogId).one();
        if (one == null) throw new InterestingBootException("新增失败，该目录不存在");


        List<XqWorkspaceFile> xqWorkspaceFiles = dto.getXqWorkspaceFileDTOS().stream().map(xqWorkspaceFileDTO -> {

            XqWorkspaceFile xqWorkspaceFile = new XqWorkspaceFile();
            BeanUtils.copyProperties(xqWorkspaceFileDTO, xqWorkspaceFile);
            xqWorkspaceFile.setCatalogId(catalogId);
            return xqWorkspaceFile;
        }).collect(Collectors.toList());


        this.saveBatch(xqWorkspaceFiles);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean moveDocuments(MoveDocumentsDTO dto) {
        if (xqWorkspaceCatalogService.getById(dto.getCatalogId()) == null) throw new InterestingBootException("该目录不存在");

        ArrayList<String> documentsIdList = ListUtil.toList(dto.getDocumentsIdList().split(","));


        return this.update(new LambdaUpdateWrapper<XqWorkspaceFile>().set(XqWorkspaceFile::getCatalogId,dto.getCatalogId()).in(XqWorkspaceFile::getId,documentsIdList));
    }



}
