package com.shuojie.serverImpl.mapsServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuojie.dao.mapsMapper.MapPointsMapper;
import com.shuojie.domain.maps.MapPoints;
import com.shuojie.service.mapsService.MapPointsService;
import com.shuojie.utils.vo.Result;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("mapPointsServiceImpl")
public class MapPointsServiceImpl extends ServiceImpl<MapPointsMapper,MapPoints> implements MapPointsService {
    @Resource
    private MapPointsMapper mapPointsMapper;

    private Result result;


    @Override
    public Result insertMapPoints(MapPoints mapPoints) {
            mapPointsMapper.insert(mapPoints);
            if ( mapPoints.getMaLatitude() != null
                    && mapPoints.getMaLongitude() != null
                    && !StringUtil.isNullOrEmpty(mapPoints.getPointsName())) {
                result = new Result(200, "insertMapPointsSuccess", "insertMapPoints");
            } else {
                result = new Result(201, "insertMapPointsError", "insertMapPoints");
            }
       return result;
    }
}
