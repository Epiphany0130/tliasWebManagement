package com.gyqstd.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Data
public class StudentQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;
    private Integer clazzId;
    private Integer degree;
    private String name;
}
