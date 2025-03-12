package com.interesting.modules.orderdetail.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.orderdetail.entity.XqOrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.orderdetail.vo.XqOrderDetailVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.orderdetail.dto.QueryXqOrderDetailDTO;

/**
 * @Description: 订单产品明细
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Mapper
public interface XqOrderDetailMapper extends BaseMapper<XqOrderDetail> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqOrderDetailVO> pageList(@Param("page") Page<XqOrderDetailVO> page, @Param("dto") QueryXqOrderDetailDTO dto);
}
