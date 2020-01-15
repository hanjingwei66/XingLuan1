package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.User;
import com.shuojie.serverImpl.UserServiceImpl;
import com.shuojie.service.IUserService;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.vo.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

public class api_register implements Command{
    @Autowired
    private IUserService userServer;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        IUserService userServer = (UserServiceImpl) SpringUtil.getBean("userServiceImpl");
        JSONObject json = JSONObject.parseObject(msg.text().toString());
        User user = new User();
        user.setMobile(json.getString("mobile"));
        user.setPassword(json.getString("password"));
        user.setYzm(json.getString("yzm"));
        user.setUsername(json.getString("username"));
        user.setIdNumber(json.getString("idNumber"));
        user.setFirmId(json.getInteger("firmId"));
//                user.setPosition();//职位
//                user.setAreaname(login.getAreaname());//所属地区
        Result results = userServer.register(user);
        results.setCommand("api_register");

        String registerReponse = JSONObject.toJSONString(results);
        ctx.writeAndFlush(new TextWebSocketFrame(registerReponse));
    }
}
