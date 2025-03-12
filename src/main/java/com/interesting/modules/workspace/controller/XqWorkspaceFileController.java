package com.interesting.modules.workspace.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.workspace.dto.InsertDocumentsDTO;
import com.interesting.modules.workspace.dto.ListXqWorkspaceFileDTO;
import com.interesting.modules.workspace.dto.MoveDocumentsDTO;
import com.interesting.modules.workspace.service.IXqWorkspaceFileService;
import com.interesting.modules.workspace.vo.ListXqWorkspaceFileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * 工作区文件表(XqWorkspaceFile)表控制层
 *
 * @author 郭征宇
 * @since 2023-11-13 10:01:28
 */

@Api(tags = "工作区文件表")
@Slf4j
@RestController
@RequestMapping("/xqWorkspaceFile")
public class XqWorkspaceFileController {


    @Resource
    private IXqWorkspaceFileService xqWorkspaceFileService;

    /**
     * 分页查询
     *
     * @param dto
     * @return 查询结果
     */
    @AutoLog(value = "工作区文件表-分页列表查询")
    @ApiOperation(value = "分页列表查询",response = ListXqWorkspaceFileVO.class)
    @GetMapping("/listFile")
    public Result<?> queryPageList(ListXqWorkspaceFileDTO dto,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<ListXqWorkspaceFileVO> page = new Page<>(pageNo,pageSize);
        
        return Result.OK(xqWorkspaceFileService.queryByPage(dto, page));
    }

    /**
     * 新增数据
     *
     * @param dto
     * @return
     */
    @AutoLog(value = "工作区文件表-新增文件")
    @ApiOperation(value = "新增文件")
    @PostMapping("insertDocument")
    public Result<?> addDocuments(@RequestBody @Valid InsertDocumentsDTO dto) {
        xqWorkspaceFileService.insertDocuments(dto);
        return Result.OK("新增成功");
    }

    @AutoLog(value = "工作区文件表-移动文件")
    @ApiOperation(value = "移动文件")
    @PostMapping("moveDocuments")
    public Result<?> moveDocuments(@RequestBody @Valid MoveDocumentsDTO dto) {
        return xqWorkspaceFileService.moveDocuments(dto) ? Result.OK("移动成功") : Result.error("移动失败，请刷新后重试");
    }


    /**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "工作区文件表-删除记录")
    @ApiOperation(value = "删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {

		return xqWorkspaceFileService.removeById(id)?Result.OK("删除成功!"):Result.error("删除失败，请刷新后重试");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
    @AutoLog(value = "工作区文件表-删除记录")
    @ApiOperation(value = "批量删除，英文逗号隔开")
    @GetMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids")String ids) {
        return xqWorkspaceFileService.removeByIds(Arrays.asList(ids.split(","))) ? Result.OK("删除成功") : Result.error("删除失败，请刷新后重试");
    }



}

