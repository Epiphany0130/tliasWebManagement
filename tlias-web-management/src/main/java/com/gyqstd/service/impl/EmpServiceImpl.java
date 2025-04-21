package com.gyqstd.service.impl;

import com.gyqstd.mapper.EmpMapper;
import com.gyqstd.pojo.Emp;
import com.gyqstd.pojo.PageResult;
import com.gyqstd.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        Long total = empMapper.count();
        Integer start = (page - 1) * pageSize;
        List<Emp> rows = empMapper.list(start, pageSize);
        return new PageResult<Emp>(total, rows);
    }
}
