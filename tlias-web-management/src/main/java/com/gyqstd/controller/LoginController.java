package com.gyqstd.controller;

import com.gyqstd.pojo.Emp;
import com.gyqstd.pojo.LoginInfo;
import com.gyqstd.pojo.Result;
import com.gyqstd.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp) {
        log.info("登陆：{}", emp);
        LoginInfo info = empService.login(emp);
        if(info != null) {
            return Result.success(info);
        }
        return Result.error("用户或密码错误");
    }
}
