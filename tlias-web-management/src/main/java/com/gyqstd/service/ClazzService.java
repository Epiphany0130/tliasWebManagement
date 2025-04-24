package com.gyqstd.service;

import com.gyqstd.pojo.Clazz;
import com.gyqstd.pojo.ClazzQueryParam;
import com.gyqstd.pojo.PageResult;

import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    void add(Clazz clazz);

    Clazz getInfo(Integer id);

    void update(Clazz clazz);

    void deleteById(Integer id);

    List<Clazz> findAll();
}
