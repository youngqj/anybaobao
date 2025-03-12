package com.interesting.business.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.business.system.entity.SysCodeRole;
import com.interesting.business.system.entity.SysDepartRole;
import com.interesting.business.system.vo.SysUserDepVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 编码规则
 * @Author: zjk
 * @Date: 2021-07-23
 * @Version: V1.0
 */
@Mapper
public interface SysCodeRoleMapper extends BaseMapper<SysCodeRole> {

    /**
     *  根据类型获取编码规则
     * @param type
     * @return
     */
    public SysCodeRole getSysCodeRolebyType(@Param("type") Integer type);

}
