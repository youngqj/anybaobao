package com.interesting.business.system.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.interesting.business.message.websocket.WebSocket;
import com.interesting.business.system.entity.SysAnnouncement;
import com.interesting.business.system.entity.SysAnnouncementSend;
import com.interesting.business.system.service.ISysAnnouncementSendService;
import com.interesting.business.system.service.ISysAnnouncementService;
import com.interesting.common.api.vo.Result;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.constant.CommonSendStatus;
import com.interesting.common.constant.WebsocketConst;
import com.interesting.common.system.util.JwtUtil;
import com.interesting.common.system.vo.LoginUser;
import com.interesting.common.util.StringUtils;
import com.interesting.common.util.oConvertUtils;
import com.interesting.config.FilterContextHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: Controller
 * @Description: 系统通告表
 * @Author: interesting-boot
 * @Date: 2019-01-02
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sys/annountCement")
@Slf4j
public class SysAnnouncementController {
    @Autowired
    private ISysAnnouncementService sysAnnouncementService;
    @Autowired
    private ISysAnnouncementSendService sysAnnouncementSendService;
    @Resource
    private WebSocket webSocket;

    /**
     * 分页列表查询
     *
     * @param sysAnnouncement
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<SysAnnouncement>> queryPageList(SysAnnouncement sysAnnouncement,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        Result<IPage<SysAnnouncement>> result = new Result<IPage<SysAnnouncement>>();
        sysAnnouncement.setIzDelete(CommonConstant.IZ_DELETE_0.toString());
        QueryWrapper<SysAnnouncement> queryWrapper = new QueryWrapper<SysAnnouncement>(sysAnnouncement);
        Page<SysAnnouncement> page = new Page<SysAnnouncement>(pageNo, pageSize);
        //排序逻辑 处理
        String column = req.getParameter("column");
        String order = req.getParameter("order");
        if (oConvertUtils.isNotEmpty(column) && oConvertUtils.isNotEmpty(order)) {
            if ("asc".equals(order)) {
                queryWrapper.orderByAsc(oConvertUtils.camelToUnderline(column));
            } else {
                queryWrapper.orderByDesc(oConvertUtils.camelToUnderline(column));
            }
        }
        IPage<SysAnnouncement> pageList = sysAnnouncementService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param sysAnnouncement
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<SysAnnouncement> add(@RequestBody SysAnnouncement sysAnnouncement) {
        Result<SysAnnouncement> result = new Result<SysAnnouncement>();
        try {
            sysAnnouncement.setIzDelete(CommonConstant.IZ_DELETE_0.toString());
            sysAnnouncement.setSendStatus(CommonSendStatus.UNPUBLISHED_STATUS_0);//未发布
            sysAnnouncementService.saveAnnouncement(sysAnnouncement);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param sysAnnouncement
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Result<SysAnnouncement> eidt(@RequestBody SysAnnouncement sysAnnouncement) {
        Result<SysAnnouncement> result = new Result<SysAnnouncement>();
        SysAnnouncement sysAnnouncementEntity = sysAnnouncementService.getById(sysAnnouncement.getId());
        if (sysAnnouncementEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = sysAnnouncementService.upDateAnnouncement(sysAnnouncement);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Result<SysAnnouncement> delete(@RequestParam(name = "id", required = true) String id) {
        Result<SysAnnouncement> result = new Result<SysAnnouncement>();
        SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
        if (sysAnnouncement == null) {
            result.error500("未找到对应实体");
        } else {
            sysAnnouncement.setIzDelete(CommonConstant.IZ_DELETE_1.toString());
            boolean ok = sysAnnouncementService.updateById(sysAnnouncement);
            if (ok) {
                result.success("删除成功!");
            }
        }

        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    public Result<SysAnnouncement> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<SysAnnouncement> result = new Result<SysAnnouncement>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            String[] id = ids.split(",");
            for (int i = 0; i < id.length; i++) {
                SysAnnouncement announcement = sysAnnouncementService.getById(id[i]);
                announcement.setIzDelete(CommonConstant.IZ_DELETE_1.toString());
                sysAnnouncementService.updateById(announcement);
            }
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    public Result<SysAnnouncement> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<SysAnnouncement> result = new Result<SysAnnouncement>();
        SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
        if (sysAnnouncement == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(sysAnnouncement);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 更新发布操作
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/doReleaseData", method = RequestMethod.GET)
    public Result<SysAnnouncement> doReleaseData(@RequestParam(name = "id", required = true) String id, HttpServletRequest request) {
        Result<SysAnnouncement> result = new Result<SysAnnouncement>();
        SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
        if (sysAnnouncement == null) {
            result.error500("未找到对应实体");
        } else {
            sysAnnouncement.setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);//发布中
            sysAnnouncement.setSendTime(new Date());
            String currentUserName = JwtUtil.getUserNameByToken(request);
            sysAnnouncement.setSender(currentUserName);
            boolean ok = sysAnnouncementService.updateById(sysAnnouncement);
            if (ok) {
                result.success("该系统通知发布成功");
                if (sysAnnouncement.getMsgType().equals(CommonConstant.MSG_TYPE_ALL)) {
                    JSONObject obj = new JSONObject();
                    obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_TOPIC);
                    obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
                    obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
                    webSocket.sendMessage(obj.toJSONString());
                } else {
                    // 2.插入用户通告阅读标记表记录
                    String userId = sysAnnouncement.getUserIds();
                    String[] userIds = userId.substring(0, (userId.length() - 1)).split(",");
                    String anntId = sysAnnouncement.getId();
                    Date refDate = new Date();
                    JSONObject obj = new JSONObject();
                    obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
                    obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
                    obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
                    webSocket.sendMessage(userIds, obj.toJSONString());
                }
            }
        }

        return result;
    }

    /**
     * 更新撤销操作
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/doReovkeData", method = RequestMethod.GET)
    public Result<SysAnnouncement> doReovkeData(@RequestParam(name = "id", required = true) String id, HttpServletRequest request) {
        Result<SysAnnouncement> result = new Result<SysAnnouncement>();
        SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(id);
        if (sysAnnouncement == null) {
            result.error500("未找到对应实体");
        } else {
            sysAnnouncement.setSendStatus(CommonSendStatus.REVOKE_STATUS_2);//撤销发布
            sysAnnouncement.setCancelTime(new Date());
            boolean ok = sysAnnouncementService.updateById(sysAnnouncement);
            if (ok) {
                result.success("该系统通知撤销成功");
            }
        }

        return result;
    }

    /**
     * @return
     * @功能：补充用户数据，并返回系统消息
     */
    @RequestMapping(value = "/listByUser", method = RequestMethod.GET)
    public Result<Map<String, Object>> listByUser() {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        LoginUser sysUser = FilterContextHandler.getUserInfo();
        String userId = sysUser.getId();
        // 1.将系统消息补充到用户通告阅读标记表中
        LambdaQueryWrapper<SysAnnouncement> querySaWrapper = new LambdaQueryWrapper<SysAnnouncement>();
        querySaWrapper.eq(SysAnnouncement::getMsgType, CommonConstant.MSG_TYPE_ALL); // 全部人员
        querySaWrapper.eq(SysAnnouncement::getIzDelete, CommonConstant.IZ_DELETE_0.toString());  // 未删除
        querySaWrapper.eq(SysAnnouncement::getSendStatus, CommonConstant.HAS_SEND); //已发布
        querySaWrapper.ge(SysAnnouncement::getEndTime, sysUser.getCreateTime()); //新注册用户不看结束通知
        //update-begin--Author:liusq  Date:20210108 for：[JT-424] 【开源issue】bug处理--------------------
        querySaWrapper.notInSql(SysAnnouncement::getId, "select annt_id from sys_announcement_send where user_id='" + userId + "'");
        //update-begin--Author:liusq  Date:20210108  for： [JT-424] 【开源issue】bug处理--------------------
        List<SysAnnouncement> announcements = sysAnnouncementService.list(querySaWrapper);
        if (announcements.size() > 0) {
            for (int i = 0; i < announcements.size(); i++) {
                //update-begin--Author:wangshuai  Date:20200803  for： 通知公告消息重复LOWCOD-759--------------------
                //因为websocket没有判断是否存在这个用户，要是判断会出现问题，故在此判断逻辑
                LambdaQueryWrapper<SysAnnouncementSend> query = new LambdaQueryWrapper<>();
                query.eq(SysAnnouncementSend::getAnntId, announcements.get(i).getId());
                query.eq(SysAnnouncementSend::getUserId, userId);
                SysAnnouncementSend one = sysAnnouncementSendService.getOne(query);
                if (null == one) {
                    SysAnnouncementSend announcementSend = new SysAnnouncementSend();
                    announcementSend.setAnntId(announcements.get(i).getId());
                    announcementSend.setUserId(userId);
                    announcementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
                    sysAnnouncementSendService.save(announcementSend);
                }
                //update-end--Author:wangshuai  Date:20200803  for： 通知公告消息重复LOWCOD-759------------
            }
        }
        // 2.查询用户未读的系统消息
        Page<SysAnnouncement> anntMsgList = new Page<SysAnnouncement>(0, 5);
        anntMsgList = sysAnnouncementService.querySysCementPageByUserId(anntMsgList, userId, "1");//通知公告消息
        Page<SysAnnouncement> sysMsgList = new Page<SysAnnouncement>(0, 5);
        sysMsgList = sysAnnouncementService.querySysCementPageByUserId(sysMsgList, userId, "2");//系统消息
        Map<String, Object> sysMsgMap = new HashMap<String, Object>();
        sysMsgMap.put("sysMsgList", sysMsgList.getRecords());
        sysMsgMap.put("sysMsgTotal", sysMsgList.getTotal());
        sysMsgMap.put("anntMsgList", anntMsgList.getRecords());
        sysMsgMap.put("anntMsgTotal", anntMsgList.getTotal());
        result.setSuccess(true);
        result.setResult(sysMsgMap);
        return result;
    }

