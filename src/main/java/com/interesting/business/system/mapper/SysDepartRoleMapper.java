package com.interesting.business.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.interesting.business.system.entity.SysDepartRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 部门角色
 * @Author: interesting-boot
 * @Date: 2020-02-12
 * @Version: V1.0
 */
@Mapper
public interface SysDepartRoleMapper extends BaseMapper<SysDepartRole> {
    /**
     * 根据用户id，部门id查询可授权所有部门角色
     *
     * @param orgCode
     * @param userId
     * @return
     */
    public List<SysDepartRole> queryDeptRoleByDeptAndUser(
            @Param("orgCode") String orgCode,
            @Param("userId") String userId);
}
