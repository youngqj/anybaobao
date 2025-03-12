package com.interesting.modules.paymentmethod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.paymentmethod.dto.ListXqOrderPaymentMethodDTO;
import com.interesting.modules.paymentmethod.entity.XqOrderPaymentMethod;
import com.interesting.modules.paymentmethod.vo.ListXqOrderPaymentMethodVO;
import org.apache.ibatis.annotations.Param;

/**
 * 付款方式表(XqOrderPaymentMethod)表数据库访问层
 *
 * @author 郭征宇
 * @since 2023-09-15 11:51:47
 */
public interface XqOrderPaymentMethodMapper extends BaseMapper<XqOrderPaymentMethod> {

    IPage<ListXqOrderPaymentMethodVO> queryByPage(@Param("dto") ListXqOrderPaymentMethodDTO dto, @Param("page") Page<ListXqOrderPaymentMethodVO> page);

}