    /**
     * 同步消息
     *
     * @param anntId
     * @return
     */
    @RequestMapping(value = "/syncNotic", method = RequestMethod.GET)
    public Result<SysAnnouncement> syncNotic(@RequestParam(name = "anntId", required = false) String anntId, HttpServletRequest request) {
        Result<SysAnnouncement> result = new Result<SysAnnouncement>();
        JSONObject obj = new JSONObject();
        if (StringUtils.isNotBlank(anntId)) {
            SysAnnouncement sysAnnouncement = sysAnnouncementService.getById(anntId);
            if (sysAnnouncement == null) {
                result.error500("未找到对应实体");
            } else {
                if (sysAnnouncement.getMsgType().equals(CommonConstant.MSG_TYPE_ALL)) {
                    obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_TOPIC);
                    obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
                    obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
                    webSocket.sendMessage(obj.toJSONString());
                } else {
                    // 2.插入用户通告阅读标记表记录
                    String userId = sysAnnouncement.getUserIds();
                    if (oConvertUtils.isNotEmpty(userId)) {
                        String[] userIds = userId.substring(0, (userId.length() - 1)).split(",");
                        obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_USER);
                        obj.put(WebsocketConst.MSG_ID, sysAnnouncement.getId());
                        obj.put(WebsocketConst.MSG_TXT, sysAnnouncement.getTitile());
                        webSocket.sendMessage(userIds, obj.toJSONString());
                    }
                }
            }
        } else {
            obj.put(WebsocketConst.MSG_CMD, WebsocketConst.CMD_TOPIC);
            obj.put(WebsocketConst.MSG_TXT, "批量设置已读");
            webSocket.sendMessage(obj.toJSONString());
        }
        return result;
    }
}
