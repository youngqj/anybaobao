package com.interesting.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.order.dto.sub.XqOrderExtraCostDTO;
import com.interesting.modules.order.entity.XqOrderExtraCost;

import java.util.List;

/**
 * 付款方式表(XqOrderExtraCost)表服务接口
 *
 * @author 郭征宇
 * @since 2023-09-18 14:48:40
 */
public interface IXqOrderExtraCostService extends IService<XqOrderExtraCost> {

    /**
     * 新增数据
     *
     * @param dto
     * @return boolean
     */
    void insert(List<XqOrderExtraCostDTO> dto,String orderId);

    /**
     * 修改数据
     *
     * @param dto
     * @return boolean
     */
    void update(List<XqOrderExtraCostDTO> dto,String orderId);


}
