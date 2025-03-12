package com.interesting.business.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.interesting.business.system.loca.api.ISysBaseAPI;

import com.interesting.common.api.vo.Result;
import com.interesting.common.constant.CommonConstant;
import com.interesting.common.exception.InterestingBootException;
import com.interesting.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author: interesting-boot
 * @since 2018-12-20
 */
@Slf4j
@RestController
@RequestMapping("/sys/common")
@Api(tags = "公共接口")

public class CommonController {

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;

    /**
     * 本地：local minio：minio 阿里：alioss
     */
    @Value(value = "${jeecg.uploadType}")
    private String uploadType;

    @Value(value = "${jeecg.oss.endpoint}")
    private String endpoint;
    @Value(value = "${jeecg.oss.accessKey}")
    private String accessKeyId;
    @Value(value = "${jeecg.oss.secretKey}")
    private String accessKeySecret;
    @Value(value = "${jeecg.oss.bucketName}")
    private String bucketName;

    /**
     * @return
     * @Author 政辉
     */
    @GetMapping("/403")
    public Result<?> noauth() {
        return Result.error("没有权限，请联系管理员授权");
    }

    /**
     * 文件上传统一方法
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation(value = "公共接口-本地文件上传", notes = "公共接口-本地文件上传")
    public Result<?> upload(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(value = "file", required = false) MultipartFile file) {
        long maxSizeInBytes = 104857600L;
        if (file.getSize() > maxSizeInBytes) {
            throw new InterestingBootException("上传的文件大小超过了100MB");
        }
        Result<?> result = new Result<>();
        String savePath = "";
        String bizPath = request.getParameter("biz");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        OssUtil2 ossUtil = new OssUtil2();
//        String contentPath=ossUtil.getImgUrl(file.getOriginalFilename());
//
//
//        // 创建OSSClient实例
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        // 判断文件是否存在
//        boolean exists = ossClient.doesObjectExist(bucketName, contentPath);
//
//        // 输出结果
//        if (exists) {
//            System.out.println("文件存在");
//        } else {
//            System.out.println("文件不存在");
//        }
//        MultipartFile file = multipartRequest.getFile("file");// 获取上传文件对象


        if (oConvertUtils.isEmpty(bizPath)) {
            if (CommonConstant.UPLOAD_TYPE_OSS.equals(uploadType)) {
                //未指定目录，则用阿里云默认目录 upload
                bizPath = "upload";
            } else {
                bizPath = "";
            }
        }
        if (CommonConstant.UPLOAD_TYPE_LOCAL.equals(uploadType)) {
            savePath = this.uploadLocal(file, bizPath);
        }
        if (CommonConstant.UPLOAD_TYPE_OSS.equals(uploadType)) {
            savePath = this.uploadOss(file, bizPath);
        }
        if (oConvertUtils.isNotEmpty(savePath)) {
            result.setMessage(savePath);
            result.setSuccess(true);
        } else {
            result.setMessage("上传失败！");
            result.setSuccess(false);
        }
        return result;
    }

    private String uploadOss(MultipartFile file, String bizPath) {
        OssUtil2 ossUtil = new OssUtil2();
        String url = ossUtil.checkImage(file);
        String[] imgUrls = url.split("\\?");
//        Map<String, Object> returnMap = new HashMap<>();
//        String profile = SpringContextUtils.getActiveProfile();
//        if (profile != null && (profile.equals("pro"))) {
//            String replace = imgUrls[0].replace("http://hztiyuju.oss-cn-hz-zt-d01-a.zt-ops.com", "https://api.cloudsymbol.cn/url");
//            returnMap.put("imageUrl", replace);
//            return replace;
//        } else {
//            return imgUrls[0];
//        }
        return imgUrls[0];
    }

    /**
     * 本地文件上传
     *
     * @param mf      文件
     * @param bizPath 自定义路径
     * @return
     */
    private String uploadLocal(MultipartFile mf, String bizPath) {
        try {
            Calendar cal = Calendar.getInstance();
            String ctxPath = uploadpath + "//" + cal.get(Calendar.YEAR) + "//" + (cal.get(Calendar.MONDAY) + 1) + "//" + cal.get(Calendar.DATE) + "//";
            String fileName = null;
            File file = new File(ctxPath + File.separator + bizPath + File.separator);
            if (!file.exists()) {
                file.mkdirs();// 创建文件根目录
            }
            String orgName = mf.getOriginalFilename();// 获取文件名
            orgName = CommonUtils.getFileName(orgName);
            if (orgName.indexOf(".") != -1) {
                fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
            } else {
                fileName = orgName + "_" + System.currentTimeMillis();
            }
            String savePath = file.getPath() + File.separator + fileName;
            File savefile = new File(savePath);
            FileCopyUtils.copy(mf.getBytes(), savefile);
            String dbpath = null;
            if (oConvertUtils.isNotEmpty(bizPath)) {
                dbpath = ctxPath + bizPath + File.separator + fileName;
            } else {
                dbpath = ctxPath + fileName;
            }
            if (dbpath.contains("\\")) {
                dbpath = dbpath.replace("\\", "/");
            }
            return dbpath;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 预览图片&下载文件
     * 请求地址：http://localhost:8080/common/static/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/static/**")
    public void view(HttpServletRequest request, HttpServletResponse response) {
        // ISO-8859-1 ==> UTF-8 进行编码转换
        String imgPath = extractPathFromPattern(request);
        if (oConvertUtils.isEmpty(imgPath) || imgPath == "null") {
            return;
        }
        // 其余处理略
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            imgPath = imgPath.replace("src/main", "");
            if (imgPath.endsWith(",")) {
                imgPath = imgPath.substring(0, imgPath.length() - 1);
            }
            String filePath = imgPath;
            // String filePath = "/opt/upFiles/2022/3/2/yssoBK1FMSiaad19abd0ceb4aa7ed401288668c137e0_1646183584476.jpg";
            File file = new File(filePath);
            if (!file.exists()) {
                response.setStatus(404);
//                throw new RuntimeException("文件[" + imgPath + "]不存在..");
            }
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("UTF-8"), "iso-8859-1"));
            inputStream = new BufferedInputStream(new FileInputStream(filePath));
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (IOException e) {
            log.error("预览文件失败" + e.getMessage());
            response.setStatus(404);
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

    }

    /**
     * 下载文件
     * 请求地址：http://localhost:8080/common/download/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping(value = "/download/**")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // ISO-8859-1 ==> UTF-8 进行编码转换
        String filePath = extractPathFromPattern(request);
        // 其余处理略
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            filePath = filePath.replace("..", "");
            if (filePath.endsWith(",")) {
                filePath = filePath.substring(0, filePath.length() - 1);
            }
            String localPath = uploadpath;
            String downloadFilePath = localPath + File.separator + filePath;
            File file = new File(downloadFilePath);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开            
                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(file.getName().getBytes("UTF-8"), "iso-8859-1"));
                inputStream = new BufferedInputStream(new FileInputStream(file));
                outputStream = response.getOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, len);
                }
                response.flushBuffer();
            }

        } catch (Exception e) {
            log.info("文件下载失败" + e.getMessage());
            // e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * @param modelAndView
     * @return
     * @功能：pdf预览Iframe
     */
    @RequestMapping("/pdf/pdfPreviewIframe")
    public ModelAndView pdfPreviewIframe(ModelAndView modelAndView) {
        modelAndView.setViewName("pdfPreviewIframe");
        return modelAndView;
    }

    /**
     * 把指定URL后的字符串全部截断当成参数
     * 这么做是为了防止URL中包含中文或者特殊字符（/等）时，匹配不了的问题
     *
     * @param request
     * @return
     */
    private static String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

    /**
     * 中转HTTP请求，解决跨域问题
     *
     * @param url 必填：请求地址
     * @return
     */
    @RequestMapping("/transitRESTful")
    public Result transitRESTful(@RequestParam("url") String url, HttpServletRequest request) {
        try {
            ServletServerHttpRequest httpRequest = new ServletServerHttpRequest(request);
            // 中转请求method、body
            HttpMethod method = httpRequest.getMethod();
            JSONObject params;
            try {
                params = JSON.parseObject(JSON.toJSONString(httpRequest.getBody()));
            } catch (Exception e) {
                params = new JSONObject();
            }
            // 中转请求问号参数
            JSONObject variables = JSON.parseObject(JSON.toJSONString(request.getParameterMap()));
            variables.remove("url");
            // 在 headers 里传递Token
            String token = TokenUtils.getTokenByRequest(request);
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Access-Token", token);
            // 发送请求
            String httpURL = URLDecoder.decode(url, "UTF-8");
            ResponseEntity<String> response = RestUtil.request(httpURL, method, headers, variables, params, String.class);
            // 封装返回结果
            Result<Object> result = new Result<>();
            int statusCode = response.getStatusCodeValue();
            result.setCode(statusCode);
            result.setSuccess(statusCode == 200);
            String responseBody = response.getBody();
            try {
                // 尝试将返回结果转为JSON
                Object json = JSON.parse(responseBody);
                result.setResult(json);
            } catch (Exception e) {
                // 转成JSON失败，直接返回原始数据
                result.setResult(responseBody);
            }
            return result;
        } catch (Exception e) {
            log.debug("中转HTTP请求失败", e);
            return Result.error(e.getMessage());
        }
    }

}
