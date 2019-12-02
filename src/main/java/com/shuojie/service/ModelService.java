package com.shuojie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuojie.domain.Model;
import com.shuojie.utils.vo.Result;

import java.util.List;

public interface ModelService extends IService<Model> {

    Result insertModel(Model model);

    Result getModel();
}
