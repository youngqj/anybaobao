package com.interesting.business.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.business.system.entity.SysCodeRole;
import com.interesting.business.system.mapper.SysCodeRoleMapper;
import com.interesting.business.system.service.ISysCodeRoleService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.system.base.controller.InterestingBootController;
import com.interesting.common.system.query.QueryGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 编码规则
 * </p>
 *
 * @author: zjk
 * @since 2021-7-23
 */
@Api(tags = "编码规则")
@Slf4j
@RestController
@RequestMapping("/sys/coderole")
public class SysCodeRoleController extends InterestingBootController<SysCodeRole, ISysCodeRoleService> {

    @Autowired
    ISysCodeRoleService sysCodeRoleService;

    @Autowired
    SysCodeRoleMapper sysCodeRoleMapper;

    /**
     * 根据类型获取最大单据编号
     *
     * @param type
     * @param save_flag
     * @return
     */
    @AutoLog(value = "编码规则-获取单据编号")
    @ApiOperation(value = "编码规则-获取单据编号", notes = "编码规则-获取单据编号")
    @GetMapping(value = "/getCodeNumByType")
    public Result<?> getCodeNumByType(
            @ApiParam(name = "type", value = "编码类型 1合同2账单3订单4工单5会议室6预约访客", example = "1") @RequestParam(value = "type") Integer type,
            @ApiParam(name = "save_flag", value = "前端传0；后端保存时候必须传1", example = "0") @RequestParam(value = "save_flag") Integer save_flag) {
        Result<String> result = new Result<>();
        String code_number = sysCodeRoleService.getCodeNumByType(type, save_flag);
        result.setResult(code_number);
        return result;
    }

    /**
     * 获取所有编码规则
     *
     * @return
     */
    @AutoLog(value = "编码规则-获取所有编码规则")
    @ApiOperation(value = "编码规则-获取所有编码规则", notes = "编码规则-获取所有编码规则")
    @GetMapping(value = "/getCodeRoleList")
    public Result<?> getCodeRoleList() {
        List<SysCodeRole> codeRoleList = sysCodeRoleMapper.selectList(null);
        if (codeRoleList == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(codeRoleList);
    }

    /**
     * 分页列表查询
     *
     * @param sysCodeRole
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "单据编码规则表-分页列表查询")
    @ApiOperation(value="单据编码规则表-分页列表查询", notes="单据编码规则表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(SysCodeRole sysCodeRole,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<SysCodeRole> queryWrapper = QueryGenerator.initQueryWrapper(sysCodeRole, req.getParameterMap());
        Page<SysCodeRole> page = new Page<SysCodeRole>(pageNo, pageSize);
        IPage<SysCodeRole> pageList = sysCodeRoleService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     *   添加
     *
     * @param sysCodeRole
     * @return
     */
    @AutoLog(value = "单据编码规则表-添加")
    @ApiOperation(value="单据编码规则表-添加", notes="单据编码规则表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody SysCodeRole sysCodeRole) {
        sysCodeRoleService.save(sysCodeRole);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     *
     * @param sysCodeRole
     * @return
     */
    @AutoLog(value = "单据编码规则表-编辑")
    @ApiOperation(value="单据编码规则表-编辑", notes="单据编码规则表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody SysCodeRole sysCodeRole) {
        sysCodeRoleService.updateById(sysCodeRole);
        return Result.OK("编辑成功!");
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "单据编码规则表-通过id删除")
    @ApiOperation(value="单据编码规则表-通过id删除", notes="单据编码规则表-通过id删除")
    @GetMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        sysCodeRoleService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "单据编码规则表-批量删除")
    @ApiOperation(value="单据编码规则表-批量删除", notes="单据编码规则表-批量删除")
    @GetMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.sysCodeRoleService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "单据编码规则表-通过id查询")
    @ApiOperation(value="单据编码规则表-通过id查询", notes="单据编码规则表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        SysCodeRole sysCodeRole = sysCodeRoleService.getById(id);
        if(sysCodeRole==null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(sysCodeRole);
    }


}
