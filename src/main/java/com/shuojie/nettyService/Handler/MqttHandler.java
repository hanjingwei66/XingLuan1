package com.shuojie.nettyService.Handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;

import java.io.IOException;

public class MqttHandler extends SimpleChannelInboundHandler<MqttMessage> {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception {
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNECT, false, MqttQoS.AT_LEAST_ONCE, false, 2);
        MqttMessageFactory.newMessage(mqttFixedHeader,MqttMessageIdVariableHeader.from(123),null);
        switch (msg.fixedHeader().messageType()) {
            case CONNECT:

                break;
//            case CONNACK:
//                break;
//            case PUBLISH:
//                protocolProcess.publish().processPublish(ctx.channel(), (MqttPublishMessage) msg);
//                break;
//            case PUBACK:
//                protocolProcess.pubAck().processPubAck(ctx.channel(), (MqttMessageIdVariableHeader) msg.variableHeader());
//                break;
//            case PUBREC:
//                protocolProcess.pubRec().processPubRec(ctx.channel(), (MqttMessageIdVariableHeader) msg.variableHeader());
//                break;
//            case PUBREL:
//                protocolProcess.pubRel().processPubRel(ctx.channel(), (MqttMessageIdVariableHeader) msg.variableHeader());
//                break;
//            case PUBCOMP:
//                protocolProcess.pubComp().processPubComp(ctx.channel(), (MqttMessageIdVariableHeader) msg.variableHeader());
//                break;
//            case SUBSCRIBE:
//                protocolProcess.subscribe().processSubscribe(ctx.channel(), (MqttSubscribeMessage) msg);
//                break;
//            case SUBACK:
//                break;
//            case UNSUBSCRIBE:
//                protocolProcess.unSubscribe().processUnSubscribe(ctx.channel(), (MqttUnsubscribeMessage) msg);
//                break;
//            case UNSUBACK:
//                break;
//            case PINGREQ:
//                protocolProcess.pingReq().processPingReq(ctx.channel(), msg);
//                break;
//            case PINGRESP:
//                break;
//            case DISCONNECT:
//                protocolProcess.disConnect().processDisConnect(ctx.channel(), msg);
//                break;
//            default:
//                break;
        }

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            // 远程主机强迫关闭了一个现有的连接的异常
            ctx.close();
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }

//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
//            if (idleStateEvent.state() == IdleState.ALL_IDLE) {
//                Channel channel = ctx.channel();
//                String clientId = (String) channel.attr(AttributeKey.valueOf("clientId")).get();
//                // 发送遗嘱消息
//                if (this.protocolProcess.getSessionStoreService().containsKey(clientId)) {
//                    SessionStore sessionStore = this.protocolProcess.getSessionStoreService().get(clientId);
//                    if (sessionStore.getWillMessage() != null) {
//                        this.protocolProcess.publish().processPublish(ctx.channel(), sessionStore.getWillMessage());
//                    }
//                }
//                ctx.close();
//            }
//        } else {
//            super.userEventTriggered(ctx, evt);
//        }
//    }

//    @Override
//    protected void channelRead0() throws Exception {
//
//    }


//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        super.channelInactive(ctx);
//    }
//
//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        super.channelRegistered(ctx);
//    }
//
//    @Override
//    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
//        super.channelUnregistered(ctx);
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
//    }
//
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);
//    }
//
//    @Override
//    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//        super.channelWritabilityChanged(ctx);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
//    }


}
