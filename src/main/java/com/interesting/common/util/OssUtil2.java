package com.interesting.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Configuration
public class OssUtil2 {
    private final static Logger logger = LoggerFactory.getLogger(OssUtil2.class);


    private static String endpoint;

    private static String accessKeyId;

    private static String accessKeySecret;

    private static String bucketName;

    //文件存储目录
    private static String filedir;


    /**
     * 功能描述: byte数组转 InputStream
     *
     * @param bytes byte数组
     * @return java.io.InputStream
     * @author xiaobu
     * @date 2019/3/28 16:01
     * @version 1.0
     */
    public static InputStream byte2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }


    /**
     * 功能描述:
     *
     * @param inputStream 输入流
     * @return byte[] 数组
     * @author xiaobu
     * @date 2019/3/28 16:03
     * @version 1.0
     */
    public static byte[] inputStream2byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void setConfigInfo(OSSDTO ossdto) {
        OssUtil2.endpoint = ossdto.getEndpoint();
        OssUtil2.accessKeyId = ossdto.getAccessKey();
        OssUtil2.accessKeySecret = ossdto.getSecretKey();
        OssUtil2.bucketName = ossdto.getBucketName();
        OssUtil2.filedir = ossdto.getUpload();
    }

    public String getImage(InputStream inputStream, String name) {
        try {
            uploadFile2OSS(inputStream, name);
        } catch (Exception e) {
            throw new RuntimeException("上传失败");
        }
        String str = getImgUrl(name);
        return str.trim();
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    public static String uploadImg2Oss(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();

            uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new RuntimeException("上传失败");
        }
    }

    /**
     * 上传图片获取fileUrl
     *
     * @param instream
     * @param fileName
     * @return
     */
    public static String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            Calendar cal = Calendar.getInstance();
            String ctxPath = filedir + "/" +cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONDAY) + 1) + "/" + cal.get(Calendar.DATE) + "/";
            String profile = SpringContextUtils.getActiveProfile();
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putResult = ossClient.putObject(bucketName, ctxPath + fileName, instream, objectMetadata);
            ret = putResult.getETag();
            logger.info("结果是" + ret + "环境是" + profile);
        } catch (IOException e) {
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    public static String getcontentType(String str) {
        String FilenameExtension = "." + str.substring(str.lastIndexOf(".") + 1);
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".xlsx")) {
            return "application/vnd.ms-excel";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp4")) {
            return "video/mpeg4";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获取图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
//        String profile = SpringContextUtils.getActiveProfile();
        Calendar cal = Calendar.getInstance();
        String ctxPath = filedir + "/" +cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONDAY) + 1) + "/" + cal.get(Calendar.DATE) + "/";
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            String url = this.getUrl(ctxPath + split[split.length - 1]);
            return url;
        }
        return null;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
//        String profile = SpringContextUtils.getActiveProfile();
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return key;
        }
        return null;
    }


    /**
     * 多图片上传
     *
     * @param fileList
     * @return
     */
    public String checkList(List<MultipartFile> fileList) {
        String fileUrl = "";
        String str = "";
        String photoUrl = "";
        for (int i = 0; i < fileList.size(); i++) {
            fileUrl = uploadImg2Oss(fileList.get(i));
            str = getImgUrl(fileUrl);
            if (i == 0) {
                photoUrl = str;
            } else {
                photoUrl += "," + str;
            }
        }
        return photoUrl.trim();
    }

    /**
     * 单个图片上传
     *
     * @param file
     * @return
     */
    public String checkImage(MultipartFile file) {
        String fileUrl = uploadImg2Oss(file);
        String str = getImgUrl(fileUrl);
        return str.trim();
    }
}
