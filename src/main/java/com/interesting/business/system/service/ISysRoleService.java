package com.interesting.business.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.common.api.vo.Result;
import com.interesting.business.system.entity.SysRole;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author: interesting-boot
 * @since 2018-12-19
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 删除角色
     * @param roleid
     * @return
     */
    public boolean deleteRole(String roleid);

    /**
     * 批量删除角色
     * @param roleids
     * @return
     */
    public boolean deleteBatchRole(String[] roleids);

}
