package com.gyqstd.service;

import com.gyqstd.pojo.Emp;
import com.gyqstd.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author GuYuqi
 * @version 1.0
 */
public interface EmpService {
    PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);
}
