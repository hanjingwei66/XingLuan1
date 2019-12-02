package com.shuojie.serverImpl.sensorServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuojie.dao.sensorMappers.LaserSensorMapper;
import com.shuojie.dao.sensorMappers.SensorMapper;
import com.shuojie.domain.sensorModle.LaserSensor;
import com.shuojie.domain.sensorModle.SensorTitle;
import com.shuojie.domain.sensorModle.ZullProperty;
import com.shuojie.service.sensorService.SensorAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 异步录入传感器数据
 */
@Service("AsyncService")
public class AsyncServiceImpl implements SensorAsyncService {
    @Autowired
    private SensorMapper sensorMapper;
    @Autowired
    private LaserSensorMapper laserMapper;
    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync(ZullProperty sensor) {
        sensorMapper.insert(sensor);
    }

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync(LaserSensor sensor) {
        laserMapper.insert(sensor);
    }


}
