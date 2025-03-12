package com.interesting.business.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interesting.business.system.entity.SysCodeRole;
import com.interesting.business.system.mapper.SysCodeRoleMapper;
import com.interesting.business.system.service.ISysCodeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 编码规则
 * @Author: zjk
 * @Date: 2021-07-23
 * @Version: V1.0
 */
@Service
public class SysCodeRuleServiceImpl extends ServiceImpl<SysCodeRoleMapper, SysCodeRole> implements ISysCodeRoleService {
    @Autowired
    SysCodeRoleMapper sysCodeRoleMapper;
    private static final String STR_FORMAT = "000000";

    @Override
    public String getCodeNumByType(Integer type, Integer save_flag) {
        String code_number = "";
        SysCodeRole sysCodeRole = sysCodeRoleMapper.getSysCodeRolebyType(type);
        if (sysCodeRole != null) {
            //拼接固定开头
            code_number = code_number + sysCodeRole.getHead();
            //拼接流水规则
            if (sysCodeRole.getRoleNy() != null && sysCodeRole.getRoleNy() == 1) {
                Date date = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
                code_number = code_number + sf.format(date);
            } else if (sysCodeRole.getRoleNy() != null && sysCodeRole.getRoleNy() == 2) {
                Date date = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                code_number = code_number + sf.format(date);
            }
            //拼接当前流水号+1
            int now_num = sysCodeRole.getNowNum() + 1;
            DecimalFormat df = new DecimalFormat(STR_FORMAT);
            code_number = code_number + df.format(now_num);

            if (save_flag == 1) {
                sysCodeRole.setNowNum(now_num);
                sysCodeRoleMapper.updateById(sysCodeRole);
            }
        }
        return code_number;
    }

    @Override
    public String getCodeNumByType2(Integer type, Integer saveFlag) {
        String code_number = "";
        SysCodeRole sysCodeRole = sysCodeRoleMapper.getSysCodeRolebyType(type);
        if (sysCodeRole != null) {
            //拼接固定开头
            code_number = code_number + sysCodeRole.getHead();
            //拼接流水规则
            if (sysCodeRole.getRoleNy() != null && sysCodeRole.getRoleNy() == 1) {
                Date date = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
                code_number = code_number + sf.format(date);
            } else if (sysCodeRole.getRoleNy() != null && sysCodeRole.getRoleNy() == 2) {
                Date date = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                code_number = code_number + sf.format(date);
            } else if (sysCodeRole.getRoleNy() != null && sysCodeRole.getRoleNy() == 3) {
                Date date = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy");
                code_number = code_number + sf.format(date);
            }
            //拼接当前流水号+1
            int now_num = sysCodeRole.getNowNum() + 1;

            Integer roleBit = sysCodeRole.getRoleBit();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < roleBit; i++) {
                builder.append("0");
            }

            DecimalFormat df = new DecimalFormat(builder.toString());
            code_number = code_number + df.format(now_num);

            if (saveFlag == 1) {
                sysCodeRole.setNowNum(now_num);
                sysCodeRoleMapper.updateById(sysCodeRole);
            }
        }
        return code_number;
    }
}
