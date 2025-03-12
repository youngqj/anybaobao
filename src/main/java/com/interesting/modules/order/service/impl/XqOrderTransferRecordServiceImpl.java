package com.interesting.modules.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.entity.SysUser;
import com.interesting.business.system.entity.SysUserRole;
import com.interesting.business.system.service.ISysUserRoleService;
import com.interesting.business.system.service.ISysUserService;
import com.interesting.config.FilterContextHandler;
import com.interesting.modules.base.IdAndNameVO;
import com.interesting.modules.order.entity.XqOrderTransferRecord;
import com.interesting.modules.order.mapper.XqOrderTransferRecordMapper;
import com.interesting.modules.order.service.IXqOrderTransferRecordService;
import com.interesting.modules.order.vo.ListXqOrderTransferRecordVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 面单管理-转让记录表(XqOrderTransferRecord)表服务实现类
 *
 * @author 郭征宇
 * @since 2023-09-15 09:19:48
 */
@Service
public class XqOrderTransferRecordServiceImpl extends ServiceImpl<XqOrderTransferRecordMapper,XqOrderTransferRecord> implements IXqOrderTransferRecordService {

    @Resource
    private XqOrderTransferRecordMapper xqOrderTransferRecordMapper;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Override
    public List<ListXqOrderTransferRecordVO> listTransferRecord(String orderId) {

        return xqOrderTransferRecordMapper.listTransferRecord(orderId);

    }

    @Override
    public List<IdAndNameVO> listOrderFollower() {

        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, "1"));
        List<String> userIds = sysUserRoles.stream().map(SysUserRole::getUserId).collect(Collectors.toList());
        ArrayList<IdAndNameVO> idAndNameVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(userIds)) return idAndNameVOS;
        // 结果集的查询条件去掉当前登录用户
        String userId = FilterContextHandler.getUserId();
        userIds.remove(userId);
        userIds.remove("1463691138900930562");
        for (SysUser sysUser : sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId, userIds))) {
            IdAndNameVO idAndNameVO = new IdAndNameVO();
            idAndNameVO.setId(sysUser.getId());
            idAndNameVO.setName(sysUser.getRealname());
            idAndNameVOS.add(idAndNameVO);
        }

        return idAndNameVOS;
    }

}
