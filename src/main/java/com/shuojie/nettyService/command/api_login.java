package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.User;
import com.shuojie.domain.system.Session;
import com.shuojie.serverImpl.UserServiceImpl;
import com.shuojie.service.IUserService;
import com.shuojie.service.UserMerberService;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.nettyUtil.LoginCheckUtil;
import com.shuojie.utils.nettyUtil.SessionUtil;
import com.shuojie.utils.vo.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class api_login implements  Command {
    private ChannelHandlerContext ctx;
    @Autowired
    private IUserService userServer;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        //{"command":"api_login","mobile":"admin","password":"admin"}
        IUserService userServer = (IUserService) SpringUtil.getBean("userServiceImpl");
        JSONObject json = JSONObject.parseObject(msg.text().toString());
        User user = new User();
        user.setMobile(json.getString("mobile"));
        user.setPassword(json.getString("password"));
        System.out.println(user.toString());
        Result result = userServer.toLogin(user);
//                String id =ctx.channel().id().asLongText();
//                result.setChanleId(id);
        result.setCommand("api_login");
        String loginRespone = JSONObject.toJSONString(result);//json对象解析为json字符串
        ctx.writeAndFlush(new TextWebSocketFrame(loginRespone));
        if (result.getCode() == 200) {
            LoginCheckUtil.markAsLogin(ctx.channel());//给当前的通道打上登录标记；
            User user2 = (User)result.getData();
            SessionUtil.bindSession(new Session(user2.getId(),user2.getUsername()),ctx.channel());//绑定session用map管理用户id为key channel为value
            this.ctx=ctx;//存储到 ChannelHandlerContext的引用（用于传感器模块引用）
        } else {
//                    ctx.channel().writeAndFlush(new TextWebSocketFrame());
        }

    }
}
