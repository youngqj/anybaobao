package com.interesting.modules.freightcompany.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.freightcompany.dto.ListXqFreightCompanyDTO;
import com.interesting.modules.freightcompany.entity.XqFreightCompany;
import com.interesting.modules.freightcompany.vo.ListXqFreightCompanyVO;
import org.apache.ibatis.annotations.Param;

/**
 * 货代公司(XqFreightCompany)表数据库访问层
 *
 * @author 郭征宇
 * @since 2023-12-25 16:27:00
 */
public interface XqFreightCompanyMapper extends BaseMapper<XqFreightCompany> {
    
    IPage<ListXqFreightCompanyVO> queryByPage(@Param("dto") ListXqFreightCompanyDTO dto, Page<ListXqFreightCompanyVO> page);
    

}

