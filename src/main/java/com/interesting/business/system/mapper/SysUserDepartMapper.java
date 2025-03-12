package com.interesting.business.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.interesting.business.system.entity.SysUserDepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysUserDepartMapper extends BaseMapper<SysUserDepart>{

	List<SysUserDepart> getUserDepartByUid(@Param("userId") String userId);
}
