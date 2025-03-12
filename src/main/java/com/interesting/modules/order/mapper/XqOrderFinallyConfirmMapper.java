package com.interesting.modules.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.modules.order.entity.XqOrderFinallyConfirm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 面单管理-最终确认情况(XqOrderFinallyConfirm)表数据库访问层
 *
 * @author 郭征宇
 * @since 2023-09-14 11:36:57
 */
public interface XqOrderFinallyConfirmMapper extends BaseMapper<XqOrderFinallyConfirm> {


    Set<String> selectOrderNumByRoleCodes(@Param("roleCode") Integer roleCode,
                                          @Param("followerAudit") String followerAudit,
                                          @Param("izRole") Integer izRole,
                                          @Param("userId") String userId);

    Set<String> selectOrderNumByRoles(@Param("roleCode") Integer roleCode,
                                      @Param("followerAudit") String followerAudit,
                                      @Param("izRole") Integer izRole,
                                      @Param("shippingRole")Integer shippingRole);

}

