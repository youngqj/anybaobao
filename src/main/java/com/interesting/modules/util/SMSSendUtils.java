package com.interesting.modules.util;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.interesting.common.util.DateUtils;
import com.interesting.common.util.FileMd5Util;
import com.interesting.common.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SMSSendUtils {


    private static final String USERNAME = "eshequ";
    private static final String PASSWORD = "ULJA6U83";
    private static final String URL = "http://www.ztsms.cn/sendNSms.do";
    private static final String TITLE = "【E-社区】";

    private static final Logger logger = LoggerFactory.getLogger(SMSSendUtils.class);

    public static void main(String[] args) {

        //   sendMsg("15712649270", "123456", 5);
        String s = sendMessage("18962534858", "我们要做的是越短越好，信息要高度浓缩，长篇大论每人愿意看。模板是：还可以结合最新");
        JSONObject jsonObject = JSONObject.parseObject(s);
        String code = jsonObject.getString("code");
        System.out.println(s);
    }

    /**
     * 手机验证码发送
     *
     * @param mobile
     * @param code
     * @param time   单位:分
     * @return
     */
    public static String sendMsg(String mobile, String code, Integer time) {

        String content = "您的验证码为：" + code + "，有效期为" + time + "分钟。【E-社区】";//内容
        String xh = "";//没有
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String tkey = DateUtils.nowTime("yyyyMMddHHmmss");
        String param = "url=" + URL + "&username=" + USERNAME + "&password=" + FileMd5Util.md5Lower(FileMd5Util.md5Lower(
                PASSWORD) + tkey) + "&tkey=" + tkey + "&mobile=" + mobile + "&content=" + content + "&productid=" + 676767 + "&xh=" + xh;
        String ret = HttpUtil.doPost(URL, param);
        System.out.println("发送短信返回=================: {}" + ret + "code:" + code + " 手机号：" + mobile);
        return ret;
    }

    public static String sendMsg(String mobile, String code) {
//        String content = "您正在登录家宴小程序员工版，验证码为：" + code + "，有效期为10分钟！转发可能导致账号被盗，请勿泄露他人。【家宴中心】";//内容
        String content = "您的验证码为：" + code + "，有效期为10分钟。【E-社区】";//内容
        String xh = "";//没有
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String tkey = DateUtils.nowTime("yyyyMMddHHmmss");
        String param = "url=" + URL + "&username=" + USERNAME + "&password=" + FileMd5Util.md5Lower(FileMd5Util.md5Lower(PASSWORD) + tkey) + "&tkey=" + tkey + "&mobile=" + mobile + "&content=" + content + "&productid=" + 676767 + "&xh=" + xh;
        String ret = HttpUtil.doPost(URL, param);
        logger.info("ret: {}", ret);
        return ret;
    }


    public static String sendMessage(String mobile,String content) {
        String urls = "https://api.mix2.zthysms.com/v2/sendSms";
        long tKey = System.currentTimeMillis() / 1000;
        JSONObject json = new JSONObject();
        //账号
        json.put("username", USERNAME);
        //密码
        json.put("password", SecureUtil.md5(SecureUtil.md5(PASSWORD) + tKey));
        //tKey
        json.put("tKey", tKey + "");
        //手机号
        json.put("mobile", mobile);
        //内容
        json.put("content", "【E-社区】"+content);
        String result = HttpRequest.post(urls)
                .timeout(60000)
                .body(json.toJSONString(), MediaType.APPLICATION_JSON_UTF8_VALUE)
                .execute()
                .body();
        logger.info("URL={} json={} 响应值为={}", urls, json, result);
        return result;
    }

    public static String sendMsg(String mobile) {

        String content = "您有新的订单消息，请前往小程序查看。【E-社区】";//内容
        String xh = "";//没有
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String tkey = DateUtils.nowTime("yyyyMMddHHmmss");
        String param = "url=" + URL + "&username=" + USERNAME + "&password=" + FileMd5Util.md5Lower(FileMd5Util.md5Lower(PASSWORD) + tkey) + "&tkey=" + tkey + "&mobile=" + mobile + "&content=" + content + "&productid=" + 676767 + "&xh=" + xh;
        String ret = HttpUtil.doPost(URL, param);
        logger.info("ret: {}", ret);
        return ret;
    }



    public static String sendSmsTp(String mobile,String temContent) {
        //地址
        String urls = "https://api.mix2.zthysms.com/v2/sendSmsTp";
        //请求入参
        JSONObject requestJson = new JSONObject();
        //账号
        requestJson.put("username", USERNAME);
        //tKey
        long tKey = System.currentTimeMillis() / 1000;
        requestJson.put("tKey", tKey);
        //明文密码
        requestJson.put("password", SecureUtil.md5(SecureUtil.md5(PASSWORD) + tKey));
        //模板ID
        requestJson.put("tpId", 46057);
        //签名
        requestJson.put("signature", "【E-社区】");
        //扩展号
        requestJson.put("ext", "");
        //自定义参数
        requestJson.put("extend", "");
        //发送记录集合
        JSONArray records = new JSONArray();
        JSONObject record = new JSONObject();
        //手机号
        record.put("mobile", mobile);
        //替换变量
        JSONObject param = new JSONObject();
        param.put("content", temContent);
        record.put("tpContent", param);
        records.add(record);
        requestJson.put("records", records);
        String result = HttpRequest.post(urls)
                .timeout(60000)
                .body(requestJson.toJSONString(), MediaType.APPLICATION_JSON_UTF8_VALUE).execute().body();
        logger.info("URL={} , json={} ,响应值为={}", urls, requestJson, result);
        return result;
    }


}
