package com.gyqstd.mapper;

import com.gyqstd.pojo.Student;
import com.gyqstd.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  @author GuYuqi
 *  @version 1.0
 */
@Mapper
public interface StudentMapper {

    List<Student> list(StudentQueryParam studentQueryParam);

    void add(Student student);

    Student getById(Integer id);

    void updateById(Student student);

    void deleteById(List<Integer> ids);
}
