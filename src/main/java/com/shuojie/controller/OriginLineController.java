package com.shuojie.controller;

import com.shuojie.service.mapsService.OriginLineService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/originLine")
public class OriginLineController {
    @Resource(name = "originLineServiceImpl")
    private OriginLineService originLineServer;

}

