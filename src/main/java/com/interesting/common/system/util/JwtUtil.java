package com.interesting.common.system.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Joiner;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.constant.DataBaseConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.system.vo.LoginUser;
import com.interesting.common.system.vo.SysUserCacheInfo;
import com.interesting.common.util.DateUtils;
import com.interesting.common.util.SpringContextUtils;
import com.interesting.common.util.oConvertUtils;
import com.interesting.config.FilterContextHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author: interesting-boot
 * @Date 2018-07-12 14:23
 * @Desc JWT工具类
 **/
public class JwtUtil {

    // Token过期时间30分钟（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）
    public static final long EXPIRE_TIME = 60 * 60 * 1000 * 24 * 30;

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getSysWechatId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("sysWechatId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     *
     * @param username    用户名
     * @param sysWechatId 访客表id
     * @param secret      用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String sysWechatId, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("username", username).withClaim("sysWechatId", sysWechatId).withExpiresAt(date).sign(algorithm);
    }

    public static void main(String[] args) {
        String swerwer = sign("", "swerwer", "234324");
        String sysWechatId = getSysWechatId("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NDY0NzIyOTMsInVzZXJuYW1lIjoiMTg2MzkzODUxMzMifQ.KDvLBL5MG6Un5GkvLHxV0gNIU15f8U8rrSyhzp8oHjQ");
        int cc = 0;
    }

    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
    }

    /**
     * 根据request中的token获取用户账号
     *
     * @param request
     * @return
     * @throws InterestingBootException
     */
    public static String getUserNameByToken(HttpServletRequest request) throws InterestingBootException {
        String accessToken = request.getHeader("X-Access-Token");
        String username = getUsername(accessToken);
        if (oConvertUtils.isEmpty(username)) {
            throw new InterestingBootException("未获取到用户");
        }
        return username;
    }

    /**
     * 从session中获取变量
     *
     * @param key
     * @return
     */
    public static String getSessionData(String key) {
        //${myVar}%
        //得到${} 后面的值
        String moshi = "";
        if (key.indexOf("}") != -1) {
            moshi = key.substring(key.indexOf("}") + 1);
        }
        String returnValue = null;
        if (key.contains("#{")) {
            key = key.substring(2, key.indexOf("}"));
        }
        if (oConvertUtils.isNotEmpty(key)) {
            HttpSession session = SpringContextUtils.getHttpServletRequest().getSession();
            returnValue = (String) session.getAttribute(key);
        }
        //结果加上${} 后面的值
        if (returnValue != null) {
            returnValue = returnValue + moshi;
        }
        return returnValue;
    }

    /**
     * 从当前用户中获取变量
     *
     * @param key
     * @param user
     * @return
     */
    //TODO 急待改造 sckjkdsjsfjdk
    public static String getUserSystemData(String key, SysUserCacheInfo user) {
        if (user == null) {
            user = JeecgDataAutorUtils.loadUserInfo();
        }
        //#{sys_user_code}%

        // 获取登录用户信息
        LoginUser sysUser = FilterContextHandler.getUserInfo();

        String moshi = "";
        if (key.indexOf("}") != -1) {
            moshi = key.substring(key.indexOf("}") + 1);
        }
        String returnValue = null;
        //针对特殊标示处理#{sysOrgCode}，判断替换
        if (key.contains("#{")) {
            key = key.substring(2, key.indexOf("}"));
        } else {
            key = key;
        }
        //替换为当前系统时间(年月日)
        if (key.equals(DataBaseConstant.SYS_DATE) || key.toLowerCase().equals(DataBaseConstant.SYS_DATE_TABLE)) {
            returnValue = DateUtils.formatDate();
        }
        //替换为当前系统时间（年月日时分秒）
        else if (key.equals(DataBaseConstant.SYS_TIME) || key.toLowerCase().equals(DataBaseConstant.SYS_TIME_TABLE)) {
            returnValue = DateUtils.now();
        }
        //流程状态默认值（默认未发起）
        else if (key.equals(DataBaseConstant.BPM_STATUS) || key.toLowerCase().equals(DataBaseConstant.BPM_STATUS_TABLE)) {
            returnValue = "1";
        }
        //update-begin-author:taoyan date:20210330 for:多租户ID作为系统变量
        //update-end-author:taoyan date:20210330 for:多租户ID作为系统变量
        if (returnValue != null) {
            returnValue = returnValue + moshi;
        }
        return returnValue;
    }


    /**
     * 生成签名,5min后过期
     *
     * @param openId
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String wechatSign(String openId, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("openId", openId).withExpiresAt(date).sign(algorithm);

    }


    /**
     * 从token 中获取 openid
     *
     * @param token
     * @return
     */
    public static String getOpenID(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("openId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }



//	public static void main(String[] args) {
//		 String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjUzMzY1MTMsInVzZXJuYW1lIjoiYWRtaW4ifQ.xjhud_tWCNYBOg_aRlMgOdlZoWFFKB_givNElHNw3X0";
//		 System.out.println(JwtUtil.getUsername(token));
//	}
}
