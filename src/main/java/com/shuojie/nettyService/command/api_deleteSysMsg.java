package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shuojie.serverImpl.UserServiceImpl;
import com.shuojie.service.IUserService;
import com.shuojie.service.sysService.SysContactService;
import com.shuojie.utils.autowiredUtil.SpringUtil;
import com.shuojie.utils.vo.Result;
import com.shuojie.utils.vo.SingleResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

public class api_deleteSysMsg implements Command {
    @Autowired
    private SysContactService sysContactService;
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        SysContactService sysContactService = (SysContactService) SpringUtil.getBean("sysContactService");
        JSONObject json = JSONObject.parseObject(msg.text().toString());
        JSONArray array = json.getJSONArray("sysContactId");

        try {
            for (int i = 0; i < array.size(); i++) {
                sysContactService.deleteById(array.getInteger(i));
            }
            SingleResult singleResult=SingleResult.buildResult(SingleResult.Status.OK,"SUCCESS", "api_deleteSysMsg");
            Result result1 = new Result(200, "SUCCESS", "api_deleteSysMsg");
            String respon = JSONObject.toJSONString(singleResult);

            ctx.writeAndFlush(new TextWebSocketFrame(respon));
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
