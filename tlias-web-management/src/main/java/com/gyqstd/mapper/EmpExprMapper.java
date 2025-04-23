package com.gyqstd.mapper;

import com.gyqstd.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Mapper
public interface EmpExprMapper {

    void insertBatch(List<EmpExpr> exprList);

    void deleteByEmpId(List<Integer> empIds);
}
