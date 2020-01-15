package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.Contact;
import com.shuojie.serverImpl.UserServiceImpl;
import com.shuojie.service.ContactService;
import com.shuojie.service.IUserService;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.vo.Result;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

public class api_insertContact implements Command {
    @Autowired
    private ContactService contactServer;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        ContactService userServer = (ContactService) SpringUtil.getBean("contactServiceImpl");
        Contact contact = new Contact();
        JSONObject json = JSONObject.parseObject(msg.text().toString());
        contact.setId(Long.valueOf(json.getString("id")));
        contact.setContactText(new String(json.getString("contactText")));
        Result con = contactServer.insertContact(contact);
        String insertContactResponse = JSONObject.toJSONString(con);
        ctx.writeAndFlush(new TextWebSocketFrame(insertContactResponse));
    }
}
