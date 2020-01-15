package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.service.ContactService;
import com.shuojie.service.UpdateLogService;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.vo.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

public class api_getUpdateLog implements Command {
    @Autowired
    private UpdateLogService updateLogService;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        UpdateLogService userServer = (UpdateLogService) SpringUtil.getBean("updateLogServiceImpl");
        Result updateLog = updateLogService.getUpdateLog();
        String getUpdateLogReponse = JSONObject.toJSONString(updateLog);
        ctx.writeAndFlush(new TextWebSocketFrame(getUpdateLogReponse));
    }
}
