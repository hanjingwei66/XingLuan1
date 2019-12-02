package com.shuojie.controller;

import com.shuojie.domain.maps.CurrentLine;
import com.shuojie.service.mapsService.CurrentLineService;
import com.shuojie.utils.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/currentLine")
public class CurrentLineController {
    @Resource(name = "currentLineServiceImpl")
    private CurrentLineService currentLineServer;

    @RequestMapping(value  = "/insertCurrentLine", method = RequestMethod.POST)
    public Result insertCurrentLine(@RequestBody CurrentLine currentLine){
        Result currentLineResult = currentLineServer.insertCurrentLine(currentLine);
        return currentLineResult;
    }
}
