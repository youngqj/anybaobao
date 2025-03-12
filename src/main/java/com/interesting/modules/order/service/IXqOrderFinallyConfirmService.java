package com.interesting.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.modules.order.entity.XqOrderFinallyConfirm;
import com.interesting.modules.order.vo.ListXqOrderFinallyConfirmVO;

import java.util.List;
import java.util.Set;

/**
 * 面单管理-最终确认情况(XqOrderFinallyConfirm)表服务接口
 *
 * @author 郭征宇
 * @since 2023-09-14 11:36:16
 */
public interface IXqOrderFinallyConfirmService extends IService<XqOrderFinallyConfirm> {


    List<ListXqOrderFinallyConfirmVO> listCompleteState(String orderId);


    /**
     *
     * @param roleCode 当前人角色
     * @param followerAudit 跟单审核状态
     * @param izRole 已确认角色
     * @return
     */
    Set<String> selectOrderNumByRoleCodes(Integer roleCode, String followerAudit,Integer izRole, String userId);

    Set<String> selectOrderNumByRoles(Integer domesticFinance, String followerAudit, Integer accessoryPurchase, Integer shipping);
}
