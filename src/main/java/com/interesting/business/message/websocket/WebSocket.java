package com.interesting.business.message.websocket;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.interesting.business.system.entity.SysRole;
import com.interesting.business.system.entity.SysUserRole;
import com.interesting.business.system.service.ISysUserRoleService;
import com.interesting.common.constant.WebsocketConst;
import com.interesting.config.redis.base.BaseMap;
import com.interesting.config.redis.client.JeecgRedisClient;
import com.interesting.modules.order.service.IXqOrderFinallyConfirmService;
import com.interesting.modules.order.service.IXqOrderService;
import com.interesting.modules.order.vo.XqOrderCompleteMsgVO;
import com.interesting.modules.rawmaterial.service.IXqRawMaterialPurchaseService;
import com.interesting.modules.remittance.service.XqRemittanceExpireService;
import com.interesting.modules.remittance.vo.QueryDataNotInDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @author: interesting-boot
 * @Date 2019/11/29 9:41
 * @Description: 此注解相当于设置访问URL
 */
@Component
@Slf4j
@ServerEndpoint("/websocket/{userId}") //此注解相当于设置访问URL
public class WebSocket {

    private Session session;

    private String userId;

    private static final String REDIS_TOPIC_NAME = "socketHandler";

    @Resource
    private JeecgRedisClient jeecgRedisClient;

    private static ISysUserRoleService sysUserRoleService;
    private static IXqOrderService xqOrderService;

    private static XqRemittanceExpireService xqRemittanceExpireService;

    private static IXqRawMaterialPurchaseService xqRawMaterialPurchaseService;

    @Autowired
    private IXqOrderFinallyConfirmService xqOrderFinallyConfirmService;

    /**
     * 缓存 webSocket连接到单机服务class中（整体方案支持集群）
     */
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String, Session> sessionPool = new HashMap<String, Session>();


    @Autowired
    public WebSocket(XqRemittanceExpireService chatService, ISysUserRoleService sysUserRoleService, IXqOrderService xqOrderService, IXqRawMaterialPurchaseService xqRawMaterialPurchaseService) {
        WebSocket.xqRemittanceExpireService = chatService;
        WebSocket.sysUserRoleService = sysUserRoleService;
        WebSocket.xqOrderService = xqOrderService;
        WebSocket.xqRawMaterialPurchaseService = xqRawMaterialPurchaseService;
    }

