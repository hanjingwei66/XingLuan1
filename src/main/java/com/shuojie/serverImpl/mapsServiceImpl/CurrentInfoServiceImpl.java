package com.shuojie.serverImpl.mapsServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuojie.dao.mapsMapper.CurrentInfoMapper;
import com.shuojie.domain.maps.CurrentInfo;
import com.shuojie.service.mapsService.CurrentInfoService;
import com.shuojie.utils.vo.Result;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("currentServiceImpl")
public class CurrentInfoServiceImpl extends ServiceImpl<CurrentInfoMapper,CurrentInfo> implements CurrentInfoService {

    @Resource
    private CurrentInfoMapper currentInfoMapper;
    private Result result;

    //添加当前线路
    @Override
    public Result insertCurrentInfo(CurrentInfo currentInfo) {
        currentInfoMapper.insert(currentInfo);
        if (!StringUtil.isNullOrEmpty(currentInfo.getLineName())
                && !StringUtil.isNullOrEmpty(currentInfo.getCurrentShijian()) ){
             result = new Result(200,"insertCurrentSuccess","insertCurrent");
        }else {
             result = new Result(201,"insertCurrentError","insertCurrent");
        }
        return result;
    }
}
