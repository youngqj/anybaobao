package com.interesting.config.redis.client;

import com.interesting.common.constant.GlobalConstants;
import com.interesting.config.redis.base.BaseMap;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * redis客户端
 */
@Configuration
public class JeecgRedisClient {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 发送消息
     *
     * @param handlerName
     * @param params
     */
    public void sendMessage(String handlerName, BaseMap params) {
        params.put(GlobalConstants.HANDLER_NAME, handlerName);
        redisTemplate.convertAndSend(GlobalConstants.REDIS_TOPIC_NAME, params);
    }


}