    public WebSocket() {
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
        try {
            this.session = session;
            this.userId = userId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            log.info("【websocket消息】有新的连接，总数为:" + webSockets.size());

            new Thread(() -> {
                Integer count = sysUserRoleService.lambdaQuery().eq(SysUserRole::getUserId, userId).eq(SysUserRole::getRoleId, "3").count();
                // count > 0 说明当前登录用户有国外财务权限
                if (count > 0) {
                    // 查询收款到期日有数据，收汇情况没有数据的记录
                    List<QueryDataNotInDetailVO> vo = xqRemittanceExpireService.queryDataNotInDetail();
                    // 若vo 长度为0，则不需要继续
                    if (vo.size() > 0) {
                        ArrayList<JSONObject> jsonObjects = new ArrayList<>(vo.size());
                        vo.forEach(queryDataNotInDetailVO -> {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("id", queryDataNotInDetailVO.getId());
                            jsonObject1.put("orderNum", queryDataNotInDetailVO.getOrderNum());
                            jsonObject1.put("customerOrderNum", queryDataNotInDetailVO.getCustomerOrderNum());
                            jsonObject1.put("expireDate", JSONObject.toJSONStringWithDateFormat(queryDataNotInDetailVO.getRemittanceExpireDate(), "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat).replaceAll("\"", ""));
                            jsonObjects.add(jsonObject1);
                        });
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", "收汇到期日提醒");
                        jsonObject.put("type", "remittanceRemind");
                        jsonObject.put("data", jsonObjects);

                        try {
                            log.info(userId);
                            pushMessage(userId, JSONObject.toJSONString(jsonObject));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                Integer count2 = sysUserRoleService.lambdaQuery().eq(SysUserRole::getUserId, userId).eq(SysUserRole::getRoleId, "4").count();
                if (count2 > 0) {
                    List<QueryDataNotInDetailVO> list = xqRawMaterialPurchaseService.queryNotPayOrder();
                    if (!CollectionUtils.isEmpty(list)) {
                        ArrayList<JSONObject> jsonObjects = new ArrayList<>(list.size());
                        list.forEach(queryDataNotInDetailVO -> {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("id", queryDataNotInDetailVO.getId());
                            jsonObject1.put("orderNum", queryDataNotInDetailVO.getOrderNum());
                            jsonObject1.put("expireDate", JSONObject.toJSONStringWithDateFormat(queryDataNotInDetailVO.getRemittanceExpireDate(), "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat).replaceAll("\"", ""));
                            jsonObjects.add(jsonObject1);
                        });
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("name", "应付款到期日提醒");
                        jsonObject.put("type", "paymentRemind");
                        jsonObject.put("data", jsonObjects);

                        try {
                            log.info(userId);
                            pushMessage(userId, JSONObject.toJSONString(jsonObject));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }).start();

        } catch (Exception e) {
        }
    }

    @OnClose
    public void onClose() {
        try {
            webSockets.remove(this);
            sessionPool.remove(this.userId);
            log.info("【websocket消息】连接断开，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }


    /**
     * 服务端推送消息
     *
     * @param userId
     * @param message
     */
    public void pushMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:" + message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 服务器端推送消息
     */
    public void pushMessage(String message) {
        try {
            webSockets.forEach(ws -> ws.session.getAsyncRemote().sendText(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnMessage
    public void onMessage(String message, @PathParam(value = "userId") String userId) {
        log.info("【websocket消息】收到客户端消息:" + message);
        JSONObject obj = new JSONObject();
        //业务类型
        obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_CHECK);
        //消息内容
        obj.put(WebsocketConst.MSG_TXT, "心跳响应");
        this.pushMessage(userId, obj.toJSONString());
    }

    /**
     * 后台发送消息到redis
     *
     * @param message
     */
    public void sendMessage(String message) {
        log.info("【websocket消息】广播消息:" + message);
        BaseMap baseMap = new BaseMap();
        baseMap.put("userId", "");
        baseMap.put("message", message);
        jeecgRedisClient.sendMessage(REDIS_TOPIC_NAME, baseMap);
    }

    /**
     * 此为单点消息
     *
     * @param userId
     * @param message
     */
    public void sendMessage(String userId, String message) {
        BaseMap baseMap = new BaseMap();
        baseMap.put("userId", userId);
        baseMap.put("message", message);
        jeecgRedisClient.sendMessage(REDIS_TOPIC_NAME, baseMap);
    }

    /**
     * 此为单点消息(多人)
     *
     * @param userIds
     * @param message
     */
    public void sendMessage(String[] userIds, String message) {
        for (String userId : userIds) {
            sendMessage(userId, message);
        }
    }

    /**
     * 推送相应角色完成的消息
     */
    public void sendCompleteMsg() {
        try {
            Set<String> userIdList = sessionPool.keySet();
            if (userIdList != null && userIdList.size() > 0) {
                userIdList.forEach(ws -> {
                    List<SysRole> roleByUserId = sysUserRoleService.getRoleByUserId(ws);
                    if (CollectionUtil.isNotEmpty(roleByUserId)) {
                        List<Integer> roleCodes = roleByUserId.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
                        XqOrderCompleteMsgVO msgByRole = xqOrderService.getMsgByRole(roleCodes, ws);
                        if (msgByRole.getUnCompleteNum() > 0) {
                            if (msgByRole != null) {
                                String msg = JSONObject.toJSONString(msgByRole);
                                pushMessage(ws, msg);

                            } else {
                                Map emptyMap = Collections.EMPTY_MAP;
                                pushMessage(ws, JSONObject.toJSONString(emptyMap));
                            }
                        }
                    }

                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//        /**
//         * 推送相应角色完成的消息
//         */
//        public void sendCompleteMsg() {
//            try {
//                Set<String> userIdList = sessionPool.keySet();
//                if (userIdList != null && userIdList.size() > 0) {
//                    userIdList.forEach(ws -> {
//
//                        List<SysRole> roleByUserId = sysUserRoleService.getRoleByUserId(ws);
//                        if (CollectionUtil.isNotEmpty(roleByUserId)) {
//                            SysRole sysRole = roleByUserId.get(0);
//                            Integer roleCode = sysRole.getRoleCode();
//
//                            XqOrderCompleteMsgVO msgByRole = xqOrderService.getMsgByRole(roleCode);
//                            if (msgByRole != null) {
//                                String msg = JSONObject.toJSONString(msgByRole);
//                                pushMessage(ws, msg);
//                            }
//                        }
//                    });
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    /**
//     * 推送相应角色完成的消息
//     */
//    public void sendCompleteMsg() {
//        try {
//            webSockets.forEach(ws -> {
//                String userId1 = ws.userId;
//                List<SysRole> roleByUserId = sysUserRoleService.getRoleByUserId(userId1);
//                if (CollectionUtil.isNotEmpty(roleByUserId)) {
//                    SysRole sysRole = roleByUserId.get(0);
//                    Integer roleCode = sysRole.getRoleCode();
//
//                    XqOrderCompleteMsgVO msgByRole = xqOrderService.getMsgByRole(roleCode);
//                    if (msgByRole != null) {
//                        String msg = JSONObject.toJSONString(msgByRole);
//                        ws.pushMessage(userId1, msg);
//                    } else {
//                        Map emptyMap = Collections.EMPTY_MAP;
//                        ws.pushMessage(userId1, JSONObject.toJSONString(emptyMap));
//                    }
//                }
//
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * 发送心跳 30秒一次
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void webSocket() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "0");

        jsonObject.put("name", "心跳");
        jsonObject.put("userUid", "");
        jsonObject.put("当前连接主机数", sessionPool.size());
        sendMessage(jsonObject.toJSONString());
    }

    /**
     * 推送相应角色完成的消息
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void remittanceRemind() {
        Set<String> userIds = sessionPool.keySet();
        if (userIds.size() > 0) {
            // 查询收款到期日有数据，收汇情况没有数据的记录
            List<QueryDataNotInDetailVO> vo = xqRemittanceExpireService.queryDataNotInDetail();
            // 若vo 长度为0，则不需要继续
            if (vo.size() > 0) {
                ArrayList<JSONObject> jsonObjects = new ArrayList<>(vo.size());
                vo.forEach(queryDataNotInDetailVO -> {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("id", queryDataNotInDetailVO.getId());
                    jsonObject1.put("orderNum", queryDataNotInDetailVO.getOrderNum());
                    jsonObject1.put("expireDate", JSONObject.toJSONStringWithDateFormat(queryDataNotInDetailVO.getRemittanceExpireDate(), "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat).replaceAll("\"", ""));
                    jsonObjects.add(jsonObject1);
                });
                // 这个集合是推送目标用户集合
                List<SysUserRole> sysUserRoles = sysUserRoleService.lambdaQuery().in(SysUserRole::getUserId, userIds).eq(SysUserRole::getRoleId, "3").list();
                if (sysUserRoles.size() > 0) {
                    List<String> remindUserId = sysUserRoles.stream().map(SysUserRole::getUserId).collect(Collectors.toList());

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", "收汇到期日提醒");
                    jsonObject.put("type", "remittanceRemind");
                    jsonObject.put("data", jsonObjects);

                    try {
                        remindUserId.forEach(userId -> {
                            log.info(userId);
                            pushMessage(userId, JSONObject.toJSONString(jsonObject));
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
