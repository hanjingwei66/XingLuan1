package com.shuojie.service.mapsService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuojie.domain.maps.CurrentInfo;
import com.shuojie.utils.vo.Result;

public interface CurrentInfoService extends IService<CurrentInfo> {

    //添加当前线路
    Result insertCurrentInfo(CurrentInfo current);

}
