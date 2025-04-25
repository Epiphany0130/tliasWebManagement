package com.gyqstd.controller;

import com.gyqstd.pojo.*;
import com.gyqstd.service.ClazzService;
import com.gyqstd.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  @author GuYuqi
 *  @version 1.0
 */
@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询：{}", studentQueryParam);
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result add(@RequestBody Student student) {
        log.info("新增学生：{}", student);
        studentService.add(student);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据 ID 查询学生信息：{}", id);
        Student student = studentService.getInfo(id);
        return Result.success(student);
    }
}
