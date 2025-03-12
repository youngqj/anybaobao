package com.interesting.modules.accmaterial.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.accmaterial.vo.XqAccessoriesExportVO;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.accmaterial.entity.XqAccessoriesPurchase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.accmaterial.vo.XqAccessoriesPurchaseVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.accmaterial.dto.QueryXqAccessoriesPurchaseDTO;

/**
 * @Description: 辅料采购表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface XqAccessoriesPurchaseMapper extends BaseMapper<XqAccessoriesPurchase> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqAccessoriesPurchaseVO> pageList(@Param("page") Page<XqAccessoriesPurchaseVO> page, @Param("dto") QueryXqAccessoriesPurchaseDTO dto);

    List<XqAccessoriesExportVO> listByExport(@Param("orderNum") String orderNum);
}
