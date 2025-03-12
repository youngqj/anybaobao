package com.interesting.modules.freightInfo.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.rawmaterial.entity.XqRawMaterialPurchase;
import org.apache.ibatis.annotations.Param;
import com.interesting.modules.freightInfo.entity.XqFreightInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.freightInfo.vo.XqFreightInfoVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.freightInfo.dto.QueryXqFreightInfoDTO;

/**
 * @Description: 货运信息表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface XqFreightInfoMapper extends BaseMapper<XqFreightInfo> {
    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    IPage<XqFreightInfoVO> pageList(@Param("page") Page<XqFreightInfoVO> page, @Param("dto") QueryXqFreightInfoDTO dto);


    void updateImport(@Param("fret") XqFreightInfo xqFreightInfo);
}
