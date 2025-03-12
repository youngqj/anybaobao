package com.interesting.modules.workspace.controller;

import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.workspace.dto.XqWorkspaceCatalogDTO;
import com.interesting.modules.workspace.entity.XqWorkspaceCatalog;
import com.interesting.modules.workspace.model.XqWorkspaceFileTreeModel;
import com.interesting.modules.workspace.service.IXqWorkspaceCatalogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * 工作区目录表(XqWorkspaceCatalog)表控制层
 *
 * @author 郭征宇
 * @since 2023-11-13 10:01:10
 */

@Api(tags = "工作区目录表")
@Slf4j
@RestController
@RequestMapping("/xqWorkspaceCatalog")
public class XqWorkspaceCatalogController {


    @Resource
    private IXqWorkspaceCatalogService xqWorkspaceCatalogService;

    /**
     * 分页查询
     *
     * @param roleId
     * @return 查询结果
     */
    @AutoLog(value = "工作区目录表-目录树形列表查询")
    @GetMapping("queryCatalogTree")
    @ApiOperation(value = "目录树形列表查询", response = XqWorkspaceFileTreeModel.class)
    public Result<?> queryCatalogTree(@RequestParam(name = "roleId",required = false) String roleId) {

        return Result.OK(xqWorkspaceCatalogService.getCatalogTree(roleId));
    }

    /**
     * 分页查询
     *
     * @param roleId
     * @return 查询结果
     */
    @AutoLog(value = "工作区目录表-空运列表查询")
    @GetMapping("queryCatalogTreeByAirport")
    @ApiOperation(value = "空运列表查询", response = XqWorkspaceFileTreeModel.class)
    public Result<?> queryCatalogTreeByAirport(@RequestParam(name = "roleId", required = false) String roleId) {

        return Result.OK(xqWorkspaceCatalogService.getCatalogTreeByAirport(roleId));
    }


    /**
     * 新增数据
     *
     * @param dto
     * @return 
     */
    @AutoLog(value = "工作区目录表-新增记录")
    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Valid XqWorkspaceCatalogDTO dto) {
        return xqWorkspaceCatalogService.insert(dto) ? Result.OK("新增成功") : Result.error("新增失败");
    }

    /**
     * 编辑数据
     *
     * @param dto
     * @return 编辑结果
     */
    @AutoLog(value = "工作区目录表-编辑")
    @ApiOperation(value = "编辑")
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody @Valid XqWorkspaceCatalogDTO dto) {
        return xqWorkspaceCatalogService.update(dto) ? Result.OK("编辑成功") : Result.error("编辑失败");    
    }

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "工作区目录表-删除记录")
    @ApiOperation(value = "删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {
        XqWorkspaceCatalog xqWorkspaceCatalog = xqWorkspaceCatalogService.getById(id);
        if ("0".equals(xqWorkspaceCatalog.getPId())) {
            return Result.error("删除失败，根目录无法删除");
        }
		return xqWorkspaceCatalogService.removeById(id)?Result.OK("删除成功!"):Result.error("删除失败，请刷新后重试");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
    @AutoLog(value = "工作区目录表-删除记录")
    @ApiOperation(value = "批量删除，英文逗号隔开")
    @GetMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids")String ids) {
        return xqWorkspaceCatalogService.removeByIds(Arrays.asList(ids.split(","))) ? Result.OK("删除成功") : Result.error("删除失败，请刷新后重试");
    }


    /**
     *  移动目录
     *
     * @param id
     * @return
     */
    @AutoLog(value = "工作区目录表-移动目录")
    @ApiOperation(value = "移动目录，英文逗号隔开")
    @GetMapping("/moveCategory")
    public Result<?> moveCategory(@RequestParam(name = "id")String id,
                                  @RequestParam(name = "pId")String pId) {
        if ("0".equals(pId)) {
            return Result.error("移动失败，根目录无法移动");
        }
        return xqWorkspaceCatalogService.moveCategory(id,pId) ? Result.OK("移动成功") : Result.error("移动失败，请刷新后重试");
    }


}

