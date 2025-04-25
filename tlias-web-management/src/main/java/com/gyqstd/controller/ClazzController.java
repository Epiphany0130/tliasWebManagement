package com.gyqstd.controller;

import com.github.pagehelper.Page;
import com.gyqstd.pojo.*;
import com.gyqstd.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        log.info("新增班级：{}", clazz);
        clazzService.add(clazz);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据 ID 查询班级信息：{}", id);
        Clazz clazz = clazzService.getInfo(id);
        return Result.success(clazz);
    }

    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("修改班级：{}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("根据 ID 删除班级：{}", id);
        clazzService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list() {
        log.info("查询全部班级数据");
        List<Clazz> clazzList = clazzService.findAll();
        return Result.success(clazzList);
    }

}
