package com.shuojie.nettyService.command;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;


public interface Command {
    void reponse(ChannelHandlerContext ctx, TextWebSocketFrame msg);
}
