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

public class api_xiugaiPassword implements Command {
    @Autowired
    private IUserService userServer;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        IUserService userServer = (UserServiceImpl) SpringUtil.getBean("userServiceImpl");
        JSONObject json = JSONObject.parseObject(msg.text().toString());
        User user = new User();
        user.setId(json.getLong("id"));
        user.setMobile(json.getString("mobile"));
        user.setOldPassword(json.getString("oldPassword"));
        user.setPassword(json.getString("password"));
        user.setYzm(json.getString("yzm"));
        Result response = userServer.xiugaiUserPassworld(user);
        String xiugaiPasswordReponse = JSONObject.toJSONString(response);
        ctx.writeAndFlush(new TextWebSocketFrame(xiugaiPasswordReponse));
        System.out.println("updatePassword");
    }
}
