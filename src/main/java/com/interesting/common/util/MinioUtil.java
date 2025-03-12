package com.interesting.common.util;

import com.interesting.common.util.filter.StrAttackFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * minio文件上传工具类
 */
@Slf4j
public class MinioUtil {
    private static String minioUrl;
    private static String minioName;
    private static String minioPass;
    private static String bucketName;

    public static void setMinioUrl(String minioUrl) {
        MinioUtil.minioUrl = minioUrl;
    }

    public static void setMinioName(String minioName) {
        MinioUtil.minioName = minioName;
    }

    public static void setMinioPass(String minioPass) {
        MinioUtil.minioPass = minioPass;
    }

    public static void setBucketName(String bucketName) {
        MinioUtil.bucketName = bucketName;
    }

    public static String getMinioUrl() {
        return minioUrl;
    }

    public static String getBucketName() {
        return bucketName;
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public static String upload(MultipartFile file, String bizPath, String customBucket) {
        String file_url = "";
        //update-begin-author:wangshuai date:20201012 for: 过滤上传文件夹名特殊字符，防止攻击
        bizPath=StrAttackFilter.filter(bizPath);
        //update-end-author:wangshuai date:20201012 for: 过滤上传文件夹名特殊字符，防止攻击
        String newBucket = bucketName;
        if(oConvertUtils.isNotEmpty(customBucket)){
            newBucket = customBucket;
        }
        try {
            // 检查存储桶是否已经存在
            InputStream stream = file.getInputStream();
            // 获取文件名
            String orgName = file.getOriginalFilename();
            if("".equals(orgName)){
                orgName=file.getName();
            }
            orgName = CommonUtils.getFileName(orgName);
            String objectName = bizPath+"/"+orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.lastIndexOf("."));

            // 使用putObject上传一个本地文件到存储桶中。
            if(objectName.startsWith("/")){
                objectName = objectName.substring(1);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return file_url;
    }

    /**
     * 文件上传
     * @param file
     * @param bizPath
     * @return
     */
    public static String upload(MultipartFile file, String bizPath) {
        return  upload(file,bizPath,null);
    }


}
