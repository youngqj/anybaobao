package com.interesting.common.util;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author guoshicheng
 * @date 2021/03/15
 * 对象工具类
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * 日志操作类
     */
    private static Logger logger = Logger.getLogger(String.valueOf(BeanUtils.class));

    /**
     * 比较两个BEAN或MAP对象的值是否相等
     * 如果是BEAN与MAP对象比较时MAP中的key值应与BEAN的属性值名称相同且字段数目要一致
     *
     * @param source 源对象
     * @param target 比较对象
     * @param target 过滤不比较字段
     * @return
     */
    public static boolean domainEquals(Object source, Object target, String param) {
        if (source == null || target == null) {
            return false;
        }
        boolean rv = true;
        if (source instanceof Map) {
            rv = mapOfSrc(source, target, rv, param);
        } else {
            rv = classOfSrc(source, target, rv, param);
        }
        logger.info("THE EQUALS RESULT IS " + rv);
        return rv;
    }

    /**
     * 源目标为MAP类型时
     *
     * @param source
     * @param target
     * @param rv
     * @param param  过滤字段
     * @return
     */
    private static boolean mapOfSrc(Object source, Object target, boolean rv, String param) {
        HashMap<String, String> map = new HashMap<String, String>(16);
        map = (HashMap) source;
        for (String key : map.keySet()) {
            //过滤字段不比较
            if (param.indexOf(key) != -1) {
                continue;
            }
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>(16);
                tarMap = (HashMap) target;
                if (tarMap.get(key) == null) {
                    rv = false;
                    break;
                }
                if (!map.get(key).equals(tarMap.get(key))) {
                    rv = false;
                    break;
                }
            } else {
                String tarValue = getClassValue(target, key) == null ? "" : getClassValue(target, key).toString();
                if (!tarValue.equals(map.get(key))) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

    /**
     * 源目标为非MAP类型时
     *
     * @param source 源对象
     * @param target 待比较对象
     * @param rv     返回类型
     * @param param  过滤不比较字符串
     * @return
     */
    private static boolean classOfSrc(Object source, Object target, boolean rv, String param) {
        Class<?> srcClass = source.getClass();
        Field[] fields = srcClass.getDeclaredFields();
        for (Field field : fields) {
            String nameKey = field.getName();
            if (param != null && param.indexOf(nameKey) != -1) {
                continue;
            }
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>(16);
                tarMap = (HashMap) target;
                String srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey)
                        .toString();
                if (tarMap.get(nameKey) == null) {
                    rv = false;
                    break;
                }
                if (!tarMap.get(nameKey).equals(srcValue)) {
                    rv = false;
                    break;
                }
            } else {
                String srcValue = getClassValue(source, nameKey) == null ? "" : getClassValue(source, nameKey)
                        .toString();
                String tarValue = getClassValue(target, nameKey) == null ? "" : getClassValue(target, nameKey)
                        .toString();
                if (!srcValue.equals(tarValue)) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

    /**
     * 根据字段名称取值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj, new Object[]{});
                } catch (Exception e) {
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase())
                        || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if ("SID".equals(fieldName.toUpperCase())
                        && ("ID".equals(ms[i].getName().toUpperCase()) || "ID".equals(ms[i].getName().substring(3).toUpperCase()))) {
                    return objValue;
                }
            }
        } catch (Exception e) {
            // logger.info("取方法出错！" + e.toString());
        }
        return null;
    }

    /***
     * 拷贝对象  当源对象值为null是不拷贝该字段
     *
     * @param source 源
     * @param target 目标
     * @author gcc
     * @throws
     * @return
     */
    public static void copyProperties(Object source, Object target) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();

        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        PropertyDescriptor[] var7 = targetPds;
        int var8 = targetPds.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            PropertyDescriptor targetPd = var7[var9];
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            // 判断value是否为空
                            if (value != null) {
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable var15) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", var15);
                        }
                    }
                }
            }
        }
    }
}
