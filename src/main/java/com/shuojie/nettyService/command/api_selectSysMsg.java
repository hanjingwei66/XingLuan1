package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.system.SysContact;
import com.shuojie.service.sysService.SysContactService;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class api_selectSysMsg implements Command {
    @Autowired
    private SysContactService sysContactService;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        SysContactService sysContactService = (SysContactService) SpringUtil.getBean("sysContactServiceImpl");
        JSONObject json = JSONObject.parseObject(msg.text().toString());
        Long id = json.getLong("id");
        List<SysContact> list = sysContactService.selectById(id);
        Map map = new HashMap();
        map.put("data", list);
        map.put("command", "api_selectSysMsg");
        String contectlist = JSONObject.toJSONString(map);
        System.out.println(contectlist);
        ctx.writeAndFlush(new TextWebSocketFrame(contectlist));
    }
}
