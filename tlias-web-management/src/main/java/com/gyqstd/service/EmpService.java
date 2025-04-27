package com.gyqstd.service;

import com.gyqstd.pojo.Emp;
import com.gyqstd.pojo.EmpQueryParam;
import com.gyqstd.pojo.LoginInfo;
import com.gyqstd.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
public interface EmpService {

    PageResult<Emp> page(EmpQueryParam empQueryParam);

    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);

    List<Emp> findAll();

    LoginInfo login(Emp emp);
}
