package com.shuojie.nettyService.Handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class SelectHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;
    @Autowired
    private SensorHandler sensorHandler;
    @Autowired
    private MapsWebSocketFrameHandle mapsWebSocketFrameHandle;
    @Autowired
    private CommonHandler commonHandler;

    private  SelectHandler(){

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof TextWebSocketFrame) {
            this.channelRead0(ctx, (TextWebSocketFrame) msg);
        }else {
            ctx.fireChannelRead(msg);
        }
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        JSONObject json = JSONObject.parseObject(msg.text());//json字符串转json对象
        String command = json.getString("command");
//        command.substring(0, 4).equals("api_")
        String command_prefix = command.substring(0, 4);
        switch (command_prefix){
            case "api_":
                textWebSocketFrameHandler.channelRead0(ctx,msg);
              break;
            case "comm":
                commonHandler.channelRead0(ctx,json);
                break;
            case "sens":
                sensorHandler.channelRead0(ctx,msg);
                break;
            case "maps":
                mapsWebSocketFrameHandle.channelRead0(ctx,msg);
                break;
        }
    }
}
