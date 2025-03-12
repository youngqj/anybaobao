package com.interesting.config;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.interesting.common.api.CommonAPI;
import com.interesting.common.api.vo.Result;
import com.interesting.common.system.util.JwtUtil;
import com.interesting.common.system.vo.LoginUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private CommonAPI commonAPI;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("X-Access-Token");
        if (token==null){
            return doErrorReturn(response, Result.noauth());
        }
        String username = JwtUtil.getUsername(token);
        // 用户名不为空
        LoginUser userByName;
        if (!StringUtil.isEmpty(username)) {
            //  从数据库中获取用户信息
            userByName = commonAPI.getUserByName(username);
            if (userByName != null) {
                //            鉴权
//                if (!isJurisdiction(userByName, request.getRequestURI())) {
////                return doErrorReturn(response, Result.noauth());
//                }
                FilterContextHandler.setUserId(userByName.getId());
                FilterContextHandler.setToken(token);
                FilterContextHandler.setUserInfo(userByName);
                return true;
            }
        }
        return doErrorReturn(response, Result.noauth());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        FilterContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }

    private boolean doErrorReturn(HttpServletResponse response, Result r) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSONObject.toJSONString(r));
        return false;

    }

    @Value("${jeecg.path.upload}")
    private String api;


}
