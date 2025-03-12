package com.interesting.config;


import com.interesting.common.constant.CommonConstant;
import com.interesting.common.system.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author user
 */
@Slf4j
public class FilterContextHandler {
    public FilterContextHandler() {
    }

    private static final ThreadLocal<Map<String, Object>> THREADLOCAL = new InheritableThreadLocal<>();

    public static void set(String key, Object value) {

        Map<String, Object> map = THREADLOCAL.get();
        if (map == null) {
            map = new HashMap<>(1);
            map.put("cccc", Thread.currentThread().getId());
            THREADLOCAL.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = THREADLOCAL.get();
        if (map == null) {
            map = new HashMap<>(1);
            THREADLOCAL.set(map);
        }
        return map.get(key);
    }

    public static String getUserId() {
        Object value = get(CommonConstant.CONTEXT_USER_ID);
        return returnObjectValue(value);
    }

    public static String getToken() {
        Object value = get(CommonConstant.CONTEXT_TOKEN);
        return returnObjectValue(value);
    }

    public static void setToken(String token) {
        set(CommonConstant.CONTEXT_TOKEN, token);
    }

    public static void setName(String name) {
        set(CommonConstant.CONTEXT_NAME, name);
    }

    public static void setUserId(String userId) {
        set(CommonConstant.CONTEXT_USER_ID, userId);
    }

    public static void setUserInfo(LoginUser loginUser) {
        set(CommonConstant.CONTEXT_USER_INFO, loginUser);
    }

    public static LoginUser getUserInfo() {
        Object value = get(CommonConstant.CONTEXT_USER_INFO);
        return returnLoginUserValue(value);
    }

    private static LoginUser returnLoginUserValue(Object value) {
        if (value == null) {
            return new LoginUser();
        }
        return (LoginUser) value;
    }


    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        Map<String, Object> map = THREADLOCAL.get();
//        map.put("cccc", Thread.currentThread().getId());
        THREADLOCAL.remove();

    }

    public static void setVisitorId(String id) {
        set(CommonConstant.CONTEXT_VISITOR_ID, id);
    }

    public static String getVisitorId() {
        Object value = get(CommonConstant.CONTEXT_VISITOR_ID);
        return returnObjectValue(value);

    }

    public static void setOpenId(String openId) {
        set(CommonConstant.CONTEXT_OPEN_ID, openId);
    }

//    public static String getOpenId() {
//        Object o = get(CommonConstant.CONTEXT_OPEN_ID);
//        return returnObjectValue(o);
//    }
}
