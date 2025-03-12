package com.interesting.config.redis.receiver;


import cn.hutool.core.util.ObjectUtil;
import com.interesting.common.constant.GlobalConstants;
import com.interesting.common.context.SpringContextHolder;
import com.interesting.config.redis.base.BaseMap;
import com.interesting.config.redis.listener.JeecgRedisListerer;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author zyf
 */
@Component
@Data
public class RedisReceiver {


    /**
     * 接受消息并调用业务逻辑处理器
     *
     * @param params
     */
    public void onMessage(BaseMap params) {
        Object handlerName = params.get(GlobalConstants.HANDLER_NAME);
        JeecgRedisListerer messageListener = SpringContextHolder.getHandler(handlerName.toString(), JeecgRedisListerer.class);
        if (ObjectUtil.isNotEmpty(messageListener)) {
            messageListener.onMessage(params);
        }
    }

}
