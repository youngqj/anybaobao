package com.interesting.business.system.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.constant.CacheConstant;
import com.interesting.config.FilterContextHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.interesting.common.api.vo.Result;

import com.interesting.common.constant.CommonConstant;
import com.interesting.common.system.query.QueryGenerator;
import com.interesting.common.system.vo.DictModel;
import com.interesting.common.system.vo.DictQuery;
import com.interesting.common.system.vo.LoginUser;
import com.interesting.common.util.ImportExcelUtil;
import com.interesting.common.util.SqlInjectionUtil;
import com.interesting.common.util.oConvertUtils;
import com.interesting.business.system.entity.SysDict;
import com.interesting.business.system.entity.SysDictItem;
import com.interesting.business.system.model.SysDictTree;
import com.interesting.business.system.model.TreeSelectModel;
import com.interesting.business.system.service.ISysDictItemService;
import com.interesting.business.system.service.ISysDictService;
import com.interesting.business.system.vo.SysDictPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @Author Gcc
 * @since 2018-12-28
 */
@RestController
@RequestMapping("/sys/dict")
@Slf4j
@Api(tags = "字典信息")
public class SysDictController {

    @Autowired
    private ISysDictService sysDictService;
    @Autowired
    private ISysDictItemService sysDictItemService;
    @Autowired
    public RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<SysDict>> queryPageList(SysDict sysDict, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<IPage<SysDict>> result = new Result<IPage<SysDict>>();
        QueryWrapper<SysDict> queryWrapper = QueryGenerator.initQueryWrapper(sysDict, req.getParameterMap());
        Page<SysDict> page = new Page<SysDict>(pageNo, pageSize);
        IPage<SysDict> pageList = sysDictService.page(page, queryWrapper);
        log.debug("查询当前页：" + pageList.getCurrent());
        log.debug("查询当前页数量：" + pageList.getSize());
        log.debug("查询结果数量：" + pageList.getRecords().size());
        log.debug("数据总数：" + pageList.getTotal());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * @param sysDict
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     * @功能：获取树形字典数据
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有字典主表数据", notes = "获取所有字典主表数据")
    public Result<List<SysDictTree>> treeList(SysDict sysDict, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<List<SysDictTree>> result = new Result<>();
        LambdaQueryWrapper<SysDict> query = new LambdaQueryWrapper<>();
        // 构造查询条件
        String dictName = sysDict.getDictName();
        if (oConvertUtils.isNotEmpty(dictName)) {
            query.like(true, SysDict::getDictName, dictName);
        }
        query.orderByDesc(true, SysDict::getCreateTime);
        List<SysDict> list = sysDictService.list(query);
        List<SysDictTree> treeList = new ArrayList<>();
        for (SysDict node : list) {
            treeList.add(new SysDictTree(node));
        }
        result.setSuccess(true);
        result.setResult(treeList);
        return result;
    }

    /**
     * 获取字典数据
     *
     * @param dictCode 字典code
     * @param dictCode 表名,文本字段,code字段  | 举例：sys_user,realname,id
     * @return
     */
    @RequestMapping(value = "/getDictItems/{dictCode}", method = RequestMethod.GET)
    public Result<List<DictModel>> getDictItems(@PathVariable String dictCode, @RequestParam(value = "sign", required = false) String sign, HttpServletRequest request) {
        log.info(" dictCode : " + dictCode);
        Result<List<DictModel>> result = new Result<List<DictModel>>();
        List<DictModel> ls = null;
        try {
            if (dictCode.indexOf(",") != -1) {
                //关联表字典（举例：sys_user,realname,id）
                String[] params = dictCode.split(",");

                if (params.length < 3) {
                    result.error500("字典Code格式不正确！");
                    return result;
                }
                //SQL注入校验（只限制非法串改数据库）
                final String[] sqlInjCheck = {params[0], params[1], params[2]};
                SqlInjectionUtil.filterContent(sqlInjCheck);

                if (params.length == 4) {
                    //SQL注入校验（查询条件SQL 特殊check，此方法仅供此处使用）
                    SqlInjectionUtil.specialFilterContent(params[3]);
                    ls = sysDictService.queryTableDictItemsByCodeAndFilter(params[0], params[1], params[2], params[3]);
                } else if (params.length == 3) {
                    ls = sysDictService.queryTableDictItemsByCode(params[0], params[1], params[2]);
                } else {
                    result.error500("字典Code格式不正确！");
                    return result;
                }
            } else {
                //字典表
                ls = sysDictService.queryDictItemsByCode(dictCode);
            }

            result.setSuccess(true);
            result.setResult(ls);
            log.debug(result.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
            return result;
        }

        return result;
    }

    /**
     * 获取全部字典数据
     *
     * @return
     */
    @RequestMapping(value = "/queryAllDictItems", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部字典数据", notes = "获取全部字典数据")
    public Result<?> queryAllDictItems(HttpServletRequest request) {
        Map<String, List<DictModel>> res = new HashMap<String, List<DictModel>>();
        res = sysDictService.queryAllDictItems();
        return Result.ok(res);
    }

    /**
     * 获取字典数据
     *
     * @param dictCode
     * @return
     */
    @RequestMapping(value = "/getDictText/{dictCode}/{key}", method = RequestMethod.GET)
    public Result<String> getDictText(@PathVariable("dictCode") String dictCode, @PathVariable("key") String key) {
        log.info(" dictCode : " + dictCode);
        Result<String> result = new Result<String>();
        String text = null;
        try {
            text = sysDictService.queryDictTextByKey(dictCode, key);
            result.setSuccess(true);
            result.setResult(text);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
            return result;
        }
        return result;
    }

    /**
     * 大数据量的字典表 走异步加载  即前端输入内容过滤数据
     *
     * @param dictCode
     * @return
     */
    @RequestMapping(value = "/loadDict/{dictCode}", method = RequestMethod.GET)
    public Result<List<DictModel>> loadDict(@PathVariable String dictCode,
                                            @RequestParam(name = "keyword") String keyword,
                                            @RequestParam(value = "sign", required = false) String sign,
                                            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info(" 加载字典表数据,加载关键字: " + keyword);
        Result<List<DictModel>> result = new Result<List<DictModel>>();
        List<DictModel> ls = null;
        try {
            if (dictCode.indexOf(",") != -1) {
                String[] params = dictCode.split(",");
                if (params.length != 3) {
                    result.error500("字典Code格式不正确！");
                    return result;
                }
                if (pageSize != null) {
                    ls = sysDictService.queryLittleTableDictItems(params[0], params[1], params[2], keyword, pageSize);
                } else {
                    ls = sysDictService.queryTableDictItems(params[0], params[1], params[2], keyword);
                }
                result.setSuccess(true);
                result.setResult(ls);
                log.info(result.toString());
            } else {
                result.error500("字典Code格式不正确！");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
            return result;
        }

        return result;
    }

    /**
     * 根据字典code加载字典text 返回
     */
    @RequestMapping(value = "/loadDictItem/{dictCode}", method = RequestMethod.GET)
    public Result<List<String>> loadDictItem(@PathVariable String dictCode, @RequestParam(name = "key") String keys, @RequestParam(value = "sign", required = false) String sign, HttpServletRequest request) {
        Result<List<String>> result = new Result<>();
        try {
            if (dictCode.indexOf(",") != -1) {
                String[] params = dictCode.split(",");
                if (params.length != 3) {
                    result.error500("字典Code格式不正确！");
                    return result;
                }
                List<String> texts = sysDictService.queryTableDictByKeys(params[0], params[1], params[2], keys);

                result.setSuccess(true);
                result.setResult(texts);
                log.info(result.toString());
            } else {
                result.error500("字典Code格式不正确！");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
            return result;
        }

        return result;
    }

    /**
     * 根据表名——显示字段-存储字段 pid 加载树形数据
     */
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "加载树形数据", notes = "加载树形数据")
    @RequestMapping(value = "/loadTreeData", method = RequestMethod.GET)
    public Result<List<TreeSelectModel>> loadTreeData(@RequestParam(name = "pid") String pid, @RequestParam(name = "pidField") String pidField,
                                                      @RequestParam(name = "tableName") String tbname,
                                                      @RequestParam(name = "text") String text,
                                                      @RequestParam(name = "code") String code,
                                                      @RequestParam(name = "hasChildField") String hasChildField,
                                                      @RequestParam(name = "condition") String condition,
                                                      @RequestParam(value = "sign", required = false) String sign, HttpServletRequest request) {
        Result<List<TreeSelectModel>> result = new Result<List<TreeSelectModel>>();
        Map<String, String> query = null;
        if (oConvertUtils.isNotEmpty(condition)) {
            query = JSON.parseObject(condition, Map.class);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("iz_delete", "0");
            query = map;
        }

        // SQL注入漏洞 sign签名校验(表名,label字段,val字段,条件)
        String dictCode = tbname + "," + text + "," + code + "," + condition;
        SqlInjectionUtil.filterContent(dictCode);
        List<TreeSelectModel> ls = sysDictService.queryTreeList(query, tbname, text, code, pidField, pid, hasChildField);
        result.setSuccess(true);
        result.setResult(ls);
        return result;
    }

    /**
     * 【APP接口】根据字典配置查询表字典数据（目前暂未找到调用的地方）
     *
     * @param query
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Deprecated
    @GetMapping("/queryTableData")
    public Result<List<DictModel>> queryTableData(DictQuery query,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "sign", required = false) String sign, HttpServletRequest request) {
        Result<List<DictModel>> res = new Result<List<DictModel>>();
        // SQL注入漏洞 sign签名校验
        String dictCode = query.getTable() + "," + query.getText() + "," + query.getCode();
        SqlInjectionUtil.filterContent(dictCode);
        List<DictModel> ls = this.sysDictService.queryDictTablePageList(query, pageSize, pageNo);
        res.setResult(ls);
        res.setSuccess(true);
        return res;
    }

    /**
     * @param sysDict
     * @return
     * @功能：新增
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "字典主表添加", notes = "字典主表添加")
    public Result<SysDict> add(@RequestBody SysDict sysDict) {
        Result<SysDict> result = new Result<SysDict>();
        try {
            sysDict.setCreateTime(new Date());
            sysDict.setIzDelete(CommonConstant.IZ_DELETE_0);
            sysDictService.save(sysDict);
            result.success("保存成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * @param sysDict
     * @return
     * @功能：编辑
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Result<SysDict> edit(@RequestBody SysDict sysDict) {
        Result<SysDict> result = new Result<SysDict>();
        SysDict sysdict = sysDictService.getById(sysDict.getId());
        if (sysdict == null) {
            result.error500("未找到对应实体");
        } else {
            sysDict.setUpdateTime(new Date());
            boolean ok = sysDictService.updateById(sysDict);
            if (ok) {
                result.success("编辑成功!");
            }
        }
        return result;
    }

    /**
     * @param id
     * @return
     * @功能：删除
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
    public Result<SysDict> delete(@RequestParam(name = "id", required = true) String id) {
        Result<SysDict> result = new Result<SysDict>();
        boolean ok = sysDictService.removeById(id);
        if (ok) {
            result.success("删除成功!");
        } else {
            result.error500("删除失败!");
        }
        return result;
    }

    /**
     * @param ids
     * @return
     * @功能：批量删除
     */
    //@RequiresRoles({"admin"})
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    @CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
    public Result<SysDict> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<SysDict> result = new Result<SysDict>();
        if (oConvertUtils.isEmpty(ids)) {
            result.error500("参数不识别！");
        } else {
            sysDictService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * @return
     * @功能：刷新缓存
     */
    @RequestMapping(value = "/refleshCache")
    public Result<?> refleshCache() {
        Result<?> result = new Result<SysDict>();
        //清空字典缓存
        Set keys = redisTemplate.keys(CacheConstant.SYS_DICT_CACHE + "*");
        Set keys2 = redisTemplate.keys(CacheConstant.SYS_DICT_TABLE_CACHE + "*");
        Set keys3 = redisTemplate.keys(CacheConstant.SYS_DEPARTS_CACHE + "*");
        Set keys4 = redisTemplate.keys(CacheConstant.SYS_DEPART_IDS_CACHE + "*");
        Set keys5 = redisTemplate.keys("jmreport:cache:dict*");
        Set keys6 = redisTemplate.keys("jmreport:cache:dictTable*");
        redisTemplate.delete(keys);
        redisTemplate.delete(keys2);
        redisTemplate.delete(keys3);
        redisTemplate.delete(keys4);
        redisTemplate.delete(keys5);
        redisTemplate.delete(keys6);
        return result;
    }



    /**
     * 查询被删除的列表
     *
     * @return
     */
    @RequestMapping(value = "/deleteList", method = RequestMethod.GET)
    public Result<List<SysDict>> deleteList() {
        Result<List<SysDict>> result = new Result<List<SysDict>>();
        List<SysDict> list = this.sysDictService.queryDeleteList();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deletePhysic/{id}", method = RequestMethod.GET)
    public Result<?> deletePhysic(@PathVariable String id) {
        try {
            sysDictService.deleteOneDictPhysically(id);
            return Result.ok("删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败!");
        }
    }

    /**
     * 取回
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/back/{id}", method = RequestMethod.PUT)
    public Result<?> back(@PathVariable String id) {
        try {
            sysDictService.updateDictDelFlag(0, id);
            return Result.ok("操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败!");
        }
    }

}
