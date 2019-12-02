package com.shuojie.dao.sensorMappers;

import com.shuojie.domain.sensorModle.DistanceSensor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DistanceSensorMapper {
    void insert(DistanceSensor distanceSensor);
}