package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.nettyService.Handler.AuthHandler;
import com.shuojie.serverImpl.UserServiceImpl;
import com.shuojie.service.IUserService;
import com.shuojie.service.UserMerberService;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.nettyUtil.SessionUtil;
import com.shuojie.utils.vo.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

public class api_logout implements Command {
    @Autowired
    AuthHandler authHandler;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        AuthHandler userServer = (AuthHandler) SpringUtil.getBean("authHandler");
        Result logoutResult=new Result(200,"SUCCESS","api_logout");
        String logout = JSONObject.toJSONString(logoutResult);
        ctx.writeAndFlush(new TextWebSocketFrame(logout));
        SessionUtil.unBindSession(ctx.channel());
        //登出之后加回校验
        ctx.pipeline().addAfter("TextWebSocketFrameHandler","authHandler",authHandler);
    }
}
