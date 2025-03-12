package com.interesting.modules.workspace.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.workspace.dto.XqWorkspaceCatalogDTO;
import com.interesting.modules.workspace.entity.XqWorkspaceCatalog;
import com.interesting.modules.workspace.mapper.XqWorkspaceCatalogMapper;
import com.interesting.modules.workspace.model.XqWorkspaceFileTreeModel;
import com.interesting.modules.workspace.service.IXqWorkspaceCatalogService;
import com.interesting.modules.workspace.util.FindsXqBuildDocumentChildrenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工作区目录表(XqWorkspaceCatalog)表服务实现类
 *
 * @author 郭征宇
 * @since 2023-11-13 10:01:13
 */
@Service
public class XqWorkspaceCatalogServiceImpl extends ServiceImpl<XqWorkspaceCatalogMapper, XqWorkspaceCatalog> implements IXqWorkspaceCatalogService {
    @Resource
    private XqWorkspaceCatalogMapper xqWorkspaceCatalogMapper;


    /**
     * 新增数据
     *
     * @param dto
     * @return
     */
    @Override
    public boolean insert(XqWorkspaceCatalogDTO dto) {
        XqWorkspaceCatalog xqWorkspaceCatalog = new XqWorkspaceCatalog();
        BeanUtils.copyProperties(dto, xqWorkspaceCatalog);

        return this.save(xqWorkspaceCatalog);
    }

    /**
     * 修改数据
     *
     * @param dto
     * @return
     */
    @Override
    public boolean update(XqWorkspaceCatalogDTO dto) {
        String id = dto.getId();
        XqWorkspaceCatalog xqWorkspaceCatalog = this.getById(id);
        if (xqWorkspaceCatalog == null) throw new InterestingBootException("修改失败，该数据不存在，请刷新后重试");

        return this.lambdaUpdate()
                .set(XqWorkspaceCatalog::getCatalogName, dto.getCatalogName())
                .eq(XqWorkspaceCatalog::getId, dto.getId())
                .update();
    }

    @Override
    public List<XqWorkspaceFileTreeModel> getCatalogTree(String roleId) {


        List<XqWorkspaceCatalog> list = this.lambdaQuery().eq(StringUtils.isNotBlank(roleId), XqWorkspaceCatalog::getRoleId, roleId)
                .eq(XqWorkspaceCatalog::getIzAir, 0).list();
        return FindsXqBuildDocumentChildrenUtil.wrapTreeDataToTreeList(list);
    }

    @Override
    public List<XqWorkspaceFileTreeModel> getCatalogTreeByAirport(String roleId) {
        List<XqWorkspaceCatalog> list = this.lambdaQuery().eq(XqWorkspaceCatalog::getIzAir, 1)
                .eq(StringUtils.isNotBlank(roleId), XqWorkspaceCatalog::getRoleId, roleId).list();
        return FindsXqBuildDocumentChildrenUtil.wrapTreeDataToTreeList(list);
    }

    @Override
    public boolean moveCategory(String id, String pId) {

        if (!"0".equals(pId)) {
            XqWorkspaceCatalog xqWorkspaceCatalog = this.getById(pId);
            if (xqWorkspaceCatalog == null) throw new InterestingBootException("父目录不存在");
        }
        if (id.equals(pId)) {
            throw new InterestingBootException("父目录不能是当前目录");
        }

        this.lambdaUpdate().set(XqWorkspaceCatalog::getPId, pId).eq(XqWorkspaceCatalog::getId, id).update();
        return true;

    }


}
