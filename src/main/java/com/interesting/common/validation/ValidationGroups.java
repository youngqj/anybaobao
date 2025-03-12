package com.interesting.common.validation;

import org.springframework.stereotype.Component;

/**
 * 分组校验
 */
@Component
public class ValidationGroups {
    public interface Add {
    }

    public interface Update {
    }

    public interface Query {
    }

    /**
     * 账号密码登录
     */
    public interface PwdLogin {
    }

    /**
     * 手机号登录
     */
    public interface CodeLogin {
    }
}
