package com.interesting.business.system.service;

import com.interesting.business.system.entity.SysThirdAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.interesting.business.system.entity.SysUser;

/**
 * @Description: 第三方登录账号表
 * @Author: interesting-boot
 * @Date:   2020-11-17
 * @Version: V1.0
 */
public interface ISysThirdAccountService extends IService<SysThirdAccount> {
    /**更新第三方账户信息*/
    void updateThirdUserId(SysUser sysUser, String thirdUserUuid);
    /**创建第三方用户*/
    SysUser createUser(String phone, String thirdUserUuid);

}
