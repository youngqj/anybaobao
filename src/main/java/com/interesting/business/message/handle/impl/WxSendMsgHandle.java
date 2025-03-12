package com.interesting.business.message.handle.impl;

import com.interesting.business.message.handle.ISendMsgHandle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WxSendMsgHandle implements ISendMsgHandle {

    @Override
    public void SendMsg(String es_receiver, String es_title, String es_content) {
        // TODO Auto-generated method stub
        log.info("发微信消息模板");
    }

}
