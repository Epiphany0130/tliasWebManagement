package com.gyqstd.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gyqstd.mapper.StudentMapper;
import com.gyqstd.pojo.Clazz;
import com.gyqstd.pojo.PageResult;
import com.gyqstd.pojo.Student;
import com.gyqstd.pojo.StudentQueryParam;
import com.gyqstd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());

        List<Student> studentList = studentMapper.list(studentQueryParam);

        Page<Student> p = (Page<Student>) studentList;
        return new PageResult<Student>(p.getTotal(), p.getResult());
    }

    @Override
    public void add(Student student) {
        studentMapper.add(student);
    }

    @Override
    public Student getInfo(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateById(student);
    }
}
