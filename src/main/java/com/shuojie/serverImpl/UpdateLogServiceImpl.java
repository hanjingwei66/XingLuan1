package com.shuojie.serverImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuojie.dao.UpdateLogMapper;
import com.shuojie.domain.UpdateLog;
import com.shuojie.service.UpdateLogService;
import com.shuojie.utils.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("updateLogServiceImpl")
public class UpdateLogServiceImpl implements UpdateLogService {

    @Resource
    private UpdateLogMapper updateLogMapper;

//    private ReturnUpdateLog returnUpdateLog;

    @Override
    public Result getUpdateLog() {
        LambdaQueryWrapper<UpdateLog> updateLambda = Wrappers.lambdaQuery();
        List<UpdateLog> updateLog = updateLogMapper.getUpdateLog(updateLambda);
        if (updateLog != null) {
            Result<UpdateLog> result = new Result(200, "getUpdateLogSuccess", "api_getUpdateLog", updateLog);
            return result;
        } else {
            Result result = new Result(201, "getUpdateLogError", "api_getUpdateLog");
            return result;
        }
    }
}
