package com.interesting.common.util;

import com.interesting.common.api.CommonAPI;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.AuthorizationException;
import com.interesting.common.system.util.JwtUtil;
import com.interesting.common.system.vo.LoginUser;
import com.interesting.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: interesting-boot
 * @Date 2019/9/23 14:12
 * @Description: 编程校验token有效性
 */
@Slf4j
public class TokenUtils {

    /**
     * 获取 request 里传递的 token
     *
     * @param request
     * @return
     */
    public static String getTokenByRequest(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (token == null) {
            token = request.getHeader("X-Access-Token");
        }
        return token;
    }

    /**
     * 验证Token
     */
    public static boolean verifyToken(HttpServletRequest request, CommonAPI commonAPI, RedisUtil redisUtil) throws AuthorizationException {
        log.debug(" -- url --" + request.getRequestURL());
        String token = getTokenByRequest(request);

        if (StringUtils.isBlank(token)) {
            throw new AuthorizationException("Token不能为空!");
        }

        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthorizationException("Token非法无效!");
        }

        // 查询用户信息
        LoginUser user = commonAPI.getUserByName(username);
        if (user == null) {
            throw new AuthorizationException("用户不存在!");
        }
        // 判断用户状态
        if (user.getStatus() != 1) {
            throw new AuthorizationException("账号已锁定,请联系管理员!");
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!jwtTokenRefresh(token, username, user.getPassword(), redisUtil)) {
            throw new AuthorizationException("Token失效，请重新登录");
        }
        return true;
    }

    /**
     * 刷新token（保证用户在线操作不掉线）
     * @param token
     * @param userName
     * @param passWord
     * @param redisUtil
     * @return
     */
    private static boolean jwtTokenRefresh(String token, String userName, String passWord, RedisUtil redisUtil) {
        String cacheToken = String.valueOf(redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
        if (oConvertUtils.isNotEmpty(cacheToken)) {
            // 校验token有效性
            if (!JwtUtil.verify(cacheToken, userName, passWord)) {
                String newAuthorization = JwtUtil.sign(userName, passWord);
                // 设置Toekn缓存有效时间
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, newAuthorization);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);
            }
            //update-begin--Author:scott  Date:20191005  for：解决每次请求，都重写redis中 token缓存问题
//            else {
//                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, cacheToken);
//                // 设置超时时间
//                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 1000);
//            }
            //update-end--Author:scott  Date:20191005  for：解决每次请求，都重写redis中 token缓存问题
            return true;
        }
        return false;
    }

    /**
     * 验证Token
     */
    public static boolean verifyToken(String token, CommonAPI commonAPI, RedisUtil redisUtil) throws AuthorizationException {
        if (StringUtils.isBlank(token)) {
            throw new AuthorizationException("token不能为空!");
        }

        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthorizationException("token非法无效!");
        }

        // 查询用户信息
        LoginUser user = commonAPI.getUserByName(username);
        if (user == null) {
            throw new AuthorizationException("用户不存在!");
        }
        // 判断用户状态
        if (user.getStatus() != 1) {
            throw new AuthorizationException("账号已被锁定,请联系管理员!");
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!jwtTokenRefresh(token, username, user.getPassword(), redisUtil)) {
            throw new AuthorizationException("Token失效，请重新登录!");
        }
        return true;
    }

}
