package com.shuojie.service.sensorService;

import com.shuojie.domain.sensorModle.SensorEvent;

import java.util.List;

public interface SensorEventService {
    void insert(SensorEvent sensorEvent);
    void update(SensorEvent sensorEvent);
    List<SensorEvent> findList(Long userId);
    List<SensorEvent> findAll();
}
