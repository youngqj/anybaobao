package com.interesting.modules.orderconfig.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.system.base.controller.InterestingBootController;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.modules.orderconfig.XqOrderConfigDto;
import com.interesting.modules.orderconfig.entity.XqOrderConfig;
import com.interesting.modules.orderconfig.service.IXqOrderConfigService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 面单配置表
 * @Author: jeecg-boot
 * @Date:   2024-12-19
 * @Version: V1.0
 */
@Api(tags="面单配置表")
@RestController
@RequestMapping("/xqOrderConfig")
@Slf4j
public class XqOrderConfigController extends InterestingBootController<XqOrderConfig, IXqOrderConfigService> {
	@Autowired
	private IXqOrderConfigService xqOrderConfigService;
	
	/**
	 *  编辑
	 *
	 * @param xqOrderConfig
	 * @return
	 */
	@AutoLog(value = "面单配置表-编辑")
	@ApiOperation(value="面单配置表-编辑", notes="面单配置表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.POST})
	public Result<String> edit(@RequestBody XqOrderConfig xqOrderConfig) {
		xqOrderConfigService.updateById(xqOrderConfig);
		return Result.OK("编辑成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @return
	 */
	@AutoLog(value = "面单配置表-获取配置信息")
	@ApiOperation(value="面单配置表-获取配置信息", notes="面单配置表-获取配置信息")
	@GetMapping(value = "/queryById")
	public Result<XqOrderConfigDto> queryById() {
		XqOrderConfigDto xqOrderConfigDto = new XqOrderConfigDto();
		XqOrderConfig xqOrderConfig = xqOrderConfigService.getOne(new LambdaQueryWrapper<XqOrderConfig>().last("limit 1"));
		xqOrderConfigDto.setId(xqOrderConfig.getId());
		xqOrderConfigDto.setCreditInsuranceRate(xqOrderConfig.getCreditInsuranceRate().toPlainString());
		xqOrderConfigDto.setFactoringInterestRate(xqOrderConfig.getFactoringInterestRate().toPlainString());
		xqOrderConfigDto.setDays(xqOrderConfig.getDays());
		return Result.OK(xqOrderConfigDto);
	}

}
