package com.interesting.modules.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2021/9/18 10:44
 * @Project: cmi
 * @Description: 获取当前服务的IP端口
 */

@Component
public class ServerConfig {

    @Value("${spring.url}")
    private String Url;

    public String getUrl() {
        return Url;
    }


//
//    public String handleUrl(String str){
//        return str.replace("<img src=\"", "<img src=\"" + Url);
//    }

    /**
     * 处理富文本路径
     *
     * @param
     * @return
     */
//    public List<ProjectManageVO> handleImgUrl(List<ProjectManageVO> records) {
//
//        for (ProjectManageVO record : records) {
//            if (!StringUtil.isEmpty(record.getContent())) {
//                String replace = record.getContent().replace("<img src=\"", "<img src=\"" + Url);
//                record.setContent(replace);
//            }
//        }
//        return records;
//    }


}
