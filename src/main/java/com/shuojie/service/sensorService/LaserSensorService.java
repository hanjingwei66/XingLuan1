package com.shuojie.service.sensorService;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuojie.domain.sensorModle.LaserSensor;
import com.shuojie.utils.vo.Result;

public interface LaserSensorService {
    Result selectPage(Page<LaserSensor> page, String startTime, String endTime);
}
