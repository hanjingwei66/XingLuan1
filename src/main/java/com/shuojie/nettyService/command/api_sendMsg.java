package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.serverImpl.UserServiceImpl;
import com.shuojie.service.IUserService;
import com.shuojie.service.UserMerberService;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.vo.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

public class api_sendMsg implements Command {
    @Autowired
    private UserMerberService usermerberservice;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        UserMerberService usermerberservice = (UserMerberService) SpringUtil.getBean("UserMerberServiceImpl");
        JSONObject json = JSONObject.parseObject(msg.text().toString());
        String telephone = json.getString("mobile").toString();
        try {
            Result result1 = usermerberservice.sendMsg(telephone);
            result1.setCommand("api_sendMsg");
            String res = JSONObject.toJSONString(result1);
            ctx.writeAndFlush(new TextWebSocketFrame(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
