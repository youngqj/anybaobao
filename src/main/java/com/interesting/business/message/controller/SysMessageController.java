package com.interesting.business.message.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.business.message.entity.SysMessage;
import com.interesting.business.message.service.ISysMessageService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.system.base.controller.InterestingBootController;
import com.interesting.common.system.query.QueryGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 消息
 * @author: interesting-boot
 * @date: 2019-04-09
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/sys/message/sysMessage")
//@Api(tags = "消息管理")
public class SysMessageController extends InterestingBootController<SysMessage, ISysMessageService> {
    @Autowired
    private ISysMessageService sysMessageService;

    /**
     * 分页列表查询
     *
     * @param sysMessage
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<?> queryPageList(SysMessage sysMessage, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        QueryWrapper<SysMessage> queryWrapper = QueryGenerator.initQueryWrapper(sysMessage, req.getParameterMap());
        Page<SysMessage> page = new Page<SysMessage>(pageNo, pageSize);
        IPage<SysMessage> pageList = sysMessageService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param sysMessage
     * @return
     */
    @PostMapping(value = "/add")
//    @ApiOperation(value = "消息管理-添加消息", notes = "消息管理-添加消息")
    public Result<?> add(@RequestBody SysMessage sysMessage) {
        sysMessageService.save(sysMessage);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param sysMessage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody SysMessage sysMessage) {
        sysMessageService.updateById(sysMessage);
        return Result.ok("修改成功!");

    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        sysMessageService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @GetMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {

        this.sysMessageService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        SysMessage sysMessage = sysMessageService.getById(id);
        return Result.ok(sysMessage);
    }

}
