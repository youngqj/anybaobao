package com.interesting.business.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.interesting.business.system.entity.SysAnnouncement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description: 系统通告表
 * @Author: interesting-boot
 * @Date:  2019-01-02
 * @Version: V1.0
 */
@Mapper
public interface SysAnnouncementMapper extends BaseMapper<SysAnnouncement> {


	List<SysAnnouncement> querySysCementListByUserId(Page<SysAnnouncement> page, @Param("userId") String userId, @Param("msgCategory") String msgCategory);

}
