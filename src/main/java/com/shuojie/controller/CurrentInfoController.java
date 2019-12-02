package com.shuojie.controller;

import com.shuojie.domain.maps.CurrentInfo;
import com.shuojie.service.mapsService.CurrentInfoService;
import com.shuojie.utils.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/current")
public class CurrentInfoController {
    @Resource(name = "currentServiceImpl")
    private CurrentInfoService currentServer;

    //添加当前线路
    @RequestMapping(value = "/insertCurrentInfo",method = RequestMethod.POST)
    public Result insertCurrentInfo(@RequestBody CurrentInfo currentInfo){
         Result result = currentServer.insertCurrentInfo(currentInfo);
        return result;
    }
}
