package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.service.sysService.SysContactService;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.vo.Result;
import com.shuojie.utils.vo.SingleResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

public class api_updateStatus implements Command {
    @Autowired
    private SysContactService sysContactService;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        SysContactService sysContactService = (SysContactService) SpringUtil.getBean("sysContactServiceImpl");
        JSONObject json = JSONObject.parseObject(msg.text().toString());
        Integer array1 = json.getInteger("sysContactId");
        try {
            sysContactService.updateStatus(array1);
            Result result1 = new Result(200, "SUCCESS", "api_updateStatus");
            SingleResult singleResult=SingleResult.buildResult(SingleResult.Status.OK,"SUCCESS","api_updateStatus");
            String respon = JSONObject.toJSONString(singleResult);
            ctx.writeAndFlush(new TextWebSocketFrame(respon));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
