package com.shuojie.dao.mapsMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuojie.domain.maps.CurrentLine;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CurrentLineMapper extends BaseMapper<CurrentLine> {

    //添加经纬度
    void insertCurrentLine(CurrentLine currentLine);
}