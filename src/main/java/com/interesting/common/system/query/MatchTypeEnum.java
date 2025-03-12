package com.interesting.common.system.query;


import com.interesting.common.util.oConvertUtils;

/**
 * 查询链接规则
 *
 * @author: interesting-boot
 */
public enum MatchTypeEnum {

    AND("AND"),
    OR("OR");

    private String value;

    MatchTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MatchTypeEnum getByValue(Object value) {
        if (oConvertUtils.isEmpty(value)) {
            return null;
        }
        return getByValue(value.toString());
    }

    public static MatchTypeEnum getByValue(String value) {
        if (oConvertUtils.isEmpty(value)) {
            return null;
        }
        for (MatchTypeEnum val : values()) {
            if (val.getValue().toLowerCase().equals(value.toLowerCase())) {
                return val;
            }
        }
        return null;
    }
}
