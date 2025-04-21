package com.gyqstd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gyqstd.mapper.EmpMapper;
import com.gyqstd.pojo.Emp;
import com.gyqstd.pojo.EmpQueryParam;
import com.gyqstd.pojo.PageResult;
import com.gyqstd.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    //-------------------------------------------------------
    // 传统方式
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//        Long total = empMapper.count();
//        Integer start = (page - 1) * pageSize;
//        List<Emp> rows = empMapper.list(start, pageSize);
//        return new PageResult<Emp>(total, rows);
//    }
    //-------------------------------------------------------
    // PageHelper
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 1. 设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        // 2. 执行查询
        List<Emp> empList = empMapper.list(empQueryParam);

        // 3. 查询结果并封装
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }
}
