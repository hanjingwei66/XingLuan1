package com.shuojie.utils.vo;

import lombok.Data;

@Data
public class Page {
    private String startTime ;//开始时间
    private String endTime ;//结束时间
    private Long currentPage ;//当前页
    private Long pageSize ;//每页数据大小
    private Long totle;//总记录数
    private Long pages;//总页数
}
