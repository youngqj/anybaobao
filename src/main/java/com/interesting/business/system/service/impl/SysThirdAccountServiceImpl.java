package com.interesting.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.util.PasswordUtil;
import com.interesting.common.util.UUIDGenerator;
import com.interesting.common.util.oConvertUtils;
import com.interesting.business.system.entity.SysRole;
import com.interesting.business.system.entity.SysThirdAccount;
import com.interesting.business.system.entity.SysUser;
import com.interesting.business.system.entity.SysUserRole;
import com.interesting.business.system.mapper.SysRoleMapper;
import com.interesting.business.system.mapper.SysThirdAccountMapper;
import com.interesting.business.system.mapper.SysUserMapper;
import com.interesting.business.system.mapper.SysUserRoleMapper;
import com.interesting.business.system.service.ISysThirdAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 第三方登录账号表
 * @Author: interesting-boot
 * @Date:   2020-11-17
 * @Version: V1.0
 */
@Service
public class SysThirdAccountServiceImpl extends ServiceImpl<SysThirdAccountMapper, SysThirdAccount> implements ISysThirdAccountService {

    @Autowired
    private  SysThirdAccountMapper sysThirdAccountMapper;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public void updateThirdUserId(SysUser sysUser,String thirdUserUuid) {
        //修改第三方登录账户表使其进行添加用户id
        LambdaQueryWrapper<SysThirdAccount> query = new LambdaQueryWrapper<>();
        query.eq(SysThirdAccount::getThirdUserUuid,thirdUserUuid);
        SysThirdAccount account = sysThirdAccountMapper.selectOne(query);
        SysThirdAccount sysThirdAccount = new SysThirdAccount();
        sysThirdAccount.setSysUserId(sysUser.getId());
        //根据当前用户id和登录方式查询第三方登录表
        LambdaQueryWrapper<SysThirdAccount> thirdQuery = new LambdaQueryWrapper<>();
        thirdQuery.eq(SysThirdAccount::getSysUserId,sysUser.getId());
        thirdQuery.eq(SysThirdAccount::getThirdType,account.getThirdType());
        SysThirdAccount sysThirdAccounts = sysThirdAccountMapper.selectOne(thirdQuery);
        if(sysThirdAccounts!=null){
          sysThirdAccountMapper.deleteById(sysThirdAccounts.getId());
        }
        //更新用户账户表sys_user_id
        sysThirdAccountMapper.update(sysThirdAccount,query);
    }

    @Override
    public SysUser createUser(String phone, String thirdUserUuid) {
       //先查询第三方，获取登录方式
        LambdaQueryWrapper<SysThirdAccount> query = new LambdaQueryWrapper<>();
        query.eq(SysThirdAccount::getThirdUserUuid,thirdUserUuid);
        SysThirdAccount account = sysThirdAccountMapper.selectOne(query);
        //添加用户
        SysUser user = new SysUser();
        user.setActivitiSync(CommonConstant.ACT_SYNC_0);
        user.setIzDelete(CommonConstant.IZ_DELETE_0);
        user.setStatus(1);
        user.setUsername(thirdUserUuid);
        user.setPhone(phone);
        //设置初始密码
        String salt = oConvertUtils.randomGen(8);
        user.setSalt(salt);
        String passwordEncode = PasswordUtil.encrypt(user.getUsername(), "123456", salt);
        user.setPassword(passwordEncode);
        user.setRealname(account.getRealname());
        user.setAvatar(account.getAvatar());
        String s = this.saveThirdUser(user);
        //更新用户第三方账户表的userId
        SysThirdAccount sysThirdAccount = new SysThirdAccount();
        sysThirdAccount.setSysUserId(s);
        sysThirdAccountMapper.update(sysThirdAccount,query);
        return user;
    }

    public String saveThirdUser(SysUser sysUser) {
        //保存用户
        String userid = UUIDGenerator.generate();
        sysUser.setId(userid);
        sysUserMapper.insert(sysUser);
        //获取第三方角色
        SysRole sysRole = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleCode, "third_role"));
        //保存用户角色
        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(sysRole.getId());
        userRole.setUserId(userid);
        sysUserRoleMapper.insert(userRole);
        return userid;
    }
}
