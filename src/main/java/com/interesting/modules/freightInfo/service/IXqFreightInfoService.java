package com.interesting.modules.freightInfo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.interesting.modules.freightInfo.entity.XqFreightInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interesting.modules.freightInfo.dto.QueryXqFreightInfoDTO;
import com.interesting.modules.freightInfo.vo.XqFreightInfoVO;
import com.interesting.modules.order.dto.sub.AddOrderFretDTO;

import java.util.List;

/**
 * @Description: 货运信息表
 * @Author: interesting-boot
 * @Date:   2023-06-02
 * @Version: V1.0
 */
public interface IXqFreightInfoService extends IService<XqFreightInfo> {
    /**
     * 分页列表查询
     *
     * @param page
     * @return
     */
    IPage<XqFreightInfoVO> pageList(Page<XqFreightInfoVO> page, QueryXqFreightInfoDTO dto);

    void updateOrderFretInfo(String orderId, List<AddOrderFretDTO> orderFretVOS, Integer roleCode);

}
