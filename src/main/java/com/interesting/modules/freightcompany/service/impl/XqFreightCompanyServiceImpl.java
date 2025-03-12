package com.interesting.modules.freightcompany.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.freightcompany.dto.ListXqFreightCompanyDTO;
import com.interesting.modules.freightcompany.dto.XqFreightCompanyDTO;
import com.interesting.modules.freightcompany.entity.XqFreightCompany;
import com.interesting.modules.freightcompany.mapper.XqFreightCompanyMapper;
import com.interesting.modules.freightcompany.service.IXqFreightCompanyService;
import com.interesting.modules.freightcompany.vo.ListCompanyNameAndContactVO;
import com.interesting.modules.freightcompany.vo.ListXqFreightCompanyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 货代公司(XqFreightCompany)表服务实现类
 *
 * @author 郭征宇
 * @since 2023-12-25 16:27:00
 */
@Service
public class XqFreightCompanyServiceImpl extends ServiceImpl<XqFreightCompanyMapper,XqFreightCompany> implements IXqFreightCompanyService {
    @Resource
    private XqFreightCompanyMapper xqFreightCompanyMapper;


    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @param page      分页对象
     * @return 查询结果
     */
    @Override
    public IPage<ListXqFreightCompanyVO> queryByPage(ListXqFreightCompanyDTO dto, Page<ListXqFreightCompanyVO> page) {
    
        return xqFreightCompanyMapper.queryByPage(dto,page);
    }

    /**
     * 新增数据
     *
     * @param dto
     * @return 
     */
    @Override
    public boolean insert(XqFreightCompanyDTO dto) {
        // 判断是否有重复
        Integer count = this.lambdaQuery().eq(XqFreightCompany::getCompanyName, dto.getCompanyName()).count();
        if (count != 0) throw new InterestingBootException("公司名称重复");

        XqFreightCompany xqFreightCompany = new XqFreightCompany();
        BeanUtils.copyProperties(dto,xqFreightCompany);
        
        return this.save(xqFreightCompany);
    }

    /**
     * 修改数据
     *
     * @param dto
     * @return 
     */
    @Override
    public boolean update(XqFreightCompanyDTO dto) {

        String id = dto.getId();
        XqFreightCompany xqFreightCompany = this.getById(id);
        if(xqFreightCompany == null) throw new InterestingBootException("修改失败，该数据不存在，请刷新后重试");
        // 判断是否有重复
        if (!xqFreightCompany.getCompanyName().equals(dto.getCompanyName())) {
            Integer count = this.lambdaQuery().eq(XqFreightCompany::getCompanyName, dto.getCompanyName()).count();
            if (count != 0) throw new InterestingBootException("公司名称重复");
        }
        BeanUtils.copyProperties(dto,xqFreightCompany);

        return this.updateById(xqFreightCompany);
    }

    @Override
    public List<ListCompanyNameAndContactVO> listNameAndContact() {

        List<XqFreightCompany> list = this.lambdaQuery().list();
        return list.stream().map(xqFreightCompany -> {
            ListCompanyNameAndContactVO listCompanyNameAndContactVO = new ListCompanyNameAndContactVO();
            listCompanyNameAndContactVO.setId(xqFreightCompany.getId());
            listCompanyNameAndContactVO.setCompanyName(xqFreightCompany.getCompanyName());
            if (StringUtils.isNotBlank(xqFreightCompany.getContactName()))
                listCompanyNameAndContactVO.setContactNameAndPhone(xqFreightCompany.getContactName()+" "+xqFreightCompany.getContactPhone());
            return listCompanyNameAndContactVO;
        }).collect(Collectors.toList());

    }


}
