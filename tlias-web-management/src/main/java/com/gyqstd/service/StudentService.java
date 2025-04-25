package com.gyqstd.service;

import com.gyqstd.pojo.Clazz;
import com.gyqstd.pojo.PageResult;
import com.gyqstd.pojo.Student;
import com.gyqstd.pojo.StudentQueryParam;

/**
 *  @author GuYuqi
 *  @version 1.0
 */
public interface StudentService {

    PageResult<Student> page(StudentQueryParam studentQueryParam);

    void add(Student student);

    Student getInfo(Integer id);

    void update(Student student);
}
