package com.interesting.business.system.controller;

import com.interesting.common.api.vo.Result;
import com.interesting.common.util.OssUtil2;
import com.interesting.common.util.SpringContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oss")
@Api(tags = "图片上传接口")
public class OSSController {

    @ApiOperation(value = "图片上传接口oss(返回地址)")
    @PostMapping("/postfile")
    public Result<?> fileUpload(@RequestParam(value = "img", required = false) MultipartFile file) throws IOException {
        return Result.OK(oss(file));
    }

    public Map<String, Object> oss(MultipartFile file) throws IOException {
        Map<String, Object> map = new HashMap<>();

        if (file.getBytes() == null) {
            map.put("msg", "文件为空");
            return map;
        }

        OssUtil2 ossUtil = new OssUtil2();
        String url = ossUtil.checkImage(file);
        String[] imgUrls = url.split("\\?");

        Map<String, Object> returnMap = new HashMap<>();
        String profile = SpringContextUtils.getActiveProfile();
        if (profile != null && (profile.equals("pro"))) {
            String replace = imgUrls[0].replace("http://hztiyuju.oss-cn-hz-zt-d01-a.zt-ops.com", "https://api.cloudsymbol.cn/url");
            returnMap.put("imageUrl", replace);
        }else {
            returnMap.put("imageUrl", imgUrls[0]);
        }
        map.put("data", returnMap);
        return map;
    }

    /**
     * 附件下载
     * <p>
     *
     * @param param
     * @return ResponseDTO
     */
    @PostMapping(value = "/download")
    @ApiOperation(value = "图片下载接口oss(下载地址)")
    public void downloadFile(@RequestBody Map<String, Object> param, HttpServletResponse response) throws Exception {
        String url1 = param.get("url").toString();
        URL url = new URL(url1);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        response.reset();
        response.setContentType(conn.getContentType());
        //纯下载方式 文件名应该编码成UTF-8
        response.setHeader("Content-Disposition",
                "attachment; filename=" + URLEncoder.encode(param.get("name").toString(), "UTF-8"));
        byte[] buffer = new byte[1024];
        int len;
        OutputStream outputStream = response.getOutputStream();
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
    }



}
