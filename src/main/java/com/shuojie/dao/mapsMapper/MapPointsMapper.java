package com.shuojie.dao.mapsMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuojie.domain.maps.MapPoints;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MapPointsMapper extends BaseMapper<MapPoints> {

    //添加地图初始点
    void insertMapPoints(MapPoints mapPoints);
}