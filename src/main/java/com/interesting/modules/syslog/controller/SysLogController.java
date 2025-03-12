package com.interesting.modules.syslog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.business.system.entity.SysLog;
import com.interesting.business.system.service.ISysLogService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.util.oConvertUtils;
import com.interesting.modules.overseas.dto.QueryPageEnterStorageDTO;
import com.interesting.modules.overseas.vo.EnterStoragePageVO;
import com.interesting.modules.syslog.dto.QueryPageSysLogDTO;
import com.interesting.modules.syslog.vo.SysLogPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags = "系统日志")
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    /**
     * 查询日志(分页列表)
     *
     */
    @AutoLog(value = "查询日志(分页列表)")
    @ApiOperation(value="查询日志(分页列表)", notes="查询日志(分页列表)", response = SysLogPageVO.class)
    @GetMapping(value = "/pageSysLog")
    public Result<?> pageSysLog(QueryPageSysLogDTO dto) {
        IPage<SysLogPageVO> vo = sysLogService.pageSysLog(dto);
        return Result.OK(vo);
    }

    /**
     * @功能：查询日志记录
     * @param syslog
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<SysLog>> queryPageList(SysLog syslog, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                               @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) {
        Result<IPage<SysLog>> result = new Result<IPage<SysLog>>();
        QueryWrapper<SysLog> queryWrapper = QueryGenerator.initQueryWrapper(syslog, req.getParameterMap());
        Page<SysLog> page = new Page<SysLog>(pageNo, pageSize);
        //日志关键词
        String keyWord = req.getParameter("keyWord");
        if(oConvertUtils.isNotEmpty(keyWord)) {
            queryWrapper.like("log_content",keyWord);
        }
        //TODO 过滤逻辑处理
        //TODO begin、end逻辑处理
        //TODO 一个强大的功能，前端传一个字段字符串，后台只返回这些字符串对应的字段
        //创建时间/创建人的赋值
        IPage<SysLog> pageList = sysLogService.page(page, queryWrapper);
        log.info("查询当前页："+pageList.getCurrent());
        log.info("查询当前页数量："+pageList.getSize());
        log.info("查询结果数量："+pageList.getRecords().size());
        log.info("数据总数："+pageList.getTotal());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }
}
