package com.interesting.modules.freightcompany.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.freightcompany.dto.ListXqFreightCompanyDTO;
import com.interesting.modules.freightcompany.dto.XqFreightCompanyDTO;
import com.interesting.modules.freightcompany.entity.XqFreightCompany;
import com.interesting.modules.freightcompany.vo.ListCompanyNameAndContactVO;
import com.interesting.modules.freightcompany.vo.ListXqFreightCompanyVO;

import java.util.List;

/**
 * 货代公司(XqFreightCompany)表服务接口
 *
 * @author 郭征宇
 * @since 2023-12-25 16:27:00
 */
public interface IXqFreightCompanyService extends IService<XqFreightCompany> {


    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @param page      分页对象
     * @return 查询结果
     */
    IPage<ListXqFreightCompanyVO> queryByPage(ListXqFreightCompanyDTO dto, Page<ListXqFreightCompanyVO> page);

    /**
     * 新增数据
     *
     * @param dto 
     * @return boolean
     */
    boolean insert(XqFreightCompanyDTO dto);

    /**
     * 修改数据
     *
     * @param dto
     * @return boolean
     */
    boolean update(XqFreightCompanyDTO dto);


    List<ListCompanyNameAndContactVO> listNameAndContact();

}
