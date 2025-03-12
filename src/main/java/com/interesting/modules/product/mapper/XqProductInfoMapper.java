package com.interesting.modules.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.product.dto.QueryProductInfoDTO;
import com.interesting.modules.product.entity.XqProductInfo;
import com.interesting.modules.product.vo.XqProductInfoVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 26773
* @description 针对表【xq_product_info(产品信息表)】的数据库操作Mapper
* @createDate 2023-06-07 16:50:57
* @Entity com.interesting.modules.product.XqProductInfo
*/
public interface XqProductInfoMapper extends BaseMapper<XqProductInfo> {

    IPage<XqProductInfoVO> queryXqProductInfoVOPage(@Param("page") Page<XqProductInfoVO> page,
                                                    @Param("dto") QueryProductInfoDTO dto);
}




