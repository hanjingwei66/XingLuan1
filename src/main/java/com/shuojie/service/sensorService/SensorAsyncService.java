package com.shuojie.service.sensorService;

import com.shuojie.domain.sensorModle.LaserSensor;
import com.shuojie.domain.sensorModle.ZullProperty;

public interface SensorAsyncService {
    /**
     * 执行异步任务
     */
    void executeAsync(ZullProperty sensor);
    void executeAsync(LaserSensor sensor);
}
