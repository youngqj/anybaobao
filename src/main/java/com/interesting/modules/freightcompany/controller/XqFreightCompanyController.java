package com.interesting.modules.freightcompany.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.freightcompany.dto.ListXqFreightCompanyDTO;
import com.interesting.modules.freightcompany.dto.XqFreightCompanyDTO;
import com.interesting.modules.freightcompany.service.IXqFreightCompanyService;
import com.interesting.modules.freightcompany.vo.ListCompanyNameAndContactVO;
import com.interesting.modules.freightcompany.vo.ListXqFreightCompanyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * 货代公司(XqFreightCompany)表控制层
 *
 * @author 郭征宇
 * @since 2023-12-25 16:26:58
 */

@Api(tags = "货代公司")
@Slf4j
@RestController
@RequestMapping("/xqFreightCompany")
public class XqFreightCompanyController {


    @Resource
    private IXqFreightCompanyService xqFreightCompanyService;

    /**
     * 分页查询
     *
     * @param dto
     * @return 查询结果
     */
    @AutoLog(value = "货代公司-分页列表查询")
    @ApiOperation(value = "分页列表查询",response =ListXqFreightCompanyVO.class)
    @GetMapping("/list")
    public Result<?> queryPageList(ListXqFreightCompanyDTO dto,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<ListXqFreightCompanyVO> page = new Page<>(pageNo,pageSize);
        
        return Result.OK(xqFreightCompanyService.queryByPage(dto, page));
    }

    /**
     * 新增数据
     *
     * @param dto
     * @return 
     */
    @AutoLog(value = "货代公司-新增记录")
    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Valid XqFreightCompanyDTO dto) {
        return xqFreightCompanyService.insert(dto) ? Result.OK("新增成功") : Result.error("新增失败");
    }

    /**
     * 编辑数据
     *
     * @param dto
     * @return 编辑结果
     */
    @AutoLog(value = "货代公司-编辑")
    @ApiOperation(value = "编辑")
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody @Valid XqFreightCompanyDTO dto) {
        return xqFreightCompanyService.update(dto) ? Result.OK("编辑成功") : Result.error("编辑失败");    
    }

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "货代公司-删除记录")
    @ApiOperation(value = "删除")
	@GetMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id") String id) {

		return xqFreightCompanyService.removeById(id)?Result.OK("删除成功!"):Result.error("删除失败，请刷新后重试");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
    @AutoLog(value = "货代公司-删除记录")
    @ApiOperation(value = "批量删除，英文逗号隔开")
    @GetMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids")String ids) {
        return xqFreightCompanyService.removeByIds(Arrays.asList(ids.split(","))) ? Result.OK("删除成功") : Result.error("删除失败，请刷新后重试");
    }

    /**
     *  批量删除
     *
     * @param
     * @return
     */
    @AutoLog(value = "货代公司-选择列表")
    @ApiOperation(value = "货代公司-选择列表",response = ListCompanyNameAndContactVO.class)
    @GetMapping("/listNameAndContact")
    public Result<?> listNameAndContact() {
        return Result.OK(xqFreightCompanyService.listNameAndContact());
    }





}

