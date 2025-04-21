package com.gyqstd.mapper;

import com.gyqstd.pojo.Emp;
import com.gyqstd.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Mapper
public interface EmpMapper {
    //-------------------------------------------------------
    // 传统方式
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public Long count();
//
//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc limit #{start}, #{pageSize}")
//    public List<Emp> list(Integer start, Integer pageSize);
    //-------------------------------------------------------
    // PageHelper
//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc")
    public List<Emp> list(EmpQueryParam empQueryParam);
}
