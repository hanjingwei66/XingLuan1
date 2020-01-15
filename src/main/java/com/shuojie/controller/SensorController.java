package com.shuojie.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuojie.domain.User;
import com.shuojie.domain.sensorModle.LaserSensor;
import com.shuojie.domain.sensorModle.ZullProperty;
import com.shuojie.service.sensorService.LaserSensorService;
import com.shuojie.service.sensorService.SensorService;
import com.shuojie.utils.vo.Result;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/sensor")
public class SensorController {
    @Autowired
    private SensorService sensorService;
    @Autowired
    private LaserSensorService laserSensorService;
    @RequestMapping(value = "/getData",method = RequestMethod.POST)
    public Result getData(@RequestBody com.shuojie.utils.vo.Page page ){
        String selectStartTime = page.getStartTime();
        String selectEndTime = page.getEndTime();
        Long currentPage = page.getCurrentPage();
        Long pageSize = page.getPageSize();
       // Long sensorId1= Long.parseLong(json.getString("sensorId"));
        Page mPage = new Page<ZullProperty>(currentPage, pageSize);
        com.shuojie.utils.vo.Result sensorlist = sensorService.selectPage(mPage, selectStartTime, selectEndTime);
        return sensorlist;
    }
    @RequestMapping(value = "/getLaserData",method = RequestMethod.POST)
    public Result getLaserData(@RequestBody com.shuojie.utils.vo.Page page ){
        String selectStartTime = page.getStartTime();
        String selectEndTime = page.getEndTime();
        Long currentPage = page.getCurrentPage();
        Long pageSize = page.getPageSize();
        // Long sensorId1= Long.parseLong(json.getString("sensorId"));
        Page mPage = new Page<LaserSensor>(currentPage, pageSize);
        com.shuojie.utils.vo.Result sensorlist = laserSensorService.selectPage(mPage, selectStartTime, selectEndTime);
        return sensorlist;
    }
}
