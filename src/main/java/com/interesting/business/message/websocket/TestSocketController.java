package com.interesting.business.message.websocket;

import com.alibaba.fastjson.JSONObject;
import com.interesting.common.api.vo.Result;
import com.interesting.common.constant.WebsocketConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/socketTest")
//@Api(tags = "websocketTest消息测试")
public class TestSocketController {

    @Autowired
    private WebSocket webSocket;

    @PostMapping("/sendAll")
    @ApiOperation(value = "消息测试-群发", notes = "消息测试-群发")
    public Result<String> sendAll(@RequestBody JSONObject jsonObject) {
        Result<String> result = new Result<String>();
        String message = jsonObject.getString("message");
        JSONObject obj = new JSONObject();
        obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_TOPIC);
        obj.put(WebsocketConst.MSG_ID, "M0001");
        obj.put(WebsocketConst.MSG_TXT, message);
        webSocket.sendMessage(obj.toJSONString());
        result.setResult("群发！");
        return result;
    }

    @PostMapping("/sendUser")
    @ApiOperation(value = "消息测试-指定用户", notes = "消息测试-指定用户")
    public Result<String> sendUser(@RequestBody JSONObject jsonObject) {
        Result<String> result = new Result<String>();
        String userId = jsonObject.getString("userId");
        String message = jsonObject.getString("message");
        JSONObject obj = new JSONObject();
        obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
        obj.put(WebsocketConst.MSG_USER_ID, userId);
        obj.put(WebsocketConst.MSG_ID, "M0001");
        obj.put(WebsocketConst.MSG_TXT, message);
        webSocket.sendMessage(userId, obj.toJSONString());
        result.setResult("单发");
        return result;
    }

}
