package com.interesting.modules.accmaterial.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.entity.SysCategory;
import com.interesting.business.system.entity.SysUser;
import com.interesting.business.system.model.TreeSelectModel;
import com.interesting.common.constant.FillRuleConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.FillRuleUtil;
import com.interesting.common.util.StringUtils;
import com.interesting.common.util.oConvertUtils;
import com.interesting.modules.accmaterial.dto.AddUptAccCategoryDTO;
import com.interesting.modules.accmaterial.dto.InstUptCategoryTreeNodeDTO;
import com.interesting.modules.accmaterial.entity.XqAccessoryCategory;
import com.interesting.modules.accmaterial.entity.XqAccessoryInfo;
import com.interesting.modules.accmaterial.service.XqAccessoryCategoryService;
import com.interesting.modules.accmaterial.mapper.XqAccessoryCategoryMapper;
import com.interesting.modules.accmaterial.service.XqAccessoryInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 26773
* @description 针对表【xq_accessory_category(辅料分类表)】的数据库操作Service实现
* @createDate 2023-07-03 13:48:00
*/
@Service
public class XqAccessoryCategoryServiceImpl extends ServiceImpl<XqAccessoryCategoryMapper, XqAccessoryCategory>
    implements XqAccessoryCategoryService{

    @Autowired
    private XqAccessoryInfoService xqAccessoryInfoService;
    @Autowired
    private XqAccessoryCategoryService xqAccessoryCategoryService;

    @Override
    public boolean deleteBatchAccCategory(String ids) {
        List<String> strings = Arrays.asList(ids.split(","));
        for (String string : strings) {
            if (xqAccessoryInfoService.lambdaQuery()
                    .eq(XqAccessoryInfo::getCategoryId, string)
                    .count() > 0) {
                throw new InterestingBootException("所选分类下有辅料，请先删除分类下的辅料");
            }
        }

        return this.removeByIds(strings);
    }

    @Override
    public boolean addAccCategory(AddUptAccCategoryDTO dto) {
        String name = dto.getName();
        if (xqAccessoryCategoryService.lambdaQuery()
                .eq(XqAccessoryCategory::getName, name)
                .count() > 0) {
            throw new InterestingBootException("分类名称已存在");
        }

        XqAccessoryCategory xqAccessoryCategory = new XqAccessoryCategory();
        BeanUtils.copyProperties(dto, xqAccessoryCategory);

        return xqAccessoryCategoryService.save(xqAccessoryCategory);
    }

    @Override
    public List<JSONObject> listAccByCategoryId(String id) {
        List<XqAccessoryInfo> list = xqAccessoryInfoService.lambdaQuery()
                .eq(XqAccessoryInfo::getCategoryId, id)
                .list();

        List<JSONObject> collect = list.stream().map(i -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("accessoryName", i.getAccessoryName());
            jsonObject.put("size", i.getSize());
            return jsonObject;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public boolean editAccCategory(AddUptAccCategoryDTO dto) {
        String name = dto.getName();
        if (xqAccessoryCategoryService.lambdaQuery()
                .eq(XqAccessoryCategory::getName, name)
                .ne(XqAccessoryCategory::getId, dto.getId())
                .count() > 0) {
            throw new InterestingBootException("分类名称已存在");
        }

        XqAccessoryCategory xqAccessoryCategory = new XqAccessoryCategory();
        BeanUtils.copyProperties(dto, xqAccessoryCategory);
        boolean b = xqAccessoryCategoryService.updateById(xqAccessoryCategory);
        return b;
    }

    @Override
    public List<TreeSelectModel> queryAccCategoryTree(String pid) {
        if (StringUtils.isBlank(pid)) {
            pid = "0";
        }
        return this.baseMapper.queryAccCategoryTree(pid);
    }

    @Override
    public List<TreeSelectModel> queryListByPid(String pid) {
        if(oConvertUtils.isEmpty(pid)) {
            pid = "0";
        }
        return this.baseMapper.queryAccCategoryTree(pid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAccCategoryTreeNode(String id) {
        String allIds = this.queryTreeChildIds(id);
        String pids = this.queryTreePids(id);

        if (xqAccessoryInfoService.lambdaQuery()
                .in(XqAccessoryInfo::getCategoryId, Arrays.asList(allIds.split(",")))
                .count() > 0) {
            throw new InterestingBootException("所选分类下有辅料，请先删除分类下的辅料");
        }

        //1.删除时将节点下所有子节点一并删除
        this.baseMapper.deleteBatchIds(Arrays.asList(allIds.split(",")));
        //2.将父节点中已经没有下级的节点，修改为没有子节点
        if(oConvertUtils.isNotEmpty(pids)){
            LambdaUpdateWrapper<XqAccessoryCategory> updateWrapper = new UpdateWrapper<XqAccessoryCategory>()
                    .lambda()
                    .in(XqAccessoryCategory::getId,Arrays.asList(pids.split(",")))
                    .set(XqAccessoryCategory::getHasChild,"0");
            this.update(updateWrapper);
        }
    }

    @Override
    public void addAccCategoryTreeNode(InstUptCategoryTreeNodeDTO dto) {
        String categoryPid = "0";
        if(oConvertUtils.isNotEmpty(dto.getPid())){
            categoryPid = dto.getPid();

            if(!"0".equals(categoryPid)){
                XqAccessoryCategory parent = baseMapper.selectById(categoryPid);
                if(parent != null && !"1".equals(parent.getHasChild())){
                    parent.setHasChild("1");
                    baseMapper.updateById(parent);
                }
            }
        }

        XqAccessoryCategory xqAccessoryCategory = new XqAccessoryCategory();
        xqAccessoryCategory.setPid(categoryPid);
        xqAccessoryCategory.setName(dto.getName());
        baseMapper.insert(xqAccessoryCategory);
    }

    @Override
    public void editAccCategoryTreeNode(InstUptCategoryTreeNodeDTO dto) {
        XqAccessoryCategory byId = this.getById(dto.getId());

        if(oConvertUtils.isEmpty(dto.getPid())){
            byId.setPid("0");
        }else{
            XqAccessoryCategory parent = baseMapper.selectById(dto.getPid());
            if(parent!=null && !"1".equals(parent.getHasChild())){
                parent.setHasChild("1");
                baseMapper.updateById(parent);
            }
            byId.setPid(dto.getPid());
        }

        byId.setName(dto.getName());
        baseMapper.updateById(byId);
    }

    /**
     * 查询节点下所有子节点
     * @param ids
     * @return
     */
    private String queryTreeChildIds(String ids) {
        //获取id数组
        String[] idArr = ids.split(",");
        StringBuffer sb = new StringBuffer();
        for (String pidVal : idArr) {
            if(pidVal != null){
                if(!sb.toString().contains(pidVal)){
                    if(sb.toString().length() > 0){
                        sb.append(",");
                    }
                    sb.append(pidVal);
                    this.getTreeChildIds(pidVal,sb);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 查询需修改标识的父节点ids
     * @param ids
     * @return
     */
    private String queryTreePids(String ids) {
        StringBuffer sb = new StringBuffer();
        //获取id数组
        String[] idArr = ids.split(",");
        for (String id : idArr) {
            if(id != null){
                XqAccessoryCategory category = this.baseMapper.selectById(id);
                //根据id查询pid值
                String metaPid = category.getPid();
                //查询此节点上一级是否还有其他子节点
                LambdaQueryWrapper<XqAccessoryCategory> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(XqAccessoryCategory::getPid,metaPid);
                queryWrapper.notIn(XqAccessoryCategory::getId,Arrays.asList(idArr));
                List<XqAccessoryCategory> dataList = this.baseMapper.selectList(queryWrapper);
                if((dataList == null || dataList.size()==0) && !Arrays.asList(idArr).contains(metaPid)
                        && !sb.toString().contains(metaPid)){
                    //如果当前节点原本有子节点 现在木有了，更新状态
                    sb.append(metaPid).append(",");
                }
            }
        }
        if(sb.toString().endsWith(",")){
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 递归 根据父id获取子节点id
     * @param pidVal
     * @param sb
     * @return
     */
    private StringBuffer getTreeChildIds(String pidVal,StringBuffer sb){
        LambdaQueryWrapper<XqAccessoryCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(XqAccessoryCategory::getPid,pidVal);
        List<XqAccessoryCategory> dataList = baseMapper.selectList(queryWrapper);
        if(dataList != null && dataList.size()>0){
            for(XqAccessoryCategory category : dataList) {
                if(!sb.toString().contains(category.getId())){
                    sb.append(",").append(category.getId());
                }
                this.getTreeChildIds(category.getId(), sb);
            }
        }
        return sb;
    }
}




