package com.interesting.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SMSSendUtils {

    private static final String USERNAME = "jxkl";
    private static final String PASSWORD = "Xq123456";
    private static final String URL = "http://www.ztsms.cn/sendNSms.do";
    private static final String TITLE = "【智慧公寓】";

    private static final Logger logger = LoggerFactory.getLogger(SMSSendUtils.class);

    public static void main(String[] args) {

    //   sendMsg("15712649270", "123456", 5);
        sendMessage("15712649270","您好,您的合同将于五天后请您及时处理,合同编号：1419486414185238530【年哥好帅】");
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

        String content = "您的验证码为：" + code + "，有效期为" + time + "分钟。【智慧公寓】";//内容
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
        System.out.println("发送短信返回=================: {}" + ret + "code:" + code+" 手机号："+mobile);
        return ret;
    }

    public static String sendMsg(String mobile, String code) {

        String content = "您的验证码为：" + code + "，有效期为3分钟。【智慧公寓】";//内容
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

    public static String sendMessage(String mobile, String msg) {
        String content =msg;//内容
        String xh = "";//没有
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String tkey = DateUtils.nowTime("yyyyMMddHHmmss");
        String param = "url=" + URL + "&username=" + USERNAME + "&password=" + FileMd5Util.md5Lower(FileMd5Util.md5Lower(PASSWORD) + tkey) + "&tkey=" + tkey + "&mobile=" + mobile + "&content=" + content + "&productid=" + 676767 + "&xh=" + xh;
       String ret = HttpUtil.doPost(URL, param);
        System.out.println("发送短信返回=================: {}" +content);
        return ret;
    }

   /* public static R sendMsg(String phone,String code){
        R r = null;
        HashMap<String, Object> result = null;

        result = restAPI.sendTemplateSMS(phone,"1" ,new String[]{code,"5"});

        System.out.println("SDKTestSendTemplateSMS result=" + result);

        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
            r = R.ok(data);
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            r = R.error("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
        return r;
    }
    public static R sendMsg(String phone,String code,String effectiveDuration){
        R r = null;
        HashMap<String, Object> result = null;

        result = restAPI.sendTemplateSMS(phone,"1" ,new String[]{code,effectiveDuration});

        System.out.println("SDKTestSendTemplateSMS result=" + result);

        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
            r = R.ok(data);
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            r = R.error("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
        return r;
    }*/
}
