package com.interesting.modules.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.util.BeanUtils;
import com.interesting.modules.order.dto.sub.XqOrderExtraCostDTO;
import com.interesting.modules.order.entity.XqOrderExtraCost;
import com.interesting.modules.order.mapper.XqOrderExtraCostMapper;
import com.interesting.modules.order.service.IXqOrderExtraCostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 付款方式表(XqOrderExtraCost)表服务实现类
 *
 * @author 郭征宇
 * @since 2023-09-18 14:48:40
 */
@Service
public class XqOrderExtraCostServiceImpl extends ServiceImpl<XqOrderExtraCostMapper, XqOrderExtraCost> implements IXqOrderExtraCostService {
    /**
     * 新增数据
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(List<XqOrderExtraCostDTO> dto, String orderId) {

        if (CollectionUtil.isEmpty(dto)) return;

        saveBatch(dto.stream().map(xqOrderExtraCostDTO -> {
            XqOrderExtraCost xqOrderExtraCost = new XqOrderExtraCost();
            com.interesting.common.util.BeanUtils.copyProperties(xqOrderExtraCostDTO, xqOrderExtraCost);
            xqOrderExtraCost.setId(null);
            xqOrderExtraCost.setOrderId(orderId);
            return xqOrderExtraCost;

        }).collect(Collectors.toList()));


    }

    /**
     * 修改数据
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<XqOrderExtraCostDTO> dto, String orderId) {

        if (CollectionUtils.isEmpty(dto)){
            this.remove(new LambdaQueryWrapper<XqOrderExtraCost>().eq(XqOrderExtraCost::getOrderId,orderId));
            return;
        }

        // 原有的列表
        List<XqOrderExtraCost> xqOrderExtraCosts = this.list(new LambdaQueryWrapper<XqOrderExtraCost>().eq(XqOrderExtraCost::getOrderId, orderId));

        if (CollectionUtils.isEmpty(xqOrderExtraCosts)) {
            // 若原有的列表为空，则全部新增
            this.saveBatch(dto.stream().map(xqFileDTO -> {
                XqOrderExtraCost xqOrderExtraCost = new XqOrderExtraCost();
                BeanUtils.copyProperties(xqFileDTO, xqOrderExtraCost);
                xqOrderExtraCost.setId(null);
                xqOrderExtraCost.setOrderId(orderId);
                return xqOrderExtraCost;
            }).collect(Collectors.toList()));

        } else {
            // 获取原有的id集合
            List<String> ids = xqOrderExtraCosts.stream().map(XqOrderExtraCost::getId).collect(Collectors.toList());


            // 获取dto中的id集合
            List<String> dtoIds = dto
                    .stream()
                    .map(XqOrderExtraCostDTO::getId)
                    .collect(Collectors.toList());

            if (dtoIds.size() == 0){
                this.removeByIds(ids);
                return;
            }

            // 从ids中把要保留的除去，剩下的则是需要删掉的
            // 如果去重成功，则说明有被删除的
            ids.removeAll(dtoIds);
            if (ids.size() != 0) {
                this.removeByIds(ids);
            }
            List<String> ids2 = xqOrderExtraCosts.stream().map(XqOrderExtraCost::getId).collect(Collectors.toList());
            // 对dto循环，原有id集合中没有的dto的id设为空
            for (XqOrderExtraCostDTO xqOrderExtraCostDTO : dto) {
                if (!ids2.contains(xqOrderExtraCostDTO.getId())) xqOrderExtraCostDTO.setId(null);
            }

            // 修改和新增
            this.saveOrUpdateBatch(dto.stream().map(xqFileDTO -> {
                XqOrderExtraCost xqOrderExtraCost = new XqOrderExtraCost();
                org.springframework.beans.BeanUtils.copyProperties(xqFileDTO, xqOrderExtraCost);
                xqOrderExtraCost.setOrderId(orderId);
                return xqOrderExtraCost;
            }).collect(Collectors.toList()));

        }



    }


}
