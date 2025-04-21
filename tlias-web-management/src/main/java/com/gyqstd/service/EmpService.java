package com.gyqstd.service;

import com.gyqstd.pojo.Emp;
import com.gyqstd.pojo.PageResult;

/**
 * @author GuYuqi
 * @version 1.0
 */
public interface EmpService {
    PageResult<Emp> page(Integer page, Integer pageSize);
}
