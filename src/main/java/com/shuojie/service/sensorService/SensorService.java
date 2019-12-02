package com.shuojie.service.sensorService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuojie.domain.sensorModle.LaserSensor;
import com.shuojie.domain.sensorModle.SensorTitle;
import com.shuojie.domain.sensorModle.ZullProperty;
import com.shuojie.utils.vo.Result;

import java.util.List;

public interface SensorService {
    Result selectPage(Page<ZullProperty> page, String startTime, String endTime);
}
