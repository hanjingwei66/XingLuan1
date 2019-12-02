package com.shuojie.domain.sensorModle;

import lombok.Data;

@Data
public class SensorEvent  {
    private Long id;
    private Long userId;
    private  String startTime ;
    private  String endTime;
    private String sensorEventName;
    private String sensorType;
    private Long sensorId;
}
