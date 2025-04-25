package com.gyqstd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 班级人数统计
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzCountOption {
    private List<String> clazzList; //职位列表
    private List<Integer> dataList; //人数列表
}
