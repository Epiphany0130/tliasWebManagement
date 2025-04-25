package com.gyqstd.controller;

import com.gyqstd.pojo.Dept;
import com.gyqstd.pojo.Result;
import com.gyqstd.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.table.TableRowSorter;
import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result list() {
//        System.out.println("查询全部部门数据");
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    @DeleteMapping
    public Result delete(Integer id) {
//        System.out.println("根据 ID 删除部门" + id);
        log.info("根据 ID 删除部门: {}", id);
        
        // 检查部门下是否有员工
        if (deptService.hasEmployees(id)) {
            return Result.error("对不起，当前部门下有员工，不能直接删除！");
        }
        
        deptService.deleteById(id);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Dept dept) {
//        System.out.println("新增部门：" + dept);
        log.info("新增部门: {}", dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
//        System.out.println("根据 ID 查询部门：" + id);
        log.info("根据 ID 查询部门: {}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @PutMapping
    public Result update(@RequestBody Dept dept) {
//        System.out.println("修改部门：" + dept);
        log.info("修改部门: {}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
