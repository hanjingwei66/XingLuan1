package com.shuojie.service.mapsService;

import com.shuojie.domain.maps.Origin;
import com.shuojie.utils.vo.Result;

import java.util.ArrayList;

public interface OriginService {

    //根据orName查询经纬度
   // ReturnOrigin getByName(Origin origin);

    ArrayList getOriginName();

    Result getOriginLine();
}
