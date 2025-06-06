package com.gyqstd.mapper;

import com.gyqstd.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Mapper
public interface DeptMapper {
    @Select("select id, name,create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    void add(Dept dept);

    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
