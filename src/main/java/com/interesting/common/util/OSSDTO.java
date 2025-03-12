package com.interesting.common.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Zhejiang Xiaoqu information technology Co., Ltd.  All rights reserved.
 *
 * @author: anian
 * @version: 1.0
 * @date: 2023/9/5 16:11
 * @Project: trade-manage
 * @Description:
 */

@Data
@Configuration
public class OSSDTO {

    @Value("${jeecg.oss.accessKey}")
    private  String accessKey ;
    @Value("${jeecg.oss.secretKey}")
    private  String secretKey ;
    @Value("${jeecg.oss.bucketName}")
    private  String bucketName ;
    @Value("${jeecg.oss.endpoint}")
    private  String endpoint ;
    //文件存储目录
    @Value("${jeecg.path.upload}")
    private  String upload ;

    @PostConstruct
    public void init(){
        OssUtil2.setConfigInfo(this);
    }


}
