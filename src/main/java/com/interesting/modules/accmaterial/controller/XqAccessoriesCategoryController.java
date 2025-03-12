package com.interesting.modules.accmaterial.controller;

import com.interesting.business.system.entity.SysCategory;
import com.interesting.business.system.model.TreeSelectModel;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.accmaterial.dto.InstUptCategoryTreeNodeDTO;
import com.interesting.modules.accmaterial.entity.XqAccessoryCategory;
import com.interesting.modules.accmaterial.service.XqAccessoryCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Api(tags="辅料分类管理")
@RestController
@RequestMapping("/accmaterial/xqAccessoryCategory")
public class XqAccessoriesCategoryController {
    @Autowired
    private XqAccessoryCategoryService xqAccessoryCategoryService;

    /**
     * 查询辅料分类树形
     *
     * @param id
     * @return
     */
    @AutoLog(value = "查询辅料分类树形")
    @ApiOperation(value="查询辅料分类树形", notes="查询辅料分类树形")
    @GetMapping(value = "/listAccCategoryTree")
    public Result<?> listAccCategoryTree(@RequestParam(required = false) String pid) {
        List<TreeSelectModel> ls = this.xqAccessoryCategoryService.queryAccCategoryTree(pid);
        loadAllCategoryChildren(ls);
        return Result.OK(ls);
    }

    /**
     * 递归求子节点 同步加载用到
     */
    private void loadAllCategoryChildren(List<TreeSelectModel> ls) {
        for (TreeSelectModel tsm : ls) {
            List<TreeSelectModel> temp = this.xqAccessoryCategoryService.queryListByPid(tsm.getKey());
            if(temp!=null && temp.size()>0) {
                tsm.setChildren(temp);
                loadAllCategoryChildren(temp);
            }
        }
    }

    // 删除节点
    /**
     *  通过id删除
     * @param id
     * @return
     */
    @GetMapping(value = "/deleteAccCategoryTreeNode")
    @ApiOperation(value = "辅料分类树形节点删除", notes = "辅料分类树形节点删除")
    @AutoLog(value = "辅料分类树形节点删除")
    public Result<SysCategory> deleteAccCategoryTreeNode(@RequestParam(name="ids") String ids) {
        Result<SysCategory> result = new Result<>();
        List<String> strings = Arrays.asList(ids.split(","));

        for (String id : strings) {
            XqAccessoryCategory sysCategory = xqAccessoryCategoryService.getById(id);

            if(sysCategory==null) {
                result.error500("未找到对应实体");
                break;
            }else {

                this.xqAccessoryCategoryService.deleteAccCategoryTreeNode(id);
                result.success("删除成功!");
            }
        }

        return result;
    }

    /**
     * 添加辅料分类树形节点
     * @param sysCategory
     * @return
     */
    @PostMapping(value = "/addAccCategoryTreeNode")
    @ApiOperation(value="添加辅料分类树形节点", notes="添加辅料分类树形节点")
    @AutoLog(value = "添加辅料分类树形节点")
    public Result<?> addAccCategoryTreeNode(@RequestBody @Valid InstUptCategoryTreeNodeDTO dto) {
        Result<?> result = new Result<>();
        try {
            xqAccessoryCategoryService.addAccCategoryTreeNode(dto);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑辅料分类树形节点
     * @param sysCategory
     * @return
     */
    @PostMapping(value = "/editAccCategoryTreeNode")
    @ApiOperation(value = "编辑辅料分类树形节点", notes = "编辑辅料分类树形节点")
    @AutoLog(value = "编辑辅料分类树形节点")
    public Result<?> editAccCategoryTreeNode(@RequestBody InstUptCategoryTreeNodeDTO dto) {
        if (StringUtils.isNotBlank(dto.getPid())) {
            String pid = dto.getPid();
            if (pid.equals(dto.getId())) {
                return Result.error("上级不能选择自己");
            }
        }

        Result<?> result = new Result<>();
        XqAccessoryCategory sysCategoryEntity = xqAccessoryCategoryService.getById(dto.getId());
        if(sysCategoryEntity==null) {
            result.error500("未找到对应实体");
        }else {
            xqAccessoryCategoryService.editAccCategoryTreeNode(dto);
            result.success("修改成功!");
        }
        return result;
    }

}
