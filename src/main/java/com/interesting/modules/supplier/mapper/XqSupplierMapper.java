package com.interesting.modules.supplier.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.supplier.entity.XqSupplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.supplier.vo.XqSupplierVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.supplier.dto.QueryXqSupplierDTO;

/**
 * @Description: 供应商表
 * @Author: interesting-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface XqSupplierMapper extends BaseMapper<XqSupplier> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqSupplierVO> pageList(@Param("page") Page<XqSupplierVO> page, @Param("dto") QueryXqSupplierDTO dto);

    XqSupplier getByName(@Param("name") String name);
}
