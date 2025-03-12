package com.interesting.modules.rawmaterial.controller;

import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.rawmaterial.service.XqWithholdDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2024/4/10 17:47
 * @Project: trade-manage
 * @Description:
 */



@Api(tags = "预付款")
@RestController
@RequestMapping("/withholdDetail")
public class XqWithholdDetailController {



    @Autowired
    private XqWithholdDetailService xqWithholdDetailService;
    /**
     * 预扣款批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "预扣款批量删除")
    @ApiOperation(value="预扣款批量删除", notes="预扣款批量删除")
    @GetMapping(value = "/deleteBatchByIds")
    public Result<?> deleteBatchByIds(@RequestParam(name="ids") String ids) {
        xqWithholdDetailService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

}
