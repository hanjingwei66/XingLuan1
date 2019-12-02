package com.shuojie.serverImpl.mapsServiceImpl;

import com.shuojie.dao.mapsMapper.OriginMapper;
import com.shuojie.domain.maps.Origin;
import com.shuojie.service.mapsService.OriginService;
import com.shuojie.utils.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("originServiceImpl")
public class OriginServiceImpl implements OriginService {
    @Resource
    private OriginMapper originMapper;

    //根据orName查询经纬度
/*    @Override
    public ReturnOrigin getByName(Origin origin) {
        ArrayList o = originMapper.getByName(origin);
        ReturnOrigin returnOrigin= new ReturnOrigin();
        returnOrigin.setOrName(getOriginName());
        returnOrigin.setOriginAndLine(o);
        return returnOrigin;
    }*/

    @Override

    //查询orName
    public ArrayList getOriginName(){
        ArrayList originName = originMapper.getOriginName();
        return originName;
    }

    //查询线路名称和线路经纬度
    @Override
    public Result getOriginLine() {
        List<Origin> originLine = originMapper.getOriginLine();
        if (originLine != null){
            Result listResult = new Result<List<Origin>>(200, "getOriginLineSuccess", "maps_getOriginLine",originLine);
            return listResult;
        }else {
            Result result = new Result(201, "getOriginLineError", "maps_getOriginLine");
            return result;
        }
    }

}
