package com.interesting.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 * <p>
 * 字符串 工具类
 *
 * @author cc
 * @version 1.0
 * @date 2021/6/7
 */
public class StringUtils {
    /**
     * 验证字符串是否为空   “” 也是空
     *
     * @param str 待校验字符串
     * @return true  不为空 false 空
     */
    public static boolean isNotEmpty(String str) {
        if (str != null && str.trim().length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符串是否为空   “” 也是空
     *
     * @param str 待校验字符串
     * @return true  不为空 false 空
     */
    public static boolean isNotEmpty(Collection<?> str) {
        if (str != null && str.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证字符串是否为空   “” 也是空
     *
     * @param str 待校验字符串
     * @return true  不为空 false 空
     */
    public static boolean isNotEmpty(Object... str) {
        if (str != null && str.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证对象是否为空   0也是空
     *
     * @param str 待校验字符串
     * @return true  不为空 false 空
     */
    public static boolean isNotEmpty(Integer str) {
        if (str != null && str > 0) {
            return true;
        }
        return false;
    }

    /**
     * List 转 String，并且将指定字符删除
     *
     * @param dateDetail
     * @param s          删除字符串
     * @return
     */
    public static String ListToStringAndDelete(List<String> dateDetail, String s) {
        if (isNotEmpty(dateDetail) && isNotEmpty(s)) {
            String cc = "";
            for (String s1 : dateDetail) {
                String replace = s1.replace(s, "");
                if (replace.trim().length() > 0) {
                    cc = cc + replace + ",";
                }
            }
            return cc;
        }
        return null;
    }

    /**
     * 将
     *
     * @param item
     * @return
     */
    public static List<String> StringToListTime(String item) {
        List<String> ss = new ArrayList<>();
        String[] split = item.split(",");
        for (String s : split) {
            ss.add(s + ":00-" + (Integer.parseInt(s) + 1) + ":00");
        }
        return ss;
    }

    /**
     * 判断是否为空
     * @param token
     * @return true空 false不为空
     */
    public static boolean isBlank(String token) {
        if (token == null || token.trim().length() == 0){
            return true;
        }
        return false;
    }
    /**
     * 判断是否为空
     * @param ip
     * @return true空 false不为空
     */
    public static boolean isEmpty(String ip) {
        if (ip == null || ip.trim().length() == 0){
            return true;
        }
        return false;
    }

    /**
     * 检验不等于空  “”也是空
     * @param str
     * @return ture 字符串长度大于0
     */
    public static boolean isNotBlank(String str) {
        if (str != null && str.length() > 0) {
            return true;
        }
        return false;
    }
}
