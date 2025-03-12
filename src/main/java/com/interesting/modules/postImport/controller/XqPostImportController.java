package com.interesting.modules.postImport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.postImport.entity.XqPostImport;
import com.interesting.modules.postImport.service.XqPostImportService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.util.StringUtils;
import com.interesting.config.FilterContextHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "导入文件记录")
@RestController
@RequestMapping("/postImport")
@Slf4j
public class XqPostImportController {
    @Autowired
    private XqPostImportService xqPostImportService;

    /**
     * 分页列表查询
     *
     * @param importType
     * @param pageNo
     * @param pageSize
     * @return
     */
    @AutoLog(value = "导入文件记录-分页列表查询")
    @ApiOperation(value = "导入文件记录-分页列表查询", notes = "导入文件记录-分页列表查询")
    @GetMapping(value = "/getPostImportList")
    public Result<IPage<XqPostImport>> getPostImportList(@RequestParam(name = "importType", required = false) String importType,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userId = FilterContextHandler.getUserId();
        QueryWrapper<XqPostImport> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("import_type", "卡码翻译记录");
        if (StringUtils.isNotBlank(importType)) {
            queryWrapper.like("import_type", importType);
        }
        queryWrapper.eq("import_person", userId);
        queryWrapper.orderByDesc("import_time");
        Page<XqPostImport> page = new Page<>(pageNo, pageSize);
        IPage<XqPostImport> pageList = xqPostImportService.page(page, queryWrapper);
        return Result.OK(pageList);
    }
}
