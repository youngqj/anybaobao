package com.interesting.common.validation;

import com.alibaba.fastjson.JSON;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EarlyWarningApplication implements ApplicationContextAware, CommandLineRunner {

    // 定义一个私有的方便本class中调用
    private ApplicationContext applicationContext;

    // 通过重写ApplicationContextAware感知接口，将ApplicationContext赋值给当前的私有context容器
    @Override
    public void setApplicationContext(ApplicationContext arg0) {
        this.applicationContext = arg0;
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EarlyWarningApplication.class);
        application.run(args);
    }


    @Override
    public void run(String... args) throws Exception {

        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);

        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            Object value = entry.getValue();
            System.out.println("拿到controller："+entry.getKey()+"，拿到value："+value);
            Class<?> aClass = AopUtils.getTargetClass(value);
            System.out.println("拿到Class:"+aClass);
            RequestMapping annotation = aClass.getAnnotation(RequestMapping.class);
            RequestMapping declaredAnnotation = aClass.getDeclaredAnnotation(RequestMapping.class);

            List<Method> methods = Arrays.asList(aClass.getMethods());
            System.out.println("Public Methods:" + methods);
            List<Method> declaredMethods = Arrays.asList(aClass.getDeclaredMethods());
            for (int i = 0; i < declaredMethods.size() ; i++) {
                GetMapping getMapping = declaredMethods.get(i).getAnnotation(GetMapping.class);
                PostMapping postMapping = declaredMethods.get(i).getDeclaredAnnotation(PostMapping.class);
                System.out.println("Get相关的："+ JSON.toJSONString(getMapping));
                System.out.println("Post相关的："+JSON.toJSONString(postMapping));
            }
        }
    }
}
