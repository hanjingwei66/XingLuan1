package com.shuojie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/taiwan")
public class TaiwanController {
    Boolean CLOSE=true;
    @RequestMapping("/close")
    public Boolean close(){
        return CLOSE;
    }
    @RequestMapping("/isOpen")
    public Boolean oppen(){
        if(CLOSE) {
            CLOSE = false;
        }else {
            CLOSE=true;
        }
        return CLOSE;
    }
}
