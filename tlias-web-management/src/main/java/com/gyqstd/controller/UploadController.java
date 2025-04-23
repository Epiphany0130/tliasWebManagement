package com.gyqstd.controller;

import com.gyqstd.pojo.Result;
import com.gyqstd.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}", file.getOriginalFilename());
//        System.out.println("Key: " + System.getenv("OSS_ACCESS_KEY_ID"));
//        System.out.println("Key: " + System.getenv("OSS_ACCESS_KEY_SECRET"));
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传 OSS：{}", url);
        return Result.success(url);
    }
}
