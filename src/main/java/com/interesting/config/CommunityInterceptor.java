package com.interesting.config;

import com.alibaba.fastjson.JSONObject;
import com.interesting.common.api.vo.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class CommunityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String communityId = request.getHeader("CommunityId-X");
        if (communityId == null) {
            // return doOKReturn(response, Result.noauthNoCommunityId());
        } else {
//            FilterContextHandler.setCommunityId(communityId);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        FilterContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }

    private boolean doOKReturn(HttpServletResponse response, Result r) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSONObject.toJSONString(r));
        return false;

    }
}
