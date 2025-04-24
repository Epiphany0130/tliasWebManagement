package com.gyqstd.controller;

import com.github.pagehelper.Page;
import com.gyqstd.pojo.*;
import com.gyqstd.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("分页查询：{}", clazzQueryParam);
        PageResult<Clazz> pageResult = clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result add(@RequestBody Clazz clazz) {
        log.info("新增部门：{}", clazz);
        clazzService.add(clazz);
        return Result.success();
    }

}
