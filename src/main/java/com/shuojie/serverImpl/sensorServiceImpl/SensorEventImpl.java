package com.shuojie.serverImpl.sensorServiceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shuojie.dao.sensorMappers.SensorEventMapper;
import com.shuojie.domain.sensorModle.SensorEvent;
import com.shuojie.service.sensorService.SensorEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SensorEventImpl implements SensorEventService {
    @Autowired
    private SensorEventMapper sensorEventMapper;
    @Override
    public void insert(SensorEvent sensorEvent) {
        int insert = sensorEventMapper.insert(sensorEvent);
    }

    @Override
    public void update(SensorEvent sensorEvent) {
        QueryWrapper<SensorEvent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sensor_event_name",sensorEvent.getSensorEventName());
        sensorEventMapper.update(sensorEvent,queryWrapper);
    }

    @Override
    public List<SensorEvent> findList(Long userId) {
        QueryWrapper<SensorEvent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<SensorEvent> sensorEvents = sensorEventMapper.selectList(queryWrapper);
        return sensorEvents;
    }

    @Override
    public List<SensorEvent> findAll() {
        QueryWrapper<SensorEvent> queryWrapper = new QueryWrapper<>();
        List<SensorEvent> sensorEvents = sensorEventMapper.selectList(queryWrapper);
        return sensorEvents;
    }
}
