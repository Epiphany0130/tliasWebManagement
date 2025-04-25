package com.gyqstd.service;

import com.gyqstd.pojo.ClazzCountOption;
import com.gyqstd.pojo.JobOption;

import java.util.List;
import java.util.Map;

/**
 * @author GuYuqi
 * @version 1.0
 */
public interface ReportService {
    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();

    List<Map<String, Object>> getStudentDegreeData();

    ClazzCountOption getStudentCountData();
}
