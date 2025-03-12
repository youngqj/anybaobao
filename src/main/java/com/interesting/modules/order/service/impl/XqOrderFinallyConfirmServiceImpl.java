package com.interesting.modules.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.entity.SysRole;
import com.interesting.business.system.entity.SysUser;
import com.interesting.business.system.service.ISysRoleService;
import com.interesting.business.system.service.ISysUserService;
import com.interesting.common.util.StringUtils;
import com.interesting.modules.order.entity.XqOrderFinallyConfirm;
import com.interesting.modules.order.mapper.XqOrderFinallyConfirmMapper;
import com.interesting.modules.order.service.IXqOrderFinallyConfirmService;
import com.interesting.modules.order.vo.ListXqOrderFinallyConfirmVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 面单管理-最终确认情况(XqOrderFinallyConfirm)表服务实现类
 *
 * @author 郭征宇
 * @since 2023-09-14 11:36:16
 */
@Service
public class XqOrderFinallyConfirmServiceImpl extends ServiceImpl<XqOrderFinallyConfirmMapper, XqOrderFinallyConfirm> implements IXqOrderFinallyConfirmService {
    @Resource
    private XqOrderFinallyConfirmMapper xqOrderFinallyConfirmMapper;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysRoleService sysRoleService;

    @Override
    public List<ListXqOrderFinallyConfirmVO> listCompleteState(String orderId) {

        List<XqOrderFinallyConfirm> xqOrderFinallyConfirms = this.list(new LambdaQueryWrapper<XqOrderFinallyConfirm>().eq(XqOrderFinallyConfirm::getOrderId, orderId));
        HashMap<Integer, XqOrderFinallyConfirm> stringXqOrderFinallyConfirmHashMap = new HashMap<>();
        for (XqOrderFinallyConfirm xqOrderFinallyConfirm : xqOrderFinallyConfirms) {
            int roleId = Integer.parseInt(xqOrderFinallyConfirm.getRoleId());
            stringXqOrderFinallyConfirmHashMap.put(roleId, xqOrderFinallyConfirm);
        }
        List<ListXqOrderFinallyConfirmVO> collect = stringXqOrderFinallyConfirmHashMap.values().stream().map(xqOrderFinallyConfirm -> {
            ListXqOrderFinallyConfirmVO listXqOrderFinallyConfirmVO = new ListXqOrderFinallyConfirmVO();
            BeanUtils.copyProperties(xqOrderFinallyConfirm, listXqOrderFinallyConfirmVO);
            listXqOrderFinallyConfirmVO.setIzConfirmed(1);
            return listXqOrderFinallyConfirmVO;
        }).peek(listXqOrderFinallyConfirmVO -> {
            if (StringUtils.isNotBlank(listXqOrderFinallyConfirmVO.getUserId())) {
                SysUser sysUser = sysUserService.getById(listXqOrderFinallyConfirmVO.getUserId());
                if (sysUser != null) {
                    listXqOrderFinallyConfirmVO.setUserId(sysUser.getRealname());
                }
            }
        }).collect(Collectors.toList());

        // 已有的角色集合
        Set<Integer> integers = stringXqOrderFinallyConfirmHashMap.keySet();

        // 总的角色集合
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        // 已有的角色迭代器
        // 如果已有的不包含总集合中的元素，则创建角色并添加入结果集
        for (Integer integer : list) {
            if (!integers.contains(integer)) {
                ListXqOrderFinallyConfirmVO listXqOrderFinallyConfirmVO = new ListXqOrderFinallyConfirmVO();
                listXqOrderFinallyConfirmVO.setId(UUID.randomUUID().toString().replace("-", ""));
                listXqOrderFinallyConfirmVO.setRoleId(integer.toString());
                listXqOrderFinallyConfirmVO.setIzConfirmed(0);
                collect.add(listXqOrderFinallyConfirmVO);
            }
        }
        List<ListXqOrderFinallyConfirmVO> listXqOrderFinallyConfirmVOS = collect.stream().sorted(Comparator.comparing(ListXqOrderFinallyConfirmVO::getRoleId)).collect(Collectors.toList());


        List<SysRole> roleList = sysRoleService.list(new LambdaQueryWrapper<SysRole>().in(SysRole::getId, list));
        for (int i = 0; i < 7; i++) {
            listXqOrderFinallyConfirmVOS.get(i).setRoleId(roleList.get(i).getDescription());
        }

        return listXqOrderFinallyConfirmVOS.subList(0,7);


    }

    @Override
    public Set<String> selectOrderNumByRoleCodes(Integer roleCode, String followerAudit, Integer izRole, String userId) {
        return getBaseMapper().selectOrderNumByRoleCodes(roleCode, followerAudit, izRole, userId);
    }

    @Override
    public Set<String> selectOrderNumByRoles(Integer roleCode, String followerAudit, Integer izRole, Integer shippingRole) {
        return getBaseMapper().selectOrderNumByRoles(roleCode, followerAudit, izRole, shippingRole);
    }


}
