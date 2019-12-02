package com.shuojie.controller;

import com.shuojie.domain.Model;
import com.shuojie.service.ModelService;
import com.shuojie.utils.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/model")
public class ModelController {
    @Resource(name = "modelServiceImpl")
    private ModelService modelService;

    @RequestMapping(value = "/insertModel", method = RequestMethod.POST)
    public Result insertModel(@RequestBody Model model) {
        return modelService.insertModel(model);
    }

    @RequestMapping(value = "/getModel", method = RequestMethod.GET)
    public Result getModel() {
        return modelService.getModel();
    }
}

