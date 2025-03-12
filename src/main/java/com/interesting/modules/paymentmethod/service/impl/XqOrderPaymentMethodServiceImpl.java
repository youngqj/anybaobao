package com.interesting.modules.paymentmethod.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.modules.base.IdAndNameVO;
import com.interesting.modules.paymentmethod.dto.ListXqOrderPaymentMethodDTO;
import com.interesting.modules.paymentmethod.dto.XqOrderPaymentMethodDTO;
import com.interesting.modules.paymentmethod.entity.XqOrderPaymentMethod;
import com.interesting.modules.paymentmethod.mapper.XqOrderPaymentMethodMapper;
import com.interesting.modules.paymentmethod.service.IXqOrderPaymentMethodService;
import com.interesting.modules.paymentmethod.vo.ListXqOrderPaymentMethodVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 付款方式表(XqOrderPaymentMethod)表服务实现类
 *
 * @author 郭征宇
 * @since 2023-09-15 11:51:47
 */
@Service
public class XqOrderPaymentMethodServiceImpl extends ServiceImpl<XqOrderPaymentMethodMapper, XqOrderPaymentMethod> implements IXqOrderPaymentMethodService {
    @Resource
    private XqOrderPaymentMethodMapper xqOrderPaymentMethodMapper;


    /**
     * 分页查询
     *
     * @param dto  筛选条件
     * @param page 分页对象
     * @return 查询结果
     */
    @Override
    public IPage<ListXqOrderPaymentMethodVO> queryByPage(ListXqOrderPaymentMethodDTO dto, Page<ListXqOrderPaymentMethodVO> page) {

        return xqOrderPaymentMethodMapper.queryByPage(dto, page);
    }

    /**
     * 新增数据
     *
     * @param dto
     * @return
     */
    @Override
    public boolean insert(XqOrderPaymentMethodDTO dto) {
        XqOrderPaymentMethod xqOrderPaymentMethod = new XqOrderPaymentMethod();
        BeanUtils.copyProperties(dto, xqOrderPaymentMethod);

        return this.save(xqOrderPaymentMethod);
    }

    /**
     * 修改数据
     *
     * @param dto
     * @return
     */
    @Override
    public boolean update(XqOrderPaymentMethodDTO dto) {
        String id = dto.getId();
        XqOrderPaymentMethod xqOrderPaymentMethod = this.getById(id);
        if (xqOrderPaymentMethod == null) throw new InterestingBootException("修改失败，该数据不存在，请刷新后重试");
        BeanUtils.copyProperties(dto, xqOrderPaymentMethod);

        return this.updateById(xqOrderPaymentMethod);
    }

    @Override
    public List<IdAndNameVO> listPaymentMethod() {
        List<XqOrderPaymentMethod> xqOrderPaymentMethods = list();

        return xqOrderPaymentMethods.stream().sorted((x1, x2) -> Long.compare(x2.getCreateTime().getTime(), x1.getCreateTime().getTime()))
                .map(xqOrderPaymentMethod -> {
                    IdAndNameVO idAndNameVO = new IdAndNameVO();
                    idAndNameVO.setId(xqOrderPaymentMethod.getId());
                    idAndNameVO.setName(xqOrderPaymentMethod.getText());

                    return idAndNameVO;
                }).collect(Collectors.toList());

    }


}
