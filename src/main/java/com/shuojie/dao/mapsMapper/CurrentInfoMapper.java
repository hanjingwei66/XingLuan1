package com.shuojie.dao.mapsMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuojie.domain.maps.CurrentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CurrentInfoMapper extends BaseMapper<CurrentInfo> {

    //当前线路存储 line_name,clid,current_date
    void insertCurrentInfo(CurrentInfo currentInfo);

}