package com.interesting.config;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private CommunityInterceptor communityInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<>();
        list.add("/**");
        //过滤的
        List<String> strings = new ArrayList<>();
        String str = "";
        strings.add(str+"/sys/cas/client/validateLogin"); //cas验证登录
        strings.add(str+"/sys/randomImage/**"); //登录验证码接口排除
        strings.add(str+"/sys/checkCaptcha"); //登录验证码接口排除
        strings.add(str+"/sys/login"); //登录接口排除
        strings.add(str+"/app/login/analysis"); //登录接口排除
        strings.add(str+"/sys/mLogin"); //登录接口排除
        strings.add(str+"/sys/logout"); //登出接口排除
        strings.add(str+"/sys/thirdLogin/**"); //第三方登录
        strings.add(str+"/sys/getEncryptedString"); //获取加密串
        strings.add(str+"/sys/sms");//短信验证码
        strings.add(str+"/sys/phoneLogin");//手机登录

        strings.add(str+"/sys/user/checkOnlyUser");//校验用户是否存在
        strings.add(str+"/sys/user/register");//用户注册
        strings.add(str+"/sys/user/querySysUser");//根据手机号获取用户信息
        strings.add(str+"/sys/user/phoneVerification");//用户忘记密码验证手机号
        strings.add(str+"/sys/user/passwordChange");//用户更改密码
        strings.add(str+"/auth/2step-code");//登录验证码
        strings.add(str+"/sys/common/static/**");//图片预览 &下载文件不限制token
        strings.add(str+"/sys/common/pdf/**");//pdf预览
        strings.add(str+"/generic/**");//pdf预览需要文件
        strings.add(str+"/swagger-resources");
        strings.add(str+"/swagger**/**");

        strings.add(str+"/doc.html");
        strings.add(str+"/community/doc.html");
        strings.add("/**/*.js");
        strings.add("/**/*.css");
        strings.add("/**/*.html");
        strings.add("/**/*.svg");
        strings.add("/**/*.pdf");
        strings.add("/**/*.jpg");
        strings.add("/**/*.png");
        strings.add("/**/*.ico");

        strings.add("/**/*.ttf");
        strings.add("/**/*.woff");
        strings.add("/**/*.woff2");

        strings.add(str+"/druid/**");
        //积木报表排除
        strings.add(str+"/jmreport/**");
        strings.add("/**/*.js.map");
        strings.add("/**/*.css.map");
        //大屏设计器排除
        strings.add(str+"/bigscreen/**");

        //测试示例
        strings.add("/test/bigScreen/**"); //大屏模板例子
        //strings.add("/test/jeecgDemo/rabbitMqClientTest/**", "anon"); //MQ测试
        //strings.add("/test/jeecgDemo/html", "anon"); //模板页面
        //strings.add("/test/jeecgDemo/redis/**", "anon"); //redis测试

        //websocket排除
        strings.add(str+"/websocket/**");//系统通知和公告
        strings.add(str+"/newsWebsocket/**");//CMS模块
        strings.add(str+"/vxeSocket/**");//JVxeTable无痕刷新示例
        strings.add(str+"/eoaSocket/**");//我的聊天

        //性能监控  TODO 存在安全漏洞泄露TOEKN（durid连接池也有）
        strings.add(str+"/actuator/**");
        registry.addInterceptor(tokenInterceptor).addPathPatterns(list).excludePathPatterns(strings);

        List<String> communitys = new ArrayList<>();
        communitys.addAll(strings);
        communitys.add("/location/cityArea/selectVillageMap");
        communitys.add("/app/api/getMyResidentPassport");
        communitys.add("/app/api/getTokenByVisitor");
        registry.addInterceptor(communityInterceptor).addPathPatterns(list).excludePathPatterns(communitys);

    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
