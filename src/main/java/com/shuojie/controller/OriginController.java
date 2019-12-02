package com.shuojie.controller;

import com.shuojie.domain.maps.Origin;
import com.shuojie.service.mapsService.OriginService;
import com.shuojie.utils.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/origin")
public class OriginController {
    @Resource(name = "originServiceImpl")
    private OriginService originServer;
    //根据orName查询经纬度
    /*@RequestMapping(value = "/getByName",method = RequestMethod.POST)
    public ReturnOrigin getByName(@RequestBody Origin origin){
        ReturnOrigin oa = originServer.getByName(origin);
        return oa;
    }*/
    @RequestMapping(value = "/getOriginName",method = RequestMethod.GET)
    public ArrayList getOriginName(){
        return originServer.getOriginName();
    }


    @RequestMapping(value = "/getOriginLine",method = RequestMethod.GET)
    public Result getOriginLine(){
        return originServer.getOriginLine();
    }
}
