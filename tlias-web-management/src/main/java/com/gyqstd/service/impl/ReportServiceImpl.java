package com.gyqstd.service.impl;

import com.gyqstd.mapper.ClazzMapper;
import com.gyqstd.mapper.EmpMapper;
import com.gyqstd.mapper.StudentMapper;
import com.gyqstd.pojo.ClazzCountOption;
import com.gyqstd.pojo.JobOption;
import com.gyqstd.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("total")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }

    @Override
    public ClazzCountOption getStudentCountData() {
        List<Map<String, Object>> list = studentMapper.countStudentClazzData();
        List<String> clazzList = list.stream().map(dataMap -> (String) dataMap.get("clazz")).toList();
        List<Integer> dataList = list.stream().map(dataMap -> ((Long) dataMap.get("total")).intValue()).toList();
        return new ClazzCountOption(clazzList, dataList);
    }
}
