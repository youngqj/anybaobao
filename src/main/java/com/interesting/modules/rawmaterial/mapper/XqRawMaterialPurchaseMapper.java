package com.interesting.modules.rawmaterial.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.remittance.vo.QueryDataNotInDetailVO;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.rawmaterial.entity.XqRawMaterialPurchase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.rawmaterial.vo.XqRawMaterialPurchaseVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.rawmaterial.dto.QueryXqRawMaterialPurchaseDTO;

/**
 * @Description: 原料采购表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface XqRawMaterialPurchaseMapper extends BaseMapper<XqRawMaterialPurchase> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqRawMaterialPurchaseVO> pageList(@Param("page") Page<XqRawMaterialPurchaseVO> page, @Param("dto") QueryXqRawMaterialPurchaseDTO dto);


    void updateImport(@Param("raw") XqRawMaterialPurchase rawMaterialPurchase);

    List<QueryDataNotInDetailVO> queryNotPayOrder();
}
