package com.interesting.common.util;

import cn.hutool.extra.qrcode.QrCodeUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 * <p>
 * 文件工具类
 *
 * @author cc
 * @version 1.0
 * @date 2021/11/1
 */
public class FileUtil {
    /**
     * 获取项目相对路径
     *
     * @return
     */
    public static String getRelativePath() {
        // 获取当前线程的路径
        String projectPath = System.getProperty("user.dir");
        return projectPath;
    }

    /**
     * 获取相对路径根据
     *
     * @param par 相对路径后的前缀
     * @return /www/jar/cc/2021/10/11/static/visitorCode
     */
    public static String getRelativePath(String par) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");// 格式化为年月
        String date = sf.format(new Date());
        // 获取当前线程的路径
        String projectPath = System.getProperty("user.dir") + "/static/" + par + "/" + date;
        return projectPath;
    }

    /**
     * 创建二维码code
     *
     * @param code 二维码code
     * @return 返回文件路径
     */
    public static String createCodeImg(String code) {
        String visitorCode = getRelativePath("visitorCode");
        String ccc = visitorCode + "/" + code+".png";
        File f = new File(visitorCode + "/");
        if (!f.exists()) {
            //创建目录
            f.mkdirs();
        }
        QrCodeUtil.generate(code, 200, 200, new File(ccc));
        return ccc;
    }

    public static void main(String[] args) {
        String cocci = createCodeImg("cocci");
        int cc = 0;
    }

}
