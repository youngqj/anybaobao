package com.interesting.config;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author czh
 * @version 1.0
 * @date 2019/12/17 15:47
 * @Project cashier
 */
public class MyException extends Exception {
    /**
     *定义无参构造方法
     */
    public MyException() {
        super();
    }
    /**
     *定义有参构造方法
     */
    public MyException(String message) {
        super(message);
    }
}
