package com.gyqstd.controller;

import com.gyqstd.pojo.Dept;
import com.gyqstd.pojo.Result;
import com.gyqstd.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/depts")
    public Result list() {
        System.out.println("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    @DeleteMapping("/depts")
    public Result delete(Integer id) {
        System.out.println("根据 ID 删除部门" + id);
        deptService.deleteById(id);
        return Result.success();
    }
}
