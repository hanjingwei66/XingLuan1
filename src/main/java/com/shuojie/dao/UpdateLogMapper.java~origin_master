package com.shuojie.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shuojie.domain.UpdateLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UpdateLogMapper extends BaseMapper<UpdateLog> {

   // UpdateLog getUpdateLog();
    List<UpdateLog> getUpdateLog(@Param(Constants.WRAPPER)Wrapper<UpdateLog> wrapper);
}