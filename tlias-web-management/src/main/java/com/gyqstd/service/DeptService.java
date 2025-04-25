package com.gyqstd.service;

import com.gyqstd.pojo.Dept;

import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
public interface DeptService {
    List<Dept> findAll();

    void deleteById(Integer id);

    void add(Dept dept);

    Dept getById(Integer id);

    void update(Dept dept);

    /**
     * 检查部门下是否有员工
     * @param id 部门ID
     * @return 如果有员工返回true，否则返回false
     */
    boolean hasEmployees(Integer id);
}
