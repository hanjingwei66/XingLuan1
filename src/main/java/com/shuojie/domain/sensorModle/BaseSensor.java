package com.shuojie.domain.sensorModle;

import lombok.Data;

@Data

public class BaseSensor extends BaseEntity{

    private Long sensorId;//传感器id
    private Integer sensorType;//传感器类型 0 测角 1测距 2 激光 3十轴
    private Integer power;//电量 0-100;
    private Integer status;//状态0，正常 1，非正常
    private Integer signal ;//信号 0 无信号，1 信号弱， 2 信号差， 3 信号中4 信号好
//    private String angle;//角度 左为正 右为负 -15c +15c
//    private Double distance;//距离


}
