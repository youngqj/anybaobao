package com.interesting.config.redis.listener;


import com.interesting.config.redis.base.BaseMap;

/**
 * 自定义消息监听
 */
public interface JeecgRedisListerer {

    void onMessage(BaseMap message);

}
