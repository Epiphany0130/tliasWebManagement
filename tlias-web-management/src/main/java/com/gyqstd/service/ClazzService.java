package com.gyqstd.service;

import com.gyqstd.pojo.Clazz;
import com.gyqstd.pojo.ClazzQueryParam;
import com.gyqstd.pojo.PageResult;

/**
 * @author GuYuqi
 * @version 1.0
 */
public interface ClazzService {
    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    void add(Clazz clazz);

    Clazz getInfo(Integer id);
}
