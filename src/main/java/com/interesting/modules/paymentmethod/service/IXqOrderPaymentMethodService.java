package com.interesting.modules.paymentmethod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.base.IdAndNameVO;
import com.interesting.modules.paymentmethod.dto.ListXqOrderPaymentMethodDTO;
import com.interesting.modules.paymentmethod.dto.XqOrderPaymentMethodDTO;
import com.interesting.modules.paymentmethod.entity.XqOrderPaymentMethod;
import com.interesting.modules.paymentmethod.vo.ListXqOrderPaymentMethodVO;

import java.util.List;

/**
 * 付款方式表(XqOrderPaymentMethod)表服务接口
 *
 * @author 郭征宇
 * @since 2023-09-15 11:51:47
 */
public interface IXqOrderPaymentMethodService extends IService<XqOrderPaymentMethod> {



    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @param page      分页对象
     * @return 查询结果
     */
    IPage<ListXqOrderPaymentMethodVO> queryByPage(ListXqOrderPaymentMethodDTO dto, Page<ListXqOrderPaymentMethodVO> page);

    /**
     * 新增数据
     *
     * @param dto 
     * @return boolean
     */
    boolean insert(XqOrderPaymentMethodDTO dto);

    /**
     * 修改数据
     *
     * @param dto
     * @return boolean
     */
    boolean update(XqOrderPaymentMethodDTO dto);


    List<IdAndNameVO> listPaymentMethod();

}
