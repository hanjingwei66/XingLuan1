package com.shuojie.controller;

import com.shuojie.domain.maps.MapPoints;
import com.shuojie.service.mapsService.MapPointsService;
import com.shuojie.utils.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mapPoints")
public class MapPointsController {
    @Resource(name = "mapPointsServiceImpl")
    private MapPointsService mapPointsServer;

    @RequestMapping(value = "/insertMapPoints",method = RequestMethod.POST)
    public Result insertMapPoints(@RequestBody MapPoints mapPoints){
        Result mps = mapPointsServer.insertMapPoints(mapPoints);
        return mps;
    }
}
