package com.interesting.common.exception;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 * <p>
 *
 * @author cc
 * @version 1.0
 * @date 2021/7/7
 */
public class AuthorizationException extends Throwable {

    public AuthorizationException(String message){
        super(message);
    }

    public AuthorizationException(Throwable cause)
    {
        super(cause);
    }

    public AuthorizationException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
