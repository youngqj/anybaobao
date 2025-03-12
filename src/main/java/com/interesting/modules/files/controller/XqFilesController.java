package com.interesting.modules.files.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.business.system.entity.SysCategory;
import com.interesting.business.system.model.TreeSelectModel;
import com.interesting.business.system.service.ISysCategoryService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.common.system.vo.LoginUser;
import com.interesting.config.FilterContextHandler;
import com.interesting.modules.files.dto.InstOrUpdtXqFilesDTO;
import com.interesting.modules.files.dto.MoveFilesDTO;
import com.interesting.modules.files.dto.MoveXqFileDTO;
import com.interesting.modules.files.entity.XqFiles;
import com.interesting.modules.files.service.XqFilesService;
import com.interesting.modules.files.vo.ListXqFileVO;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.entity.XqOrderTransferRecord;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.order.service.IXqOrderTransferRecordService;
import com.interesting.modules.overseas.vo.EnterStoragePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "附件")
@RestController
@RequestMapping("/files/xqFiles")
public class XqFilesController {
    @Autowired
    private XqFilesService xqFilesService;

    @Autowired
    private IXqOrderService xqOrderService;

    @Autowired
    private ISysCategoryService sysCategoryService;

    @Autowired
    private IXqOrderTransferRecordService xqOrderTransferRecordService;

//    /**
//     * 添加
//     *
//     * @param xqCustomer
//     * @return
//     */
//    @AutoLog(value = "附件-添加")
//    @ApiOperation(value="附件-添加", notes="附件-添加")
//    @PostMapping(value = "/add")
//    public Result<?> add(@RequestBody @Valid InstOrUpdtXqFilesDTO dto) {
//        XqFiles xqFiles = new XqFiles();
//        BeanUtils.copyProperties(dto, xqFiles);
//        boolean save = xqFilesService.save(xqFiles);
//        return save ? Result.OK("添加成功") : Result.error("添加失败");
//    }

//    @AutoLog("删除附件")
//    @GetMapping("/deleteFile")
//    @ApiOperation(value = "删除附件",notes = "删除附件")
//    public Result<?> deleteFile(@RequestParam(name = "id") String id){
//        return xqFilesService.removeById(id)?Result.OK():Result.error("删除附件失败，请刷新后重试");
//    }

//    @AutoLog("附件分页")
//    @GetMapping("/listFile")
//    @ApiOperation(value = "附件分页",notes = "附件分页",response = ListXqFileVO.class)
//    public Result<?> listFile(@RequestParam(name = "pageNo",defaultValue = "1")Integer pageNo,
//                              @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize,
//                              @Valid ListXqFileDTO dto){
//        Page<ListXqFileVO> page = new Page<>(pageNo, pageSize);
//        IPage<ListXqFileVO> list = xqFilesService.pageList(page,dto);
//        return Result.OK(list);
//    }

//    // 查询文件树接口
//    @AutoLog("查询文件树接口")
//    @GetMapping("/")
//    @ApiOperation(value = "查询文件树接口",notes = "查询文件树接口")
//    public Result<?> listFileTree(@RequestParam String moduleType,
//                                  @RequestParam String orderId){
//        List<TreeSelectModel> ls = this.sysCategoryService.queryListByCodeAndModuleType(orderId, moduleType);
//        loadAllCategoryChildren(ls);
//        return Result.OK(ls);
//    }

    /**
     * 递归求子节点 同步加载用到
     */
    private void loadAllCategoryChildren(List<TreeSelectModel> ls) {
        for (TreeSelectModel tsm : ls) {
            List<TreeSelectModel> temp = this.sysCategoryService.queryListByPid(tsm.getKey());
            if (temp != null && temp.size() > 0) {
                tsm.setChildren(temp);
                loadAllCategoryChildren(temp);
            }
        }
    }


    // 删除节点

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/deleteFileTree")
    @ApiOperation(value = "文件树节点删除", notes = "文件树节点删除")
    @AutoLog(value = "文件树节点删除")
    public Result<SysCategory> deleteFileTree(@RequestParam(name = "id", required = true) String id) {
        Result<SysCategory> result = new Result<>();

        SysCategory sysCategory = sysCategoryService.getById(id);

        if (sysCategory == null) {
            result.error500("未找到对应实体");
        } else {
            if (sysCategory.getPid().equals("0")) {
                return result.error500("根节点不能删除");
            }
            this.sysCategoryService.deleteSysCategory(id);
            result.success("删除成功!");
        }
        return result;
    }

