package com.interesting.business.message.handle.impl;

import com.interesting.business.message.handle.ISendMsgHandle;
import com.interesting.common.util.SpringContextUtils;
import com.interesting.common.util.oConvertUtils;
import com.interesting.config.StaticConfig;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

//import javax.mail.internet.MimeMessage;


public class EmailSendMsgHandle implements ISendMsgHandle {
    static String emailFrom;

    public static void setEmailFrom(String emailFrom) {
        EmailSendMsgHandle.emailFrom = emailFrom;
    }

    @Override
    public void SendMsg(String es_receiver, String es_title, String es_content) {
        JavaMailSender mailSender = (JavaMailSender) SpringContextUtils.getBean("mailSender");
//        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        //update-begin-author：taoyan date:20200811 for:配置类数据获取
        if (oConvertUtils.isEmpty(emailFrom)) {
            StaticConfig staticConfig = SpringContextUtils.getBean(StaticConfig.class);
            setEmailFrom(staticConfig.getEmailFrom());
        }
        //update-end-author：taoyan date:20200811 for:配置类数据获取
//        try {
//            helper = new MimeMessageHelper(message, true);
//            // 设置发送方邮箱地址
//            helper.setFrom(emailFrom);
//            helper.setTo(es_receiver);
//            helper.setSubject(es_title);
//            helper.setText(es_content, true);
//            mailSender.send(message);
//        } catch (javax.mail.MessagingException e) {
//            e.printStackTrace();
//        }

    }
}
