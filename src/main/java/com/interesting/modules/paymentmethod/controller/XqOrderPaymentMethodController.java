package com.interesting.modules.paymentmethod.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.common.api.vo.Result;
import com.interesting.common.aspect.annotation.AutoLog;
import com.interesting.modules.order.entity.XqOrder;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.paymentmethod.dto.ListXqOrderPaymentMethodDTO;
import com.interesting.modules.paymentmethod.dto.XqOrderPaymentMethodDTO;
import com.interesting.modules.paymentmethod.entity.XqOrderPaymentMethod;
import com.interesting.modules.paymentmethod.service.IXqOrderPaymentMethodService;
import com.interesting.modules.paymentmethod.vo.ListXqOrderPaymentMethodVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 付款方式表(XqOrderPaymentMethod)表控制层
 *
 * @author 郭征宇
 * @since 2023-09-15 11:51:46
 */

@Api(tags = "付款方式表")
@Slf4j
@RestController
@RequestMapping("/xqOrderPaymentMethod")
public class XqOrderPaymentMethodController {


    @Resource
    private IXqOrderPaymentMethodService xqOrderPaymentMethodService;

    @Resource
    private IXqOrderService xqOrderService;

    /**
     * 分页查询
     *
     * @param dto
     * @return 查询结果
     */
    @AutoLog(value = "付款方式表-分页列表查询")
    @ApiOperation(value = "分页列表查询", response = ListXqOrderPaymentMethodVO.class)
    @GetMapping("/list")
    public Result<?> queryPageList(ListXqOrderPaymentMethodDTO dto,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<ListXqOrderPaymentMethodVO> page = new Page<>(pageNo, pageSize);

        return Result.OK(xqOrderPaymentMethodService.queryByPage(dto, page));
    }


    /**
     * 新增数据
     *
     * @param dto
     * @return
     */
    @AutoLog(value = "付款方式表-新增记录")
    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Valid XqOrderPaymentMethodDTO dto) {
        return xqOrderPaymentMethodService.insert(dto) ? Result.OK("新增成功") : Result.error("新增失败");
    }

    /**
     * 编辑数据
     *
     * @param dto
     * @return 编辑结果
     */
    @AutoLog(value = "付款方式表-编辑")
    @ApiOperation(value = "编辑")
    @PostMapping("/edit")
    public Result<?> edit(@RequestBody @Valid XqOrderPaymentMethodDTO dto) {
        return xqOrderPaymentMethodService.update(dto) ? Result.OK("编辑成功") : Result.error("编辑失败");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "付款方式表-删除记录")
    @ApiOperation(value = "删除")
    @GetMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id") String id) {

        return xqOrderPaymentMethodService.removeById(id) ? Result.OK("删除成功!") : Result.error("删除失败，请刷新后重试");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "付款方式表-删除记录")
    @ApiOperation(value = "批量删除，英文逗号隔开")
    @GetMapping("/deleteBatch")
    @Transactional(rollbackFor = Exception.class)
    public Result<?> deleteBatch(@RequestParam(name = "ids") String ids) {
        String[] split = ids.split(",");

        for (String s : split) {
            XqOrderPaymentMethod xqOrderPaymentMethod = xqOrderPaymentMethodService.getById(s);
            if (xqOrderPaymentMethod == null)
                return Result.error("删除失败，选中的付款方式中包括了已被删除的，请刷新后重试");
            List<XqOrder> list = xqOrderService.lambdaQuery().eq(XqOrder::getPaymentMethod, s).list();
            if (list.size() != 0) {
                String string = list.stream().map(XqOrder::getOrderNum).collect(Collectors.toList()).toString();
                return Result.error("删除失败，" + xqOrderPaymentMethod.getText() + "已经被订单"+ string + "使用");
            }
        }


        return xqOrderPaymentMethodService.removeByIds(Arrays.asList(split)) ? Result.OK("删除成功") : Result.error("删除失败，请刷新后重试");
    }


}