    // 编辑

    /**
     * 编辑
     *
     * @param sysCategory
     * @return
     */
    @PostMapping(value = "/editFileTree")
    @ApiOperation(value = "编辑文件树", notes = "编辑文件树")
    @AutoLog(value = "编辑文件树")
    public Result<SysCategory> editFileTree(@RequestBody SysCategory sysCategory) {
        Result<SysCategory> result = new Result<>();
        SysCategory sysCategoryEntity = sysCategoryService.getById(sysCategory.getId());
        if (sysCategoryEntity == null) {
            result.error500("未找到对应实体");
        } else {
            sysCategoryEntity.setName(sysCategory.getName());
            sysCategoryService.updateSysCategory(sysCategoryEntity);
            result.success("修改成功!");
        }
        return result;
    }

    // 移动

    /**
     * 移动
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/moveFileTree")
    @ApiOperation(value = "移动文件树", notes = "移动文件树")
    @AutoLog(value = "移动文件树")
    public Result<SysCategory> moveFileTree(@RequestBody MoveXqFileDTO dto) {
        Result<SysCategory> result = new Result<>();

        SysCategory sysCategoryEntity = sysCategoryService.getById(dto.getId());
        if (sysCategoryEntity.getPid().equals("0")) {
            return result.error500("不能移动根目录");
        }
        sysCategoryEntity.setPid(dto.getNowPid());
        if (sysCategoryEntity == null) {
            result.error500("未找到对应实体");
        } else {
            sysCategoryService.updateSysCategory(sysCategoryEntity);
            result.success("移动成功!");
        }
        return result;
    }

    /**
     * 添加
     *
     * @param sysCategory
     * @return
     */
    @PostMapping(value = "/addFileTree")
    @ApiOperation(value = "添加文件树", notes = "添加文件树")
    @AutoLog(value = "添加文件树")
    public Result<SysCategory> addFileTree(@RequestBody SysCategory sysCategory) {
        Result<SysCategory> result = new Result<SysCategory>();
        try {
            if (sysCategory.getPid().equals("0")) {
                return result.error500("不能添加根目录");
            }

            sysCategoryService.addSysCategory(sysCategory);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @AutoLog("查询相应节点下的文件")
    @ApiOperation(value = "查询相应节点下的文件", notes = "查询相应节点下的文件", response = ListXqFileVO.class)
    @GetMapping("/pageFileByNodeId")
    public Result<?> pageFileByNodeId(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                      @RequestParam String nodeId,
                                      @RequestParam(required = false) String username,
                                      @RequestParam(required = false) String fileName) {


        Page<ListXqFileVO> page = new Page<>(pageNo, pageSize);
        IPage<ListXqFileVO> pageList = xqFilesService.pageFileByNodeId(page, nodeId, username, fileName);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param sysCategory
     * @return
     */
    @PostMapping(value = "/addFileToTree")
    @ApiOperation(value = "添加文件到文件树", notes = "添加文件到文件树")
    @AutoLog(value = "添加文件到文件树")
    public Result<?> addFileToTree(@RequestBody InstOrUpdtXqFilesDTO dto) {
        boolean b = xqFilesService.addFileToTree(dto);
        return b ? Result.OK("添加成功") : Result.error("添加失败");
    }


    /**
     * 重命名
     *
     * @param sysCategory
     * @return
     */
    @PostMapping(value = "/renameFile")
    @ApiOperation(value = "重命名文件", notes = "修复文件")
    @AutoLog(value = "重命名文件")
    public Result<?> renameFile(@RequestBody InstOrUpdtXqFilesDTO dto) {
        XqFiles xqFiles = new XqFiles();
        BeanUtils.copyProperties(dto, xqFiles);

        return xqFilesService.updateById(xqFiles) ? Result.OK("重命名成功") : Result.error("重命名失败");
    }

    /**
     * 批量添加文件
     *
     * @param sysCategory
     * @return
     */
    @PostMapping(value = "/addFileToTreeByList")
    @ApiOperation(value = "批量添加文件", notes = "批量添加文件")
    @AutoLog(value = "批量添加文件")
    public Result<?> addFileToTreeByList(@RequestBody List<InstOrUpdtXqFilesDTO> dtos) {
        boolean b = xqFilesService.addFileToTreeByList(dtos);
        return b ? Result.OK("添加成功") : Result.error("添加失败");
    }


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/deleteBatchFiles")
    @ApiOperation(value = "批量删除文件", notes = "批量删除文件")
    @AutoLog(value = "批量删除文件")
    public Result<?> deleteBatchFiles(@RequestParam String ids) {
        LoginUser userInfo = FilterContextHandler.getUserInfo();
        String username = userInfo.getUsername();

        List<String> strings = Arrays.asList(ids.split(","));
        for (String str : strings) {
            XqFiles xqFiles = xqFilesService.getById(str);
            if (xqFiles == null) {
                return Result.error("文件已被删除");
            }
            SysCategory sysCategory = sysCategoryService.getOne(new LambdaQueryWrapper<SysCategory>().eq(SysCategory::getId, xqFiles.getNodeId()).last("limit 1"));
            if (sysCategory != null) {
                XqOrder xo = xqOrderService.getOne(new LambdaQueryWrapper<XqOrder>().eq(XqOrder::getId, sysCategory.getOrderId()).last("limit 1"));
                if (xo != null) {
                    if (userInfo.getRoleCodes().contains(1)) {
                        List<String> records = xqOrderTransferRecordService.lambdaQuery()
                                .eq(XqOrderTransferRecord::getOrderId, xo.getId()).list().stream().map(XqOrderTransferRecord::getCreateBy).distinct().collect(Collectors.toList());
                        records.add(username);
                        if (!records.contains(xqFiles.getCreateBy())) {
                            return Result.error("只能删除自己的文件");
                        }
                    } else {
                        if (!(xqFiles.getCreateBy().equals(username))) {
                            return Result.error("只能删除自己的文件");
                        }
                    }
                } else {
                    return Result.error("订单不存在");
                }
            } else {
                return Result.error("目录已被删除");
            }
        }


        boolean b = xqFilesService.removeByIds(strings);
        return b ? Result.OK("删除成功") : Result.error("删除失败");
    }

    /**
     * 批量移动文件
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/moveBatchFiles")
    @ApiOperation(value = "批量移动文件", notes = "批量移动文件")
    @AutoLog(value = "批量移动文件")
    public Result<?> moveBatchFiles(@RequestBody @Valid MoveFilesDTO dto) {
        String ids = dto.getIds();
        String nodeId = dto.getNodeId();

        boolean b = xqFilesService.moveBatchFiles(ids, nodeId);
        return b ? Result.OK("移动成功") : Result.error("移动失败");
    }


    // 查询文件树接口
    @AutoLog("查询文件树接口")
    @GetMapping("/listFileTree")
    @ApiOperation(value = "查询文件树接口", notes = "查询文件树接口")
    public Result<?> listFileTree(@RequestParam String orderId,
                                  @RequestParam(required = false) String pid) {
        List<TreeSelectModel> ls = this.sysCategoryService.queryListByCodeAndModuleType(orderId, pid);
        loadAllCategoryChildren(ls);
        if (ls.size() > 0) {
        List<TreeSelectModel> childLS = ls.get(0).getChildren();
            if (childLS.size() > 0) {
                List<TreeSelectModel> huoyunOBj = childLS.stream()
                        .filter(huoyun -> huoyun.getTitle().equals("货运"))
                        .collect(Collectors.toList());

                List<TreeSelectModel> huoyunChild = huoyunOBj.get(0).getChildren();
                huoyunChild.sort(Comparator.comparingInt(a -> {
                    if (a.getTitle().equals("出运信息")) {
                        return a.equals(huoyunChild.get(huoyunChild.size() - 1)) ? -1 : 0;
                    } else {
                        return 0;
                    }
                }));

                List<TreeSelectModel> caigouOBj = childLS.stream()
                        .filter(huoyun -> huoyun.getTitle().equals("采购"))
                        .collect(Collectors.toList());

                List<TreeSelectModel> caigouChild = caigouOBj.get(0).getChildren();
                caigouChild.sort(Comparator.comparingInt(a -> {
                    if (a.getTitle().equals("工厂检测报告")) {
                        return a.equals(caigouChild.get(caigouChild.size() - 1)) ? 1 : 0;
                    } else {
                        return -1;
                    }
                }));
            }
        }

        return Result.OK(ls);
    }
}
