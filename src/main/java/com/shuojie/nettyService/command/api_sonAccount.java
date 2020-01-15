package com.shuojie.nettyService.command;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.system.Session;
import com.shuojie.utils.nettyUtil.SessionUtil;
import com.shuojie.utils.vo.SingleResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class api_sonAccount implements Command{
    @Override
    public void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
//        Session session = SessionUtil.getSession(ctx.channel());
//        Long userId2 = session.getUserId();
//        this.flag= true;
//        SingleResult singleResult=SingleResult.buildResult(SingleResult.Status.OK,"SUCCESS","api_sonAccount");
//        String respon = JSONObject.toJSONString(singleResult);
//        ctx.writeAndFlush(new TextWebSocketFrame(respon));
    }
}
