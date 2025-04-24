package com.gyqstd.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author GuYuqi
 * @version 1.0
 */
@Data
public class ClazzQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String name;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate begin;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate end;
}
