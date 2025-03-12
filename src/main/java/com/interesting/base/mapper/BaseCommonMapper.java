package com.interesting.base.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.interesting.common.api.dto.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BaseCommonMapper extends BaseMapper<SysLog> {

//    /**
//     * 保存日志
//     *
//     * @param dto
//     */
//    @SqlParser(filter = true)
//    default void saveLog(@Param("dto") SysLog dto) {
//        this.insert(dto);
//    }

}
