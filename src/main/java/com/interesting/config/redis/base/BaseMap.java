package com.interesting.config.redis.base;



import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 自定义Map
 */
public class BaseMap extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;


    public BaseMap() {

    }

    public BaseMap(Map<String, Object> map) {
        this.putAll(map);
    }


    @Override
    public BaseMap put(String key, Object value) {
        super.put(key, Optional.ofNullable(value).orElse(""));
        return this;
    }

    public BaseMap add(String key, Object value) {
        super.put(key, Optional.ofNullable(value).orElse(""));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        Object obj = super.get(key);
        if (obj != null) {
            return (T) obj;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Boolean getBoolean(String key) {
        Object obj = super.get(key);
        if (obj != null) {
            return Boolean.valueOf(obj.toString());
        } else {
            return false;
        }
    }

    public Long getLong(String key) {
        Object v = get(key);
        if (v != null) {
            return new Long(v.toString());
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    public <T> T get(String key, T def) {
        Object obj = super.get(key);
        if (obj != null) {
            return def;
        }
        return (T) obj;
    }

    public static BaseMap toBaseMap(Map<String, Object> obj) {
        BaseMap map = new BaseMap();
        map.putAll(obj);
        return map;
    }


}
