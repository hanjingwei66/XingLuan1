package com.shuojie.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shuojie.domain.Model;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface ModelMapper extends BaseMapper<Model> {

    //添加模型信息
   // void insertModel(Model model);

    //查询模型信息
    List<Model> getModel(@Param(Constants.WRAPPER)Wrapper<Model> wrapper);
}