package com.shuojie.service.mapsService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuojie.domain.maps.MapPoints;
import com.shuojie.utils.vo.Result;

public interface MapPointsService extends IService<MapPoints> {

    Result insertMapPoints(MapPoints mapPoints);
}
