package com.interesting.common.util;

import com.alibaba.fastjson.JSONObject;
import com.interesting.config.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 * <p>
 *
 * @author cc
 * @version 1.0
 * @date 2021/8/6
 */
@Component
public class RestTemplateUtils {
    public static String apiToken = "";

    @Autowired
    private RestTemplate restTemplate;


    /**
     * post 请求
     *
     * @param url             地址
     * @param param           参数
     * @param jsonObjectClass 返回值类型
     * @return
     */
    public ResponseEntity<JSONObject> postForEntity(String url, JSONObject param, Class<JSONObject> jsonObjectClass) {
        ResponseEntity<JSONObject> jsonObjectResponseEntity = null;
        jsonObjectResponseEntity = restTemplate.postForEntity(url, getHeadApiToken(param), jsonObjectClass);
        JSONObject body = jsonObjectResponseEntity.getBody();
        if (body != null && 0 == body.getInteger("code")) {
            return jsonObjectResponseEntity;
        }
        return jsonObjectResponseEntity;
    }

    /**
     * @param url
     * @param jsonObjectClass
     * @return
     */
    public JSONObject getForEntity(String url, Class<JSONObject> jsonObjectClass) {
        JSONObject body = null;
        ResponseEntity<JSONObject> jsonObjectResponseEntity = restTemplate.exchange(url, HttpMethod.GET, getHeadApiToken(null), jsonObjectClass);
        body = jsonObjectResponseEntity.getBody();
        if (body != null && 0 == body.getInteger("code")) {
            return body;
        }
        return body;
    }

    public JSONObject get(String url, Class<JSONObject> jsonObjectClass) throws MyException {
        JSONObject forEntity = getForEntity(url, jsonObjectClass);
        if (forEntity.getInteger("code") == 0) {
            return forEntity;
        } else {
            throw new MyException(forEntity.toJSONString());
        }
    }

    /**
     * post请求 参数放在请求体中的json格式
     *
     * @param url             url地址
     * @param param           参数
     * @param jsonObjectClass
     * @return
     * @throws MyException 如果请求失败会抛出异常
     */
    public JSONObject post(String url, JSONObject param, Class<JSONObject> jsonObjectClass) throws MyException {
        ResponseEntity<JSONObject> forEntity = postForEntity(url, param, jsonObjectClass);
        JSONObject body = forEntity.getBody();
//        if (body.getInteger("code") == 0) {
            return body;
//        } else {
//            throw new MyException(body.toJSONString());
//        }
    }

    /**
     * 是否重新获取
     *
     * @param b true 是
     * @return
     */
    public HttpEntity<JSONObject> getHeadApiToken(JSONObject param) {
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiToken);
        HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(param, headers);
        return request;
    }


}
